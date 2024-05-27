package application.models;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import application.enums.OperationState;

public class LinkOperationDetails {

	private static DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm:ss");

	private String name;

	private String description;

	private OperationState operationState;

	private String timePerformed;

	public LinkOperationDetails(String name, String description, OperationState operationState) {
		this.name = name;
		this.description = description;
		this.operationState = operationState;
		this.timePerformed = setCurrentTimeString();
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public String getOperationStateString() {
		return operationState.toString();
	}

	public String getTimePerformed() {
		return timePerformed;
	}

	private String setCurrentTimeString() {
		LocalTime currentTime = LocalTime.now();
		return currentTime.format(timeFormat);
	}

}
