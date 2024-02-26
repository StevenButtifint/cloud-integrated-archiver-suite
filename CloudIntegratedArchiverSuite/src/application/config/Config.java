package application.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Config {
	private static Properties properties;

	static {
		properties = new Properties();
		try (FileInputStream fileInputStream = new FileInputStream("src/application/config/.properties")) {
			properties.load(fileInputStream);
		} catch (IOException e) {
			System.err.println("Error loading properties file: " + e.getMessage());
		}
	}

	public static String getProperty(String key) {
		return properties.getProperty(key);
	}
}
