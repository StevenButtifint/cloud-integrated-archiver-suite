package application.services;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import application.interfaces.ISchedulerService;

public class SchedulerService implements ISchedulerService {

	private static final Logger logger = LogManager.getLogger(SchedulerService.class.getName());

	private ScheduledExecutorService scheduler;

	public SchedulerService() {
		this.scheduler = Executors.newScheduledThreadPool(1);
	}

	@Override
	public void scheduleTask(Runnable task, long period) {
		scheduler.scheduleAtFixedRate(task, 0, period, TimeUnit.SECONDS);
	}

	@Override
	public void shutdown() {
		try {
			scheduler.shutdown();
			if (!scheduler.awaitTermination(60, TimeUnit.SECONDS)) {
				scheduler.shutdownNow();
				if (!scheduler.awaitTermination(60, TimeUnit.SECONDS)) {
					logger.error("Scheduler did not terminate");
				}
			}
		} catch (InterruptedException ie) {
			scheduler.shutdownNow();
			Thread.currentThread().interrupt();
		}
	}
}
