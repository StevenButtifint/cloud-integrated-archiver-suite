package application.controllers;

import application.models.Link;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class LinkBaseController {

	protected Link link;

	@FXML
	protected Label nameLabel;

	@FXML
	protected Label descriptionLabel;

	@FXML
	protected Label lastUsedLabel;

	public LinkBaseController(Link link) {
		this.link = link;
	}

	@FXML
	public void initialize() {
		initializeDetails();
	}

	protected void initializeDetails() {
		if (link != null) {
			setLinkName(link.getName());
			setLinkDescription(link.getDescription());
			setLastUsed(link.sinceSyncedString());
		}
	}

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
