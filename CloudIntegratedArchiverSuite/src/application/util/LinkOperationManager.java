package application.util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import application.interfaces.OperationManager;
import application.models.LinkOperationDetails;

public class LinkOperationManager implements OperationManager {

	private static final Logger logger = LogManager.getLogger(LinkOperationManager.class.getName());

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

	public boolean saveOperations() {
		String timePostfix = LocalTime.now().format(DateTimeFormatter.ofPattern("HH-mm-ss"));
		String fileName = "operations-save_" + timePostfix + ".txt";

		try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
			for (LinkOperationDetails linkOperation : linkOperations) {
				writer.write(linkOperation.getDetailsFormatted());
				writer.newLine();
			}
			return true;
		} catch (IOException e) {
			logger.error("Unable to save operations file.", e);
		}
		return false;
	}
}
