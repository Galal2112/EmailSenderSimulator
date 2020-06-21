package commands;

import database.DatabaseBlock;
import database.DatabaseConnectionHandler;

public class InsertSeedCommand implements ICommand {
    @Override
    public void execute() {
        try
        {
            try
            {
                DatabaseBlock block = stmt -> {
                    for (int i = 1; i <= 1000_000; i++) {
                        System.out.print("\rInserting " + i + " of 1000_000");
                        stmt.executeUpdate( "INSERT INTO users ( name, email ) VALUES ( 'User" + i +"', 'user" + i+ "@test.de' )" );
                    }
                    System.out.println("\nData inserted");
                };
                DatabaseConnectionHandler.sharedInstance.executeBlock(block);
            }
            catch( Exception e )
            {
                System.err.println( e.getMessage() );
            }
        }
        catch( Exception e )
        {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public String toString() {
        return "Insert 1M test user as the database seed";
    }
}
