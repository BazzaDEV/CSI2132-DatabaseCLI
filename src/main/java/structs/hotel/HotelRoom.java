package structs.hotel;

import database.SQLDatabaseConnection;
import structs.booking.Booking;

import java.sql.ResultSet;
import java.sql.SQLException;

public class HotelRoom {

    private int roomNumber;
    private int hotelID;
    private double price;
    private String roomCapacity;
    private String view;
    private boolean isExtendable;
    private String roomStatus;

    public HotelRoom(Booking b) {

        SQLDatabaseConnection db = SQLDatabaseConnection.getInstance();

        try {
            ResultSet rs = db.executeQuery(
                    "SELECT DISTINCT HR.*" +
                            " FROM BooksFor as BF, HotelRoom as HR" +
                            " WHERE BF.booking_ID = " + b.getBookingID() +
                            " AND (BF.room_number, BF.hotel_ID) = (HR.room_number, HR.hotel_ID)"
            );

            if (!rs.next()) { // ResultSet is empty

            } else {

                do {
                    this.roomNumber = rs.getInt("room_number");
                    this.hotelID = rs.getInt("hotel_ID");
                    this.price = rs.getDouble("price");
                    this.roomCapacity = rs.getString("room_capacity");
                    this.view = rs.getString("view");
                    this.isExtendable = rs.getBoolean("is_extendable");
                    this.roomStatus = rs.getString("room_status");

                } while (rs.next());
            }


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public HotelRoom(int hotelID, int roomNumber) {

        SQLDatabaseConnection db = SQLDatabaseConnection.getInstance();

        try {
            ResultSet rs = db.executeQuery(
                    "SELECT DISTINCT *" +
                            " FROM HotelRoom" +
                            " WHERE hotel_ID = " + hotelID +
                            " AND room_number = " + roomNumber
            );

            if (!rs.next()) { // ResultSet is empty

            } else {

                do {
                    this.roomNumber = rs.getInt("room_number");
                    this.hotelID = rs.getInt("hotel_ID");
                    this.price = rs.getDouble("price");
                    this.roomCapacity = rs.getString("room_capacity");
                    this.view = rs.getString("view");
                    this.isExtendable = rs.getBoolean("is_extendable");
                    this.roomStatus = rs.getString("room_status");

                } while (rs.next());
            }


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    /************************
     *       GETTERS       *
    ************************/

    public int getRoomNumber() {
        return roomNumber;
    }

    public int getHotelID() {
        return hotelID;
    }

    public double getPrice() {
        return price;
    }

    public String getRoomCapacity() {
        return roomCapacity;
    }

    public String getView() {
        return view;
    }

    public boolean isExtendable() {
        return isExtendable;
    }

    public String getRoomStatus() {
        return roomStatus;
    }
}
