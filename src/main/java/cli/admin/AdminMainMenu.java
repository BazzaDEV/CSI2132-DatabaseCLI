package cli.admin;

import cli.Menu;
import org.apache.commons.lang3.StringUtils;
import users.Admin;
import users.User;
import utils.Helper;
import utils.Vars;

public class AdminMainMenu extends Menu {

    private Admin a;

    @Override
    public void start() {

        User u = cliManager.getUser();

        if (u instanceof Admin) {
            a = (Admin) u;
        }

        Helper.println("\n" + Vars.DIVIDER_EQUALS +
                "\n" + StringUtils.center("Database Admin - Main Menu", Vars.DIVIDER_EQUALS.length()) +
                "\n" + Vars.DIVIDER_EQUALS);

        Helper.println("\nWelcome back, " + a.getName().getFirstName() + "!");

        Helper.println("\nEmployee info:\n\n" +
                a.toString());

    }

}
