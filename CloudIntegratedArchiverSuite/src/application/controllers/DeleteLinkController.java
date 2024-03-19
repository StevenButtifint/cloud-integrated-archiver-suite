package application.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import application.database.DatabaseConnection;
import application.models.Link;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class DeleteLinkController extends LinkDetails {
	
	private static final Logger logger = LogManager.getLogger(DeleteLinkController.class.getName());
	
	private DatabaseConnection databaseConnection;
	
	@FXML
	private Button deleteButton;
	
	private int linkID;
	
	public void initialize() {
		super.initialize();
		deleteButton.setOnAction(event -> deleteLink());
	}
	
	@Override
	public void showLinkDetails(Link link) {
		super.showLinkDetails(link);
		linkID = link.getId();
	}
	
	private void deleteLink() {
		databaseConnection = new DatabaseConnection();
		if (databaseConnection.connectToDatabase()) {
			databaseConnection.deleteLinkById(linkID);
			databaseConnection.closeConnection();
			manageController.goToManageHome();
			logger.info("Succesfully deleted link.");
		} else {
			logger.error("No database connection established to delete link.");
		}
	}
}
