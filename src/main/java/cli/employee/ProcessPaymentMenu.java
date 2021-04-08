package cli.employee;

import cli.Menu;
import org.apache.commons.lang3.StringUtils;
import structs.booking.Booking;
import structs.booking.Payment;
import structs.booking.Renting;
import users.Customer;
import users.Employee;
import users.User;
import utils.Helper;
import utils.Messages;
import utils.Vars;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProcessPaymentMenu extends Menu {

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
                "\n" + StringUtils.center("Employee >> Process Customer Payment", Vars.DIVIDER_EQUALS.length()) +
                "\n" + Vars.DIVIDER_EQUALS);

        // Get Customer object to see their active bookings
        Helper.println("\nWhat is the customer's SIN number?");

        boolean FLAG = false;
        while (!FLAG) {
            String res = Helper.getInput(">> ");

            if (Helper.isValidSIN(res)) {
                c = Helper.getCustomerFromSIN(res);

                if (c != null) {
                    FLAG = true;
                    cliManager.setCustomer(c);

                } else { // Customer does not exist
                    Helper.println("\n⛔ Invalid entry - make sure the SIN exists and try again.");
                }

            } else { // Invalid entry
                Helper.println("\n⛔ Invalid entry - Make sure the SIN is an 8-digit number and try again.");

            }
        }

        Date todaysDate = cliManager.getCurrentDate();

        // Get Customer's unpaid Rentings
        boolean FLAG43 = false;
        while (!FLAG43) {
            ArrayList<Renting> unpaidRentings = (ArrayList<Renting>) c.getUnpaidRentings(todaysDate);

            if (unpaidRentings.size() >= 1) { // At least 1 unpaid renting
                printRentings(unpaidRentings);

                Helper.println("\nWhich renting would you like to pay for?");

                boolean FLAG2 = false;
                while (!FLAG2) {
                    // Get renting to pay for
                    String res = Helper.getInput(">> Renting ID: ");

                    if (Helper.isDigitsOnly(res)) {

                        int rentingID = Integer.parseInt(res);

                        Renting renting = unpaidRentings.stream()
                                .filter(r -> r.getRentingID() == rentingID)
                                .findFirst()
                                .orElse(null);

                        if (renting != null) {
                            FLAG2 = true;
                            // Print renting details
                            printRentingDetails(renting);

                            Helper.println("\nAre you sure you'd like to pay " + Helper.toCurrency(renting.getBalance()) + " to cover, " +
                                    "in full, the outstanding balance on this renting?" +
                                    "\n(Y)es" +
                                    "\n(N)o, show the unpaid rentings again.\n");

                            boolean FLAG3 = false;
                            while (!FLAG3) {

                                String res2 = Helper.getInput(">> ");

                                if (Helper.multiCheck(res2, Vars.YES_NO_OPTIONS)) {
                                    FLAG3 = true;

                                    if (res2.equalsIgnoreCase(Vars.YES_NO_OPTIONS[0])) { // Pay for renting

                                        Booking b = Booking.fromRenting(renting);

                                        assert b != null;
                                        Payment payment = new Payment(rentingID, null, b.getEndDate(), renting.getBalance(), todaysDate);

                                        Helper.println("\n⏳ Processing payment...");
                                        boolean success = e.insertCustomerPayment(renting, payment);

                                        if (success) {
                                            Helper.println("✅ The customer's payment was successfully processed.");

                                        } else {
                                            Helper.println("❌ An error occurred; payment processing failed.");

                                        }

                                    } else {// No, go back to unpaid rentings list

                                    }

                                    FLAG43 = false;

                                } else { // Invalid entry
                                    Helper.println(Messages.INVALID_ENTRY + "\n");

                                }

                            }

                        } else { // Renting ID is not part of Customer's list of unpaid rentings
                            Helper.println("\n⛔ Please select a renting ID from the customer's unpaid rentings.\n");
                        }

                    } else { // Invalid entry
                        Helper.println("\n⛔ Invalid entry - make sure the renting ID is only digits and try again.\n");
                    }

                }

            } else { // No unpaid rentings; return to main menu
                Helper.println("\nThe customer has no unpaid rentings." +
                        "\nReturning to main menu...");

                cliManager.prevMenu();

            }

        }


    }

    private void printRentings(List<Renting> rentings) {
        Helper.println("\n" + Vars.DIVIDER_DASH_LONG +
                "\n" + StringUtils.center("Customer's Unpaid Rentings", Vars.DIVIDER_DASH_LONG.length()) +
                "\n" + Vars.DIVIDER_DASH_LONG);

        Helper.print("\nYou have " + rentings.size() + " unpaid renting(s):\n");

        for (Renting r : rentings) {
            Helper.newLine(1);
            Helper.println(r.toStringWithTitle());
        }
    }

    private void printRentingDetails(Renting renting) {
        Helper.println("\n" + StringUtils.center("Renting Details", Vars.DIVIDER_DASH.length()) +
                "\n" + Vars.DIVIDER_DASH);
        Helper.println(renting.toString());
    }
}
