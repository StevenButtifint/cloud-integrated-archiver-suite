package application.interfaces;

public interface ISchedulerService {
	public void scheduleTask(Runnable task, long period);

	public void shutdown();
}
