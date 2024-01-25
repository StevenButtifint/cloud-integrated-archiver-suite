package application.controllers;

import java.io.IOException;

import application.util.ViewNavigator;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;


public class IndexController extends BaseController{
	
    @FXML
    private BorderPane mainPane;
    @FXML
    private AnchorPane contentPane;
    @FXML
    private Button cloudLoginButton;
    @FXML
    private Button dashboardButton;
    @FXML
    private Button newLinkButton;
    @FXML
    @FXML 
    private Button quitButton;
    @FXML
    private Label headingLabel;
    
    
    DashboardController dashboardController = new DashboardController();
    
    @FXML
    private void initialize() {
    	dashboardButton.setOnAction(event -> goToDashboard());
    	cloudLoginButton.setOnAction(event -> goToCloudLogin());
    	newLinkButton.setOnAction(event -> goToNewLink());
    	quitButton.setOnAction(event -> quit());
    	dashboardController.startDashboardService();
    }
    
    @FXML
    private void goToDashboard() {
    	updateTite("Dashboard");
        loadView("../views/dashboard.fxml");
        //, new Page2Controller());
    }
    
    @FXML
    private void goToNewLink() {
    	updateTite("Create New Link");
        loadView("../views/new_link.fxml");
    }
    
    @FXML
    private void goToCloudLogin() {
    	updateTite("Cloud Login");
        loadView("../views/cloud_login.fxml");
    }
    
    @FXML void quit() {
    	exit();
    }
    
        //, new Page1Controller());
    }
    
    @FXML void exit() {
    	Platform.exit();
    }
    
    private void updateTite(String newTitle) {
    	headingLabel.setText(newTitle);
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
