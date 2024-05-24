package application.interfaces;

public interface TaskCompleteListener<T> {
	void onTaskComplete(T result);

	void onTaskFailed(Exception e);
}
