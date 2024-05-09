package application.services;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import application.database.DatabaseConnection;
import application.models.Link;

public class DashboardService {

	private DatabaseConnection databaseConnection;

	private ExecutorService executorService;

	public DashboardService(DatabaseConnection databaseConnection, ExecutorService executorService) {
		this.databaseConnection = databaseConnection;
		this.executorService = executorService;
	}

	public CompletableFuture<List<Link>> getLinksAsync() {
		// Submit the task to be processed asynchronously and return a CompletableFuture
		return CompletableFuture.supplyAsync(() -> getCurrentLinksList(), executorService);
	}

	// background process
	private List<Link> getCurrentLinksList() {
		return databaseConnection.getAllLinks();
	}

	public void shutdown() {
		executorService.shutdown();
		databaseConnection.closeConnection();
		try {
			if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
				executorService.shutdownNow();
			}
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}
}
