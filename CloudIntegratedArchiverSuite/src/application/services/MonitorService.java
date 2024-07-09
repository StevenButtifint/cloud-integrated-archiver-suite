package application.services;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.management.ManagementFactory;

import com.sun.management.OperatingSystemMXBean;

import application.controllers.MonitorController;
import application.enums.OperationState;
import application.interfaces.IMonitorService;
import application.interfaces.OperationManager;
import application.models.Link;
import application.models.LinkOperationDetails;
import javafx.application.Platform;

public class MonitorService implements IMonitorService {

	private static final Logger logger = LogManager.getLogger(MonitorService.class.getName());

	private MonitorController monitorController;

	private OperationManager operationManager;

	private ExecutorService executorService;

	public MonitorService(MonitorController monitorController, OperationManager operationManager, ExecutorService executorService) {
		this.monitorController = monitorController;
		this.operationManager = operationManager;
		this.executorService = executorService;
	}

	@Override
	public void initializeControllerManager() {
		monitorController.setOperationManager(operationManager);
	}

	@Override
	public void addNewOperation(Link link, OperationState operationState) {
		LinkOperationDetails linkOperationDetails = new LinkOperationDetails(link.getName(), link.getDescription(), operationState);
		monitorController.addNewOperation(linkOperationDetails);
		monitorController.updateActiveLinks(operationState);
		operationManager.addOperation(linkOperationDetails);
	}

	public void refreshUtilisationStats() {
	}

	public void shutdown() {
		Platform.runLater(() -> {
			monitorController.clearUsageStats();
		});
		executorService.shutdown();
		try {
			if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
				executorService.shutdownNow();
			}
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}

}
