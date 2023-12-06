package application.navigation;

import java.io.IOException;

import application.controllers.BaseController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class PageNavigator {
	private Stage stage;
	
	public PageNavigator(Stage stage) {
		this.stage = stage;
	}
	
    public void navigateToIndex() {
        navigateTo("index.fxml");
    }

    private void navigateTo(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent root = loader.load();
            stage.setScene(new Scene(root, 900, 600));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
