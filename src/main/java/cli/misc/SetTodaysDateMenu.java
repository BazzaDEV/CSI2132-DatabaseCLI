package cli.misc;

import cli.Menu;
import org.apache.commons.lang3.StringUtils;
import users.Employee;
import utils.Emoji;
import utils.Helper;
import utils.Messages;
import utils.Vars;

import java.util.Date;

public class SetTodaysDateMenu extends Menu {

    @Override
    public void start() {

        if (cliManager.getUser() instanceof Employee) {
            Helper.println("\n" + Vars.DIVIDER_EQUALS +
                    "\n" + StringUtils.center("DEBUG: Set Today's Date", Vars.DIVIDER_EQUALS.length()) +
                    "\n" + Vars.DIVIDER_EQUALS);

            Helper.println("\nEnter today's date in YYYY-MM-DD format: ");

            boolean FLAG = false;
            while(!FLAG) {
                String input = Helper.getInput(">> ");
                Date newDate = Helper.stringToDate(input);

                if (newDate != null) {
                    FLAG = true;
                    cliManager.setCurrentDate(newDate);

                    Helper.println("\n\n" + Vars.DIVIDER_ASTERICK_LONG);
                    Helper.println(StringUtils.center(" Today's date has been set to:", Vars.DIVIDER_ASTERICK_LONG.length() - 2));
                    Helper.println(StringUtils.center(Emoji.CALENDAR + " " + Vars.DATE_FORMAT.format(cliManager.getCurrentDate()) + " " + Emoji.CALENDAR, Vars.DIVIDER_ASTERICK_LONG.length() - 2));
                    Helper.println(Vars.DIVIDER_ASTERICK_LONG + "\n");

                } else {
                    Helper.println(Messages.INVALID_ENTRY);
                }

            }
        }

        cliManager.prevMenu();


    }
}
