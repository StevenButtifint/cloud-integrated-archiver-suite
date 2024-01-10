package application;
	
import application.controllers.IndexController;
import application.util.ViewNavigator;
import javafx.application.Application;
import javafx.stage.Stage;


public class Main extends Application {
	
	public static void main(String[] args) {
		launch(args);
	}
	
    @Override
    public void start(Stage primaryStage) {
    	ViewNavigator.loadView("../views/index.fxml", new IndexController(), "Archiver Suite");
    }
}
