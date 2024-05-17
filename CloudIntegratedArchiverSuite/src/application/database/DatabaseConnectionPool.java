package application.database;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbcp2.BasicDataSource;

public class DatabaseConnectionPool {

	private BasicDataSource dataSource;

	public DatabaseConnectionPool(String driver, String url, String username, String password) {
		dataSource = new BasicDataSource();
		dataSource.setDriverClassName(driver);
		dataSource.setUrl(url);
		dataSource.setUsername(username);
		dataSource.setPassword(password);
	}

	public Connection getConnection() throws SQLException {
		return dataSource.getConnection();
	}

	public void close() throws SQLException {
		dataSource.close();
	}
}
