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

    public void startCLI(String userType, String sinNumber) {
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

    public void prevMenu() {
        menuStack.pop();
        menuStack.peek().start();
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
