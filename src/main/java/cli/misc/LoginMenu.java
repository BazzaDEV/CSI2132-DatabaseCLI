package cli.misc;

import cli.Menu;
import cli.admin.AdminMainMenu;
import cli.customer.CustomerMainMenu;
import cli.employee.EmployeeMainMenu;
import org.apache.commons.lang3.StringUtils;
import users.Admin;
import users.Customer;
import users.Employee;
import utils.Helper;
import utils.Vars;

import java.util.Date;

public class LoginMenu extends Menu {


    public LoginMenu() {
        super();
    }

    @Override
    public void start() {

        Helper.println("\n" + Vars.DIVIDER_EQUALS +
                "\n" + StringUtils.center("Login Menu", Vars.DIVIDER_EQUALS.length()) +
                "\n" + Vars.DIVIDER_EQUALS);

        Helper.println("\nWelcome!");

        boolean FLAG = false;

        while (!FLAG) {

            String[] userTypeOptions = new String[] {"C", "E", "A"};
            String userType = Helper.getInput("Are you a (C)ustomer, (E)mployee, or Database (A)dministrator?" +
                    "\n>> ");

            if (Helper.multiCheck(userType, userTypeOptions)) { // userType is valid

                FLAG = true;

                if (userType.equalsIgnoreCase("C")) { // Customer login
                    // Get customer's SIN number to login
                    lookupCustomerSIN();

                } else if (userType.equalsIgnoreCase("E")) { // Employee login
                    lookupEmployeeSIN();

                } else if (userType.equalsIgnoreCase("A")) { // Admin login
                    lookupAdminSIN();

                }

            }

            else { // Invalid entry
                Helper.println("Invalid entry - try again.");

            }
        }
    }

    private void lookupCustomerSIN() {

        Customer c = null;

        Helper.println("\nHey Customer! What's your SIN number? We need this to identify you.");

        boolean FLAG = false;
        while (!FLAG) {
            String sinNumber = Helper.getInput("SIN number: ");

            if (Helper.isDigitsOnly(sinNumber) && sinNumber.trim().length() == 8) { // SIN is valid input
                c = Helper.getCustomerFromSIN(sinNumber);

                if (c == null) { // Customer with this SIN does not exist
                    Helper.println("\nA customer does not exist with this SIN number.");

                } else { // Customer with this SIN exists
                    FLAG = true;

                    cliManager.setUser(c);
                    cliManager.loadMenu(new CustomerMainMenu());

                }
            } else { // Invalid entry

                Helper.println("\nInvalid entry - make sure you only enter digits & your SIN number is exactly 8 digits.");

            }

        }
    }

    private void lookupEmployeeSIN() {

        Employee e = null;

        Helper.println("\nHey Employee! Enter your SIN number to access the employee portal.");

        boolean FLAG = false;

        while (!FLAG) {

            String sinNumber = Helper.getInput("SIN number: ");

            if (Helper.isValidSIN(sinNumber)) { // Input is valid
                FLAG = true;
                e = Helper.getEmployeeFromSIN(sinNumber);

                if (e != null) { // Employee exists
                    cliManager.setUser(e);
                    cliManager.loadMenu(new EmployeeMainMenu());

                } else { // Employee does not exist
                    FLAG = false;

                    Helper.println("\nSorry, there is no employee with this SIN number." +
                            "\nTry again.\n");

                    }

            } else { // Invalid entry

                Helper.println("\nInvalid entry - make sure you only enter digits & your SIN number is exactly 8 digits.");
            }

        }

    }

    private void lookupAdminSIN() {

        Admin a = null;
        Employee e = null;

        Helper.println("\nHey Admin! Enter your SIN number to access the admin portal.");

        boolean FLAG = false;

        while (!FLAG) {

            String sinNumber = Helper.getInput("SIN number: ");

            if (Helper.isValidSIN(sinNumber)) { // Input is valid
                FLAG = true;
                e = Helper.getEmployeeFromSIN(sinNumber);

                if (e != null && e.getRole().equalsIgnoreCase(Admin.ROLE_NAME)) { // Employee exists
                    a = new Admin(e);
                    cliManager.setUser(a);
                    cliManager.loadMenu(new AdminMainMenu());

                } else { // Admin does not exist
                    FLAG = false;

                    Helper.println("\nSorry, there is no admin with this SIN number." +
                            "\nTry again.\n");

                }

            } else { // Invalid entry

                Helper.println("\nInvalid entry - make sure you only enter digits & your SIN number is exactly 8 digits.");
            }

        }
    }



}

