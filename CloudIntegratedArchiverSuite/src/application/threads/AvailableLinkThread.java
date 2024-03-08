package application.threads;

import java.util.function.Consumer;

import application.database.DatabaseConnection;
import application.models.Link;
import application.util.FileOperations;

public class AvailableLinkThread extends Thread {

	private static final int CHECK_FREQUENCY_MS = 3000;

	private Consumer<Boolean> uiAvailableUpdater;

	private Link link;

	private boolean availableState;

	public AvailableLinkThread(Consumer<Boolean> uiUpdater, Link link) {
		this.uiAvailableUpdater = uiUpdater;
		this.link = link;
		this.availableState = link.getAccessible();
	}

	@Override
	public void run() {

		DatabaseConnection databaseConnection = new DatabaseConnection();
		if (databaseConnection.connectToDatabase()) {
			uiAvailableUpdater.accept(availableState);
			while (true) {
				try {
					// validate source and destination location exists and are directories
					boolean currentState = isAccessible();
					if (currentState != availableState) {
						uiAvailableUpdater.accept(currentState);
					}
					Thread.sleep(CHECK_FREQUENCY_MS);
					
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
	
				System.out.println("This Should repeat");
				break;
			}
		}
	}

	private boolean isAccessible() {
		return FileOperations.validDirectory(link.getSource()) && FileOperations.validDirectory(link.getDestination());
	}
}
