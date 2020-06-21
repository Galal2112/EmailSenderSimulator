package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DatabaseConnectionHandler {
    public static DatabaseConnectionHandler sharedInstance = new DatabaseConnectionHandler();

    private DatabaseConnectionHandler() { }

    public void executeBlock(DatabaseBlock block) throws Exception {
        Class.forName("org.h2.Driver");
        Connection con = DriverManager.getConnection("jdbc:h2:~/Users", "galal", "123" );
        Statement stmt = con.createStatement();
        block.execute(stmt);
        stmt.close();
        con.close();
    }
}
