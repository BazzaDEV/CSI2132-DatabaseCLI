package cli;

import cli.admin.AdminMainMenu;
import cli.customer.CustomerMainMenu;
import cli.employee.EmployeeMainMenu;
import cli.login.LoginMenu;
import database.SQLDatabaseConnection;
import users.Admin;
import users.Customer;
import users.Employee;
import users.User;
import utils.Helper;
import utils.Vars;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Stack;

public class CLIManager {

    private static final CLIManager instance = new CLIManager();

    private CLIManager() {
        menuStack = new Stack<>();
    }

    public static CLIManager getInstance() {
        return instance;
    }

    private Stack<Menu> menuStack;

    private User user;
    private Date currentDate;

    private SQLDatabaseConnection db;

    public void startCLI() {
        LoginMenu loginMenu = new LoginMenu();
        loadMenu(loginMenu);
    }

    public void startCLI(String todaysDate) {
        setCurrentDate(todaysDate);
        startCLI();
    }

    public void startCLI(String todaysDate, String userType, String sinNumber) {
        setCurrentDate(todaysDate);
        menuStack.add(new LoginMenu());

        if (userType.equalsIgnoreCase("C")) { // Customer login
            Customer c = Helper.getCustomerFromSIN(sinNumber);
            setUser(c);
            loadMenu(new CustomerMainMenu());
        } else if (userType.equalsIgnoreCase("E")) { // Employee login
            Employee e = Helper.getEmployeeFromSIN(sinNumber);
            setUser(e);
            loadMenu(new EmployeeMainMenu());

        } else if (userType.equalsIgnoreCase("A")) { // Admin login
            Employee e = Helper.getEmployeeFromSIN(sinNumber);
            if (e != null && e.getRole().equalsIgnoreCase(Admin.ROLE_NAME)) { // Employee exists
                Admin a = new Admin(e);
                setUser(a);
                loadMenu(new AdminMainMenu());

            }

        }
    }

    public void loadMenu(Menu menu) {
        menuStack.add(menu);
        menuStack.peek().start();
    }

    public void popMenu() {
        menuStack.pop();
    }

    public void popAndLoadMenu(Menu menu) {
        popMenu();
        loadMenu(menu);
    }

    public void prevMenu() {
        menuStack.pop();
        menuStack.peek().start();
    }

    public void setCurrentDate(String todaysDate) {
        if (todaysDate.matches(Vars.DATE_REGEX)) {

            try {
                currentDate = Vars.DATE_FORMAT.parse(todaysDate);

            } catch (ParseException ignored) {
                Helper.println("[ERROR] Invalid date in CLI arguments.");
                System.exit(1);
            }

        } else {
            Helper.println("[ERROR] Invalid date in CLI arguments.");
            System.exit(1);
        }
    }

    public void setCurrentDate(Date newDate) {
        currentDate = newDate;
    }

    public void setUser(User newUser) {
        user = newUser;
    }

    public User getUser() {
        return user;
    }

    public Date getCurrentDate() {
        return currentDate;
    }

    public SQLDatabaseConnection getDB() {
        return db;
    }

}
