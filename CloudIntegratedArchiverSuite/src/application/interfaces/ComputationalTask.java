package application.interfaces;

@FunctionalInterface
public interface ComputationalTask<T> {
	T performTask();
}