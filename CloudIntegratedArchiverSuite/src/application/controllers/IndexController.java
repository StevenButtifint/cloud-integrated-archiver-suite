package application.controllers;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import application.config.Config;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class IndexController {
	
	private static final Logger logger = LogManager.getLogger(IndexController.class.getName());
	
	private DashboardController dashboardController;
	
	private ManageController manageController;
	
	private Config config;

	@FXML
	private BorderPane mainPane;
	@FXML
	private AnchorPane contentPane;
	@FXML
	private AnchorPane indexTopBar;
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

    private double xOffset = 0;
    
    private double yOffset = 0;

	@FXML
	private void initialize() {
		// load application properties
		config = new Config("app.properties");
		
		dashboardController = new DashboardController();
		manageController = new ManageController();
		manageController.setDashboardController(dashboardController);
		
		// setup tab pane
		hideTabePaneHeader();
		initializePages();

		// set starting page
		goToDashboard();
		
		setDraggableBehavior(indexTopBar);

		dashboardButton.setOnAction(event -> goToDashboard());
		cloudLoginButton.setOnAction(event -> goToCloudLogin());
		manageButton.setOnAction(event -> goToManage());
		monitorButton.setOnAction(event -> goToMonitor());
		duplicationButton.setOnAction(event -> goToDuplication());
		quitButton.setOnAction(event -> quit());
	}
	
	private void setDraggableBehavior(Node node) {
        node.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });

        node.setOnMouseDragged(event -> {
            Stage stage = (Stage) node.getScene().getWindow();
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
        });
    }

	private void hideTabePaneHeader() {
		tabPages.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
		tabPages.getStyleClass().add("tab-pane-no-header");
	}

	public void initializePages() {
		try {
		tabPages.getTabs().add(initializeTab("dash", config.getProperty("view.path.dashboard"), dashboardController));
		tabPages.getTabs().add(initializeTab("login", config.getProperty("view.path.cloudlogin"), new CloudController()));
		tabPages.getTabs().add(initializeTab("manage", config.getProperty("view.path.manage"), manageController));
		tabPages.getTabs().add(initializeTab("monitor", config.getProperty("view.path.monitor"), new MonitorController()));
		tabPages.getTabs().add(initializeTab("duplication", config.getProperty("view.path.duplication"), new DuplicationController()));
		} catch (Exception e) {
			logger.error("Could not initalise pages.", e);
		}
	}

	private Tab initializeTab(String tabName, String viewName, Object controller) {
		return new Tab(tabName, loadView(viewName, controller));
	}

	private Parent loadView(String fxmlPath, Object controller) {
		Parent view = null;
		try {
			FXMLLoader loader = new FXMLLoader(controller.getClass().getResource(fxmlPath));
			loader.setController(controller);
			view = loader.load();
	    } catch (IOException e) {
	    	logger.error("Could not load view:" + fxmlPath, e);
	    }
		return view;
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
	private void goToManage() {
		changeTab(2);
		updateTite("Manage");
		manageController.goToManageHome();
	}

	@FXML
	private void goToMonitor() {
		changeTab(3);
		updateTite("Monitor");
	}

	@FXML
	private void goToDuplication() {
		changeTab(4);
		updateTite("Duplication");
	}

	@FXML
	void quit() {
		dashboardController.stopDashboardThread();
		exit();
	}

	@FXML
	private void goToQuitConfirm() {
	}

	@FXML
	void exit() {
		Platform.exit();
	}

	private void updateTite(String newTitle) {
		headingLabel.setText(newTitle);
	}

}
