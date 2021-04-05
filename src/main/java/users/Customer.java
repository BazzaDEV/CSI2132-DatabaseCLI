package users;

import database.SQLDatabaseConnection;
import org.apache.commons.lang3.StringUtils;
import structs.Address;
import structs.Name;
import structs.Pair;
import structs.booking.Booking;
import structs.booking.Renting;
import structs.hotel.HotelRoom;
import utils.Vars;
import utils.Helper;

import java.sql.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import database.SQLDatabaseConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    public List<HotelRoom> getRooms(String VAR_START_DATE, String VAR_END_DATE, String VAR_CITY, String VAR_STATE, double VAR_MIN_PRICE, double VAR_MAX_PRICE, String VAR_VIEW, String VAR_ROOM_CAPACITY, String VAR_IS_EXTENDABLE, String VAR_AMENITIES) {
        SQLDatabaseConnection db = SQLDatabaseConnection.getInstance();
        List<HotelRoom> roomResults = new ArrayList<>();

        try {
            ResultSet rs = db.executeQuery(
                    "WITH HotelRoom_CUST(room_number, hotel_ID, price, room_capacity, view, is_extendable, room_status) AS" +
                        " (SELECT HR.*" +
                        " FROM HotelRoom as HR, Hotel as H" +
                        " WHERE HR.hotel_ID = H.hotel_ID" +
                            " AND (H.city, H.state_name) = ('"+ VAR_CITY +"', '" +VAR_STATE+ "'))," +

                    " ExistingBookings(hotel_ID, room_number, booking_ID) AS" +
                        " (SELECT HR.hotel_ID, HR.room_number, B.booking_ID" +
                        " FROM Hotel as H, HotelRoom_CUST as HR, BooksFor as BF, Booking as B" +
                        " WHERE H.hotel_ID = HR.hotel_ID" +
                            " AND (HR.hotel_ID, HR.room_number) = (BF.hotel_ID, BF.room_number)" +
                                " AND BF.booking_ID = B.booking_ID)" +

                    " SELECT DISTINCT HR.*, HA.amenity, H.star_category, H.city, H.state_name, H.zip, H.email_address" +
                    " FROM Hotel as H, HotelRoom_CUST as HR, HotelRoomAmenities as HA" +
                    " WHERE H.hotel_ID = HR.hotel_ID" +
                        " AND HR.price >= " + VAR_MIN_PRICE + " AND HR.price <= " + VAR_MAX_PRICE +
                        " AND (HR.view, HR.room_capacity, HR.is_extendable) = ('" + VAR_VIEW + "', '" + VAR_ROOM_CAPACITY + "', '" + VAR_IS_EXTENDABLE + "')" +
                        " AND (HR.hotel_ID, HR.room_number) = (HA.hotel_ID, HA.room_number) AND HA.amenity = '" + VAR_AMENITIES + "'" +
                    " AND ( " +
                        " NOT EXISTS" +
                            " (SELECT *" +
                            " FROM ExistingBookings as EB, Booking as B" +
                            " WHERE (HR.hotel_ID, HR.room_number) = (EB.hotel_ID, EB.room_number)" +
                                " AND EB.booking_ID = B.booking_ID" +
                                    " AND B.status = 'scheduled'" +
                                    " AND '" + VAR_START_DATE + "' <= B.end_date" +
                                    " AND '" + VAR_END_DATE + "' >= B.start_date" +
                            ")" +
                    ");");

            if (!rs.next()) {
                System.out.println("There were no available rooms that met that criteria");

            } else {
                roomResults = extractRoomsFromResultSet(rs);

            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return roomResults;
    }

    public void bookRoom(HotelRoom hotelRoom, Booking booking) {

        SQLDatabaseConnection db = SQLDatabaseConnection.getInstance();

        //execute the sql SELECT statement
        try {
            //first insert into Booking
            db.executeUpdate(
                    "INSERT INTO Booking(booking_ID, status, room_type, num_occupants, start_date, end_date)" +
                     " VALUES(default, 'scheduled', '" + booking.getRoomType() + "', '"+ booking.getNumOccupants() + "', '" + Helper.dateToString(booking.getStartDate()) + "', '" + Helper.dateToString(booking.getEndDate()) + "');"
            );

            //need this select statement to get the booking ID we just inserted
            ResultSet rs = db.executeQuery(
                    "SELECT max(booking_ID) AS b_ID FROM Booking"
            );

            rs.next();
            int b_ID = rs.getInt(1);
            //System.out.println("Hotel ID: " + hotel_ID);

            // Update booking Id for booking object
            booking.setBookingID(b_ID);

            //Now insert into BooksFor
            db.executeUpdate(
                    "INSERT INTO BooksFor(booking_ID, room_number, hotel_ID)" +
                            " VALUES(" + booking.getBookingID() + ", "+ hotelRoom.getRoomNumber() + ", " + hotelRoom.getHotelID() + ");"
            );

            // Insert CanCreate
            db.executeUpdate(
                    "INSERT INTO CanCreate(booking_ID, sin_number)" +
                            " VALUES(" + booking.getBookingID() + ", " + sinNumber + ");"
            );

            Helper.println("\n** The booking has been created. **");

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public List<Booking> getBookingsAfter(Date date) {

        SQLDatabaseConnection db = SQLDatabaseConnection.getInstance();
        List<Booking> bookingsList = new ArrayList<>();

        String afterDate = Vars.DATE_FORMAT.format(date);

        try {
            ResultSet rs = db.executeQuery("SELECT B.*" +
                    " FROM Booking as B, CanCreate as CC" +
                    " WHERE CC.sin_number = " + super.sinNumber +
                    " AND CC.booking_ID = B.booking_ID" +
                    " AND B.start_date >= " + "'" + afterDate + "'" +
                    " AND NOT EXISTS (SELECT *" +
                    " FROM TransformsInto as TI" +
                    " WHERE B.booking_ID = TI.booking_ID)" +
                    " ORDER BY B.start_date ASC, B.end_date ASC");

            bookingsList = extractBookingsFromResultSet(rs);
            return bookingsList;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return bookingsList;


    }

    public List<Booking> extractBookingsFromResultSet(ResultSet rs) throws SQLException {

        List<Booking> bookingsList = new ArrayList<>();

        if (!rs.next()) { // Query has no results
            return bookingsList;
        } else {

            do {
                int bookingID = rs.getInt("booking_ID");

                String status = rs.getString("status");
                String roomType = rs.getString("room_type");
                int numOccupants = rs.getInt("num_occupants");

                Date startDate = rs.getDate("start_date");
                Date endDate = rs.getDate("end_date");

                bookingsList.add(new Booking(bookingID, status, roomType, numOccupants, startDate, endDate));

            } while(rs.next());
        }

        return bookingsList;
    }

    public List<Booking> getBookingsFor(Date date) {

        SQLDatabaseConnection db = SQLDatabaseConnection.getInstance();
        List<Booking> bookingsList = new ArrayList<>();

        String tDate = Vars.DATE_FORMAT.format(date);

        try {
            ResultSet rs = db.executeQuery("SELECT B.*" +
                    " FROM Booking as B, CanCreate as CC" +
                    " WHERE CC.sin_number = " + super.sinNumber +
                    " AND CC.booking_ID = B.booking_ID" +
                    " AND B.start_date = " + "'" + tDate + "'" +
                    " AND NOT EXISTS (SELECT *" +
                                    " FROM TransformsInto as TI" +
                                    " WHERE B.booking_ID = TI.booking_ID)");

            bookingsList = extractBookingsFromResultSet(rs);
            return bookingsList;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return bookingsList;
    }

    public List<Renting> getUnpaidRentings(Date todaysDate) {

        SQLDatabaseConnection db = SQLDatabaseConnection.getInstance();
        List<Renting> rentingsList = new ArrayList<>();

        String tDate = Vars.DATE_FORMAT.format(todaysDate);

        try {
            ResultSet rs = db.executeQuery(
                    "SELECT BF.hotel_ID, BF.room_number, R.*" +
                    " FROM CanCreate as CC, TransformsInto as TI, BooksFor as BF, Booking as B, Renting as R" +
                    " WHERE CC.sin_number = " + sinNumber +
                    " AND CC.booking_ID = TI.booking_ID" +
                        " AND TI.booking_ID = B.booking_ID" +
                            " AND B.start_date >= '" + tDate + "'" +
                            " AND B.booking_ID = BF.booking_ID" +
                        " AND TI.renting_ID = R.renting_ID" +
                            " AND R.balance > 0.0"
            );

            if (!rs.next()) { // No unpaid rentings
                return rentingsList;

            } else { // At least 1 unpaid renting

                do {

                    int rentingID = rs.getInt("renting_ID");
                    String status = rs.getString("status");
                    double balance = rs.getDouble("balance");

                    rentingsList.add(new Renting(rentingID, status, balance));

                } while(rs.next());

            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return rentingsList;
    }
}

