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

public class CreateLinkController extends BaseController {

	private static final Logger logger = LogManager.getLogger(CreateLinkController.class.getName());

	private DatabaseConnection databaseConnection;
	@FXML
	private ManageController manageController;

	@FXML
	private Button localSourceButton;

	@FXML
	private Button localDestinationButton;

	@FXML
	private TextField nameField;

	@FXML
	private TextField descriptionField;

	@FXML
	private TextField sourceField;

	@FXML
	private TextField destinationField;

	@FXML
	private Button backButton;

	@FXML
	private Button saveLinkButton;

	@FXML
	private CheckBox syncModifiedBox;

	@FXML
	private CheckBox syncDeletedBox;

}
