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


public class IndexController extends BaseController {
	
    @FXML
    private BorderPane mainPane;
    @FXML
    private AnchorPane contentPane;
    @FXML
    private Button cloudLoginButton;
    @FXML
    private Button dashboardButton;
    @FXML
    private Button manageButton;
    @FXML
    private Button monitorButton;
    @FXML
    private Button duplicationButton;
    @FXML 
    private Button quitButton;
    @FXML
    private Label headingLabel;
    @FXML
    private TabPane tabPages;
    
    
    private static String VIEWS_BASE_DIR = "../views/";
        
    @FXML
    private void initialize() throws Exception {
    	// setup tab pane
    	hideTabePaneHeader();
    	initializePages();
        
    	// set starting page
    	goToDashboard();
    	    	
    	dashboardButton.setOnAction(event -> goToDashboard());
    	cloudLoginButton.setOnAction(event -> goToCloudLogin());
    	manageButton.setOnAction(event -> goToManage());
    	monitorButton.setOnAction(event -> goToMonitor());
    	duplicationButton.setOnAction(event -> goToDuplication());

    	quitButton.setOnAction(event -> quit());
    }
    
    private void hideTabePaneHeader() {
    	tabPages.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
    	tabPages.getStyleClass().add("tab-pane-no-header");
    }
    
    private void initializePages() throws Exception {
        tabPages.getTabs().add(initializeTab("dash", "dashboard.fxml", new DashboardController()));
        tabPages.getTabs().add(initializeTab("login", "cloud_login.fxml", new CloudController()));
        tabPages.getTabs().add(initializeTab("new", "new_link.fxml", new NewLinkController()));
        tabPages.getTabs().add(initializeTab("manage", "manage.fxml", new ManageController()));
        tabPages.getTabs().add(initializeTab("monitor", "monitor.fxml", new MonitorController()));
        tabPages.getTabs().add(initializeTab("duplication", "duplication.fxml", new DuplicationController()));
    }
       
    private Tab initializeTab(String tabName, String viewName, BaseController controller) throws Exception {
    	return new Tab(tabName, loadView(viewName, controller));
    }
    
    private javafx.scene.Parent loadView(String fxmlPath, BaseController controller) throws Exception {
    	FXMLLoader loader = new FXMLLoader(controller.getClass().getResource(VIEWS_BASE_DIR + fxmlPath));
    	loader.setController(controller);
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
    private void goToCloudLogin() {
    	changeTab(1);
    	updateTite("Cloud Login");
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
    
    @FXML
    private void goToMonitor() {
    	changeTab(4);
    	updateTite("Monitor");
    }
    
    @FXML
    private void goToDuplication() {
    	changeTab(5);
    	updateTite("Duplication");
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
