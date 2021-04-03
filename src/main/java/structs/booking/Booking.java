package structs.booking;

import utils.Vars;

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
}
