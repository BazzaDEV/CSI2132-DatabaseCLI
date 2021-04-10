package users;

import database.SQLDatabaseConnection;
import org.apache.commons.lang3.StringUtils;
import structs.Address;
import structs.Name;
import structs.Pair;
import structs.booking.Booking;
import structs.booking.Payment;
import structs.booking.Renting;
import structs.hotel.HotelRoom;
import utils.Helper;
import utils.Vars;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    public List<HotelRoom> getRooms(String VAR_START_DATE, String VAR_END_DATE, double VAR_MIN_PRICE, double VAR_MAX_PRICE, String VAR_VIEW, String VAR_ROOM_CAPACITY, String VAR_IS_EXTENDABLE, String VAR_AMENITIES) {
        SQLDatabaseConnection db = SQLDatabaseConnection.getInstance();
        List<HotelRoom> roomResults = new ArrayList<>();

        // Helper.println(VAR_START_DATE + "\n" + VAR_END_DATE + "\n" + VAR_MIN_PRICE + "\n" + VAR_MAX_PRICE + "\n" + VAR_VIEW + "\n" + VAR_ROOM_CAPACITY + "\n" + VAR_IS_EXTENDABLE + "\n" + VAR_AMENITIES);

        try {
            ResultSet rs = db.executeQuery(
                    "WITH HotelRoom_EMP(room_number, hotel_ID, price, room_capacity, view, is_extendable, room_status) AS" +
                        " (SELECT HR.*" +
                        " FROM HotelRoom as HR, WorksFor as WF" +
                        " WHERE HR.hotel_ID = WF.hotel_ID" +
                            " AND WF.sin_number = " + super.sinNumber + ")," +

                    " ExistingBookings(hotel_ID, room_number, booking_ID) AS" +
                        " (SELECT HR.hotel_ID, HR.room_number, B.booking_ID" +
                        " FROM Hotel as H, HotelRoom_EMP as HR, BooksFor as BF, Booking as B" +
                        " WHERE H.hotel_ID = HR.hotel_ID" +
                            " AND (HR.hotel_ID, HR.room_number) = (BF.hotel_ID, BF.room_number)" +
                                " AND BF.booking_ID = B.booking_ID)" +

                    " SELECT DISTINCT HR.*, HA.amenity, H.star_category, H.city, H.state_name, H.zip, H.email_address" +
                    " FROM Hotel as H, HotelRoom_EMP as HR, HotelRoomAmenities as HA" +
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
                System.out.println("\nThere were no available rooms that met that criteria");

            } else {
                roomResults = extractRoomsFromResultSet(rs);
            }



        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return roomResults;

    }

    public boolean checkInCustomer(Booking b) {

        SQLDatabaseConnection db = SQLDatabaseConnection.getInstance();

        HotelRoom hotelRoom = HotelRoom.fromBooking(b);

        try {

            db.executeUpdate(
                    "INSERT INTO Renting(renting_id, status, balance)" +
                    " VALUES (DEFAULT, 'active', " + hotelRoom.getPrice() + ");"
            );

            // Get renting ID for Renting we just created
            ResultSet rs = db.executeQuery(
                    "SELECT MAX(renting_ID) as r_ID" +
                    " FROM Renting;");

            rs.next();
            int r_ID = rs.getInt(1);

            db.executeUpdate(
                    "INSERT INTO TransformsInto(booking_ID, renting_ID)" +
                            " VALUES (" + b.getBookingID() + ", " + r_ID + ");"
            );

            db.executeUpdate(
                    "UPDATE HotelRoom" +
                    " SET room_status = 'occupied'" +
                    " WHERE hotel_ID = " + hotelRoom.getHotelID() +
                      " AND room_number = " + hotelRoom.getRoomNumber() + ";"
            );

            return true;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }

    }

    public boolean insertCustomerPayment(Renting renting, Payment payment) {

        SQLDatabaseConnection db = SQLDatabaseConnection.getInstance();

        try {

            db.executeUpdate(
                    "INSERT INTO Payment(renting_ID, transaction_ID, due_date, amount, received_on)" +
                            " VALUES (" + renting.getRentingID() + ", " +
                                "default, " +
                                "'" + Helper.dateToString(payment.getDueDate()) + "', " +
                                payment.getAmount() + ", " +
                                "'" + Helper.dateToString(payment.getReceivedOn()) + "');"
            );

            // Renting balance will update using a trigger


            return true;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }

    }

}
