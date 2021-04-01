package cli.customer;

import cli.Menu;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import users.Admin;
import users.Customer;
import users.User;
import utils.Helper;
import utils.Vars;

public class CustomerMainMenu extends Menu {

    private Customer c;

    @Override
    public void start() {

        User u = cliManager.getUser();

        if (u instanceof Customer) {
            c = (Customer) u;
        }

        Helper.println("\n" + Vars.DIVIDER_EQUALS +
                        "\n" + StringUtils.center("Customer Main Menu", Vars.DIVIDER_EQUALS.length()) +
                        "\n" + Vars.DIVIDER_EQUALS);

        Helper.println("\nWelcome back, " + c.getName().getFirstName() + "!");

        Helper.println("\nCustomer info:\n\n" +
                c.toString());
        

        // choose checkin and checkout date  
        boolean F6 = false;
        while (!F6) {

            String start_date = Helper.getInput("What day would you like to check in? (yyyy-mm-dd)\n");
            String end_date = Helper.getInput("What day would you like to check out? (yyyy-mm-dd)\n");

            if (start_date.matches("\\d{4}-\\d{2}-\\d{2}") && end_date.matches("\\d{4}-\\d{2}-\\d{2}")) { 

                F6 = true;

            } else { // Invalid entry
                Helper.println("Please enter a valid date");

            }
        }
        
        //choose city and state    
        boolean F4 = false;
        Helper.println("Please enter your city and state you're searching for");
        
        while (!F4) {

            String VAR_CITY = Helper.getInput("City: \n");
            String VAR_STATE = Helper.getInput("State: \n");

            if (VAR_CITY.matches("[a-zA-Z]+") && VAR_STATE.matches("[a-zA-Z]+")) { 

                F4 = true;
                

            } else { // Invalid entry
                Helper.println("Please enter a valid city and state");

            }
        }


        
        //choose price range
        boolean F5 = false;
        Helper.println("Please enter the price range or SKIP");
        
        while (!F5) {

            String VAR_MIN_PRICE = Helper.getInput("Minimum price: \n");
            String VAR_MAX_PRICE = Helper.getInput("Maximum price: \n");

            if (VAR_MIN_PRICE.equalsIgnoreCase("SKIP")) { 
            	VAR_MIN_PRICE = null;

            } 
            if (VAR_MAX_PRICE.equalsIgnoreCase("SKIP")) { 
            	VAR_MAX_PRICE = null;
            	F5 = true;

            } 
            if (VAR_MIN_PRICE.matches("[0-9]+") && VAR_MAX_PRICE.matches("[0-9]+")) { 

                F5 = true;
                

            } else { // Invalid entry
                Helper.println("Please enter a valid price range");

            }
        }

        
        //choose sea or mountain
        boolean F1 = false;
        while (!F1) {
        	
        	String[] view_options = new String[] {"sea", "mountain"};
        	String VAR_VIEW = Helper.getInput("Would you like a (sea) or (mountain) view? \n");

            if (Helper.multiCheck(VAR_VIEW, view_options)) { 

                F1 = true;

            } else { // Invalid entry
                Helper.println("Please type either sea or mountain.");

            }
        }
        
        //choose single or double        
        boolean F2 = false;
        while (!F2) {
        	
        	String[] room_options = new String[] {"single", "double"};
            String VAR_ROOM_CAPACITY = Helper.getInput("Would you like a (single) or (double) room? \n");

            if (Helper.multiCheck(VAR_ROOM_CAPACITY, room_options)) { 

                F2 = true;

            } else { // Invalid entry
                Helper.println("Please type either single or double.");

            }
        }
       
        //choose extendable room or not
        boolean F3 = false;
        Helper.println("Would you like an extendable room? ");
        
        while (!F3) {
        	
        	String[] extendable_options = new String[] {"y", "n"};
            String VAR_IS_EXTENDABLE = Helper.getInput("(y)es or (n)o: \n");

            if (Helper.multiCheck(VAR_IS_EXTENDABLE, extendable_options)) { 

                F3 = true;
                
                if (VAR_IS_EXTENDABLE.equalsIgnoreCase("y")) {
                	VAR_IS_EXTENDABLE = "true";

                } else if (VAR_IS_EXTENDABLE.equalsIgnoreCase("n")) { // Employee login
                	VAR_IS_EXTENDABLE = "false";

                }

            } else { // Invalid entry
                Helper.println("Please type either y or n.");

            }
        }

    }
}
