package application.controllers;

import java.io.IOException;

import application.util.ViewNavigator;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
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
    @FXML
    private TabPane tabPages;
    
    
    DashboardController dashboardController = new DashboardController();
    
    @FXML
    private void initialize() throws Exception {
    	// set starting page
    	goToDashboard();
    	    	
    	dashboardButton.setOnAction(event -> goToDashboard());
    	cloudLoginButton.setOnAction(event -> goToCloudLogin());
    	newLinkButton.setOnAction(event -> goToNewLink());
    	quitButton.setOnAction(event -> quit());
    	dashboardController.startDashboardService();
    }
    
    
    private void initializePages() throws Exception {
        tabPages.getTabs().add(initializeTab("dash", "dashboard.fxml"));
        tabPages.getTabs().add(initializeTab("login", "cloud_login.fxml"));
        tabPages.getTabs().add(initializeTab("new", "new_link.fxml"));
        tabPages.getTabs().add(initializeTab("manage", "manage.fxml"));
        
    }
    
    private Tab initializeTab(String tabName, String viewName) throws Exception {
    	return new Tab(tabName, loadView(viewName));
    }
    
    private javafx.scene.Parent loadView(String fxmlPath) throws Exception {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource(VIEWS_BASE_DIR + fxmlPath));
        return loader.load();
    }
    
    private void changeTab(int pageIndex) {
    	tabPages.getSelectionModel().select(pageIndex);
    }
    
    @FXML
    private void goToDashboard() {
    	changeTab(0);
    	updateTite("Dashboard");
    }
    
    @FXML
    private void goToNewLink() {
    	changeTab(2);
    	updateTite("Create New Link");
    }
    
    @FXML
    private void goToManage() {
    	changeTab(3);
    	updateTite("Manage");
    }
    
    @FXML void quit() {
    	exit();
    }
    
    }
    
    @FXML void exit() {
    	Platform.exit();
    }
    
    private void updateTite(String newTitle) {
    	headingLabel.setText(newTitle);
    }

            // Replace the contentPane content with the new view

            // If needed, give the controller access to the main app or any shared data
           // BaseController controller = loader.getController();
           // controller.setMainApp(this); //this can allow controllers to talk to each other if needed???
            
	
}
