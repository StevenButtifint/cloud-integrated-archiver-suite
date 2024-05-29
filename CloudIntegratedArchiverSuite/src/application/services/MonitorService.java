package application.services;

import application.controllers.MonitorController;
import application.enums.OperationState;
import application.models.Link;
import application.models.LinkOperationDetails;
import application.util.OperationManager;

public class MonitorService {

	private MonitorController monitorController;

	private OperationManager operationManager;

	public MonitorService(MonitorController monitorController, OperationManager operationManager) {
		this.monitorController = monitorController;
		this.operationManager = operationManager;
	}

	public void addNewOperation(Link link, OperationState operationState) {
		LinkOperationDetails linkOperationDetails = new LinkOperationDetails(link.getName(), link.getDescription(), operationState);
		monitorController.addNewOperation(linkOperationDetails);
		monitorController.updateActiveLinks(operationState);
		operationManager.addSyncOperation(linkOperationDetails);
	}

}
