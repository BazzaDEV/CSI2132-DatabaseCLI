package cli.employee;

import cli.Menu;
import org.apache.commons.lang3.StringUtils;
import utils.Helper;
import utils.Vars;

public class CheckInCustomerMenu extends Menu {

    @Override
    public void start() {

        Helper.println("\n" + Vars.DIVIDER_EQUALS +
                "\n" + StringUtils.center("Employee >> Check In Customer", Vars.DIVIDER_EQUALS.length()) +
                "\n" + Vars.DIVIDER_EQUALS);

    }
}
