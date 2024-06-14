package application.services;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import application.interfaces.IDashboardService;
import application.interfaces.IDatabaseService;
import application.models.Link;

public class DashboardService implements IDashboardService {

	private IDatabaseService databaseService;

	private ExecutorService executorService;

	public DashboardService(IDatabaseService databaseService, ExecutorService executorService) {
		this.databaseService = databaseService;
		this.executorService = executorService;
	}

	@Override
	public CompletableFuture<List<Link>> getLinksAsync() {
		// Submit the task to be processed asynchronously and return a CompletableFuture
		return CompletableFuture.supplyAsync(() -> getCurrentLinksList(), executorService);
	}

	// background process
	private List<Link> getCurrentLinksList() {
		return databaseService.getAllLinks();
	}

	public void shutdown() {
		executorService.shutdown();
		try {
			if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
				executorService.shutdownNow();
			}
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}
}
