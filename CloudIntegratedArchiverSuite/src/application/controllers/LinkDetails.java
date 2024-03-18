package application.controllers;

import application.models.Link;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

public class LinkDetails {

	@FXML
	protected TextField nameField;

	@FXML
	protected TextField descriptionField;

	@FXML
	protected TextField sourceField;

	@FXML
	protected TextField destinationField;
	
	@FXML
	protected CheckBox syncModifiedBox;

	@FXML
	protected CheckBox syncDeletedBox;
	
	@FXML
	protected CheckBox archiveBox;

	@FXML
	private Button backButton;

	protected ManageController manageController;

	public void initialize() {
		backButton.setOnAction(event -> manageController.goToManageHome());
	}

}
