package users;

import database.SQLDatabaseConnection;
import org.apache.commons.lang3.StringUtils;
import structs.Address;
import structs.Name;
import structs.Pair;
import utils.Helper;
import utils.Vars;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.ArrayList;

public class Employee extends User {

    private int salary;
    private String role;

    public Employee(int sinNumber, Name name, Address address, int salary, String role) {
        super(sinNumber, name, address);
        this.salary = salary;
        this.role = role;
    }

    @Override
    public String toString() {
        StringBuilder strB = new StringBuilder();

        strB.append(super.toString()).append("\n")
                .append("Salary: ").append(NumberFormat.getCurrencyInstance().format(salary)).append("\n")
                .append("Role: ").append(role);

        return strB.toString().trim();
    }

    public int getSalary() {
        return salary;
    }

    public String getRole() {
        return role;
    }

    public boolean getRooms(ArrayList<Pair<Integer, Integer>> roomResults, String VAR_START_DATE, String VAR_END_DATE, double VAR_MIN_PRICE, double VAR_MAX_PRICE, String VAR_VIEW, String VAR_ROOM_CAPACITY, String VAR_IS_EXTENDABLE, String VAR_AMENITIES) {
        SQLDatabaseConnection db = SQLDatabaseConnection.getInstance();

        //execute the sql SELECT statement
        try {
            ResultSet rs = db.executeQuery("WITH HotelRoom_EMP(room_number, hotel_ID, price, room_capacity, view, is_extendable, room_status) AS"
                    + " (SELECT HR.*"
                    + " FROM HotelRoom as HR, WorksFor as WF"
                    + " WHERE HR.hotel_ID = WF.hotel_ID"
                    + " AND WF.sin_number = " + super.sinNumber + "),"

                    + " existing_bookings(booking_ID, start_date, end_date) AS"
                    + " (SELECT B.booking_ID, B.start_date, B.end_date"
                    + " FROM Hotel as H, HotelRoom_EMP as HR, BooksFor as BF, Booking as B"
                    + " WHERE H.hotel_ID = HR.hotel_ID"
                    + " AND HR.price >= " + VAR_MIN_PRICE + " AND HR.price <= " + VAR_MAX_PRICE
                    + " AND (HR.view, HR.room_capacity, HR.is_extendable) = ('"+ VAR_VIEW + "', '" + VAR_ROOM_CAPACITY + "', '" + VAR_IS_EXTENDABLE +"')"
                    + " AND (HR.hotel_ID, HR.room_number) = (BF.hotel_ID, BF.room_number)"
                    + " AND BF.booking_ID = B.booking_ID)"

                    //main select statement
                    + " SELECT DISTINCT H.hotel_ID, HR.room_number, HR.price, HR.room_capacity, HR.view, HR.is_extendable, HA.amenity, H.star_category, H.city, H.state_name, H.zip, H.email_address"
                    + " FROM Hotel as H, HotelRoom_EMP as HR, BooksFor as BF, Booking as B, HotelRoomAmenities as HA"
                    + " WHERE H.hotel_ID = HR.hotel_ID"
                    + " AND HR.price >= " + VAR_MIN_PRICE + " AND HR.price <= " + VAR_MAX_PRICE
                    + " AND (HR.view, HR.room_capacity, HR.is_extendable) = ('" + VAR_VIEW + "', '" + VAR_ROOM_CAPACITY + "', '" + VAR_IS_EXTENDABLE + "')"
                    + " AND (HR.hotel_ID, HR.room_number) = (BF.hotel_ID, BF.room_number)"
                    + "	AND (HR.hotel_ID, HR.room_number) = (HA.hotel_ID, HA.room_number) AND HA.amenity = '" + VAR_AMENITIES + "'"
                    // case 1: there is no booking for this room. Thus, safe to assume customer's date rangedoes not overlap with existing books
                    + " AND ((HR.room_status = 'available')"
                    //case 2: there are booking(s) for this room. Make sure customer's date range does not overlap with existing bookings
                    + " OR (BF.booking_ID = B.booking_ID"
                    + " AND '" + VAR_START_DATE + "' <= ALL(SELECT end_date FROM existing_bookings)"
                    + " AND '" + VAR_END_DATE + "' >= ALL(SELECT start_date FROM existing_bookings)))");


            if (!rs.next()) {
                System.out.println("There were no available rooms that met that criteria");
                return false;
            } else {

                do {

                    //hotel room ID
                    String hotel_ID = rs.getString("hotel_ID");
                    String room_number = rs.getString("room_number");

                    roomResults.add(new Pair<>(Integer.valueOf(hotel_ID), Integer.valueOf(room_number)));

                    String resTitle = "\nHotel ID: " + hotel_ID + ", Room Number: " + room_number;

                    System.out.println(StringUtils.center(resTitle, Vars.DIVIDER_DASH.length()));
                    Helper.println(Vars.DIVIDER_DASH);

                    //user selected info
                    System.out.println("\n* Hotel Room Info *");

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
                    System.out.println("* Hotel Information *");

                    String star_category = rs.getString("star_category");
                    System.out.println("Star Category: " + star_category);

                    String city = rs.getString("city");
                    System.out.println("City: " + city);

                    String state_name = rs.getString("state_name");
                    System.out.println("State Name: " + state_name);

                    String zip = rs.getString("zip");
                    System.out.println("Zip: " + zip);

                    String email_address = rs.getString("email_address");
                    System.out.println("Email if you have any inquires: " + email_address + "\n");

                } while (rs.next());

                Helper.println(Vars.DIVIDER_EQUALS);
                return true;
            }



        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return false;
    }

}
