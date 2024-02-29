package application.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import application.config.Config;
import application.models.Link;

public class DatabaseConnection {
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
    
    public List<Link> getAccessibleLinks(Connection connection) {
    	List<Link> accessibleLinks = new ArrayList<>();
    	String query = "SELECT * FROM " + LINK_TABLE_NAME + " WHERE accessible_state = ?";
    	try (PreparedStatement statement = connection.prepareStatement(query)) {
    		statement.setBoolean(1, true);
    		try (ResultSet resultSet = statement.executeQuery()) {
    			while (resultSet.next()) {                       
    				accessibleLinks.add(new Link(resultSet.getInt("empid"), resultSet.getString("name"), resultSet.getString("description"), resultSet.getString("source_location"), resultSet.getString("destination_location"), resultSet.getString("created_date"), resultSet.getString("last_synced"), true));

    				}
    			}
    		} catch (SQLException e) {
    			e.printStackTrace();
    		}
    	return accessibleLinks;
    }

	private Config config = new Config("db.properties");


	public Connection connectToDatabase() {
		Connection connection = null;
		try {
			Class.forName("org.postgresql.Driver");
			connection = DriverManager.getConnection(config.getProperty("db.url"), config.getProperty("db.username"),
					config.getProperty("db.password"));
			if (connection != null) {
				logger.log(Level.INFO, "Successfully connected to database: {0}", config.getProperty("db.name"));

			} else {
				logger.log(Level.SEVERE, "Failed to connect to database: {0}", config.getProperty("db.name"));
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Failed to setup database connection: {0}", e);
		}
		return connection;
	}

}
