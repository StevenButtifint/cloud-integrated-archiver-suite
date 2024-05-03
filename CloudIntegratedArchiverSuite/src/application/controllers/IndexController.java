package application.controllers;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import application.config.Config;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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

	private ComparerController comparerController;

	private Config appConfig;

	private double xOffset = 0;

	private double yOffset = 0;

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
	private Button comparerButton;

	@FXML
	private Button quitButton;

	@FXML
	private Label headingLabel;

	@FXML
	private TabPane tabPages;

	public IndexController(Config appConfig, DashboardController dashboardController,
			ComparerController comparerController, ManageController manageController) {
		this.dashboardController = dashboardController;
		this.comparerController = comparerController;
		this.manageController = manageController;
		this.appConfig = appConfig;
	}

	@FXML
	private void initialize() {
		hideTabePaneHeader();
		setDraggableBehavior();
		goToDashboard();
		setButtonActions();
		initializePages();
	}

	private void setButtonActions() {
		dashboardButton.setOnAction(event -> goToDashboard());
		cloudLoginButton.setOnAction(event -> goToCloudLogin());
		manageButton.setOnAction(event -> goToManage());
		monitorButton.setOnAction(event -> goToMonitor());
		comparerButton.setOnAction(event -> goToComparer());
		quitButton.setOnAction(event -> quit());
	}

	private void setDraggableBehavior() {
		indexTopBar.setOnMousePressed(event -> {
			xOffset = event.getSceneX();
			yOffset = event.getSceneY();
		});

		indexTopBar.setOnMouseDragged(event -> {
			Stage stage = (Stage) indexTopBar.getScene().getWindow();
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
			tabPages.getTabs().add(initializeTab("dash", appConfig.getProperty("view.path.dashboard"), dashboardController));
			tabPages.getTabs().add(initializeTab("login", appConfig.getProperty("view.path.cloudlogin"), new CloudController()));
			tabPages.getTabs().add(initializeTab("manage", appConfig.getProperty("view.path.manage"), manageController));
			tabPages.getTabs().add(initializeTab("monitor", appConfig.getProperty("view.path.monitor"), new MonitorController()));
			tabPages.getTabs().add(initializeTab("duplication", appConfig.getProperty("view.path.comparer"), comparerController));
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
	private void goToComparer() {
		changeTab(4);
		updateTite("Compare");
	}

	@FXML
	void quit() {
		dashboardController.stopDashboardThread();
		comparerController.stopComparingFolders();
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
