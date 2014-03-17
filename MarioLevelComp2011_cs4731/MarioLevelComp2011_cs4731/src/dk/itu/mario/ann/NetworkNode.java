package ann;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NetworkNode implements Serializable {

	private static final long serialVersionUID = 4111158235251688552L;
	
	private double nodeValue;
	
	private List<Double> outputWeights;
	
	private Random randGen;

	public NetworkNode(int nodeValue, int numberOut) {
		this.nodeValue = nodeValue;
		outputWeights = new ArrayList<Double>();
		randGen = new Random();
		for (int i = 0; i < numberOut; i++) {
			outputWeights.add((randGen.nextDouble() - 0.5) / 10.00);
		}
		return;
	}

	public void setNodeValue(double nodeValue) {
		this.nodeValue = nodeValue;
		return;
	}

	public void setOutputWeightAt(int index, double weight) {
		outputWeights.set(index, weight);
		return;
	}

	public void setOutPutWeight(List<Double> outputWeights) {
		this.outputWeights = outputWeights;
	}

	public double getNodeValue() {
		return nodeValue;
	}

	public double getOutputWeightAt(int index) {
		return outputWeights.get(index);
	}
}
