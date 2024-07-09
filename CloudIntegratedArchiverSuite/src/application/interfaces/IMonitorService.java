package application.interfaces;

import application.enums.OperationState;
import application.models.Link;

public interface IMonitorService {
	public void initializeControllerManager();

	public void addNewOperation(Link link, OperationState operationState);
	
	public void refreshUtilisationStats();
	
	public void shutdown();
}
