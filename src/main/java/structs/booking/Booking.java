package structs.booking;

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
}
