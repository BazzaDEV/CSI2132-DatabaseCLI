package database;

import org.apache.commons.lang3.StringUtils;
import utils.Emoji;
import utils.Helper;
import utils.Vars;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.Scanner;

public class SQLDatabaseConnection {

    private static final SQLDatabaseConnection instance = new SQLDatabaseConnection();

    private SQLDatabaseConnection() {
        load();
    }

    public static SQLDatabaseConnection getInstance() {
        return instance;
    }

    Connection db;

    private void load() {

        File file = new File("src/main/credentials/dbCLIlogin.txt");

        if (file.exists() && !file.isDirectory()) {
            getCredentialsFromFile(file);
        } else {
            promptLoginDetails();
        }

    }

    private void getCredentialsFromFile(File file) {

        Scanner sc = null;
        try {
            sc = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        assert sc != null;
        String username = sc.nextLine();
        String password = sc.nextLine();

        boolean success = login(username, password);

        if (!success) {
            promptLoginDetails();
        }
    }

    private void promptLoginDetails() {

        Helper.println("\n" + Vars.DIVIDER_DASH_LONG +
                "\n" + StringUtils.center("PostgreSQL Database Login", Vars.DIVIDER_DASH_LONG.length()) +
                "\n" + Vars.DIVIDER_DASH_LONG);

        Helper.println("\nEnter your PostgreSQL login credentials: ");

        boolean F = false;
        while (!F) {

            String username = Helper.getInput("\n>> Username: ");
            String password;

            Helper.println(">> Password: ");
            password = String.valueOf(System.console().readPassword());

            F = login(username, password);
        }

    }

    private boolean login(String username, String password) {

        Helper.println("\n⏳ Authenticating credentials...");

        try {
            Class.forName("org.postgresql.Driver");
            db = DriverManager.getConnection("jdbc:postgresql://web0.site.uottawa.ca:15432/group_b07_g38",
                    username, password);

            username = null;
            password = null;

            Helper.println(Emoji.UNLOCKED + " Your database credentials were accepted." + Emoji.UNLOCKED);
            return true;

        } catch (ClassNotFoundException | SQLException e) {
            if (e instanceof ClassNotFoundException) {
                Helper.println("❌ The PostgreSQL driver was not found. Exiting now...");
                System.exit(1);
            } else {
                Helper.println("❌ Login credentials were rejected.");
                return false;
            }

        }

        return false;

    }

    public ResultSet executeQuery(String query) throws SQLException {
        Statement st = db.createStatement();
        return st.executeQuery(query);
    }

    public void executeUpdate(String query) throws SQLException {
		Statement st = db.createStatement();
        st.executeUpdate(query);
	}

}
