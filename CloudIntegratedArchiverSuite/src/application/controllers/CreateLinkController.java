package application.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import application.database.DatabaseConnection;

public class CreateLinkController extends SaveLink {

	private static final Logger logger = LogManager.getLogger(CreateLinkController.class.getName());

	public void initialize() {
		super.initialize();
		saveButton.setOnAction(event -> saveLink());
	}

	public void setManageController(ManageController manageController) {
		this.manageController = manageController;
	}

	public void saveLink() {
		String name = getNameFieldString();
		String description = getDescriptionFieldString();
		String source = getSourceFieldString();
		String destination = getDestinationFieldString();

		Boolean syncModifed = syncModifiedBox.isSelected();
		Boolean syncDeleted = syncDeletedBox.isSelected();
		Boolean archiveBackup = archiveBox.isSelected();

		if (validateContent(name, description, source, destination)) {
			databaseConnection = new DatabaseConnection();
			if (databaseConnection.connectToDatabase()) {
				databaseConnection.insertLink(name, description, source, destination);
				databaseConnection.closeConnection();
				manageController.goToManageHome();
				clearFields();
				logger.info("Successfully Saved new link.");
			} else {
				logger.error("No database connection established to save new link.");
			}
		}
	}
	
	private void clearFields() {
		setNameField("");
		setDescriptionField("");
		setSourceField("");
		setDestinationField("");
	}
}
