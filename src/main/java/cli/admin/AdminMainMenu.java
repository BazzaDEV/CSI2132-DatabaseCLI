package cli.admin;

import cli.Menu;
import org.apache.commons.lang3.StringUtils;
import utils.Helper;
import utils.Vars;

public class AdminMainMenu extends Menu {

    @Override
    public void start() {

        Helper.println("\n" + Vars.DIVIDER_EQUALS +
                "\n" + StringUtils.center("Database Admin - Main Menu", Vars.DIVIDER_EQUALS.length()) +
                "\n" + Vars.DIVIDER_EQUALS);

        Helper.println("\nWelcome back, Admin!");

    }

}
