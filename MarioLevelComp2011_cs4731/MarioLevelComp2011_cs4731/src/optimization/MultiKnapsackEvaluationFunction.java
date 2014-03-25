package optimization;

public class MultiKnapsackEvaluationFunction<T> implements EvaluationFunction<T> {

	private double maxWeight;
	private double[] partitionWeights;
	private int knapsacks;

	public MultiKnapsackEvaluationFunction(double maxWeight, double[] ratios) {
		this.maxWeight = maxWeight;
		this.knapsacks = ratios.length;
		this.partitionWeights = new double[ratios.length];
		for(int i=0; i < ratios.length; i++) this.partitionWeights[i] = maxWeight * ratios[i];
	}

	@Override
	public double value(Individual<T> individual) {
		double value = 0.0;
		double weight[] = new double[knapsacks];
		
		for(int knapsack = 0; knapsack < knapsacks; knapsack++) weight[knapsack] = 0;
		
		double[] variables		= individual.getVariables();
		double[][] weights		= individual.getWeights();
		double[][] profits		= individual.getProfits();
		
		for(int knapsack = 0; knapsack < knapsacks; knapsack++)
			for(int i =0; i < variables.length; i++)	{
				weight[knapsack] += weights[i][knapsack] * variables[i];
				value	+= profits[i][knapsack] * variables[i];
			}
		
		for(int knapsack = 0; knapsack < knapsacks; knapsack++) {
			if(weight[knapsack] > partitionWeights[knapsack]) return Double.MIN_VALUE;
		}
		
		return value;
	}
}
