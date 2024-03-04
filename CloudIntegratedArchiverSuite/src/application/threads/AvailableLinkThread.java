package application.threads;

import java.sql.Connection;
import java.util.function.Consumer;

import application.database.DatabaseConnection;
import application.models.Link;
import application.util.FileOperations;

public class AvailableLinkThread extends Thread {

	private static final int CHECK_FREQUENCY_MS = 3000;
	
	private Consumer<Boolean> uiAvailableUpdater;
	
	private Link link;

	public AvailableLinkThread(Consumer<Boolean> uiUpdater, Link link) {
		this.uiAvailableUpdater = uiUpdater;
		this.link = link;
	}

	@Override
	public void run() {
		while (true) {
			try {
				// validate source location exists and is a directory
				if (!FileOperations.validDirectory(link.getSource())) {
					uiAvailableUpdater.accept(false);
					//////// update db if different
				}
				// validate destination location exists and is a directory
				else if (!FileOperations.validDirectory(link.getDestination())) {
					uiAvailableUpdater.accept(false);
				} else {
					uiAvailableUpdater.accept(true);
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
