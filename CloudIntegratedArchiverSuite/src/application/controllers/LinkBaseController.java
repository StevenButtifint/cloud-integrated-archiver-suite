package application.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class LinkBaseController {

	@FXML
	protected Label nameLabel;

	@FXML
	protected Label descriptionLabel;

	@FXML
	protected Label lastUsedLabel;

	public void setLinkName(String name) {
		nameLabel.setText(name);
	}

	public void setLinkDescription(String description) {
		descriptionLabel.setText(description);
	}

	public void setLastUsed(String lastUsed) {
		lastUsedLabel.setText(lastUsed);
	}
}
