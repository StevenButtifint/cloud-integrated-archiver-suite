package application.interfaces;

import application.models.LinkOperationDetails;

public interface OperationManager {

	public void addOperation(LinkOperationDetails operationDetails);

	public void clearOperations();

	public boolean saveOperations();

}
