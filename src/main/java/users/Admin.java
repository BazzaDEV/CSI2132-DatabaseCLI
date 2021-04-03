package users;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import database.SQLDatabaseConnection;
import structs.Address;
import structs.Name;
import utils.Helper;

public class Admin extends Employee {

    public static final String ROLE_NAME = "DB Administrator";

    //database table names
    private String[]tables = new String[]{"hotelbrand", "hotelbrandphonenum", "hotelbrandemail",
    		"brandchain", "hotel", "hotelphonenum", "worksfor", "employee", "supervises",
    		"hotelroom", "hotelroomamenities", "booksfor", "customer",
    		"cancreate", "booking", "transformsinto", "payment", "renting"};

    //strings for table attribute names for insert format
    private String[]cols = new String[] {
			"brand_ID, street_number, 'street_name', apt_number, 'city', 'state', zip, num_hotels", //hotelbrand
			"brand_ID, phone_number(10)", //hotelbrandphonenum
			"brand_ID, 'email_address'", //hotelbrandemail
			"hotel_ID, brand_ID", //brandchain
			"hotel_ID, star_category, number_of_rooms, 'street_number_street_name', 'city', 'state', 'zip(6)','email_address'", //hotel
			"hotel_ID, phone_number", //hotelphonenum
			"sin_number, hotel_ID", //worksfor
			"sin_number, 'first_name', 'middle_name', 'last_name', street_number, 'street_name',apt_number, 'city', 'state', 'zip', salary, 'role'", //employee
			"manager_ID, supervisee_ID", //supervises
			"hotel_ID, room_number, price, 'room_capacity', 'view', 'is_extendable', 'room_status'", //hotelroom
			"hotel_ID, room_number, 'amenity'", //hotelroomamenities
			"booking_ID, room_number, hotel_ID", //booksfor
			"sin_number, 'first_name', 'middle_name', 'last_name', 'street_number', 'street_name',apt_number, 'city', 'state', 'zip', 'date_of_registration YYYY-MM-DD'", //customer
			"booking_ID, sin_number", //cancreate
			"default (booking_ID), 'status', 'room_type', num_occupants, 'start_date YYYY-MM-DD', 'end_date YYYY-MM-DD'", //booking
			"booking_ID, renting_ID", //transformsinto
			"renting_ID, default (transaction_ID) , 'due_date YYYY-MM-DD', amount, 'received_on YYYY-MM-DD'", //payment
			"default (renting_ID), 'status', balance" //renting
	};

    public Admin(int sinNumber, Name name, Address address, int salary) {
        super(sinNumber, name, address, salary, ROLE_NAME);
    }

    public Admin(Employee e) {
        super(e.sinNumber, e.name, e.address, e.getSalary(), ROLE_NAME);
    }

    /**
     * Getter for table names array
     * @return
     */
    public String[] getTables() {
    	return tables;
    }

    /**
     * Getter for attibute names array
     * @return
     */
    public String[] getCols() {
    	return cols;
    }

    /**
     * Insert Query
     * @param tableName
     * @param data
     */
    public void adminInsert(String tableName, String data) {
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
    		//e.printStackTrace();
    		System.out.println("Could not execute Insert Query.");
    	}
    }

    /**
     * Delete Query
     * @param tableName
     * @param condition1
     */
    public void adminDelete(String tableName, String condition1) {
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
    		//e.printStackTrace();
    		System.out.println("Could not execute Delete Query.");
    	}
    }

    /**
     * Update Query
     * @param tableName
     * @param choice
     */
    public void adminUpdate(String tableName, String choice) {
    	SQLDatabaseConnection db = SQLDatabaseConnection.getInstance();

    	if(choice.equals("1")) {
    		System.out.println("UPDATE "+tableName
    				+ "\n SET condition1"
    				+ "\n WHERE condition2 ;");
    		String condition1="";
    		String condition2="";
    		boolean flag1=false;
    		while(!flag1) {
    			//get the db administrator to input the query conditions
    			condition1 = Helper.getInput("\nWhat is condition1:\n");
    			condition2 = Helper.getInput("\nWhat is condition2:\n");
    			System.out.println("UPDATE "+tableName
    					+ "\n SET "+ condition1
    					+ "\n WHERE "+ condition2 +";");
    			flag1=Helper.ask("Is this the correct update query?");
    			if(!flag1) {
					System.out.println("Update Query was not executed.");
				}
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
    			//System.out.println(numberOfColumns);
    			//print all information from table tuples
    			while (rs.next())
    			{
    				for(int i=1;i<=numberOfColumns;i++) {
    					System.out.print(rs.getString(i) +"\t");
    				}
    				System.out.println();
    			}
    		} catch (SQLException e3) {
    			//e3.printStackTrace();
    			System.out.println("Could not execute Update Query.");
    		}
    	}
    	else { // choice = 2
    		System.out.println("UPDATE "+tableName
    				+ "\n SET condition1 ;");
    		String condition3="";
    		boolean flag2=false;
    		while(!flag2) {
    			//get input from db administrator for condition
    			condition3 = Helper.getInput("\nWhat is condition1: \n");
    			System.out.println("UPDATE "+tableName
    					+ "\n SET "+ condition3 + ";");
    			flag2=Helper.ask("Is this the correct update query?");
    			if(!flag2) {
					System.out.println("Update Query was not executed.");
				}
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
    			//System.out.println(numberOfColumns);
    			//print data from table tuples that were updated
    			while (rs.next())
    			{
    				for(int i=1;i<=numberOfColumns;i++) {
    					System.out.print(rs.getString(i) +"\t");
    				}
    				System.out.println();
    			}
    		}catch (SQLException e) {
    			//e.printStackTrace();
    			System.out.println("Could not execute Update Query.");
    		}
    	}
    }
}
