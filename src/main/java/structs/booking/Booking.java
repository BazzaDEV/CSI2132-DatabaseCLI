package structs.booking;

import database.SQLDatabaseConnection;
import org.apache.commons.lang3.StringUtils;
import structs.hotel.Hotel;
import structs.hotel.HotelRoom;
import utils.Helper;
import utils.Vars;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class Booking {

    private Integer bookingID;
    private String status;
    private String roomType;
    private int numOccupants;
    private Date startDate;
    private Date endDate;

    public Booking(Integer bookingID, String status, String roomType, int numOccupants, Date startDate, Date endDate) {
        this.bookingID = bookingID;
        this.status = status;
        this.roomType = roomType;
        this.numOccupants = numOccupants;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public static Booking fromRenting(Renting r) {

        try {
            return fromRenting(r.getRentingID());

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return null;

    }

    public static Booking fromRenting(int rentingID) throws SQLException {

        SQLDatabaseConnection db = SQLDatabaseConnection.getInstance();

        ResultSet rs = db.executeQuery(
                "SELECT B.*" +
                        " FROM Renting as R, TransformsInto as TI, Booking as B" +
                        " WHERE R.renting_ID = " + rentingID +
                        " AND R.renting_ID = TI.renting_ID" +
                            " AND TI.booking_ID = B.booking_ID"
        );

        if (!rs.next()) {
            return null;

        } else {

            int bookingID = rs.getInt("booking_ID");

            String status = rs.getString("status");
            String roomType = rs.getString("room_type");
            int numOccupants = rs.getInt("num_occupants");

            Date startDate = rs.getDate("start_date");
            Date endDate = rs.getDate("end_date");

            return new Booking(bookingID, status, roomType, numOccupants, startDate, endDate);

        }

    }

    @Override
    public String toString() {
        StringBuilder strB = new StringBuilder();

        strB.append("Booking ID: ").append(bookingID)
            .append("\nStatus: ").append(status)
            .append("\nRoom Type: ").append(roomType)
            .append("\nNumber of Occupants: ").append(numOccupants)
            .append("\nDuration: ").append(Vars.DATE_FORMAT.format(startDate)).append(" -> ").append(Vars.DATE_FORMAT.format(endDate));

        return strB.toString().trim();
    }

    public String toStringAlt() {
        StringBuilder strB = new StringBuilder();

        HotelRoom hotelRoom = HotelRoom.fromBooking(this);
        Hotel hotel = Hotel.fromBooking(this);

        String title = Helper.dateToString(startDate) + " -> " + Helper.dateToString(endDate);
        String titleCentered = StringUtils.center(title, Vars.DIVIDER_DASH.length());

        assert hotel != null;
        assert hotelRoom != null;

        String city = hotel.getAddress().getCity();
        String state = hotel.getAddress().getState();

        strB.append(titleCentered)
                .append("\n" + Vars.DIVIDER_DASH)
                .append("\nBooking ID: ").append(bookingID)
                .append("\nHotel ID: ").append(hotel.getHotelID())
                .append("\nRoom Number: ").append(hotelRoom.getRoomNumber())
                .append("\nLocation: ").append(city).append(", ").append(state);


        return strB.toString();

    }

    public String toStringAlt2() {
        StringBuilder strB = new StringBuilder();

        HotelRoom hotelRoom = HotelRoom.fromBooking(this);

        String title = "Booking ID: " + bookingID;
        String titleCentered = StringUtils.center(title, Vars.DIVIDER_DASH.length());

        assert hotelRoom != null;

        strB.append(titleCentered)
                .append("\n" + Vars.DIVIDER_DASH)
                .append("\nRoom Number: ").append(hotelRoom.getRoomNumber())
                .append("\nRoom Type: ").append(roomType)
                .append("\nNumber of Occupants: ").append(numOccupants)
                .append("\nDuration: ").append(Vars.DATE_FORMAT.format(startDate)).append(" -> ").append(Vars.DATE_FORMAT.format(endDate));

        return strB.toString();
    }

    public void setBookingID(int bookingID) {
        this.bookingID = bookingID;
    }

    public Integer getBookingID() {
        return bookingID;
    }

    public String getStatus() {
        return status;
    }

    public String getRoomType() {
        return roomType;
    }

    public int getNumOccupants() {
        return numOccupants;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public HotelRoom getHotelRoom() {
        return HotelRoom.fromBooking(this);
    }
}
