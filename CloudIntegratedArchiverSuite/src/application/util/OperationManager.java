package application.util;

import java.util.ArrayList;
import java.util.List;

import application.models.LinkOperationDetails;

public class OperationManager {
	
	private List<LinkOperationDetails> linkOperations;
	
	public OperationManager() {
		this.linkOperations = new ArrayList<>();
	}
	
	public void addSyncOperation(LinkOperationDetails operationDetails) {
		linkOperations.add(operationDetails);
	}
}
