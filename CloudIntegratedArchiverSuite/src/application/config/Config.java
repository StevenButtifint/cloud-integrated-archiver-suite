package application.config;

import java.io.InputStream;
import java.util.Properties;

public class Config {
	private Properties properties;

	public Config(String propertiesFilePath) {
		properties = new Properties();
		loadProperties(propertiesFilePath);
	}

	private void loadProperties(String propertiesFilePath) {
		try (InputStream inputStream = getClass().getResourceAsStream(propertiesFilePath)) {
			if (inputStream != null) {
				properties.load(inputStream);
			} else {
				throw new RuntimeException("Unable to load properties file: " + propertiesFilePath);
			}
		} catch (Exception e) {
			System.err.println("Error loading properties file: " + propertiesFilePath);
			e.printStackTrace();
		}
	}

	public String getProperty(String key) {
		return properties.getProperty(key);
	}
}