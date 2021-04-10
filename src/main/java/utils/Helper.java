package utils;

import cli.CLIManager;
import cli.misc.SetTodaysDateMenu;
import database.SQLDatabaseConnection;
import org.apache.commons.lang3.StringUtils;
import structs.Address;
import structs.Name;
import users.Customer;
import users.Employee;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class Helper {
    /**
     * A helper method to eliminate the redundancy of typing "System.out.println()" every time
     * a user wishes to print to the console.
     *
     * @param s the string to print to the console
     */
    public static void println(String s) {
        System.out.println(s);
    }

    /**
     * A helper method to eliminate the redundancy of typing "System.out.print()" every time
     * a user wishes to print to the console.
     * @param s the string to print to the console
     */
    public static void print(String s) {System.out.print(s);}

    public static void newLine(int lines) {
        for (int i=1; i <= lines; i++) {
            print("\n");
        }
    }

    /**
     * Returns a user's given input from a specified prompt.
     *
     * @param prompt a prompt for the user's input
     * @return the user's provided input
     */
    public static String getInput(String prompt) {

        Scanner sc = new Scanner(System.in);
        print(prompt);

        String input = sc.nextLine().trim();

        checkForCall(input);

        return input;

    }

    /**
     * DB Administrator
     * Asks the user a prompt with yes or no answer
     * Returns true for user input of "yes"
     * Returns false for user input of "no"
     * @param prompt with yes or no answer
     * @return
     */
    public static boolean ask(String prompt) {
    	Scanner sc = new Scanner(System.in);
    	boolean F = false;
    	while(!F) {
            print(prompt);
            System.out.print(" (Yes / No)" +
                    "\n>> ");
            String input = sc.nextLine().trim();
            checkForCall(input);
            //sc.close();
            if (Helper.multiCheck(input, new String[] {"yes", "y", "no", "n"})) {
                F = true;
                return input.equalsIgnoreCase("yes") || input.equalsIgnoreCase("y");

            }
        }

    	return false;
    }

    public static void checkForCall(String input) {
        if (input.equals(Vars.PREV_MENU_KEYWORD)) {
            CLIManager.getInstance().prevMenu();

        } else if (input.equals(Vars.EXIT_APP_KEYWORD)) {
            Helper.println("\n\n" + Vars.DIVIDER_RED_SQUARE_LONG
                    + "\n\n" + StringUtils.center("The application has been terminated.", Vars.DIVIDER_RED_SQUARE_LONG.length())
                    + "\n\n" + Vars.DIVIDER_RED_SQUARE_LONG
                    + "\n\n");

            System.exit(1);

        } else if (input.equalsIgnoreCase(Vars.SET_TODAYS_DATE_KEYWORD)) {
            CLIManager.getInstance().loadMenu(new SetTodaysDateMenu());
        }
    }

    /**
     * Returns the Customer object associated with a given SIN number.
     *
     * @param sin the customer's SIN number
     * @return the customer with the provided SIN number
     */
    public static Customer getCustomerFromSIN(String sin) {
        SQLDatabaseConnection db = SQLDatabaseConnection.getInstance();

        Customer c = null;

        try {
            ResultSet rs = db.executeQuery("SELECT * " +
                    "FROM Customer " +
                    "WHERE sin_number = " + sin);



            if (!rs.next()) {
                return null;
            } else {

                do {

                    int sin_number = rs.getInt(1);

                    // Name
                    String first_name = rs.getString(2);
                    String middle_name = rs.getString(3);
                    String last_name = rs.getString(4);

                    Name name = new Name(first_name, middle_name, last_name);

                    // Address
                    int street_number = rs.getInt(5);
                    String street_name = rs.getString(6);
                    int apt_number = rs.getInt(7);
                    String city = rs.getString(8);
                    String state_name = rs.getString(9);
                    String zip = rs.getString(10);

                    Address address = new Address(street_number, street_name, apt_number, city, state_name, zip);

                    // Date of registration
                    String date_of_registration = rs.getString(11);

                    Date registrationDate = new SimpleDateFormat("yyyy-MM-dd").parse(date_of_registration);

                    // Create Customer object
                    c = new Customer(sin_number, name, address, registrationDate);

                } while (rs.next());
            }


        } catch (SQLException | ParseException throwables) {
            throwables.printStackTrace();
        }

        return c;
    }

    /**
     * Returns the Employee object associated with a given SIN number.
     *
     * @param sin the employee's SIN number
     * @return the employee with the provided SIN number
     */
    public static Employee getEmployeeFromSIN(String sin) {
        SQLDatabaseConnection db = SQLDatabaseConnection.getInstance();

        Employee e = null;

        try {
            ResultSet rs = db.executeQuery("SELECT * " +
                    "FROM Employee " +
                    "WHERE sin_number = " + sin);

            if (!rs.next()) {
                return null;
            } else {

                do {

                    int sin_number = rs.getInt(1);

                    // Name
                    String first_name = rs.getString(2);
                    String middle_name = rs.getString(3);
                    String last_name = rs.getString(4);

                    Name name = new Name(first_name, middle_name, last_name);

                    // Address
                    int street_number = rs.getInt(5);
                    String street_name = rs.getString(6);
                    int apt_number = rs.getInt(7);
                    String city = rs.getString(8);
                    String state_name = rs.getString(9);
                    String zip = rs.getString(10);

                    Address address = new Address(street_number, street_name, apt_number, city, state_name, zip);

                    int salary = rs.getInt(11);
                    String role = rs.getString(12);

                    // Create Customer object
                    e = new Employee(sin_number, name, address, salary, role);

                } while (rs.next());
            }


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return e;
    }

    /**
     * Checks if a string is equal to (ignoring case) any member of
     * a provided string array of options.
     *
     * Eliminates redundancy with chained OR checks with String.equalsIgnoreCase().
     *
     * @param toCheck the string to be validated against the validOptions
     * @param validOptions the string array containing the valid options for toCheck
     * @return
     */
    public static boolean multiCheck(String toCheck, String[] validOptions) {

        for (String option : validOptions) {

            if (toCheck.equalsIgnoreCase(option)) {
                return true;
            }

        }

        return false;

    }

    /**
     * NOTE: This method is from Android's TextUtils class.
     *
     * Returns whether the given CharSequence contains only digits.
     */
    public static boolean isDigitsOnly(CharSequence str) {
        final int len = str.length();
        for (int cp, i = 0; i < len; i += Character.charCount(cp)) {
            cp = Character.codePointAt(str, i);
            if (!Character.isDigit(cp)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isValid(String toCheck, int maxChoice) {
        return multiCheck(toCheck, numOpts(maxChoice));
    }

    public static String[] numOpts(int max) {

        String[] options = new String[max];

        for (int i=1; i <= max; i++) {
            options[i-1] = String.valueOf(i);
        }

        return options;

    }

    public static boolean isValidSIN(String sinNumber) {
        return Helper.isDigitsOnly(sinNumber) && sinNumber.trim().length() == 8;
    }

    public static Date stringToDate(String date) {

        try {
            return Vars.DATE_FORMAT.parse(date);
        } catch (ParseException e) {
            return null;
        }
    }

    public static String dateToString(Date date) {
        return Vars.DATE_FORMAT.format(date);
    }

    public static String toCurrency(double money) {
        Locale locale = new Locale("en", "US");
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);

        return currencyFormatter.format(money);
    }

    public static String toEmoji(boolean flag) {
        if (flag) {
            return "✅";
        } else {
            return "❌";
        }
    }

    public static String repeat(String toRepeat, int times) {
        StringBuilder strB = new StringBuilder();

        for (int i=0; i < times; i++) {
            strB.append(toRepeat);
        }

        return strB.toString().trim();
    }

    public static void sleep(int seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException ignored) {

        }
    }

    public static void cls(){
        try {

            if (System.getProperty("os.name").contains("Windows"))
                new ProcessBuilder("cmd", "/c",
                        "cls").inheritIO().start().waitFor();
            else
                Runtime.getRuntime().exec("clear");
        } catch (IOException | InterruptedException ignored) {}
    }

}
