package users;

import database.SQLDatabaseConnection;
import structs.Address;
import structs.Name;
import structs.booking.Booking;
import sun.security.acl.AclImpl;
import utils.Vars;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    public List<Booking> getBookings(Date todaysDate) {

        SQLDatabaseConnection db = SQLDatabaseConnection.getInstance();

        String tDate = Vars.DATE_FORMAT.format(todaysDate);

        try {
            ResultSet rs = db.executeQuery("SELECT B.*" +
                    " FROM Booking as B, CanCreate as CC" +
                    " WHERE CC.sin_number = " + super.sinNumber +
                    " AND CC.booking_ID = B.booking_ID" +
                    " AND B.start_date = " + "'" + tDate + "'");

            if (!rs.next()) { // Query has no results
                return null;

            } else {
                List<Booking> bookingsList = new ArrayList<>();

                do {
                    int bookingID = rs.getInt("booking_ID");

                    String status = rs.getString("status");
                    String roomType = rs.getString("room_type");
                    int numOccupants = rs.getInt("num_occupants");

                    Date startDate = rs.getDate("start_date");
                    Date endDate = rs.getDate("end_date");

                    bookingsList.add(new Booking(bookingID, status, roomType, numOccupants, startDate, endDate));

                } while(rs.next());

                return bookingsList;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }
}
