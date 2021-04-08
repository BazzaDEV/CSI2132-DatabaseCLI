package cli.customer;


import cli.Menu;
import cli.misc.RoomSearchMenu;
import org.apache.commons.lang3.StringUtils;
import users.Customer;
import users.User;
import utils.Helper;
import utils.Messages;
import utils.Vars;

public class CustomerMainMenu extends Menu {

    private Customer c;

    @Override
    public void start() {

        User u = cliManager.getUser();

        if (u instanceof Customer) {
            c = (Customer) u;
        }

        boolean FLAG2 = false;
        while (!FLAG2) {
            Helper.println("\n" + Vars.DIVIDER_EQUALS +
                    "\n" + StringUtils.center("Customer Main Menu", Vars.DIVIDER_EQUALS.length()) +
                    "\n" + Vars.DIVIDER_EQUALS);
            Helper.println("\nWelcome back, " + c.getName().getFirstName() + "!");

            boolean FLAG = false;
            while (!FLAG) {

                String res = Helper.getInput("\nWhat would you like to do?" +
                        "\n(1) Book A Room" +
                        "\n(2) View Upcoming Bookings" +
                        "\n(3) Sign Out" +
                        "\n>> ");

                if (Helper.isValid(res, 3)) { // Valid input
                    FLAG = true;

                    if (res.equalsIgnoreCase("1")) { // Book a room
                        cliManager.loadMenu(new RoomSearchMenu(RoomSearchMenu.SearchOption.CUSTOMER));

                    } else if (res.equalsIgnoreCase("2")) { // View Customer's upcoming bookings
                        cliManager.loadMenu(new ViewBookingsMenu());

                    } else if (res.equalsIgnoreCase("3")) { // Sign out
                        cliManager.prevMenu();

                    }

                } else { // Invalid entry
                    Helper.println(Messages.INVALID_ENTRY);

                }
            }
        }

    }

}
