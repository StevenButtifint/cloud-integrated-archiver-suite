package application.threads;

import java.util.concurrent.Callable;

import application.interfaces.ComputationalTask;
import application.interfaces.TaskCompleteListener;

public class WorkerThread<T> implements Callable<T> {
	private ComputationalTask<T> task;
	private TaskCompleteListener<T> listener;
	private volatile boolean terminate = false;

	public WorkerThread(ComputationalTask<T> task, TaskCompleteListener<T> listener) {
		this.task = task;
		this.listener = listener;
	}

	@Override
	public T call() {
		try {
			while (!terminate) {
				T result = task.performTask();
				listener.onTaskComplete(result);
				return result;
			}
		} catch (Exception e) {
			listener.onTaskFailed(e);
		}
		return null;
	}

	public void stopExecution() {
		terminate = true;
	}
}
