package commands;

import cli.Console;
import database.DatabaseBlock;
import database.DatabaseConnectionHandler;

import java.sql.ResultSet;

public class PrintListCommand implements ICommand {
    protected final Console console;

    public PrintListCommand(Console c) {
        this.console = c;
    }

    @Override
    public void execute() {
        int limit = console.readIntegerFromStdin("Please insert the limit: ");
        try
        {
            DatabaseBlock block = stmt -> {
                ResultSet rs = stmt.executeQuery("SELECT * FROM users LIMIT " + limit);
                while( rs.next() )
                {
                    long id = rs.getLong("id");
                    String name = rs.getString("name");
                    String email = rs.getString("email");

                    System.out.println("User {Id: "+ id + ", Name: " + name + ", Email: " + email + "}");
                }
            };
            DatabaseConnectionHandler.sharedInstance.executeBlock(block);
        }
        catch( Exception e )
        {
            System.err.println( e.getMessage() );
        }
    }

    @Override
    public String toString() {
        return "Print users list";
    }
}
