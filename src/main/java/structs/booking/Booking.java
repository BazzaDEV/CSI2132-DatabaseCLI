package structs.booking;

import database.SQLDatabaseConnection;
import structs.hotel.HotelRoom;
import utils.Vars;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class Booking {

    private int bookingID;
    private String status;
    private String roomType;
    private int numOccupants;
    private Date startDate;
    private Date endDate;

    public Booking(int bookingID, String status, String roomType, int numOccupants, Date startDate, Date endDate) {
        this.bookingID = bookingID;
        this.status = status;
        this.roomType = roomType;
        this.numOccupants = numOccupants;
        this.startDate = startDate;
        this.endDate = endDate;
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

    public int getBookingID() {
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
        return new HotelRoom(this);
    }
}
