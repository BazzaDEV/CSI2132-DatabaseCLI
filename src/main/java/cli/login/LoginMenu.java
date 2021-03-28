package cli.login;

import cli.Menu;
import cli.customer.CustomerMainMenu;
import users.Customer;
import utils.Helper;

public class LoginMenu extends Menu {


    public LoginMenu() {
        super();
    }

    @Override
    public void start() {

        Helper.println("Welcome!");

        boolean FLAG = false;

        while (!FLAG) {

            String[] userTypeOptions = new String[] {"C", "E", "A"};
            String userType = Helper.getInput("Are you a (C)ustomer, (E)mployee, or Database (A)dministrator?\n");

            if (Helper.multiCheck(userType, userTypeOptions)) { // userType is valid

                FLAG = true;

                if (userType.equalsIgnoreCase("C")) { // Customer login
                    // Get customer's SIN number to login
                    lookupCustomerSIN();

                } else if (userType.equalsIgnoreCase("E")) { // Employee login


                } else if (userType.equalsIgnoreCase("A")) { // Admin login


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

        boolean FLAG2 = false;
        while (!FLAG2) {
            String sinNumber = Helper.getInput("SIN number: ");

            if (Helper.isDigitsOnly(sinNumber) && sinNumber.trim().length() == 8) { // SIN is valid input

                FLAG2 = true;

                c = Helper.getCustomerFromSIN(sinNumber);

                if (c == null) { // Customer with this SIN does not exist
                    Helper.println("\nA customer does not exist with this SIN number.");

                    boolean FLAG3 = false;
                    while (!FLAG3) {
                        String input = Helper.getInput("\nWould you like to: " +
                                "\n(1) Create a new customer account" +
                                "\n(2) Try again");

                        if (Helper.multiCheck(input, new String[]{"1", "2"})) { // result is valid

                            FLAG3 = true;

                            if (input.equalsIgnoreCase("1")) { // Create new customer account
                                cliManager.loadMenu(new CustomerSignupMenu());

                            } else if (input.equalsIgnoreCase("2")) { // Try again
                                continue;

                            }
                        }

                        else { // Invalid entry
                            Helper.println("\nInvalid entry - try again.");
                        }
                    }

                } else { // Customer with this SIN exists

                    cliManager.setUser(c);
                    cliManager.loadMenu(new CustomerMainMenu());

                }
            } else { // Invalid entry

                Helper.println("\nInvalid entry - make sure you only enter digits & your SIN number is exactly 8 digits.");

            }

        }
    }
}
