package application.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import application.models.Link;
import application.services.DatabaseService;

public class EditLinkController extends SaveLink {

	private static final Logger logger = LogManager.getLogger(EditLinkController.class.getName());

	private int linkID;

	public EditLinkController(DatabaseService databaseService) {
		super(databaseService);
	}

	public void initialize() {
		super.initialize();
		saveButton.setOnAction(event -> saveLink());
	}

	@Override
	public void showLinkDetails(Link link) {
		super.showLinkDetails(link);
		linkID = link.getId();
	}

	@Override
	protected void saveLink() {
		String name = getNameFieldString();
		String description = getDescriptionFieldString();
		String source = getSourceFieldString();
		String destination = getDestinationFieldString();
		Boolean syncModifed = syncModifiedBox.isSelected();
		Boolean syncDeleted = syncDeletedBox.isSelected();
		Boolean syncAsArchive = syncAsArchiveBox.isSelected();

		if (validateContent(name, description, source, destination)) {
			if (databaseService.updateLink(linkID, name, description, source, destination, syncModifed, syncDeleted, syncAsArchive)) {
				manageController.goToManageHome();
				manageController.refreshDashboard();
				logger.info("Succesfully updated link content.");
			} else {
				logger.warn("Unable to save new link.");
				showErrorNotice();
			}
		}
	}

	private void showErrorNotice() {
	}
}
