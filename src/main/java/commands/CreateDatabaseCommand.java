package commands;

import database.DatabaseBlock;
import database.DatabaseConnectionHandler;

public class CreateDatabaseCommand implements ICommand {
    @Override
    public void execute() {
        try
        {
            DatabaseBlock block = stmt -> {
                stmt.executeUpdate("DROP TABLE users");
                // the max lenght for a email is 254 characters,
                stmt.executeUpdate( "CREATE TABLE users (id IDENTITY NOT NULL PRIMARY KEY, name VARCHAR(50), email VARCHAR(254))" );
            };
            DatabaseConnectionHandler.sharedInstance.executeBlock(block);
            System.out.println("Database created successfully");
        }
        catch( Exception e )
        {
            System.err.println( e.getMessage() );
        }
    }

    @Override
    public String toString() {
        return "Create users database";
    }
}
