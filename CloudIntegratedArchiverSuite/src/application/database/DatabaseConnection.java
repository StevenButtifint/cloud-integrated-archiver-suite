package application.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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
    	try {    		
            String query = "INSERT INTO " + LINK_TABLE_NAME + " (name, description, source_location, destination_location, created_date, last_synced, accessible_state) VALUES (?, ?, ?, ?, CURRENT_DATE, CURRENT_DATE, ?)";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                    statement.setString(1, name);
                    statement.setString(2, description);
                    statement.setString(3, source_location);
                    statement.setString(4, destination_location);
                    statement.setBoolean(5, true);

                    int rowsInserted = statement.executeUpdate();
                    if (rowsInserted > 0) {
                        System.out.println("Added New Link");
                    } else {
                        System.out.println("Failed to add new link");
                    }
            } catch (SQLException e) {
                    e.printStackTrace();
            }
    	} catch (Exception e) {
    		System.out.println(e);
    	}
    }
    
    public List<List<Object>> getAccessibleLinks(Connection connection) {
    	List<List<Object>> accessibleLinks = new ArrayList<>();
    	String query = "SELECT * FROM " + LINK_TABLE_NAME + " WHERE accessible_state = ?";
    	try (PreparedStatement statement = connection.prepareStatement(query)) {
    		statement.setBoolean(1, true);
    		try (ResultSet resultSet = statement.executeQuery()) {
    			while (resultSet.next()) {                       
    				List<Object> accessibleLink = new ArrayList<>();
    				accessibleLink.add(resultSet.getInt("empid"));
    				accessibleLink.add(resultSet.getString("name"));
    				accessibleLink.add(resultSet.getString("description"));
    				accessibleLink.add(resultSet.getString("last_synced"));

    				accessibleLinks.add(accessibleLink);
    				}
    			}
    		} catch (SQLException e) {
    			e.printStackTrace();
    		}
    	return accessibleLinks;
    }
}
