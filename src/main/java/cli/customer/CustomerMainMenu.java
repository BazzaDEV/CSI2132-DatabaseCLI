package cli.customer;

import cli.Menu;
import org.apache.commons.lang3.StringUtils;
import users.Customer;
import utils.Helper;
import utils.Vars;

public class CustomerMainMenu extends Menu {

    private Customer c;

    @Override
    public void start() {

        c = (Customer) cliManager.getUser();

        Helper.println("\n" + Vars.DIVIDER_EQUALS +
                        "\n" + StringUtils.center("Customer Main Menu", Vars.DIVIDER_EQUALS.length()) +
                        "\n" + Vars.DIVIDER_EQUALS);
        Helper.println("\nWelcome back, " + c.getName().getFirstName() + "!");

        Helper.println("\nCustomer info:\n\n" +
                c.toString());

    }
}
