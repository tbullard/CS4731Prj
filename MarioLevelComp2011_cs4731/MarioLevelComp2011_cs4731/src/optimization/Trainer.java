package optimization;

public abstract class Trainer<T> {

	public static double train(OptimizationAlgorithm<?> optimizationAlgorithm, int iterations) {
		double sum = 0;
		
		for(int i  = 0; i < iterations; i++)
			sum += optimizationAlgorithm.train();
		
		return sum;
	}

}
