package application;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import application.config.Config;
import application.controllers.ControllerFactory;
import application.database.DatabaseConnection;
import application.util.AlertError;

public class Main extends Application {
	private static final Logger logger = LogManager.getLogger(Main.class.getName());

	private static final String APP_CONFIG_FILE = "app.properties";
	private static final String DB_CONFIG_FILE = "db.properties";

	private static final String VIEW_PATH_INDEX = "view.path.index";
	private static final String APP_TITLE = "app.title";

	private DatabaseConnection databaseConnection;
	private ControllerFactory controllerFactory;
	private Config appConfig;
	private Config dbConfig;

	public static void main(String[] args) {
		logger.info("Starting application...");
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		if (!initialiseConfiguration()) {
			return;
		}

		if (!initialiseDatabaseTables()) {
			return;
		}

		if (!setupPrimaryStage(primaryStage)) {
			Platform.exit();
		}
	}

	private boolean initialiseConfiguration() {
		try {
			appConfig = new Config(APP_CONFIG_FILE);
			logger.info("Successfully loaded application properties.");
			dbConfig = new Config(DB_CONFIG_FILE);
			logger.info("Successfully loaded database properties.");
			return true;
		} catch (IOException e) {
			logger.error("Failed to load configuration properties: " + e.getMessage(), e);
			AlertError.showError("Failed to launch Archiver Suite", "Application Error",
					"Cannot load configuration properties.");
			return false;
		}
	}

	private boolean initialiseDatabaseTables() {
		databaseConnection = new DatabaseConnection(dbConfig);
		if (!databaseConnection.connectToDatabase()) {
			logger.error("Unable to connect to Link database.");
			AlertError.showError("Database Failure", "Connection Error", "Cannot connect to the database.");
			return false;
		}
		if (!databaseConnection.initialiseLinkTable()) {
			logger.error("Unable to initialise Link database.");
			databaseConnection.closeConnection();
			AlertError.showError("Database Failure", "Initialisation Error", "Cannot initialise the database table.");
			return false;
		}
		databaseConnection.closeConnection();
		logger.info("Successfully initialised database.");
		return true;
	}

	private boolean setupPrimaryStage(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(appConfig.getProperty(VIEW_PATH_INDEX)));
			loader.setControllerFactory(clazz -> getControllerFactory().getIndexController());
			Parent root = loader.load();
			primaryStage.setTitle(appConfig.getProperty(APP_TITLE));
			primaryStage.initStyle(StageStyle.UNDECORATED);
			primaryStage.setScene(new Scene(root, 1000, 600));
			primaryStage.show();
			logger.info("Successfully setup primary stage.");
			return true;
		} catch (IOException e) {
			logger.error("Failed to setup the primary stage: " + e.getMessage(), e);
			AlertError.showError("Failed to launch Archiver Suite", "UI Loading Error", "Failed to load user interface.");
			return false;
		}
	}
	
	@Override
	public void stop() {
		if (controllerFactory != null) {
			controllerFactory.stopServices();
		}
        System.out.println("Services shutdown.");
	}
	
	public ControllerFactory getControllerFactory() {
		if (controllerFactory == null) {
			controllerFactory = new ControllerFactory(appConfig, dbConfig);
		}
		return controllerFactory;
	}


}
