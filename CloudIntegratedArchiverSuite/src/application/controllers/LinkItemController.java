package application.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import application.config.Config;
import application.enums.OperationState;
import application.interfaces.IMonitorService;
import application.models.Link;
import application.threads.SyncLinkThread;
import application.util.FileOperations;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class LinkItemController extends LinkBaseController {

	private static final Logger logger = LogManager.getLogger(LinkItemController.class.getName());
	
	private IMonitorService monitorService;

	private SyncLinkThread syncLinkThread;

	private Config appConfig;

	private Config dbConfig;

	@FXML
	private Label availableStatus;

	@FXML
	private Label linkNotice;

	@FXML
	private Button syncButton;

	@FXML
	private ImageView syncImage;

	@FXML
	private AnchorPane backgroundPane;

	@FXML
	private Rectangle backgroundRectangle;

	public LinkItemController(IMonitorService monitorService, Link link, Config appConfig, Config dbConfig) {
		super(link);
		this.monitorService = monitorService;
		this.appConfig = appConfig;
		this.dbConfig = dbConfig;
	}

	@FXML
	public void initialize() {
		super.initialize();
		updateAvailableStateUI(isAccessible());
	}

	private void initialiseBackgroundRectangle() {
		backgroundRectangle.widthProperty().bind(backgroundPane.widthProperty());
		backgroundRectangle.heightProperty().bind(backgroundPane.heightProperty());
	}

	@FXML
	private void syncLink() {
		syncButton.setDisable(true);
		syncLinkThread = new SyncLinkThread(dbConfig, this::updateStateUI, this::setSyncedLabel, link);
		syncLinkThread.start();
	}

	private void updateStateUI(OperationState threadState) {
		Platform.runLater(() -> {
			initialiseBackgroundRectangle();
			monitorService.addNewOperation(link, threadState);
			switch (threadState) {
			case STARTED:
				logger.info("Link Sync started.");
				setStateSyncing();
				break;
			case RUNNING:
				logger.info("Link Sync Running.");
				setStateSyncing();
				break;
			case COMPLETED:
				logger.info("Link Sync Completed.");
				setStateCompleted();
				break;
			case TERMINATED:
				logger.info("Link Sync Terminated.");
				setStateTerminated();
				break;
			}
		});
	}

	public void updateAvailableStateUI(Boolean availableState) {
		if (availableState) {
			setStateAccessible();
		} else {
			setStateInaccessible();
		}
	}

	private void setStateSyncing() {
		linkNotice.setText(syncLinkThread.getNoticeMessage());
		setBackgroundAnimation(appConfig.getProperty("colour.syncing.start"), appConfig.getProperty("colour.syncing.end"));
	}

	private void setStateCompleted() {
		syncButton.getStyleClass().remove("link-button");
		syncButton.getStyleClass().add("link-button-complete");
		setButtonIcon("img.check", 0);
		linkNotice.setText(syncLinkThread.getNoticeMessage());
		setBackgroundAnimation(appConfig.getProperty("colour.link.complete"), appConfig.getProperty("colour.link.complete"));
		syncButton.setDisable(false);
	}

	private void setStateTerminated() {
		linkNotice.setText(syncLinkThread.getNoticeMessage());
		setButtonIcon("img.cross", 0);
		setBackgroundAnimation(appConfig.getProperty("colour.link.terminated"), appConfig.getProperty("colour.link.terminated"));
		syncButton.setDisable(false);
	}

	public void setStateAccessible() {
		syncButton.setDisable(false);
		availableStatus.setText("AVAILABLE");
		availableStatus.getStyleClass().setAll("link-available-label");
		backgroundPane.getStyleClass().remove("link-item-inaccessible");
		backgroundPane.getStyleClass().add("link-item-accessible");
		nameLabel.getStyleClass().setAll("link-available-text");
		descriptionLabel.getStyleClass().setAll("link-available-text");
		lastUsedLabel.getStyleClass().setAll("link-available-text");
	}

	public void setStateInaccessible() {
		syncButton.setDisable(true);
		availableStatus.setText("UNAVAILABLE");
		availableStatus.getStyleClass().setAll("link-unavailable-label");
		backgroundPane.getStyleClass().remove("link-item-accessible");
		backgroundPane.getStyleClass().add("link-item-inaccessible");
		nameLabel.getStyleClass().setAll("link-unavailable-text");
		descriptionLabel.getStyleClass().setAll("link-unavailable-text");
		lastUsedLabel.getStyleClass().setAll("link-unavailable-text");
	}

	private void setButtonIcon(String image, int translateX) {
		syncImage.setImage(new Image(appConfig.getProperty(image)));
		syncImage.setTranslateX(translateX);
	}

	public void setLink(Link link) {
		this.link = link;
	}

	public void setSyncedLabel(String lastSynced) {
		Platform.runLater(() -> {
			lastUsedLabel.setText(lastSynced);
		});
	}

	private void setBackgroundAnimation(String colourA, String colourB) {
		Timeline timeline = new Timeline(
				new KeyFrame(Duration.ZERO, new KeyValue(backgroundRectangle.fillProperty(), Color.web(colourA))),
				new KeyFrame(Duration.seconds(1), new KeyValue(backgroundRectangle.fillProperty(), Color.web(colourB))));
		timeline.setCycleCount(Animation.INDEFINITE);
		timeline.setAutoReverse(true);
		timeline.play();
	}

	public String getAvailableStatus() {
		return availableStatus.getText();
	}

	public boolean isAccessible() {
		return FileOperations.validDirectory(link.getSource()) && FileOperations.validDirectory(link.getDestination());
	}

}
