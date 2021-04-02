import cli.CLIManager;
import utils.Helper;

import java.nio.file.Paths;

public class Main {

    public static void main(String[] args) {
//        Helper.print(Paths.get(".").toAbsolutePath().normalize().toString());

        CLIManager cliManager = CLIManager.getInstance();

        if (args.length == 0) {
            cliManager.startCLI();

        } else if (args.length == 2) {
            cliManager.startCLI(args[0], args[1]);
        }


    }
}
