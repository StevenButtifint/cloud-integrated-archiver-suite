package application.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import application.database.DatabaseConnection;
import application.models.Link;

public class EditLinkController extends SaveLink {

	private static final Logger logger = LogManager.getLogger(EditLinkController.class.getName());

	private int linkID;

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

		if (validateContent(name, description, source, destination)) {
			databaseConnection = new DatabaseConnection();
			if (databaseConnection.connectToDatabase()) {
				databaseConnection.updateLink(linkID, name, description, source, destination);
				databaseConnection.closeConnection();
				manageController.goToManageHome();
				
				logger.info("Succesfully updated link content.");
			} else {
				logger.error("No database connection established to update link.");
			}
		}
	}
}
