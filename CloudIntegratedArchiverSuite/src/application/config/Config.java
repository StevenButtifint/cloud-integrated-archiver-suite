package application.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Config {
	
	private static final Logger logger = LogManager.getLogger(Config.class.getName());
	
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
				logger.error("Unable to load properties file.");
				throw new IOException("Unable to load properties file." + propertiesFilePath);
			}
		} catch (IOException e) {
			logger.error("Error loading properties file: " + e.getMessage(), e);
		}
	}

	public String getProperty(String key) {
		return properties.getProperty(key);
	}
}