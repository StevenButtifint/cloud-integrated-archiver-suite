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


	protected void setFieldInvalid(TextField invalidField) {
		invalidField.getStyleClass().add("invalid-field");
	}

	protected void setFieldvalid(TextField validField) {
		validField.getStyleClass().remove("invalid-field");
	}

	public void setManageController(ManageController manageController) {
		this.manageController = manageController;
	}

	public void setNameField(String name) {
		nameField.setText(name);
	}

	public void setDescriptionField(String description) {
		descriptionField.setText(description);
	}

	public void setSourceField(String source) {
		sourceField.setText(source);
	}

	public void setDestinationField(String destination) {
		destinationField.setText(destination);
	}

	public String getNameFieldString() {
		return nameField.getText();
	}

	public String getDescriptionFieldString() {
		return descriptionField.getText();
	}

	public String getSourceFieldString() {
		return sourceField.getText();
	}

	public String getDestinationFieldString() {
		return destinationField.getText();
	}
}
