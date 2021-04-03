package users;

import structs.Address;
import structs.Name;
import utils.Helper;

import java.sql.ResultSet;
import java.sql.SQLException;
import database.SQLDatabaseConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import database.SQLDatabaseConnection;

public class Customer extends User {

    private Date registrationDate;

    public Customer(int sinNumber, Name name, Address address, Date registrationDate) {
        super(sinNumber, name, address);
        this.registrationDate = registrationDate;
    }

    @Override
    public String toString() {
        StringBuilder strB = new StringBuilder();

        strB.append(super.toString()).append("\n")
                .append("Registration Date: ").append(new SimpleDateFormat("MMMMMMMM dd yyyy").format(registrationDate));

        return strB.toString().trim();
    }
    
    
    public static boolean getRooms(String VAR_START_DATE, String VAR_END_DATE, String VAR_CITY, String VAR_STATE, float VAR_MIN_PRICE, float VAR_MAX_PRICE, String VAR_VIEW, String VAR_ROOM_CAPACITY, String VAR_IS_EXTENDABLE, String VAR_AMENITIES) {
        SQLDatabaseConnection db = SQLDatabaseConnection.getInstance();

        //execute the sql SELECT statement
        try {
        	//With clause defines all existing bookings which satisfy the search criteria
            ResultSet rs = db.executeQuery("WITH existing_bookings(booking_ID, start_date, end_date) AS"
            		+ " (SELECT B.booking_ID, B.start_date, B.end_date"
            		+ " FROM Hotel H, HotelRoom HR, BooksFor BF, Booking B"
            		+ " WHERE (H.city, H.state_name) = ('"+ VAR_CITY +"', '" +VAR_STATE+ "')"
            		+ " AND H.hotel_ID = HR.hotel_ID"
            		+ " AND HR.price >= " + VAR_MIN_PRICE + " AND HR.price <= " + VAR_MAX_PRICE 
            		+ " AND (HR.view, HR.room_capacity, HR.is_extendable) = ('"+ VAR_VIEW + "', '" + VAR_ROOM_CAPACITY + "', '" + VAR_IS_EXTENDABLE+"')"
            		+ " AND (HR.hotel_ID, HR.room_number) = (BF.hotel_ID, BF.room_number)"
            		+ " AND BF.booking_ID = B.booking_ID)"
            		//main select statement
            		+ " SELECT H.hotel_ID, HR.room_number, HR.price, HR.room_capacity, HR.view, HR.is_extendable, HA.amenity, H.star_category, H.city, H.state_name, H.zip, H.email_address"
            		+ " FROM Hotel as H, HotelRoom as HR, BooksFor as BF, Booking as B, HotelRoomAmenities as HA"
            		+ " WHERE (H.city, H.state_name) = ('"+ VAR_CITY +"', '" +VAR_STATE+ "')"
            		+ " AND H.hotel_ID = HR.hotel_ID"
            		+ " AND HR.price >= " +VAR_MIN_PRICE+ "AND HR.price <= " +VAR_MAX_PRICE
            		+ " AND (HR.view, HR.room_capacity, HR.is_extendable) = ('"+ VAR_VIEW + "', '" + VAR_ROOM_CAPACITY + "', '" + VAR_IS_EXTENDABLE+"')"
            		+ " AND (HR.hotel_ID, HR.room_number) = (BF.hotel_ID, BF.room_number)"
            		+ "	AND (HR.hotel_ID, HR.room_number) = (HA.hotel_ID, HA.room_number) AND HA.amenity= '" +VAR_AMENITIES+ "'"
            		// case 1: there is no booking for this room. Thus, safe to assume customer's date rangedoes not overlap with existing books
            		+ " AND ((HR.room_status = 'available')"
            		//case 2: there are booking(s) for this room. Make sure customer's date range does not overlap with existing bookings
            		+ " OR (BF.booking_ID = B.booking_ID"
            		+ " AND '" +VAR_START_DATE+ "' <= ALL(SELECT end_date FROM existing_bookings)"
            		+ " AND '" +VAR_END_DATE+ "' >= ALL(SELECT start_date FROM existing_bookings)))");


            if (!rs.next()) {
            	System.out.println("There were no available rooms that met that criteria");
            	return false;
            } else {
            		
                do {
                	
                	//hotel room ID
                    String hotel_ID = rs.getString("hotel_ID");
                    System.out.println("Hotel ID: " + hotel_ID);
                    
                    String room_number = rs.getString("room_number");
                    System.out.println("Room Number: " + room_number +"\n");
                  
                    //user selected info
                    System.out.println("Selected criteria");
                    
                    String price = rs.getString("price");
                    System.out.println("Price: " + price);
                    
                    String view = rs.getString("view");
                    System.out.println("View: " + view);
                    
                    String room_capacity = rs.getString("room_capacity");
                    System.out.println("Room Capacity: " + room_capacity);
                    
                    String is_extendable = rs.getString("is_extendable");
                    System.out.println("Room is extendable: " + is_extendable);
                    
                    String amenity = rs.getString("amenity");
                    System.out.println("Amenity: " + amenity + "\n");
                    
                    //Hotel info
                    System.out.println("Hotel Information");
                    
                    String star_category = rs.getString("star_category");
                    System.out.println("Star Category: " + star_category);
                    
                    String city = rs.getString("city");
                    System.out.println("City: " + city);
                    
                    String state_name = rs.getString("state_name");
                    System.out.println("State Name: " + state_name);
                    
                    String zip = rs.getString("zip");
                    System.out.println("Zip: " + zip);
                    
                    String email_address = rs.getString("email_address");
                    System.out.println("Email if you have any inquires: " + email_address + "\n\n");

                    
                    
                } while (rs.next());
                return true;
            }



        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return false;
    }
    
    public static void bookRooms(Integer hotel_ID, Integer room_number, String VAR_ROOM_CAPACITY, Integer VAR_NUM_OCCUPANTS, String VAR_START_DATE, String VAR_END_DATE) {
    	
    	SQLDatabaseConnection db = SQLDatabaseConnection.getInstance();

        //execute the sql SELECT statement
        try {
        	//first insert into Booking
        	db.executeUpdate("INSERT into Booking(booking_ID, status, room_type, num_occupants, start_date, end_date)"
            		+ " VALUES(default, 'scheduled', '" +VAR_ROOM_CAPACITY+ "', '"+ VAR_NUM_OCCUPANTS+ "', '" +VAR_START_DATE+ "', '" +VAR_END_DATE+ "');");
            
        	//need this select statement to get the booking ID we just inserted
        	ResultSet rs = db.executeQuery("SELECT max(booking_ID) as b_ID FROM Booking");
        	rs.next();
            String b_ID = rs.getString(1);
            System.out.println("Hotel ID: " + hotel_ID);
        	//Now insert into BooksFor
            db.executeUpdate(" Insert into BooksFor(booking_ID, room_number, hotel_ID)"
            		+ " values(" +b_ID+ ", "+ room_number+ ", " +hotel_ID+ ")");
            		

        	
        	Helper.println("The booking has been successfully created!");

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    	
    }
    
    
}
