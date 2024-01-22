package application.services;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import application.threads.AvailableLinksThread;

public class DashboardService extends Service<Void> {
	@Override
	protected Task<Void> createTask() {
		return new AvailableLinksThread();
	}

}
