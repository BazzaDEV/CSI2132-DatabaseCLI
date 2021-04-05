package users;

import org.apache.commons.lang3.StringUtils;
import structs.Address;
import structs.Name;
import structs.hotel.HotelRoom;
import utils.Helper;
import utils.Vars;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class User {

    protected int sinNumber;
    protected Name name;
    protected Address address;

    public User(int sinNumber, Name name, Address address) {
        this.sinNumber = sinNumber;
        this.name = name;
        this.address = address;
    }

    public int getSinNumber() {
        return sinNumber;
    }

    public Name getName() {
        return name;
    }

    public Address getAddress() {
        return address;
    }

    @Override
    public String toString() {
        StringBuilder strB = new StringBuilder();

        strB.append("SIN: ").append(sinNumber).append("\n")
                .append("Name: ").append(name).append("\n")
                .append("Address: ").append(address).append("\n");

        return strB.toString().trim();
    }

    public static List<HotelRoom> extractRoomsFromResultSet(ResultSet rs) throws SQLException {
        List<HotelRoom> roomResults = new ArrayList<>();

        do {
            //hotel room ID
            int hotel_ID = rs.getInt("hotel_ID");
            int room_number = rs.getInt("room_number");

            Helper.newLine(1);
            String resTitle = "Hotel ID: " + hotel_ID + ", Room Number: " + room_number;
            String resTitleCentered = StringUtils.center(resTitle, Vars.DIVIDER_DASH.length());
            System.out.println(resTitleCentered);
            Helper.println(Vars.DIVIDER_DASH);

            //user selected info
            System.out.println("\n* Hotel Room Info *");

            double price = rs.getDouble("price");
            System.out.println("Price: " + price);

            String view = rs.getString("view");
            System.out.println("View: " + view);

            String room_capacity = rs.getString("room_capacity");
            System.out.println("Room Capacity: " + room_capacity);

            boolean is_extendable = rs.getBoolean("is_extendable");
            System.out.println("Room is extendable: " + is_extendable);

            String room_status = rs.getString("room_status");

            String amenity = rs.getString("amenity");
            System.out.println("Amenity: " + amenity + "\n");

            //Hotel info
            System.out.println("* Hotel Information *");

            String star_category = rs.getString("star_category");
            System.out.println("Star Category: " + star_category);

            String city = rs.getString("city");
            System.out.println("City: " + city);

            String state_name = rs.getString("state_name");
            System.out.println("State Name: " + state_name);

            String zip = rs.getString("zip");
            System.out.println("Zip: " + zip);

            String email_address = rs.getString("email_address");
            System.out.println("Email if you have any inquires: " + email_address + "\n");

            roomResults.add(new HotelRoom(room_number, hotel_ID, price, room_capacity, view, is_extendable, room_status));

        } while (rs.next());

        Helper.println(Vars.DIVIDER_EQUALS);

        return roomResults;
    }
}
