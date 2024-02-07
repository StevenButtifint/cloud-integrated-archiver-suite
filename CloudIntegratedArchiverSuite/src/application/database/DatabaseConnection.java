package application.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {

    public Connection connectToDatabase(String databaseName, String username, String password) {
    	Connection connection=null;
    	try {
    		Class.forName("org.postgresql.Driver");
    		connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/"+databaseName, username, password);
    		if (connection!=null) {
    			System.out.println("Connected to " + databaseName);
    		} else {
    			System.out.println("Failed to connect to " + databaseName);
    		}
    	} catch (Exception e) {
    		System.out.println(e);
    	}
    	return connection;
    }
}