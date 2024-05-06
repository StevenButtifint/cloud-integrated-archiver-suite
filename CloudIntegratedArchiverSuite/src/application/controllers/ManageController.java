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
