package optimization;

public interface EvaluationFunction<T> {
	
	double value(Individual<T> individual);
}
