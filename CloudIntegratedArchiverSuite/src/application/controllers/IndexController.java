package application.controllers;

import application.config.Config;
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

	// load application properties
	Config config = new Config("app.properties");

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
		tabPages.getTabs().add(initializeTab("dash", config.getProperty("view.path.dashboard"), new DashboardController()));
		tabPages.getTabs().add(initializeTab("login", config.getProperty("view.path.cloudlogin"), new CloudController()));
		tabPages.getTabs().add(initializeTab("new", config.getProperty("view.path.newlink"), new NewLinkController()));
		tabPages.getTabs().add(initializeTab("manage", config.getProperty("view.path.manage"), new ManageController()));
		tabPages.getTabs().add(initializeTab("monitor", config.getProperty("view.path.monitor"), new MonitorController()));
		tabPages.getTabs().add(initializeTab("duplication", config.getProperty("view.path.duplication"), new DuplicationController()));
	}

	private Tab initializeTab(String tabName, String viewName, BaseController controller) throws Exception {
		return new Tab(tabName, loadView(viewName, controller));
	}

	private javafx.scene.Parent loadView(String fxmlPath, BaseController controller) throws Exception {
		FXMLLoader loader = new FXMLLoader(controller.getClass().getResource(fxmlPath));
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

	@FXML
	void quit() {
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
