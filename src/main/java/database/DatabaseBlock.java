package database;

import java.sql.Statement;

public interface DatabaseBlock {
    void execute(Statement stmt) throws Exception;
}
