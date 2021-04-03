package cli.employee;

import cli.Menu;
import org.apache.commons.lang3.StringUtils;
import structs.booking.Booking;
import users.Customer;
import utils.Helper;
import utils.Vars;

import java.util.List;

public class CheckInCustomerMenu extends Menu {

    private Customer c;

    @Override
    public void start() {

        c = null;

        Helper.println("\n" + Vars.DIVIDER_EQUALS +
                "\n" + StringUtils.center("Employee >> Check In Customer", Vars.DIVIDER_EQUALS.length()) +
                "\n" + Vars.DIVIDER_EQUALS);

        // Get customer object for customer checking in
        Helper.println("\nWhat is the customer's SIN number?");

        boolean FLAG = false;
        while (!FLAG) {
            String res = Helper.getInput(">> ");

            if (Helper.isValidSIN(res)) {
                c = Helper.getCustomerFromSIN(res);

                if (c != null) {
                    FLAG = true;

                } else { // Customer does not exist
                    Helper.println("\nInvalid entry - make sure the SIN exists and try again.");
                }

            } else { // Invalid entry
                Helper.println("\nInvalid entry - Make sure the SIN is an 8-digit number and try again.");

            }
        }

        // Get Customer's bookings
        List<Booking> bookings = c.getBookings(cliManager.getCurrentDate());

        if (bookings.size() >= 1) { // Customer has at least 1 booking for today

            Helper.println("\n" + Vars.DIVIDER_DASH_LONG +
                    "\n" + StringUtils.center("Customer's Bookings for Today", Vars.DIVIDER_DASH_LONG.length()) +
                    "\n" + Vars.DIVIDER_DASH_LONG);

            for (Booking b : bookings) {
                Helper.newLine(1);
                Helper.println(b.toString());
            }




        } else { // Customer has no bookings for today

        }




    }
}
