package optimization;

public class MultiKnapsackEvaluationFunction<T> implements EvaluationFunction<T> {

	private double maxWeight;
	private int knapsacks;
	private double[] partitionWeights;
	private double[] ratios;
	public static double[] P;

	public MultiKnapsackEvaluationFunction(double maxWeight, double[] ratios) {
		this.maxWeight = maxWeight;
		this.knapsacks = ratios.length;
		this.ratios	= ratios;
		this.partitionWeights = new double[ratios.length];
		MultiKnapsackEvaluationFunction.P = new double[ratios.length];
		for(int i=0; i < ratios.length; i++) this.partitionWeights[i] = this.maxWeight * ratios[i];
		for(int i=0; i < ratios.length; i++) MultiKnapsackEvaluationFunction.P[i] = this.partitionWeights[i];
	}

	@Override
	public double value(Individual<T> individual) {
		double value = 0.0;
		double weight[] = new double[knapsacks];
		
		for(int knapsack = 0; knapsack < knapsacks; knapsack++) weight[knapsack] = 0;
		
		double[] variables		= individual.getVariables();
		double[][] weights		= individual.getWeights();
		double[][] profits		= individual.getProfits();

		boolean[] overWeight = new boolean[knapsacks];
		for(int knapsack = 0; knapsack < knapsacks; knapsack++) {
			overWeight[knapsack] = false;
//			//TODO: should we ignore the weight of 0 bags, or not? -- Re: ignoring allows to not break constraints instantly if there is no satisfying building block
//			if(partitionWeights[knapsack] != 0){
//				weight[knapsack] = 0;
//				for(int i =0; i < variables.length; i++)	{
//					weight[knapsack] += weights[i][knapsack] * variables[i];
//					value	+= (profits[i][knapsack] * variables[i] * (overWeight[knapsack] ? -1 : 1)) * this.ratios[knapsack];
//					overWeight[knapsack] = weight[knapsack] > partitionWeights[knapsack];
//				}
//			}
			weight[knapsack] = 0;
			for(int i =0; i < variables.length; i++)	{
				weight[knapsack] += weights[i][knapsack] * variables[i];
				value	+= (profits[i][knapsack] * variables[i] ) * this.ratios[knapsack];
				overWeight[knapsack] = weight[knapsack] > partitionWeights[knapsack];
			}
		}

//		int constraintsUnsatisfied = 0;
//		for(int knapsack = 0; knapsack < knapsacks; knapsack++) {
//			if(weight[knapsack] > partitionWeights[knapsack]) constraintsUnsatisfied++;
//		}
//		return constraintsUnsatisfied == 0 ? value : ((knapsacks+1.0)/(constraintsUnsatisfied+1.0)) * Double.MIN_VALUE;
		return value;
	}
}
