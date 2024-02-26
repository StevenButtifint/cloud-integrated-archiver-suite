package application;

import java.io.IOException;

import application.config.Config;
import application.controllers.IndexController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) {
		try {
			// load initial view
			FXMLLoader loader = new FXMLLoader(getClass().getResource(Config.getProperty("index.view.path")));
			Parent root = loader.load();

			// get controller for the view
			IndexController controller = loader.getController();

			// set the primary stage
			primaryStage.setTitle(Config.getProperty("application.title"));
			primaryStage.initStyle(StageStyle.UNDECORATED);
			primaryStage.setScene(new Scene(root, 1000, 600));
			primaryStage.show();

		} catch (IOException e) {
			// display an error message to the user
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Failed to launch Archiver Suite");
			alert.setHeaderText("Application Error");
			alert.setContentText("An error occurred while loading the application.");
			alert.showAndWait();
			Platform.exit();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
