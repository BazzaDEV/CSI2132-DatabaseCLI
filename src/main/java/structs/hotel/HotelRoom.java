package structs.hotel;

import database.SQLDatabaseConnection;
import structs.booking.Booking;
import structs.booking.Renting;

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

    public HotelRoom(int roomNumber, int hotelID, double price, String roomCapacity, String view, boolean isExtendable, String roomStatus) {
        this.roomNumber = roomNumber;
        this.hotelID = hotelID;
        this.price = price;
        this.roomCapacity = roomCapacity;
        this.view = view;
        this.isExtendable = isExtendable;
        this.roomStatus = roomStatus;
    }

    /*************************************
     *       STATIC CONSTRUCTORS       *
     ************************************/

    public static HotelRoom fromRenting(int rentingID) {

        SQLDatabaseConnection db = SQLDatabaseConnection.getInstance();

        try {

            ResultSet rs = db.executeQuery(
                    "SELECT DISTINCT HR.*" +
                            " FROM TransformsInto as TI, BooksFor as BF, HotelRoom as HR" +
                            " WHERE TI.renting_ID = " + rentingID +
                            " AND TI.booking_ID = BF.booking_ID" +
                            " AND (BF.room_number, BF.hotel_ID) = (HR.room_number, HR.hotel_ID)"
            );

            return fromResultSet(rs);


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return null;
    }

    public static HotelRoom fromRenting(Renting r) {

        return fromRenting(r.getRentingID());

    }

    public static HotelRoom fromBooking(Booking b) {

        SQLDatabaseConnection db = SQLDatabaseConnection.getInstance();

        try {

            ResultSet rs = db.executeQuery(
                    "SELECT DISTINCT HR.*" +
                            " FROM BooksFor as BF, HotelRoom as HR" +
                            " WHERE BF.booking_ID = " + b.getBookingID() +
                            " AND (BF.room_number, BF.hotel_ID) = (HR.room_number, HR.hotel_ID)"
            );

            return fromResultSet(rs);


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return null;
    }

    private static HotelRoom fromResultSet(ResultSet rs) throws SQLException {

        int roomNumber;
        int hotelID;
        double price;
        String roomCapacity;
        String view;
        boolean isExtendable;
        String roomStatus;

        if (!rs.next()) { // ResultSet is empty

        } else {

            roomNumber = rs.getInt("room_number");
            hotelID = rs.getInt("hotel_ID");
            price = rs.getDouble("price");
            roomCapacity = rs.getString("room_capacity");
            view = rs.getString("view");
            isExtendable = rs.getBoolean("is_extendable");
            roomStatus = rs.getString("room_status");

            return new HotelRoom(roomNumber, hotelID, price, roomCapacity, view, isExtendable, roomStatus);
        }

        return null;
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

    public int roomCapacityAsInt() {

        if (roomCapacity.equalsIgnoreCase("single")) {
            return 1;
        } else if (roomCapacity.equalsIgnoreCase("double")) {
            return 2;
        }

        return 0;

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
