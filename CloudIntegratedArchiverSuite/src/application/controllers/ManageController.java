package application.controllers;

import java.io.IOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import application.config.Config;
import application.models.Link;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public class ManageController {

	private static final Logger logger = LogManager.getLogger(ManageController.class.getName());

	private DashboardController dashboardController;

	protected ManageHomeController manageHomeController;

	private CreateLinkController createLinkController;

	private ViewLinkController viewLinkController;

	private EditLinkController editLinkController;

	private DeleteLinkController deleteLinkController;

	@FXML
	private TabPane manageTabs;

	private Config config;

	public ManageController(Config config, DashboardController dashboardController,	ManageHomeController manageHomeController, CreateLinkController createLinkController, ViewLinkController viewLinkController, EditLinkController editLinkController, DeleteLinkController deleteLinkController) {
		this.dashboardController = dashboardController;
		this.manageHomeController = manageHomeController;
		this.createLinkController = createLinkController;
		this.viewLinkController = viewLinkController;
		this.editLinkController = editLinkController;
		this.deleteLinkController = deleteLinkController;
		this.config = config;
	}

	public void initialize() {
		manageHomeController.setManageController(this);
		createLinkController.setManageController(this);
		viewLinkController.setManageController(this);
		editLinkController.setManageController(this);
		deleteLinkController.setManageController(this);
		initializePages();
		goToManageHome();
	}

	private void initializePages() {
		try {
			manageTabs.getTabs().addAll(
					initializeTab("home", config.getProperty("view.path.managehome"), manageHomeController),
					initializeTab("create", config.getProperty("view.path.newlink"), createLinkController),
					initializeTab("view", config.getProperty("view.path.viewlink"), viewLinkController),
					initializeTab("edit", config.getProperty("view.path.editlink"), editLinkController),
					initializeTab("delete", config.getProperty("view.path.deletelink"), deleteLinkController));

		} catch (Exception e) {
			logger.error("Could not initalise pages." + e.getMessage(), e);
		}
	}

	public void refreshDashboard() {
		dashboardController.reloadDashboardLinks();
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
		manageTabs.getSelectionModel().select(pageIndex);
	}

	public void goToManageHome() {
		manageHomeController.refreshLinksList();
		changeTab(0);
	}

	public void goToCreateLink() {
		changeTab(1);
	}

	public void goToViewLink(Link link) {
		viewLinkController.showLinkDetails(link);
		changeTab(2);
	}

	public void goToEditLink(Link link) {
		editLinkController.showLinkDetails(link);
		changeTab(3);
	}

	public void goToDeleteLink(Link link) {
		deleteLinkController.showLinkDetails(link);
		changeTab(4);
	}
}
