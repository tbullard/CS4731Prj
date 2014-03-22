package optimization;


public interface OptimizationProblem<T> {

	Individual<T>	createRandom();
	double			value(Individual<T> individual);
}
