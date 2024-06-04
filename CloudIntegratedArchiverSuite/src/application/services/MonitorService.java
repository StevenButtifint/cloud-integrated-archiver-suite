package application.services;

import application.controllers.MonitorController;
import application.enums.OperationState;
import application.interfaces.OperationManager;
import application.models.Link;
import application.models.LinkOperationDetails;

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
		operationManager.addOperation(linkOperationDetails);
	}

}
