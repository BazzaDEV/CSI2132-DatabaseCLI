import cli.CLIManager;
import utils.Helper;

import java.nio.file.Paths;

public class Main {

    public static void main(String[] args) {
//        Helper.print(Paths.get(".").toAbsolutePath().normalize().toString());

        CLIManager cliManager = CLIManager.getInstance();
        cliManager.startCLI();
    }
}
