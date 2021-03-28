package cli;

public abstract class Menu {

    protected final CLIManager cliManager;

    public Menu() {
        this.cliManager = CLIManager.getInstance();
    }

    public abstract void start();
}
