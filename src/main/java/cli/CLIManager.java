package cli;

import cli.login.LoginMenu;
import database.SQLDatabaseConnection;
import users.User;

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
