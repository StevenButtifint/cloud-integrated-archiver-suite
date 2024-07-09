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

	public LinkManageController(ManageController manageController, Link link) {
		super(link);
		this.manageController = manageController;
	}

	@FXML
	public void initialize() {
		super.initialize();
		initialiseButtons();
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

	public void setLink(Link link) {
		this.link = link;
	}

	public void setManageController(ManageController manageController) {
		this.manageController = manageController;
	}
}
