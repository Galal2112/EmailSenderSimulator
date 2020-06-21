package commands;

import cli.Console;
import java.util.ArrayList;

public class CommandInvoker {
    private final transient ArrayList<ICommand> commands;
    private final transient Console console;
    private final transient String headline;
    private final ExitCmd exitCmd = new ExitCmd();

    public CommandInvoker(ArrayList<ICommand> commands, Console console, String programHeadline) {
        this.commands = commands;
        this.console = console;
        this.headline = programHeadline;
    }

    public void run() {
        do {
            System.out.println(headline + System.lineSeparator());
            printCommandLineMenu();
            ICommand cmd = selectCommandByCommandLine();
            System.out.println(System.lineSeparator() + ">>> " + cmd + " <<<" + System.lineSeparator());
            cmd.execute();
            System.out.println(System.lineSeparator());
        } while (true);
    }

    private void printCommandLineMenu() {
        for (int i = 0; i < commands.size(); i++) {
            printCommandLine(i);
        }
        System.out.println(" " + 0 + ". " + exitCmd);
    }

    private void printCommandLine(int i) {
        System.out.println(" " + (i + 1) + ". " + commands.get(i));
    }

    private ICommand selectCommandByCommandLine() {
        do {
            int index = console.readIntegerFromStdin(System.lineSeparator() + "Please enter an option: ");
            if (isValidOption(index, 0, commands.size())) {
                if (index == 0) {
                    return exitCmd;
                }
                return commands.get(index - 1);
            }
        } while (true);
    }

    private boolean isValidOption(int x, int min, int max) {
        return x >= min && x <= max;
    }
}
