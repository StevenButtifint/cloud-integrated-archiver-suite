package application.util;

import java.io.IOException;

import application.controllers.BaseController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ViewNavigator {
    public static void loadView(String fxmlPath, BaseController controller, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(controller.getClass().getResource(fxmlPath));
            loader.setController(controller);
            Parent parent = loader.load();

            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
