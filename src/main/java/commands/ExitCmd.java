package commands;

public class ExitCmd implements ICommand {
    private static final String sCommendName = "Exit";

    @Override
    public void execute() {
        System.exit(0);
    }

    @Override
    public String toString() {
        return sCommendName;
    }
}
