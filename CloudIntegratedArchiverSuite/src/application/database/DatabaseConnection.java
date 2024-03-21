package application.database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import application.config.Config;
import application.models.Link;

public class DatabaseConnection {

	private static final Logger logger = LogManager.getLogger(DatabaseConnection.class.getName());

	private Config config = new Config("db.properties");

	private Connection connection;

	public boolean connectToDatabase() {
		connection = null;
		try {
			Class.forName("org.postgresql.Driver");
			connection = DriverManager.getConnection(config.getProperty("db.url"), config.getProperty("db.username"),
					config.getProperty("db.password"));
			if (connection != null) {
				logger.info("Successfully connected to database: " + config.getProperty("db.name"));
				return true;

			} else {
				logger.error("Failed to connect to database: " + config.getProperty("db.name"));
				return false;
			}
		} catch (Exception e) {
			logger.error("Failed to setup database connection: " + config.getProperty("db.name"), e);
			return false;
		}
	}

	public void closeConnection() {
		try {
			if (connection != null && !connection.isClosed()) {
				connection.close();
			}
		} catch (SQLException e) {
			logger.error("Failed to disconnect from database: " + config.getProperty("db.name"), e);
		}
	}

	public void initializeLinkTable() {
		try {
			DatabaseMetaData metaData = connection.getMetaData();
			ResultSet tables = metaData.getTables(null, null, config.getProperty("tbl.links"), null);

			// check if the table already exists
			if (!tables.next()) {
				createLinkTable(connection);
			} else {
				logger.info("Successfully initialized existing links table.");
			}
		} catch (SQLException e) {
			logger.error("Failed to initialize link table: " + e.getMessage(), e);
		}
	}

	public void createLinkTable(Connection connection) {
		try {
			Statement statement = connection.createStatement();
			String query = "CREATE TABLE " + config.getProperty("tbl.links")
					+ " (empid SERIAL, name TEXT, description TEXT, source_location TEXT, destination_location TEXT, created_date DATE, last_synced DATE, accessible_state BOOLEAN, primary key(empid));";
			statement.executeUpdate(query);
			logger.info("Successfully initialized new links table.");

		} catch (SQLException e) {
			logger.error("Failed to create link table: " + e.getMessage(), e);
		}
	}

	public void insertLink(String name, String description, String source_location, String destination_location) {
		String query = "INSERT INTO " + config.getProperty("tbl.links") + " (name, description, source_location, destination_location, created_date, last_synced, accessible_state) VALUES (?, ?, ?, ?, CURRENT_DATE, CURRENT_DATE, ?)";
			
		try (PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setString(1, name);
			statement.setString(2, description);
			statement.setString(3, source_location);
			statement.setString(4, destination_location);
			statement.setBoolean(5, true);

			int rowsInserted = statement.executeUpdate();
			if (rowsInserted > 0) {
				logger.info("Successfully added new link.");
			} else {
				logger.warn("Failed to add new link.");
			}
		} catch (SQLException e) {
			logger.error("Failed to create new link statement. " + e.getMessage(), e);
		}
	}
	
	public void updateLink(int id, String name, String description, String source_location, String destination_location) {
	    String query = "UPDATE " + config.getProperty("tbl.links") + " SET name=?, description=?, source_location=?, destination_location=? WHERE empid=?";
	    
	    try (PreparedStatement statement = connection.prepareStatement(query)) {
	        statement.setString(1, name);
	        statement.setString(2, description);
	        statement.setString(3, source_location);
	        statement.setString(4, destination_location);
	        statement.setInt(5, id);

	        int rowsUpdated = statement.executeUpdate();
	        if (rowsUpdated > 0) {
	            logger.info("Successfully updated link with ID " + id);
	        } else {
	            logger.warn("No link found with ID " + id);
	        }
	    } catch (SQLException e) {
	        logger.error("Failed to update link. " + e.getMessage(), e);
	    }
	}

	public Link getLinkById(int id) {
		String query = "SELECT * FROM " + config.getProperty("tbl.links") + " WHERE empid = ?";
		Link link = null;
		try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
			preparedStatement.setInt(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				link = new Link(resultSet.getInt("empid"), resultSet.getString("name"),
						resultSet.getString("description"), resultSet.getString("source_location"),
						resultSet.getString("destination_location"), resultSet.getDate("created_date"),
						resultSet.getDate("last_synced"), resultSet.getBoolean("accessible_state"));
			}
		} catch (SQLException e) {
			logger.error("Failed to get link by ID " + e.getMessage(), e);
		}
		return link;
	}
	

	public void updateLastSynced(int linkID) {
		try {
			String query = "UPDATE " + config.getProperty("tbl.links") + " SET last_synced = CURRENT_DATE WHERE empid = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, linkID);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			logger.error("Failed to update last synced date. " + e.getMessage(), e);
		}
	}

	public List<Link> getAccessibleLinks() {
		List<Link> accessibleLinks = new ArrayList<>();
		String query = "SELECT * FROM " + config.getProperty("tbl.links") + " WHERE accessible_state = ?";
		try (PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setBoolean(1, true);
			try (ResultSet resultSet = statement.executeQuery()) {
				while (resultSet.next()) {
					accessibleLinks.add(new Link(resultSet.getInt("empid"), resultSet.getString("name"),
							resultSet.getString("description"), resultSet.getString("source_location"),
							resultSet.getString("destination_location"), resultSet.getDate("created_date"),
							resultSet.getDate("last_synced"), resultSet.getString("accessible_state").equals("t")));
				}
			}
		} catch (SQLException e) {
			logger.error("Failed to get accessible links query " + e.getMessage(), e);
		}
		return accessibleLinks;
	}

	public List<Link> getInaccessibleLinks() {
		List<Link> inaccessibleLinks = new ArrayList<>();
		String query = "SELECT * FROM " + config.getProperty("tbl.links") + " WHERE accessible_state = ?";
		try (PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setBoolean(1, false);
			try (ResultSet resultSet = statement.executeQuery()) {
				while (resultSet.next()) {
					inaccessibleLinks.add(new Link(resultSet.getInt("empid"), resultSet.getString("name"),
							resultSet.getString("description"), resultSet.getString("source_location"),
							resultSet.getString("destination_location"), resultSet.getDate("created_date"),
							resultSet.getDate("last_synced"), resultSet.getString("accessible_state").equals("t")));
				}
			}
		} catch (SQLException e) {
			logger.error("Failed to get inaccessible links query. " + e.getMessage(), e);
		}
		return inaccessibleLinks;
	}
}
