package application.services;

import application.controllers.MonitorController;
import application.models.LinkOperationDetails;
import application.util.OperationManager;

public class MonitorService {

	private MonitorController monitorController;

	private OperationManager operationManager;

	public MonitorService(MonitorController monitorController, OperationManager operationManager) {
		this.monitorController = monitorController;
		this.operationManager = operationManager;
	}
	
	public void addNewOperation(LinkOperationDetails linkOperationDetails) {
		monitorController.addNewOperation(linkOperationDetails);
		operationManager.addSyncOperation(linkOperationDetails);
	}

}
