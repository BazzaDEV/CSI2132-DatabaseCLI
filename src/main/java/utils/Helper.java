package utils;

import database.SQLDatabaseConnection;
import structs.Address;
import structs.Name;
import users.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

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

        return sc.nextLine();

    }
    
    /**
     * Reference:
     * https://stackoverflow.com/questions/39514730/how-to-take-input-as-string-with-spaces-in-java-using-scanner/39514943
     * @param prompt
     * @return
     */
   /* public static String getInputSpace(String prompt) {
    	Scanner sc = new Scanner(System.in);
    	print(prompt);
    	
    	String res="";
    	res+=sc.nextLine();
    	
    	sc.close();
    	
    	return res;
    	
    }*/
    

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
    
    public static void adminInsert() {
    	SQLDatabaseConnection db = SQLDatabaseConnection.getInstance();
    		  
          	String choice2 = Helper.getInput("\nWhat table do you want to insert data into?" 
          							+ "\n 1) Customer" + "\n 2) Employee" + "\n 3) Hotel" + "\n 4) Hotel Room");
          	if(Helper.multiCheck(choice2,new String[] {"1","2","3","4"})){
          		switch (choice2) {
          	case "1":
          		String data = Helper.getInput("What is Customer data (do not put spaces):");
         
          		System.out.println(data);
          		//11111125,'John','Middle','Smith',27,'Streethere',5,'Paris','France','123456','2021-10-05','6131111125'
          		
          		/*
          		String sin = Helper.getInput("Sin:");
          		String fName = Helper.getInput("fist name:");
          		String mName = Helper.getInput("middle name:");
          		String lName = Helper.getInput("last name:");
          		String streetNum = Helper.getInput("streetNum:");
          		String apt = Helper.getInput(":");
          		String city = Helper.getInput("Sin:");
          		String state = Helper.getInput("Sin:");
          		String zip = Helper.getInput("Sin:");
          		String date = Helper.getInput("Sin:");
          		*/
          		
          		try {
                    db.executeUpdate("Insert into customer " +"values(" 
                    					+ data.trim() + ")" );       
                    System.out.println("Insert of Customer complete.");
                    
                    try {
                        ResultSet rs = db.executeQuery("SELECT * " +
                                "FROM Customer " );

                        if (!rs.next()) {
                            return;
                        } else {
                            do {                   
                            	System.out.println(rs.getInt(1));                          	                           
                            } while (rs.next());
                        }
                    } catch (SQLException e2) {
                        e2.printStackTrace();
                    }              
                } catch (SQLException e) {
                    e.printStackTrace();
                }
          		break;
          	case "2":
          		
          		String data2 = Helper.getInput("What is Employee data (do not put spaces):"
          				+ "\n sin,fName,mName,lName,streetNum,streetName,aptNum,city,stateName,zip,salary,role \n"); 
          		System.out.println(data2);
          		
          		//11111125,'John','Middle','Smith',27,'Streethere',5,'Paris','France','123456',35,'Manager'
          		
          		try {
                    db.executeUpdate("Insert into employee " +"values(" 
                    					+ data2.trim() + ")" );       
                    System.out.println("Insert of Employee complete.");
                    
                    try {
                        ResultSet rs = db.executeQuery("SELECT * " +
                                "FROM Employee " );

                        if (!rs.next()) {
                            return;
                        } else {
                            do {                   
                            	System.out.println(rs.getInt(1));                          	                           
                            } while (rs.next());
                        }
                    } catch (SQLException e2) {
                        e2.printStackTrace();
                    }              
                } catch (SQLException e) {
                    e.printStackTrace();
                }
          		
          		break;
          	case "3":
          		
          		String data3 = Helper.getInput("What is Hotel data (do not put spaces):"
          				+ "\n (hotelID,starCategory,numOfRooms,streetNum,streetName,city,stateName,zip,email)\n"); 
          		System.out.println(data3);
          		
          		
          		
          		try {
                    db.executeUpdate("Insert into hotel " +"values(" 
                    					+ data3.trim() + ")" );       
                    System.out.println("Insert of Hotel complete.");
                    
                    try {
                        ResultSet rs = db.executeQuery("SELECT * " +
                                "FROM Hotel " );

                        if (!rs.next()) {
                            return;
                        } else {
                            do {                   
                            	System.out.println(rs.getInt(1));                          	                           
                            } while (rs.next());
                        }
                    } catch (SQLException e2) {
                        e2.printStackTrace();
                    }              
                } catch (SQLException e) {
                    e.printStackTrace();
                }
          		
          		break;
          	case "4":
          		
          		String data4 = Helper.getInput("What is Hotel Room data (do not put spaces):"
          				+ "\n (hotelID,roomNum,price,roomCap,view,isExtendable,roomStatus)\n"); 
          		System.out.println(data4);
          		             
          		try {
                    db.executeUpdate("Insert into hotelroom " +"values(" 
                    					+ data4.trim() + ")" );       
                    System.out.println("Insert of HotelRoom complete.");
                    
                    try {
                        ResultSet rs = db.executeQuery("SELECT * " +
                                "FROM Hotelroom " );

                        if (!rs.next()) {
                            return;
                        } else {
                            do {                   
                            	System.out.println(rs.getInt(1));                          	                           
                            } while (rs.next());
                        }
                    } catch (SQLException e2) {
                        e2.printStackTrace();
                    }              
                } catch (SQLException e) {
                    e.printStackTrace();
                }
          		
          		break;
          	}
          	}
    	  
    }
    
    public static void adminDelete() {
    	SQLDatabaseConnection db = SQLDatabaseConnection.getInstance();
		  
      	String tableName = Helper.getInput("\nWhat table do you want to delete data from?" 
      							+ "\n customer" + "\n employee" + "\n hotel" + "\n hotelroom");
      	if(Helper.multiCheck(tableName,new String[] {"customer","employee","hotel","hotelroom"})){
      		
      		String condition1 = Helper.getInput("What is condition (do not put spaces):"+"\n WHERE (condition?) \n");
      		try {
                db.executeUpdate("delete from" +tableName.trim() +"where" 
                					+ condition1.trim() );       
                System.out.println("Delete on" + tableName+ "complete.");
                
                try {
                    ResultSet rs = db.executeQuery("SELECT * " +
                            "FROM"+ tableName );

                    if (!rs.next()) {
                        return;
                    } else {
                        do {                   
                        	System.out.println(rs.getInt(1));                          	                           
                        } while (rs.next());
                    }
                } catch (SQLException e2) {
                    e2.printStackTrace();
                }              
            } catch (SQLException e) {
                e.printStackTrace();
            }
      	}
    }
    
    
    public static void adminUpdate() {
    	SQLDatabaseConnection db = SQLDatabaseConnection.getInstance();
		  
      	String tableName = Helper.getInput("\nWhat table do you want to Update data for?" 
      							+ "\n customer" + "\n employee" + "\n hotel" + "\n hotelroom");
      	if(Helper.multiCheck(tableName,new String[] {"customer","employee","hotel","hotelroom"})){
      		
      		String choice = Helper.getInput("\n What Update query would you like to execute?\n 1) UPDATE tableName SET Condition1 WHERE condition2;\n"
      		+"2) UPDATE tableName SET condition1;\n");
      		if(Helper.multiCheck(choice, new String[] {"1","2"})) {
      		
      	//	try {
      			if(choice.equals("1")) {
      				String condition1 = Helper.getInput("What is condition1 (do not put spaces):\n");
      				String condition2 = Helper.getInput("What is condition2 (do not put spaces):\n");
      			/*	db.executeUpdate("update" +tableName.trim() 
                					+"set" + condition1 
                					+ "where"+condition2);       
                					*/
      				print(condition1+condition2);
      			}
      			else {
      				if(choice.equals("1")) {
      					String condition3 = Helper.getInput("What is condition1 (do not put spaces):\n");
          		/*		db.executeUpdate("update" +tableName.trim() 
                    					+"set" + condition3 
                    					);   
                    					*/
      					print(condition3);
      			}
      			}
                System.out.println("Update on" + tableName+ "complete.");
               /* try {
                    ResultSet rs = db.executeQuery("SELECT * " +
                            "FROM"+ tableName );
                            
                    if (!rs.next()) {
                        return;
                    } else {
                        do {                   
                        	System.out.println(rs.getInt(1));                          	                           
                        } while (rs.next());
                    }
                    
                } catch (SQLException e2) {
                    e2.printStackTrace();
                }       */       
           /* } catch (SQLException e) {
                e.printStackTrace();
            }*/
      		}
      	}
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

}
