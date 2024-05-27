package application.controllers;

import application.models.LinkOperationDetails;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class LinkOperationController {

	private LinkOperationDetails linkOperationDetails;

	@FXML
	private Label outcomeStatusLabel;

	@FXML
	private Label nameLabel;

	@FXML
	private Label descriptionLabel;

	@FXML
	private Label timePerformedLabel;

	public LinkOperationController(LinkOperationDetails linkOperationDetails) {
		this.linkOperationDetails = linkOperationDetails;
	}

	public void initialize() {
		initializeDetails();
	}

	private void initializeDetails() {
		nameLabel.setText(linkOperationDetails.getName());
		descriptionLabel.setText(linkOperationDetails.getDescription());
		outcomeStatusLabel.setText(linkOperationDetails.getOperationStateString());
		timePerformedLabel.setText(linkOperationDetails.getTimePerformed());
	}

}
