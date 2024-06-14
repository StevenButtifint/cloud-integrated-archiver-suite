package application.interfaces;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import application.models.Link;

public interface IDashboardService {
	public CompletableFuture<List<Link>> getLinksAsync();

	public void shutdown();

}
