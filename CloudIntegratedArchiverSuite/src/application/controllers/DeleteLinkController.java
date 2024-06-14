package application.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import application.interfaces.IDatabaseService;
import application.models.Link;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class DeleteLinkController extends LinkDetails {

	private static final Logger logger = LogManager.getLogger(DeleteLinkController.class.getName());

	private IDatabaseService databaseService;

	@FXML
	private Button deleteButton;

	private int linkID;

	public DeleteLinkController(IDatabaseService databaseService) {
		this.databaseService = databaseService;
	}

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
		if (databaseService.deleteLinkById(linkID)) {
			manageController.goToManageHome();
			manageController.refreshDashboard();
			logger.info("Succesfully deleted link.");
		} else {
			logger.warn("Unable to delete link.");
			showErrorNotice();
		}
	}

	private void showErrorNotice() {
	}

}
