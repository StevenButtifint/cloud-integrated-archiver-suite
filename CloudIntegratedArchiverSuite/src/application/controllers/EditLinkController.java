package application.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import application.config.Config;
import application.database.DatabaseConnection;
import application.models.Link;

public class EditLinkController extends SaveLink {

	private static final Logger logger = LogManager.getLogger(EditLinkController.class.getName());

	private int linkID;
	
	private Config dbConfig;
	
	public EditLinkController(Config dbConfig) {
		this.dbConfig = dbConfig;
	}

	public void initialize() {
		super.initialize();
		saveButton.setOnAction(event -> saveChanges());
	}

	@Override
	public void showLinkDetails(Link link) {
		super.showLinkDetails(link);
		linkID = link.getId();
	}

	private void saveChanges() {
		String name = getNameFieldString();
		String description = getDescriptionFieldString();
		String source = getSourceFieldString();
		String destination = getDestinationFieldString();

		Boolean syncModifed = syncModifiedBox.isSelected();
		Boolean syncDeleted = syncDeletedBox.isSelected();
		Boolean syncAsArchive = syncAsArchiveBox.isSelected();

		if (validateContent(name, description, source, destination)) {
			databaseConnection = new DatabaseConnection(dbConfig);
			if (databaseConnection.connectToDatabase()) {
				databaseConnection.updateLink(linkID, name, description, source, destination, syncModifed, syncDeleted, syncAsArchive);
				databaseConnection.closeConnection();
				manageController.goToManageHome();
				
				logger.info("Succesfully updated link content.");
			} else {
				logger.error("No database connection established to update link.");
			}
		}
	}
}
