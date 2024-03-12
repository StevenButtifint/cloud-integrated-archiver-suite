package application;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import application.config.Config;
import application.controllers.IndexController;

public class Main extends Application {
	private static final Logger logger = LogManager.getLogger(Main.class.getName());

	@Override
	public void start(Stage primaryStage) {
		try {
			// load application properties
			Config config = new Config("app.properties");
			logger.info("Successfully loaded application properties.");

			// load index view
			FXMLLoader loader = new FXMLLoader(getClass().getResource(config.getProperty("view.path.index")));
			Parent root = loader.load();
			logger.info("Successfully loaded index view.");

			// get controller for the view
			IndexController controller = loader.getController();

			// set the primary stage
			primaryStage.setTitle(config.getProperty("app.title"));
			primaryStage.initStyle(StageStyle.UNDECORATED);
			primaryStage.setScene(new Scene(root, 1000, 600));
			primaryStage.show();
			logger.info("Successfully setup primary stage.");

		} catch (IOException e) {
			logger.error("Failed to setup the primary stage: " + e.getMessage(), e);
			
			// display an error message to the user
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Failed to launch Archiver Suite");
			alert.setHeaderText("Application Error");
			alert.setContentText("An error occurred while loading the application.");
			alert.showAndWait();
			
			// exit application
			Platform.exit();
		}
	}

	public static void main(String[] args) {
		logger.info("Starting application...");
		launch(args);
	}
}
