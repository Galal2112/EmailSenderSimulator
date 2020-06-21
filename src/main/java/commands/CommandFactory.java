package commands;

import cli.Console;

import java.util.ArrayList;

public abstract class CommandFactory {

    public static ArrayList<ICommand> createTestAppCommendList(Console console) {
        ArrayList<ICommand> list = new ArrayList<>();
        list.add(new CreateDatabaseCommand());
        list.add(new InsertSeedCommand());
        list.add(new PrintListCommand(console));
        list.add(new SendEmailsCommand());
        return list;
    }
}
