package application.controllers;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import application.config.Config;
import application.enums.OperationState;
import application.interfaces.OperationManager;
import application.models.LinkOperationDetails;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class MonitorController {

	private static final Logger logger = LogManager.getLogger(MonitorController.class.getName());

	private OperationManager operationManager;

	private Config appConfig;

	@FXML
	private Label cpuLabel;

	@FXML
	private Label diskLabel;

	@FXML
	private Label ramLabel;

	@FXML
	private Button saveButton;

	@FXML
	private Button clearButton;

	@FXML
	private VBox operationsVBox;

	@FXML
	private Label activeEventsLabel;

	private int activeEventsCount;

	public MonitorController(Config appConfig) {
		this.appConfig = appConfig;
	}

	public void initialize() {
		saveButton.setOnAction(event -> operationManager.saveOperations());
		clearButton.setOnAction(event -> clearOperations());
	}

	public void setOperationManager(OperationManager operationManager) {
		this.operationManager = operationManager;
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

	private void clearOperations() {
		operationManager.clearOperations();
		clearOperationsWidget();
	}

	private void clearOperationsWidget() {
		operationsVBox.getChildren().clear();
	}

	public synchronized void updateActiveLinks(OperationState operationState) {
		if ((operationState == OperationState.COMPLETED) || (operationState == OperationState.TERMINATED)) {
			activeEventsCount -= 1;
		} else {
			activeEventsCount += 1;
		}
		updateActiveEventsLabel();
	}

	private synchronized void updateActiveEventsLabel() {
		activeEventsLabel.setText(String.valueOf(activeEventsCount));
	}

	public synchronized boolean activeEvents() {
		return activeEventsCount > 0;
	}

	public void setCPUUsage(int utilisationPercentage) {
		cpuLabel.setText("CPU: " + utilisationPercentage + "%");
	}

	public void setDiskUsage(int utilisationPercentage) {
		diskLabel.setText("Disk: " + utilisationPercentage + "%");
	}

	public void setRamUsage(int utilisationPercentage) {
		ramLabel.setText("RAM: " + utilisationPercentage + "%");
	}

	public void clearCPUUsage() {
		cpuLabel.setText("CPU: -%");
	}

	public void clearDiskUsage() {
		diskLabel.setText("CPU: -%");
	}

	public void clearRamUsage() {
		ramLabel.setText("CPU: -%");
	}
	
	public void clearUsageStats() {
		clearCPUUsage();
		clearDiskUsage();
		clearRamUsage();
	}

}
