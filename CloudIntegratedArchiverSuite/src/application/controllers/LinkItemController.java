package application.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class LinkItemController {

	private SyncLinkThread syncLinkThread;
	private AvailableLinkThread availableLinkThread;

	private Link link;
	@FXML
	private Label linkName;

	@FXML
	private Label linkDescription;

	@FXML
	private Label availableStatus;

	@FXML
	private Label linkNotice;
	
	@FXML
	private Button syncButton;

	@FXML
	private AnchorPane backgroundPane;

	private static final Logger logger = Logger.getLogger(DatabaseConnection.class.getName());


	
	public void setLinkName(String name) {
		linkName.setText(name);
	}

	public void setLinkDescription(String description) {
		linkDescription.setText(description);
	}
	
}
