package optimization;

public interface Individual<T> {

	public T getData();

	public double[] getVariables();

	public double[] getWeight();
	public double[] getProfit();

	public double[][] getWeights();
	public double[][] getProfits();
}
