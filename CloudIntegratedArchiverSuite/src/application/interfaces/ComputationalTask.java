package application.interfaces;

@FunctionalInterface
public interface ComputationalTask<T> {
	public T performTask();
}