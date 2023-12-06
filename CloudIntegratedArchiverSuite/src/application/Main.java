package application;
	
import application.navigation.PageNavigator;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	
	public static void main(String[] args) {
		launch(args);
	}
	
    @Override
    public void start(Stage primaryStage) {
        PageNavigator pageNavigator = new PageNavigator(primaryStage);
        pageNavigator.navigateToIndex();
    }
}
