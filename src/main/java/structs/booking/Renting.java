package structs.booking;

import org.apache.commons.lang3.StringUtils;
import structs.hotel.HotelRoom;
import utils.Helper;
import utils.Vars;

public class Renting {

    private int rentingID;
    private String status;
    private double balance;

    private HotelRoom hotelRoom;

    /*****************************
     *       CONSTRUCTORS       *
     ****************************/

    public Renting(int rentingID, String status, double balance) {
        this.rentingID = rentingID;
        this.status = status;
        this.balance = balance;
        this.hotelRoom = HotelRoom.fromRenting(this);
    }

    /****************************
     *         METHODS         *
     ***************************/

    @Override
    public String toString() {
        StringBuilder strB = new StringBuilder();

        strB.append("Renting ID: ").append(rentingID)
                .append("\nStatus: ").append(status)
                .append("\nBalance: ").append(Helper.toCurrency(balance));

        return strB.toString();
    }


    public String toStringWithTitle() {
        StringBuilder strB = new StringBuilder();

        String title = "Hotel ID: " + hotelRoom.getHotelID() + " | Room Number: " + hotelRoom.getRoomNumber();
        String titleCentered = StringUtils.center(title, Vars.DIVIDER_DASH.length());

        strB.append(titleCentered)
                .append("\n" + Vars.DIVIDER_DASH)
                .append("\n").append(toString());

        return strB.toString();
    }

    /*****************************
     *         GETTERS         *
     ****************************/

    public int getRentingID() {
        return rentingID;
    }

    public String getStatus() {
        return status;
    }

    public double getBalance() {
        return balance;
    }

    public HotelRoom getHotelRoom() {
        return hotelRoom;
    }
}
