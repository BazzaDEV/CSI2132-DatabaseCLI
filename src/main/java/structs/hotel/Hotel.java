package structs.hotel;

import database.SQLDatabaseConnection;
import structs.Address;
import structs.booking.Booking;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Hotel {

    private int hotelID;
    private int starCategory;
    private int numberOfRooms;
    private Address address;
    private String emailAddress;
    private long phoneNumber;

    public Hotel(int hotelID, int starCategory, int numberOfRooms, Address address, String emailAddress, long phoneNumber) {
        this.hotelID = hotelID;
        this.starCategory = starCategory;
        this.numberOfRooms = numberOfRooms;
        this.address = address;
        this.emailAddress = emailAddress;
        this.phoneNumber = phoneNumber;
    }

    public int getHotelID() {
        return hotelID;
    }

    public int getStarCategory() {
        return starCategory;
    }

    public int getNumberOfRooms() {
        return numberOfRooms;
    }

    public Address getAddress() {
        return address;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public long getPhoneNumber() {
        return phoneNumber;
    }

    public static Hotel fromBooking(Booking b) {

        SQLDatabaseConnection db = SQLDatabaseConnection.getInstance();

        try {

            ResultSet rs = db.executeQuery(
                    "SELECT DISTINCT H.*, HPN.phone_number" +
                            " FROM BooksFor as BF, Hotel as H, HotelPhoneNum as HPN" +
                            " WHERE BF.booking_ID = " + b.getBookingID() +
                            " AND BF.hotel_ID = H.hotel_ID" +
                                " AND H.hotel_ID = HPN.hotel_ID"
            );

            return fromResultSet(rs);


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return null;

    }

    private static Hotel fromResultSet(ResultSet rs) throws SQLException {

     int hotelID;
     int starCategory;
     int numberOfRooms;
     Address address;
     String emailAddress;
     long phoneNumber;

        if (!rs.next()) { // ResultSet is empty

        } else {

            hotelID = rs.getInt("hotel_ID");
            starCategory = rs.getInt("star_category");
            numberOfRooms = rs.getInt("number_of_rooms");

            int streetNumber = rs.getInt("street_number");
            String streetName = rs.getString("street_name");
            String city = rs.getString("city");
            String state = rs.getString("state_name");
            String zip = rs.getString("zip");

            address = new Address(streetNumber, streetName, null, city, state, zip);

            emailAddress = rs.getString("email_address");
            phoneNumber = rs.getLong("phone_number");

            return new Hotel(hotelID, starCategory, numberOfRooms, address, emailAddress, phoneNumber);
        }

        return null;
    }
}
