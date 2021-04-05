package cli.customer;

import cli.Menu;
import org.apache.commons.lang3.StringUtils;
import structs.booking.Booking;
import users.Customer;
import users.Employee;
import users.User;
import utils.Helper;
import utils.Vars;

import java.util.ArrayList;
import java.util.Date;

public class ViewBookingsMenu extends Menu {

    private Customer c;

    @Override
    public void start() {

        User u = cliManager.getUser();

        if (u instanceof Customer) {
            c = (Customer) u;
        }

        Helper.println("\n" + Vars.DIVIDER_EQUALS +
                "\n" + StringUtils.center("Customer >> View Upcoming Bookings", Vars.DIVIDER_EQUALS.length()) +
                "\n" + Vars.DIVIDER_EQUALS);

        Date todaysDate = cliManager.getCurrentDate();

        ArrayList<Booking> bookings = (ArrayList<Booking>) c.getBookingsAfter(todaysDate);
        int numBookings = bookings.size();

        if (numBookings >= 1) {

            Helper.println("\nYou have " + numBookings + " upcoming booking(s): ");

            for (Booking b : bookings) {
                Helper.newLine(1);
                Helper.println(b.toStringAlt());
            }
        } else {
            Helper.println("\nYou have no upcoming bookings." +
                    "\nTaking you back to the main menu...");

        }

        cliManager.prevMenu();

    }
}
