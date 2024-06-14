package application.interfaces;

public interface TaskCompleteListener<T> {
	public void onTaskComplete(T result);

	public void onTaskFailed(Exception e);
}
