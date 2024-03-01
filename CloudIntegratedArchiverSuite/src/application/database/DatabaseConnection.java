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
import java.util.logging.Level;
import java.util.logging.Logger;

import application.config.Config;
import application.models.Link;

public class DatabaseConnection {

	private Config config = new Config("db.properties");

	private static final Logger logger = Logger.getLogger(DatabaseConnection.class.getName());

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

	public void initializeLinkTable(Connection connection) {
		try {
			DatabaseMetaData metaData = connection.getMetaData();
			ResultSet tables = metaData.getTables(null, null, config.getProperty("tbl.links"), null);

			// check if the table already exists
			if (!tables.next()) {
				createLinkTablee(connection);
			} else {
				logger.log(Level.INFO, "Successfully initialized existing links table.");
			}
		} catch (SQLException e) {
			logger.log(Level.SEVERE, "Failed to initialize link table: {0}", new Object[] { e.getMessage(), e });
		}
	}

	public void createLinkTablee(Connection connection) {
		try {
			Statement statement = connection.createStatement();
			String query = "CREATE TABLE " + config.getProperty("tbl.links")
					+ " (empid SERIAL, name TEXT, description TEXT, source_location TEXT, destination_location TEXT, created_date DATE, last_synced DATE, accessible_state BOOLEAN, primary key(empid));";
			statement.executeUpdate(query);
			logger.log(Level.INFO, "Successfully initialized new links table.");

		} catch (SQLException e) {
			logger.log(Level.SEVERE, "Failed to create link table: {0}", new Object[] { e.getMessage(), e });
		}
	}

	public void insertLink(Connection connection, String name, String description, String source_location,
			String destination_location) {
		try {
			String query = "INSERT INTO " + config.getProperty("tbl.links")
					+ " (name, description, source_location, destination_location, created_date, last_synced, accessible_state) VALUES (?, ?, ?, ?, CURRENT_DATE, CURRENT_DATE, ?)";
			try (PreparedStatement statement = connection.prepareStatement(query)) {
				statement.setString(1, name);
				statement.setString(2, description);
				statement.setString(3, source_location);
				statement.setString(4, destination_location);
				statement.setBoolean(5, true);

				int rowsInserted = statement.executeUpdate();
				if (rowsInserted > 0) {
					logger.log(Level.INFO, "Successfully added new link.");
				} else {
					logger.log(Level.WARNING, "Failed to add new link.");
				}
			} catch (SQLException e) {
				logger.log(Level.SEVERE, "Failed to create new link statement: {0}",
						new Object[] { e.getMessage(), e });
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Failed to create new query: {0}", new Object[] { e.getMessage(), e });
		}
	}

	public List<Link> getAccessibleLinks(Connection connection) {
		List<Link> accessibleLinks = new ArrayList<>();
		String query = "SELECT * FROM " + config.getProperty("tbl.links") + " WHERE accessible_state = ?";
		try (PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setBoolean(1, true);
			try (ResultSet resultSet = statement.executeQuery()) {
				while (resultSet.next()) {
					accessibleLinks.add(new Link(resultSet.getInt("empid"), resultSet.getString("name"),
							resultSet.getString("description"), resultSet.getString("source_location"),
							resultSet.getString("destination_location"), resultSet.getString("created_date"),
							resultSet.getString("last_synced"), resultSet.getString("accessible_state").equals("t")));
				}
			}
		} catch (SQLException e) {
			logger.log(Level.SEVERE, "Failed to get accessible links query: {0}", new Object[] { e.getMessage(), e });
		}
		return accessibleLinks;
	}

	public List<Link> getInaccessibleLinks(Connection connection) {
		List<Link> inaccessibleLinks = new ArrayList<>();
		String query = "SELECT * FROM " + config.getProperty("tbl.links") + " WHERE accessible_state = ?";
		try (PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setBoolean(1, false);
			try (ResultSet resultSet = statement.executeQuery()) {
				while (resultSet.next()) {
					inaccessibleLinks.add(new Link(resultSet.getInt("empid"), resultSet.getString("name"),
							resultSet.getString("description"), resultSet.getString("source_location"),
							resultSet.getString("destination_location"), resultSet.getString("created_date"),
							resultSet.getString("last_synced"), resultSet.getString("accessible_state").equals("t")));
				}
			}
		} catch (SQLException e) {
			logger.log(Level.SEVERE, "Failed to get inaccessible links query: {0}", new Object[] { e.getMessage(), e });
		}
		return inaccessibleLinks;
	}
}
