package utils;

import database.SQLDatabaseConnection;
import structs.Address;
import structs.Name;
import users.Customer;
import users.Employee;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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

    /**
     * Returns a user's given input from a specified prompt.
     *
     * @param prompt a prompt for the user's input
     * @return the user's provided input
     */
    public static String getInput(String prompt) {

        Scanner sc = new Scanner(System.in);
        print(prompt);

        return sc.next().trim();

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

                    // Date of registration
                    String date_of_registration = rs.getString(11);

                    Date registrationDate = new SimpleDateFormat("yyyy-MM-dd").parse(date_of_registration);

                    // Create Customer object
                    e = new Employee(sin_number, name, address);

                } while (rs.next());
            }


        } catch (SQLException | ParseException throwables) {
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

}
