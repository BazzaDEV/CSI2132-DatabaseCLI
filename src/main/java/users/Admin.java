package users;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import database.SQLDatabaseConnection;
import structs.Address;
import structs.Name;
import utils.Helper;

public class Admin {

    public static final String ROLE_NAME = "Database Administrator";
    
    private static String[]tables = new String[]{"hotel","worksfor","employee","supervises","hotelphonenum",
			"brandchain","hotelroom","hotelroomamenities","customer","hotelbrand",
			"hotelbrandemail","hotelbrandphonenum","booksfor","cancreate","payment",
			"booking","transformsinto","renting"};

    public Admin() {
    }

    /**
     * Insert Query
     * @param tableName
     * @param data
     */
    public static void adminInsert(String tableName, String data) {
    	SQLDatabaseConnection db = SQLDatabaseConnection.getInstance();
    	try {
    		//update query on database
    		db.executeUpdate("Insert into "+tableName +" values(" 
    				+ data + ")" );       
    		System.out.println("Insert of "+data+"\n into "+tableName+" complete.");
    		/*
    				ResultSet rs = db.executeQuery("SELECT * " +
    						"FROM "+tableName );
    						ResultSetMetaData rsMetaData = rs.getMetaData(); 
    						int numberOfColumns = rsMetaData.getColumnCount(); 
    						System.out.println(numberOfColumns);
    						while (rs.next()) 
    						{ 	
    							for(int i=1;i<=numberOfColumns;i++) {
    								System.out.print(rs.getString(i) +"\t");
    							}
    							System.out.println();
    						}                       	                           
    		 */
    	} catch (SQLException e) {
    		e.printStackTrace();
    	}
    }

    /**
     * Delete Query
     * @param tableName
     * @param condition1
     */
    public static void adminDelete(String tableName, String condition1) {
    	SQLDatabaseConnection db = SQLDatabaseConnection.getInstance();
    	try {
    		//delete query on database
    		db.executeUpdate("delete from " +tableName +" where " 
    				+ condition1 );       
    		System.out.println("Delete on " + tableName+ " complete.");

    		/*
    		ResultSet rs = db.executeQuery("SELECT * " +
    				"FROM "+tableName );
    		ResultSetMetaData rsMetaData = rs.getMetaData(); 
    		int numberOfColumns = rsMetaData.getColumnCount(); 
    		System.out.println(numberOfColumns);
    		while (rs.next()) 
    		{ 	
    			for(int i=1;i<=numberOfColumns;i++) {
    				System.out.print(rs.getString(i) +"\t");
    			}
    			System.out.println();
    		}                      
    		*/ 	                           
    	} catch (SQLException e) {
    		e.printStackTrace();
    	}
    }

    /**
     * Update Query
     * @param tableName
     * @param choice
     */
    public static void adminUpdate(String tableName, String choice) {
    	SQLDatabaseConnection db = SQLDatabaseConnection.getInstance();

    	if(choice.equals("1")) {
    		System.out.println("UPDATE "+tableName
    				+ "\n SET condition1"
    				+ "\n WHERE condition2");
    		String condition1="";
    		String condition2="";
    		boolean flag1=false;
    		while(!flag1) {			
    			//get the db administrator to input the query conditions
    			condition1 = Helper.getInput("\nWhat is condition1:\n");
    			condition2 = Helper.getInput("\nWhat is condition2:\n");	
    			System.out.println("UPDATE "+tableName
    					+ "\n SET "+ condition1 
    					+ "\n WHERE "+ condition2);
    			flag1=Helper.ask("Is this the correct update query?");
    		}

    		try {	
    			//update query on database
    			db.executeUpdate("update " +tableName
    					+" set " + condition1 
    					+ " where "+condition2);       		
    			System.out.println("Update on " + tableName+ " complete.");
    		
    			//select tuples from table that were updated using condition
    			ResultSet rs = db.executeQuery("SELECT * " +
    					"FROM "+tableName +" where "+ condition2 );
    			//get number of columns from table
    			ResultSetMetaData rsMetaData = rs.getMetaData(); 
    			int numberOfColumns = rsMetaData.getColumnCount(); 
    			System.out.println(numberOfColumns);
    			//print all information from table tuples
    			while (rs.next()) 
    			{ 	
    				for(int i=1;i<=numberOfColumns;i++) {
    					System.out.print(rs.getString(i) +"\t");
    				}
    				System.out.println();
    			}                       	                           
    		} catch (SQLException e3) {
    			e3.printStackTrace();
    		}      
    	}
    	else { // choice = 2
    		System.out.println("UPDATE "+tableName
    				+ "\n SET condition1");
    		String condition3="";
    		boolean flag2=false;
    		while(!flag2) {		
    			//get input from db administrator for condition
    			condition3 = Helper.getInput("\nWhat is condition1: \n");		
    			System.out.println("UPDATE "+tableName
    					+ "\n SET "+ condition3);
    			flag2=Helper.ask("Is this the correct update query?");
    		}

    		try {
    			//update query on db
    			db.executeUpdate("update " +tableName
    					+" set " + condition3 );   			
    			System.out.println("Update on " + tableName+ " complete.");
    			
    			//select tuples that were updated
    			ResultSet rs = db.executeQuery("SELECT * " +
    					"FROM "+tableName +" where "+ condition3 );
    			
    			//get number of columns in table
    			ResultSetMetaData rsMetaData = rs.getMetaData(); 
    			int numberOfColumns = rsMetaData.getColumnCount(); 
    			System.out.println(numberOfColumns);
    			//print data from table tuples that were updated
    			while (rs.next()) 
    			{ 	
    				for(int i=1;i<=numberOfColumns;i++) {
    					System.out.print(rs.getString(i) +"\t");
    				}
    				System.out.println();
    			}                       	                           
    		}catch (SQLException e) {
    			e.printStackTrace();
    		}
    	}
    }
}
