package application.controllers;

import java.io.IOException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import application.config.Config;
import application.models.Link;
import application.services.DatabaseService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class ManageHomeController {

	private static final Logger logger = LogManager.getLogger(ManageHomeController.class.getName());

	private DatabaseService databaseService;

	private Config appConfig;

	private ManageController manageController;

	@FXML
	private Button newLink;

	@FXML
	private VBox manageLinkList;

	public ManageHomeController(Config appConfig, DatabaseService databaseService) {
		this.appConfig = appConfig;
		this.databaseService = databaseService;
	}

	public void initialize() {
		newLink.setOnAction(event -> manageController.goToCreateLink());
		refreshLinksList();
	}

	public void setManageController(ManageController manageController) {
		this.manageController = manageController;
	}
}
