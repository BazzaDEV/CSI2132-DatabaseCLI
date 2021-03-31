package users;

import java.sql.ResultSet;
import java.sql.SQLException;

import database.SQLDatabaseConnection;
import structs.Address;
import structs.Name;
import utils.Helper;

public class Admin extends Employee {

    public static final String ROLE_NAME = "Database Administrator";

    public Admin(int sinNumber, Name name, Address address, int salary) {
        super(sinNumber, name, address, salary, ROLE_NAME);
    }

    /**
     * Insert 
     */
    public static void adminInsert() {
    	SQLDatabaseConnection db = SQLDatabaseConnection.getInstance();
    	String tableName = Helper.getInput("\nWhat table do you want to insert data into?" 
    			+ "\n Customer" + "\n employee" + "\n hotel" + "\n hotelroom");
    	if(Helper.multiCheck(tableName,new String[] {"1","2","3","4"})){
    		if(tableName.equals("customer")) {
    			String data = Helper.getInput("What is" +tableName+ " data:");

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
    				db.executeUpdate("Insert into"+tableName +"values(" 
    						+ data + ")" );       
    				System.out.println("Insert of"+tableName+"complete.");

    				try {
    					ResultSet rs = db.executeQuery("SELECT * " +
    							"FROM"+tableName );
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

    }

    /**
     * Delete
     */
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

    /**
     * Update
     */
    public static void adminUpdate() {
    	SQLDatabaseConnection db = SQLDatabaseConnection.getInstance();

    	String tableName = Helper.getInput("\nWhat table do you want to Update data for?" 
    			+ "\n customer" + "\n employee" + "\n hotel" + "\n hotelroom");
    	if(Helper.multiCheck(tableName,new String[] {"customer","employee","hotel","hotelroom"})){

    		String choice = Helper.getInput("\n What Update query would you like to execute?\n 1) UPDATE tableName SET Condition1 WHERE condition2;\n"
    				+"2) UPDATE tableName SET condition1;\n");
    		if(Helper.multiCheck(choice, new String[] {"1","2"})) {

    			try {
    				if(choice.equals("1")) {
    					String condition1 = Helper.getInput("What is condition1 (do not put spaces):\n");
    					String condition2 = Helper.getInput("What is condition2 (do not put spaces):\n");
    					db.executeUpdate("update" +tableName.trim() 
    					+"set" + condition1 
    					+ "where"+condition2);       		
    					//Helper.print(condition1+condition2);
    				}
    				else {
    					if(choice.equals("1")) {
    						String condition3 = Helper.getInput("What is condition1 (do not put spaces):\n");
    						db.executeUpdate("update" +tableName.trim() 
    						+"set" + condition3 
    								);   			
    						//Helper.print(condition3);
    					}
    				}
    				System.out.println("Update on" + tableName+ "complete.");
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
    }


    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
