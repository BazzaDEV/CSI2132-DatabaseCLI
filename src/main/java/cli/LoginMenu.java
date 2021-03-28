package cli;

import users.Customer;
import utils.Helper;

public class LoginMenu extends Menu {


    public LoginMenu() {
        super();
    }

    @Override
    public void start() {

        String userType = Helper.getInput("Welcome!" +
                "\nAre you a (C)ustomer, (E)mployee, or Database (A)dministrator?");

        boolean flag = true;

        while (flag) {
            if (userType.equalsIgnoreCase("C")) { // Customer login
              // Get customer's SIN number to login
                Customer c = null;
                Helper.println("Hey Customer! What's your SIN number - we need this to identify you.");

                while (c == null) {
                    String sin = Helper.getInput("\n\nSIN number: ");

                    c = Helper.getCustomerFromSIN(sin);

                    if (c == null) {
                        Helper.println("A customer does not exist with this SIN number");
                    }
                }

            } else if (userType.equalsIgnoreCase("E")) { // Employee login


            } else if (userType.equalsIgnoreCase("A")) { // Admin login


            } else { // Invalid entry
                flag = false;
                Helper.println("Invalid entry - try again.");

            }
        }
    }
}
