package application.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class LinkItemController {
	
	@FXML
	Label linkName;
	
	@FXML
	Label linkDescription;
	
	public void setLinkName(String name) {
		linkName.setText(name);
	}
	
	public void setLinkDescription(String description) {
		linkDescription.setText(description);
	}
	
}
