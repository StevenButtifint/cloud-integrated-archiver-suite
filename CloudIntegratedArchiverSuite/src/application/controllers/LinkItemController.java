package application.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import application.models.Link;
import application.threads.AvailableLinkThread;
import application.threads.SyncLinkThread;
import application.threads.ThreadState;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class LinkItemController extends LinkBaseController {
	
	private static final Logger logger = LogManager.getLogger(LinkItemController.class.getName());

	private SyncLinkThread syncLinkThread;
	private AvailableLinkThread availableLinkThread;

	private Link link;

	@FXML
	private Label availableStatus;

	@FXML
	private Label linkNotice;

	@FXML
	private Button syncButton;

	@FXML
	private AnchorPane backgroundPane;



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
	}

	}

	private void setBackgroundAnimation(String colourA, String colourB) {
		Timeline timeline = new Timeline(
				new KeyFrame(Duration.ZERO, new KeyValue(backgroundRectangle.fillProperty(), Color.web(colourA))),
				new KeyFrame(Duration.seconds(1),
						new KeyValue(backgroundRectangle.fillProperty(), Color.web(colourB))));
		timeline.setCycleCount(Animation.INDEFINITE);
		timeline.setAutoReverse(true);
		timeline.play();
	}
}
