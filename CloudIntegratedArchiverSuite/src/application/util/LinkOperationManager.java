package application.util;

import java.util.ArrayList;
import java.util.List;

import application.interfaces.OperationManager;
import application.models.LinkOperationDetails;

public class LinkOperationManager implements OperationManager {

	private List<LinkOperationDetails> linkOperations;

	public LinkOperationManager() {
		this.linkOperations = new ArrayList<>();
	}

	public void addOperation(LinkOperationDetails operationDetails) {
		linkOperations.add(operationDetails);
	}

	public void clearOperations() {
		linkOperations.clear();
	}

}
