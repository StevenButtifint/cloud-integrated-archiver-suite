package application.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import application.services.DatabaseService;

public class CreateLinkController extends SaveLink {

	private static final Logger logger = LogManager.getLogger(CreateLinkController.class.getName());

	public CreateLinkController(DatabaseService databaseService) {
		super(databaseService);
	}

	public void initialize() {
		super.initialize();
		saveButton.setOnAction(event -> saveLink());
	}

	public void setManageController(ManageController manageController) {
		this.manageController = manageController;
	}

	@Override
	protected void saveLink() {
		String name = getNameFieldString();
		String description = getDescriptionFieldString();
		String source = getSourceFieldString();
		String destination = getDestinationFieldString();
		Boolean syncModifed = syncModifiedBox.isSelected();
		Boolean syncDeleted = syncDeletedBox.isSelected();
		Boolean archiveBackup = syncAsArchiveBox.isSelected();

		if (validateContent(name, description, source, destination)) {
			if (databaseService.insertLink(name, description, source, destination, syncModifed, syncDeleted, archiveBackup)) {
				manageController.goToManageHome();
				manageController.refreshDashboard();
				clearFields();
				logger.info("Successfully Saved new link.");
			} else {
				logger.warn("Unable to save new link.");
				showErrorNotice();
			}
		}
	}

	private void showErrorNotice() {
	}

	private void clearFields() {
		setNameField("");
		setDescriptionField("");
		setSourceField("");
		setDestinationField("");
	}
}
