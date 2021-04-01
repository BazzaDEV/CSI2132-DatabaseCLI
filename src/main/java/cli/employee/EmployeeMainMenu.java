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

        Helper.println("\nEmployee info:\n\n" +
                        e.toString());

    }
}
