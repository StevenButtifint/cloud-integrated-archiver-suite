package application.controllers;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import application.config.Config;
import application.enums.OperationState;
import application.models.LinkOperationDetails;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class MonitorController {

	private static final Logger logger = LogManager.getLogger(MonitorController.class.getName());

	private Config appConfig;

	@FXML
	private VBox operationsVBox;
	
	@FXML
	private Label activeEventsLabel;
	
	private int activeEventsCount;

	public MonitorController(Config appConfig) {
		this.appConfig = appConfig;
	}

	public void initialize() {
	}

	public void addNewOperation(LinkOperationDetails linkOperationDetails) {
		LinkOperationController linkOperationController = new LinkOperationController(linkOperationDetails);
		FXMLLoader loader = new FXMLLoader(linkOperationController.getClass().getResource(appConfig.getProperty("view.path.linkoperation")));
		loader.setController(linkOperationController);
		Node newOperation;
		try {
			newOperation = loader.load();
			operationsVBox.getChildren().add(newOperation);
		} catch (IOException e) {
			logger.error("Unable to initalise new link operation controller" + e.getMessage(), e);
		}
	}
	
	public void updateActiveLinks(OperationState operationState) {
		if ((operationState == OperationState.COMPLETED) || (operationState == OperationState.TERMINATED)) {
			activeEventsCount -= 1;
		} else {
			activeEventsCount += 1;
		}
		activeEventsLabel.setText(String.valueOf(activeEventsCount));
	}
}
