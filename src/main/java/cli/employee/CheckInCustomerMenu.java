package cli.employee;

import cli.Menu;
import org.apache.commons.lang3.StringUtils;
import structs.booking.Booking;
import users.Customer;
import users.Employee;
import users.User;
import utils.Helper;
import utils.Vars;

import java.util.ArrayList;
import java.util.List;

public class CheckInCustomerMenu extends Menu {

    private Employee e;
    private Customer c;

    @Override
    public void start() {

        User u = cliManager.getUser();

        if (u instanceof Employee) {
            e = (Employee) u;
        }

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
        List<Booking> bookingsList = c.getBookings(cliManager.getCurrentDate());

        if (bookingsList.size() >= 1) { // Customer has at least 1 booking for today

            boolean FLAG2 = false;
            while (!FLAG2) {
                // Print customer's bookings for today
                printCustomerBookings(bookingsList);

                // Get booking to check in
                Helper.println("\nWhich booking would you like to check in?");
                String res = Helper.getInput(">> Booking ID: ");

                if (Helper.isDigitsOnly(res)) {

                    int bookingID = Integer.parseInt(res);

                    Booking booking = bookingsList.stream()
                            .filter(b -> b.getBookingID() == bookingID)
                            .findFirst()
                            .orElse(null);

                    if (booking != null) {

                        // Print selected booking details
                        printBookingDetails(booking);

                        // Get confirmation to check in this booking
                        Helper.println("\nAre you sure you want to check in this booking? (Y/N)");

                        boolean FLAG3 = false;
                        while (!FLAG3) {
                            String checkIn = Helper.getInput(">> ");

                            if (Helper.multiCheck(checkIn, new String[]{"Y", "N"})) {
                                FLAG3 = true;

                                if (checkIn.equalsIgnoreCase("Y")) { // Transform booking into renting
//                                    boolean success = e.checkInCustomer(booking);
                                    boolean success = true;

                                    if (success) { // Succesfully checked in customer
                                        Helper.println("\n** The customer has been successfully checked in. **");

                                    } else { // Unsuccessful check in
                                        Helper.println("\nAn error occurred while checking in the customer.");
                                    }

                                    cliManager.prevMenu();

                                } else if (checkIn.equalsIgnoreCase("N")){ // Cancel check in; show bookings again

                                }

                            } else { // Invalid entry
                                Helper.println("\nInvalid entry - enter (Y)es or (N)o and try again.\n");

                            }
                        }

                    } else { // Booking ID is invalid, not part of the Customer's bookings for today
                        Helper.println("\nPlease select a booking ID from the customer's bookings.\n");

                    }

                } else { // Invalid entry
                    Helper.println("\nInvalid entry - make sure the booking ID is only digits and try again.\n");
                }

            }

        } else { // Customer has no bookings for today

            Helper.println("The customer does not have a booking for today." +
                    "\nWould you like to:" +
                    "(1) Directly find and rent them a room" +
                    "(2) Return to main menu");

            boolean FLAG4 = false;
            while (!FLAG4) {

                String res = Helper.getInput("\n>> ");

                if (Helper.isValid(res, 2)) {

                    if (res.equalsIgnoreCase("1")) { // Directly find and rent a room

                    } else if (res.equalsIgnoreCase("2")) { // Go back to main menu
                        cliManager.popMenu();

                    }


                } else { // Invalid entry
                    Helper.println("\nInvalid entry - try again.");

                }

            }

        }

    }

    private void printCustomerBookings(List<Booking> bookingsList) {
        Helper.println("\n" + Vars.DIVIDER_DASH_LONG +
                "\n" + StringUtils.center("Customer's Bookings for Today", Vars.DIVIDER_DASH_LONG.length()) +
                "\n" + Vars.DIVIDER_DASH_LONG);

        for (Booking b : bookingsList) {
            Helper.newLine(1);
            Helper.println(b.toString());
        }
    }

    private void printBookingDetails(Booking booking) {
        Helper.println("\n" + StringUtils.center("Booking Details", Vars.DIVIDER_DASH.length()) +
                "\n" + Vars.DIVIDER_DASH);
        Helper.println(booking.toString());
    }
}
