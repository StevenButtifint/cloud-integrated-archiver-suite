package application.controllers;

import application.models.Link;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class LinkManageController extends LinkBaseController {

	@FXML
	private Button viewButton;

	@FXML
	private Button editButton;

	@FXML
	private Button deleteButton;

	private ManageController manageController;

	private Link link;

	public LinkManageController(ManageController manageController, Link link) {
		this.manageController = manageController;
		this.link = link;
	}

	public void initialiseButtons() {
		viewButton.setOnAction(event -> viewLink());
		editButton.setOnAction(event -> editLink());
		deleteButton.setOnAction(event -> deleteLink());
	}

	private void viewLink() {
		manageController.goToViewLink(link);
	}

	private void editLink() {
		manageController.goToEditLink(link);
	}

	private void deleteLink() {
		manageController.goToDeleteLink(link);
	}
}
