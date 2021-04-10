import cli.CLIManager;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import utils.Helper;
import utils.Vars;

import java.io.IOException;
import java.nio.file.Paths;

public class Main {

    public static void main(String[] args) {
//        Helper.print(Paths.get(".").toAbsolutePath().normalize().toString());

        CLIManager cliManager = CLIManager.getInstance();

        if (args.length == 1) {
            cliManager.startCLI(args[0]);

        } else if (args.length == 3) {
            cliManager.startCLI(args[0], args[1], args[2]);

        } else {
            Helper.println("Please enter one of the following sets of information for the CLI arguments:" +
                    "\n(1) TODAYS_DATE" +
                    "\n(2) TODAYS_DATE USER_TYPE SIN_NUMBER" +
                    "\n\nEnter TODAYS_DATE in the format " + Vars.DATE_FORMAT.toPattern() +
                    "\nEnter USER_TYPE as (C)ustomer, (E)mployee, or Database (A)dministrator");
        }


    }
}
