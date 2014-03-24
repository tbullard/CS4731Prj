package ann;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FFNeuralNetwork implements NeuralNetwork, Serializable {

    private static final long serialVersionUID = 8482705242029105280L;

    public enum NetworkLevel {
        Input, Hidden, Output
    };

    private HashMap<NetworkLevel, HashMap<Integer, NetworkNode>> neuralMap;
    private HashMap<NetworkLevel, Integer> nodeLevelNumber;
    private NetworkNode biasNode;

    public FFNeuralNetwork(int inputs, int hiddens, int outputs) {
        nodeLevelNumber = new HashMap<NetworkLevel, Integer>();
        nodeLevelNumber.put(NetworkLevel.Input, inputs);
        nodeLevelNumber.put(NetworkLevel.Hidden, hiddens);
        nodeLevelNumber.put(NetworkLevel.Output, outputs);
        neuralMap = new HashMap<NetworkLevel, HashMap<Integer, NetworkNode>>();
        neuralMap.put(NetworkLevel.Input, new HashMap<Integer, NetworkNode>());
        neuralMap.put(NetworkLevel.Hidden, new HashMap<Integer, NetworkNode>());
        neuralMap.put(NetworkLevel.Output, new HashMap<Integer, NetworkNode>());
        initializeNetwork();
        return;
    }

    private void initializeNetwork() {
        for (NetworkLevel level : neuralMap.keySet()) {
            HashMap<Integer, NetworkNode> workingMap = neuralMap.get(level);
            int numberOut = 0;
            if (level == NetworkLevel.Input) {
                numberOut = nodeLevelNumber.get(NetworkLevel.Hidden);
            } else if (level == NetworkLevel.Hidden) {
                numberOut = nodeLevelNumber.get(NetworkLevel.Output);
            }
            for (int i = 0; i < nodeLevelNumber.get(level); i++) {
                NetworkNode newNode = new NetworkNode(1, numberOut);
                workingMap.put(i, newNode);
            }
        }
        biasNode = new NetworkNode(1, (nodeLevelNumber.get(NetworkLevel.Output) + nodeLevelNumber.get(NetworkLevel.Hidden)));
        return;
    }

    public List<Double> classifyInstance(Datum attributesDatum) {
        List<Double> attributesList = attributesDatum.getAttributes();
        for (int i = 0; i < nodeLevelNumber.get(NetworkLevel.Input); i++) {
            neuralMap.get(NetworkLevel.Input).get(i).setNodeValue(attributesList.get(i));
        }
        for (int i = 0; i < nodeLevelNumber.get(NetworkLevel.Hidden); i++) {
            double totalHiddenInput = 0;
            for (int j = 0; j < nodeLevelNumber.get(NetworkLevel.Input); j++) {
                double inputNodeValue = neuralMap.get(NetworkLevel.Input).get(j).getNodeValue();
                double inputNodeWeight = neuralMap.get(NetworkLevel.Input).get(j).getOutputWeightAt(i);
                totalHiddenInput += inputNodeValue * inputNodeWeight;
            }
            totalHiddenInput += biasNode.getOutputWeightAt(i) * biasNode.getNodeValue();
            double hiddenNodeValue = 1.0 / (1 + Math.pow(Math.E, -totalHiddenInput));
            neuralMap.get(NetworkLevel.Hidden).get(i).setNodeValue(hiddenNodeValue);
        }
        ArrayList<Double> intermediateValues = new ArrayList<Double>();
        double simpleTotal = 0;
        for (int i = 0; i < nodeLevelNumber.get(NetworkLevel.Output); i++) {
            double totalOutputInput = 0;
            for (int j = 0; j < nodeLevelNumber.get(NetworkLevel.Hidden); j++) {
                double hiddenNodeValue = neuralMap.get(NetworkLevel.Hidden).get(j).getNodeValue();
                double hiddenNodeWeight = neuralMap.get(NetworkLevel.Hidden).get(j).getOutputWeightAt(i);
                totalOutputInput += hiddenNodeValue * hiddenNodeWeight;
            }
            totalOutputInput += biasNode.getOutputWeightAt(i + nodeLevelNumber.get(NetworkLevel.Hidden)) * biasNode.getNodeValue();
            intermediateValues.add(totalOutputInput);
            simpleTotal += Math.pow(Math.E, totalOutputInput);
        }
        ArrayList<Double> finalValues = new ArrayList<Double>();
        for (int i = 0; i < nodeLevelNumber.get(NetworkLevel.Output); i++) {
            double trueOutputValue;
            trueOutputValue = Math.pow(Math.E, intermediateValues.get(i)) / simpleTotal;
            neuralMap.get(NetworkLevel.Output).get(i).setNodeValue(trueOutputValue);
            finalValues.add(trueOutputValue);
        }
        return finalValues;
    }

    public void backpropagation(List<Datum> trainingData, int epochs) {
        double epochWeight = 0.1;
        for (; epochs > 0; --epochs) {
            for (int l = 0; l < trainingData.size(); l++) {
                List<Double> attributes = trainingData.get(l).getAttributes();
                List<Double> trueClassification = trainingData.get(l).getClassificaitons();
                List<Double> networkClassification = classifyInstance(new Datum(attributes));
                HashMap<Integer, List<Double>> changeInHiddenWeights = new HashMap<Integer, List<Double>>();
                for (int i = 0; i < nodeLevelNumber.get(NetworkLevel.Hidden) + 1; i++) {
                    ArrayList<Double> weightChanges = new ArrayList<Double>();
                    for (int j = 0; j < nodeLevelNumber.get(NetworkLevel.Output); j++) {
                        double currentValue, weightChange, difference;
                        difference = trueClassification.get(j) - networkClassification.get(j);
                        if(i < nodeLevelNumber.get(NetworkLevel.Hidden)) {
                            currentValue = neuralMap.get(NetworkLevel.Hidden).get(i).getNodeValue();
                            weightChange = epochWeight * currentValue * difference;
                        } else {
                            currentValue = biasNode.getNodeValue();
                            weightChange = epochWeight * currentValue * difference;
                        }
                        weightChanges.add(weightChange);
                    }
                    changeInHiddenWeights.put(i, weightChanges);
                }
                HashMap<Integer, List<Double>> changeInInputWeights = new HashMap<Integer, List<Double>>();
                for (int i = 0; i < nodeLevelNumber.get(NetworkLevel.Input) + 1; i++) {
                    ArrayList<Double> weightChanges = new ArrayList<Double>();
                    for (int j = 0; j < nodeLevelNumber.get(NetworkLevel.Hidden); j++) {
                        double summedDifference = 0;
                        double hiddenValue = neuralMap.get(NetworkLevel.Hidden).get(j).getNodeValue();
                        for (int k = 0; k < nodeLevelNumber.get(NetworkLevel.Output); k++) {
                            double difference = trueClassification.get(k) - networkClassification.get(k);
                            double weight = neuralMap.get(NetworkLevel.Hidden).get(j).getOutputWeightAt(k);
                            summedDifference += difference * weight;
                        }
                        double inputWeight = i < nodeLevelNumber.get(NetworkLevel.Input) ? attributes.get(i) : biasNode.getNodeValue();
                        double weightChange = epochWeight * summedDifference * hiddenValue * (1 - hiddenValue) * inputWeight;
                        weightChanges.add(weightChange);
                    }
                    changeInInputWeights.put(i, weightChanges);
                }
                for (int i = 0; i < nodeLevelNumber.get(NetworkLevel.Hidden) + 1; i++) {
                    for (int j = 0; j < nodeLevelNumber.get(NetworkLevel.Output); j++) {
                        if(i < nodeLevelNumber.get(NetworkLevel.Hidden)) {
                            double currentHiddenWeight = neuralMap.get(NetworkLevel.Hidden).get(i).getOutputWeightAt(j);
                            double newHiddenWeight = currentHiddenWeight + changeInHiddenWeights.get(i).get(j);
                            neuralMap.get(NetworkLevel.Hidden).get(i).setOutputWeightAt(j, newHiddenWeight);
                        } else {
                            double currentHiddenBiasWeight = biasNode.getOutputWeightAt(j + nodeLevelNumber.get(NetworkLevel.Hidden));
                            double newHiddenBiasWeight = currentHiddenBiasWeight + changeInHiddenWeights.get(i).get(j);
                            biasNode.setOutputWeightAt(j + nodeLevelNumber.get(NetworkLevel.Hidden), newHiddenBiasWeight);
                        }
                    }
                }
                for (int i = 0; i < nodeLevelNumber.get(NetworkLevel.Input) + 1; i++) {
                    for (int j = 0; j < nodeLevelNumber.get(NetworkLevel.Hidden); j++) {
                        if(i < nodeLevelNumber.get(NetworkLevel.Input)) {
                            double currentInputWeight = neuralMap.get(NetworkLevel.Input).get(i).getOutputWeightAt(j);
                            double newInputWeight = currentInputWeight + changeInInputWeights.get(i).get(j);
                            neuralMap.get(NetworkLevel.Input).get(i).setOutputWeightAt(j, newInputWeight);
                        } else {
                            double currentInputBiasWeight = biasNode.getOutputWeightAt(j);
                            double newInputBiasWeight = currentInputBiasWeight + changeInInputWeights.get(i).get(j);
                            biasNode.setOutputWeightAt(j, newInputBiasWeight);
                        }
                    }
                }
            }
        }
        return;
    }

    public double networkError(List<Datum> testingData) {
        double totalSummedError = 0;
        for (int i = 0; i < testingData.size(); i++) {
            List<Double> trueClassification = testingData.get(i).getClassificaitons();
            List<Double> networkClassification = classifyInstance(new Datum(testingData.get(i).getAttributes()));
            for (int j = 0; j < trueClassification.size(); j++) {
                totalSummedError += Math.pow((networkClassification.get(j) - trueClassification.get(j)), 2);
            }
        }
        totalSummedError /= 2.0;
        return totalSummedError;
    }

    public HashMap<NetworkLevel, HashMap<Integer, NetworkNode>> getNeuralMap() {
        return neuralMap;
    }

    public void setNeuralMap(
            HashMap<NetworkLevel, HashMap<Integer, NetworkNode>> neuralMap) {
        this.neuralMap = neuralMap;
    }

    public HashMap<NetworkLevel, Integer> getNodeLevelNumber() {
        return nodeLevelNumber;
    }
}
