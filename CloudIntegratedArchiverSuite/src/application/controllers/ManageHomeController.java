package application.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class ManageHomeController extends BaseController {

	private ManageController manageController;

	@FXML
	private Button newLink;

	public void initialize() {

		newLink.setOnAction(event -> manageController.goToCreateLink());

	}

	public void setParentController(ManageController manageController) {
		this.manageController = manageController;
	}
}
