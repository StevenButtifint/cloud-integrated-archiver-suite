package application.controllers;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import application.config.Config;
import application.models.LinkOperationDetails;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

public class MonitorController {

	private static final Logger logger = LogManager.getLogger(MonitorController.class.getName());

	private Config appConfig;

	@FXML
	private VBox operationsVBox;

	public MonitorController(Config appConfig) {
		this.appConfig = appConfig;
	}

	public void initialize() {
	}

}
