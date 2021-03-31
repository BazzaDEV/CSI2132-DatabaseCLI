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
			"brandchain","hotelroom","hotelroomamenities","customer"};

    public Admin() {
    }

    /**
     * Insert 
     */
    public static void adminInsert() {
    	SQLDatabaseConnection db = SQLDatabaseConnection.getInstance();
    	String tableName = Helper.getInput("\nWhat table do you want to insert data into?\n");
    	if(Helper.multiCheck(tableName, tables)){
    		String data = Helper.getInput("What is " +tableName+ " data to insert:\n");
    		System.out.println(data);
    		//11111125,'John','Middle','Smith',27,'Streethere',5,'Paris','France','123456','2021-10-05','6131111125'
    		try {
    			db.executeUpdate("Insert into "+tableName +" values(" 
    					+ data + ")" );       
    			System.out.println("Insert on "+tableName+" complete.");

    			try {
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
    				
    			} catch (SQLException e2) {
    				e2.printStackTrace();
    			}              
    		} catch (SQLException e) {
    			e.printStackTrace();
    		}
    	}
    }

    /**
     * Delete
     */
    public static void adminDelete() {
    	SQLDatabaseConnection db = SQLDatabaseConnection.getInstance();

    	String tableName = Helper.getInput("\nWhat table do you want to delete data from?");
    	if(Helper.multiCheck(tableName,tables)){

    		String condition1 = Helper.getInput("What is condition: "+"\n WHERE (condition?) \n");
    		
    		try {
    			db.executeUpdate("delete from " +tableName +" where " 
    					+ condition1 );       
    			
    			System.out.println("Delete on " + tableName+ " complete.");

    			try {
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

    	String tableName = Helper.getInput("\nWhat table do you want to Update data for?");
    	if(Helper.multiCheck(tableName,tables)){

    		String choice = Helper.getInput("\n What Update query would you like to execute?\n 1) UPDATE tableName SET Condition1 WHERE condition2;\n"
    				+"2) UPDATE tableName SET condition1;\n");
    		if(Helper.multiCheck(choice, new String[] {"1","2"})) {
    			
    			try {
    				if(choice.equals("1")) {
    					String condition1 = Helper.getInput("\nWhat is condition1:\n");
    					String condition2 = Helper.getInput("\nWhat is condition2:\n");
    					db.executeUpdate("update " +tableName
    					+" set " + condition1 
    					+ " where "+condition2);       		
    					//Helper.print(condition1+condition2);
    					System.out.println("Update on " + tableName+ " complete.");
    					
    					try {
            				ResultSet rs = db.executeQuery("SELECT * " +
            						"FROM "+tableName +" where "+ condition2 );
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
            				
            			} catch (SQLException e3) {
            				e3.printStackTrace();
            			}      
    					
    				}
    				else {
    					String condition3 = Helper.getInput("\nWhat is condition1 (do not put spaces):\n");
    					db.executeUpdate("update " +tableName
    							+" set " + condition3 
    							);   			
    					//Helper.print(condition3);
    					System.out.println("Update on " + tableName+ " complete.");
    					
    					try {
            				ResultSet rs = db.executeQuery("SELECT * " +
            						"FROM "+tableName +" where "+ condition3 );
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
            				
            			} catch (SQLException e2) {
            				e2.printStackTrace();
            			}      
    					
    				}
    			} catch (SQLException e) {
    				e.printStackTrace();
    			}
    		}
    	}
    }
}
