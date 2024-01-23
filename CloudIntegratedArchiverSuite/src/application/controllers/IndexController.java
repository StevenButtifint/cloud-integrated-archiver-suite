package application.controllers;

import java.io.IOException;

import application.util.ViewNavigator;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;


public class IndexController extends BaseController{
	
    @FXML
    private BorderPane mainPane;

    @FXML
    private AnchorPane contentPane;
    
    @FXML
    private Button cloudLoginBtn;
    
    @FXML
    private Button dashboardButton;
    @FXML
    private Button newLinkButton;
    
    @FXML
    private void initialize() {
    	dashboardButton.setOnAction(event -> goToDashboard());
    	cloudLoginBtn.setOnAction(event -> goToCloudLogin());
    }
    
    @FXML
    private void goToDashboard() {
        loadView("../views/dashboard.fxml");
        //, new Page2Controller());
    }
    
    @FXML
    private void goToNewLink() {
        loadView("../views/new_link.fxml");
    }
    
    @FXML
    private void goToCloudLogin() {
        loadView("../views/cloud_login.fxml");
        //, new Page1Controller());
    }

    private void loadView(String fxmlPath) {
        try {
        	System.out.println("Switched to page: " + fxmlPath);
        	FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            AnchorPane newView = loader.load();
            // Replace the contentPane content with the new view
            contentPane.getChildren().setAll(newView);

            // If needed, give the controller access to the main app or any shared data
           // BaseController controller = loader.getController();
           // controller.setMainApp(this); //this can allow controllers to talk to each other if needed???
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
}
