package cli.customer;


import cli.Menu;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import users.Admin;
import users.Customer;
import users.User;
import utils.Helper;
import utils.Vars;

public class CustomerMainMenu extends Menu {

    private Customer c;
    private String VAR_START_DATE;
    private String VAR_END_DATE;
    private String VAR_CITY;
    private String VAR_STATE;
    private float VAR_MIN_PRICE;
    private float VAR_MAX_PRICE;
    private String min;
    private String max;
    private String VAR_VIEW;
    private String VAR_ROOM_CAPACITY;
    private Integer VAR_NUM_OCCUPANTS;
    private String VAR_IS_EXTENDABLE;
    private String VAR_AMENITIES;
    private String ID;
    private String number;
    private Integer hotel_ID;
    private Integer room_number;
    private ArrayList<String> rooms;
    private ArrayList<String> hotels;

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

            VAR_START_DATE  = Helper.getInput("What day would you like to check in? (yyyy-mm-dd)\n");
            VAR_END_DATE  = Helper.getInput("What day would you like to check out? (yyyy-mm-dd)\n");

            if (VAR_START_DATE.matches("\\d{4}-\\d{2}-\\d{2}") && VAR_END_DATE.matches("\\d{4}-\\d{2}-\\d{2}")) { 

                F6 = true;

            } else { // Invalid entry
                Helper.println("Please enter a valid date");

            }
        }
        
        //choose city and state    
        boolean F4 = false;
        Helper.println("Please enter your city and state you're searching for");
        
        while (!F4) {

            VAR_CITY = Helper.getInput("City: \n");
            VAR_STATE = Helper.getInput("State: \n");

            if (VAR_CITY.matches("[a-zA-Z]+") && VAR_STATE.matches("[a-zA-Z]+")) { 

                F4 = true;
                

            } else { // Invalid entry
                Helper.println("Please enter a valid city and state");

            }
        }


        
        //choose price range
        boolean F5 = false;
        Helper.println("Please enter the price range");
        
        while (!F5) {

            min = Helper.getInput("Minimum price: \n");
            max  = Helper.getInput("Maximum price: \n");

            if (min.matches("[0-9]+") && max.matches("[0-9]+")) { 
            	float VAR_MIN_PRICE=Float.parseFloat(min);  
            	float VAR_MAX_PRICE=Float.parseFloat(max); 
            	
            	if (VAR_MIN_PRICE < VAR_MAX_PRICE) {
                    F5 = true;
            	}else { // Invalid entry
                    Helper.println("The minimum must be less than maximum");

                }

            } else { // Invalid entry
                Helper.println("Please enter a valid price range");

            }
        }
        //need to convert the max and min to float outside of loop
    	float VAR_MIN_PRICE=Float.parseFloat(min);  
    	float VAR_MAX_PRICE=Float.parseFloat(max); 

        
        //choose sea or mountain
        boolean F1 = false;
        while (!F1) {
        	
        	String[] view_options = new String[] {"sea", "mountain"};
        	VAR_VIEW = Helper.getInput("Would you like a (sea) or (mountain) view? \n");

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
            VAR_ROOM_CAPACITY = Helper.getInput("Would you like a (single) or (double) room? \n");

            if (Helper.multiCheck(VAR_ROOM_CAPACITY, room_options)) { 
            	
                if (VAR_ROOM_CAPACITY.equalsIgnoreCase("single")) {
                	VAR_NUM_OCCUPANTS = 1;

                } else if (VAR_ROOM_CAPACITY.equalsIgnoreCase("double")) { // Employee login
                	VAR_NUM_OCCUPANTS = 2;

                }
                
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
            VAR_IS_EXTENDABLE = Helper.getInput("(y)es or (n)o: \n");

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
        
        boolean F7 = false;
        while (!F7) {
        	
        	String[] ameneties_options = new String[] {"AC", "TV", "fridge"};
        	VAR_AMENITIES = Helper.getInput("Are you looking for air conditioning(AC), television(TV) or a (fridge)? \n");
            

            if (Helper.multiCheck(VAR_AMENITIES, ameneties_options)) { 
            	
            	if (VAR_AMENITIES.equalsIgnoreCase("AC")) {
            		VAR_AMENITIES = "air conditioning";

                } 
                F7 = true;

            } else { // Invalid entry
                Helper.println("Please choose one of the provided options.");

            }
        }
        
        rooms = new ArrayList<>();
        hotels = new ArrayList<>();
        //displaying available hotel rooms
        System.out.println("These are the available hotel rooms: \n");
        boolean result = Customer.getRooms(VAR_START_DATE, VAR_END_DATE, VAR_CITY, VAR_STATE, VAR_MIN_PRICE, VAR_MAX_PRICE, VAR_VIEW, VAR_ROOM_CAPACITY, VAR_IS_EXTENDABLE, VAR_AMENITIES, rooms, hotels);

        
        
        if (result == false) {
        	Helper.println("If you wish to book a hotel room please restart the process. Otherwise we wish you a great day!");
        	cliManager.prevMenu();
        } else {
            //user chooose room for booking
            boolean Flag = false;
            System.out.println("Please enter the hotel ID and room number of the room you wish to book");
            
            while (!Flag) {

                ID = Helper.getInput("Hotel ID: \n");
                number  = Helper.getInput("Room Number: \n");

                
                if (hotels.contains(ID)&& rooms.contains(number)) { 
                	
                    Flag = true;

                } else { // Invalid entry
                    Helper.println("Please enter a valid hotel_ID and room_number");

                }
            }
            //need to convert to integer
        	int hotel_ID=Integer.parseInt(ID);  
        	int room_number=Integer.parseInt(number); 
        	
        	//booking room
        	Customer.bookRooms(hotel_ID, room_number, VAR_ROOM_CAPACITY, VAR_NUM_OCCUPANTS, VAR_START_DATE, VAR_END_DATE);

        }

    }
}
