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


	private void setStateSyncing() {
		syncButton.setText("SYNCING...");
		syncButton.setTextFill(Color.web("#ffffff"));
		linkNotice.setText(syncLinkThread.getNoticeMessage());
		setBackgroundAnimation("#514e79", "#655ebd");
	}

	private void setStateCompleted() {
		syncButton.setText("SYNCED");
		syncButton.setTextFill(Color.web("#514e79"));
		linkNotice.setText(syncLinkThread.getNoticeMessage());
		setBackgroundAnimation("#524abd", "#524abd");
	}

	private void setStateTerminated() {
		syncButton.setText("RETRY");
		syncButton.setTextFill(Color.web("#ff0000"));
		linkNotice.setText(syncLinkThread.getNoticeMessage());
		setBackgroundAnimation("#603f61", "#603f61");
	}

	public void setStateAccessible() {
		syncButton.setDisable(false);
		availableStatus.setText("AVAILABLE");
		availableStatus.getStyleClass().setAll("link-available-label");
		backgroundPane.getStyleClass().remove("link-item-inaccessible");
		backgroundPane.getStyleClass().add("link-item-accessible");
		linkName.getStyleClass().setAll("link-available-text");
		linkDescription.getStyleClass().setAll("link-available-text");
		syncedLabel.getStyleClass().setAll("link-available-text");
	}

	public void setStateInaccessible() {
		syncButton.setDisable(true);
		availableStatus.setText("UNAVAILABLE");
		availableStatus.getStyleClass().setAll("link-unavailable-label");
		backgroundPane.getStyleClass().remove("link-item-accessible");
		backgroundPane.getStyleClass().add("link-item-inaccessible");
		linkName.getStyleClass().setAll("link-unavailable-text");
		linkDescription.getStyleClass().setAll("link-unavailable-text");
		syncedLabel.getStyleClass().setAll("link-unavailable-text");
	}

	public void setLinkName(String name) {
		linkName.setText(name);
	}

	public void setLinkDescription(String description) {
		linkDescription.setText(description);
	}
}
