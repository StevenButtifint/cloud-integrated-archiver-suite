package application.services;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import application.config.Config;
import application.database.DatabaseConnectionPool;
import application.models.Link;

public class DatabaseService {

	private static final Logger logger = LogManager.getLogger(DatabaseService.class.getName());

	private final DatabaseConnectionPool connectionPool;

	private Config dbConfig;

	public DatabaseService(DatabaseConnectionPool connectionPool, Config dbConfig) {
		this.connectionPool = connectionPool;
		this.dbConfig = dbConfig;
	}

	public boolean initialiseLinkTable() {
		try (Connection connection = connectionPool.getConnection()) {
			DatabaseMetaData metaData = connection.getMetaData();
			try (ResultSet resultSet = metaData.getTables(null, null, dbConfig.getProperty("tbl.links"),
					new String[] { "TABLE" })) {
				if (!resultSet.next()) {
					createLinkTable();
				}
				logger.info("Successfully initialized links table.");
				return true;
			}
		} catch (SQLException e) {
			logger.error("Failed to initialize link table: " + e.getMessage(), e);
			return false;
		}
	}

	public void createLinkTable() {
		String query = "CREATE TABLE " + dbConfig.getProperty("tbl.links")
				+ " (empid SERIAL, name TEXT, description TEXT, source_location TEXT, destination_location TEXT, created_date DATE, last_synced DATE, sync_modified BOOLEAN, sync_deleted BOOLEAN, sync_as_archive BOOLEAN, primary key(empid));";
		try (Connection connection = connectionPool.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(query)) {
			Statement statement = connection.createStatement();
			statement.executeUpdate(query);
			logger.info("Successfully created new links table.");
		} catch (SQLException e) {
			logger.error("Failed to create link table: " + e.getMessage(), e);
		}
	}

	public void insertLink(String name, String description, String source_location, String destination_location,
			boolean sync_modified, boolean sync_deleted, boolean sync_as_archive) {
		String query = "INSERT INTO " + dbConfig.getProperty("tbl.links")
				+ " (name, description, source_location, destination_location, created_date, last_synced, sync_modified, sync_deleted, sync_as_archive) VALUES (?, ?, ?, ?, CURRENT_DATE, ?, ?, ?, ?)";
		try (Connection connection = connectionPool.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(query)) {
			preparedStatement.setString(1, name);
			preparedStatement.setString(2, description);
			preparedStatement.setString(3, source_location);
			preparedStatement.setString(4, destination_location);
			preparedStatement.setDate(5, Date.valueOf("9999-01-01"));
			preparedStatement.setBoolean(6, sync_modified);
			preparedStatement.setBoolean(7, sync_deleted);
			preparedStatement.setBoolean(8, sync_as_archive);
			int rowsInserted = preparedStatement.executeUpdate();
			if (rowsInserted > 0) {
				logger.info("Successfully added new link.");
			} else {
				logger.warn("Failed to add new link.");
			}
		} catch (SQLException e) {
			logger.error("Failed to create new link statement. " + e.getMessage(), e);
		}
	}

	public void updateLink(int id, String name, String description, String source_location, String destination_location,
			boolean syncModifed, boolean syncDeleted, boolean syncAsArchive) {
		String query = "UPDATE " + dbConfig.getProperty("tbl.links")
				+ " SET name=?, description=?, source_location=?, destination_location=?, sync_modified=?, sync_deleted=?, sync_as_archive=? WHERE empid=?";
		try (Connection connection = connectionPool.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(query)) {
			preparedStatement.setString(1, name);
			preparedStatement.setString(2, description);
			preparedStatement.setString(3, source_location);
			preparedStatement.setString(4, destination_location);
			preparedStatement.setBoolean(5, syncModifed);
			preparedStatement.setBoolean(6, syncDeleted);
			preparedStatement.setBoolean(7, syncAsArchive);
			preparedStatement.setInt(8, id);
			int rowsUpdated = preparedStatement.executeUpdate();
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
		String query = "SELECT * FROM " + dbConfig.getProperty("tbl.links") + " WHERE empid = ?";
		try (Connection connection = connectionPool.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(query)) {
			preparedStatement.setInt(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				return new Link(resultSet.getInt("empid"), resultSet.getString("name"),
						resultSet.getString("description"), resultSet.getString("source_location"),
						resultSet.getString("destination_location"), resultSet.getDate("created_date"),
						resultSet.getDate("last_synced"), resultSet.getBoolean("sync_modified"),
						resultSet.getBoolean("sync_deleted"), resultSet.getBoolean("sync_as_archive"));
			}
		} catch (SQLException e) {
			logger.error("Failed to get link by ID " + e.getMessage(), e);
		}
		return null;
	}

	public boolean deleteLinkById(int id) {
		String query = "DELETE FROM " + dbConfig.getProperty("tbl.links") + " WHERE empid = ?";
		try (Connection connection = connectionPool.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(query)) {
			preparedStatement.setInt(1, id);
			preparedStatement.executeUpdate();
			logger.info("Link with ID " + id + " deleted successfully.");
			return true;
		} catch (SQLException e) {
			logger.error("Failed to delete link by ID " + id + ": " + e.getMessage(), e);
			return false;
		}
	}

	public void updateLastSynced(int linkID) {
		String query = "UPDATE " + dbConfig.getProperty("tbl.links")
				+ " SET last_synced = CURRENT_DATE WHERE empid = ?";
		try (Connection connection = connectionPool.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(query)) {
			preparedStatement.setInt(1, linkID);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			logger.error("Failed to update last synced date. " + e.getMessage(), e);
		}
	}

	public List<Link> getAllLinks() {
		List<Link> allLinks = new ArrayList<>();
		String query = "SELECT * FROM " + dbConfig.getProperty("tbl.links");
		try (Connection connection = connectionPool.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(query)) {
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					allLinks.add(new Link(resultSet.getInt("empid"), resultSet.getString("name"),
							resultSet.getString("description"), resultSet.getString("source_location"),
							resultSet.getString("destination_location"), resultSet.getDate("created_date"),
							resultSet.getDate("last_synced"), resultSet.getString("sync_modified").equals("t"),
							resultSet.getString("sync_deleted").equals("t"),
							resultSet.getString("sync_as_archive").equals("t")));
				}
			}
		} catch (SQLException e) {
			logger.error("Failed to get accessible links query " + e.getMessage(), e);
		}
		return allLinks;
	}

}
