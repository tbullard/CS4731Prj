package optimization;

public interface MutationFunction<T> {

	abstract public Individual<T> mutate(Individual<T> individual);
}
