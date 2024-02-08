package application.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {
	
	private static String LINK_TABLE_NAME  = "links";

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
    public void createLinkTable(Connection connection) { 
    	Statement statement;
    	try {
    		String query = "CREATE TABLE " + LINK_TABLE_NAME + "(empid SERIAL, name TEXT, description TEXT, source_location TEXT, destination_location TEXT, created_date DATE, last_synced DATE, accessible_state BOOLEAN, primary key(empid));";
    		statement = connection.createStatement();
    		statement.executeUpdate(query);
    		System.out.println("table created");
    	} catch (Exception e) {
    		System.out.println(e);
    	}
    }
    
    public void insertLink(Connection connection, String name, String description, String source_location, String destination_location) {
    
