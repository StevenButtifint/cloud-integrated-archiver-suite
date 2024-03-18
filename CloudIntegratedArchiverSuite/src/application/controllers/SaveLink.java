package application.controllers;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import application.database.DatabaseConnection;
import application.util.FileExplorer;
import application.util.LinkValidator;
import application.util.ValidationResult;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

public class SaveLink extends LinkDetails {

	private static final Logger logger = LogManager.getLogger(SaveLink.class.getName());

	protected DatabaseConnection databaseConnection;

	@FXML
	protected Button saveButton;

	@FXML
	private Button localSourceButton;

	@FXML
	private Button localDestinationButton;

	public void initialize() {
		super.initialize();
		localSourceButton.setOnAction(event -> setLocalLocation(sourceField));
		localDestinationButton.setOnAction(event -> setLocalLocation(destinationField));
	}

	private void setLocalLocation(TextField outputField) {
		String location = FileExplorer.selectFolder();
		if (location != null) {
			outputField.setText(location);
		}
	}

	protected boolean validateContent(String name, String description, String source, String destination) {

		ValidationResult validationResult = LinkValidator.validate(name, description, source, destination);

		// check all fields where valid
		if (validationResult.isValid()) {
			clearInvalidFields();
			return true;

		} else {
			Map<String, Boolean> invalidResults = validationResult.getValidationStatus();
			updateValidFieldBorder(nameField, invalidResults.get("name"));
			updateValidFieldBorder(descriptionField, invalidResults.get("description"));
			updateValidFieldBorder(sourceField, invalidResults.get("source"));
			updateValidFieldBorder(destinationField, invalidResults.get("destination"));
			logger.info("Link content provided is invlaid.");
			return false;
		}
	}
}
