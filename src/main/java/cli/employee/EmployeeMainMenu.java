package cli.employee;

import cli.Menu;
import org.apache.commons.lang3.StringUtils;
import users.Admin;
import users.Employee;
import users.User;
import utils.Helper;
import utils.Vars;

public class EmployeeMainMenu extends Menu {

    private Employee e;

    @Override
    public void start() {

        User u = cliManager.getUser();

        if (u instanceof Employee) {
            e = (Employee) u;
        }

        Helper.println("\n" + Vars.DIVIDER_EQUALS +
                "\n" + StringUtils.center("Employee Main Menu", Vars.DIVIDER_EQUALS.length()) +
                "\n" + Vars.DIVIDER_EQUALS);
        Helper.println("\nWelcome back, " + e.getName().getFirstName() + "!");

//        Helper.println("\nEmployee info:\n\n" +
//                e.toString());

        boolean FLAG = false;

        while (!FLAG) {

            String res = Helper.getInput("\nWhat would you like to do?" +
                    "\n(1) View Room Availability" +
                    "\n(2) Check In Customer" +
                    "\n(3) Sign Out" +
                    "\n>> ");

            if (Helper.isValid(res, 3)) { // Valid input

                FLAG = true;

                if (res.equalsIgnoreCase("1")) { // View room availability

                    cliManager.loadMenu(new RoomSearchMenu());


                } else if (res.equalsIgnoreCase("2")) { // Check in a customer

                    cliManager.loadMenu(new CheckInCustomerMenu());

                } else if (res.equalsIgnoreCase("3")) { // Sign out

                    cliManager.prevMenu();

                }

            } else { // Invalid entry
                Helper.println("\nInvalid entry - try again.");

            }


        }

    }
}
