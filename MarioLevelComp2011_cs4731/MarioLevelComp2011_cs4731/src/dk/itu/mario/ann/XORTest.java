package ann;

import java.util.ArrayList;
import java.util.List;

public class XORTest {

    public static void main(String[] args) {
        FFNeuralNetwork network = new FFNeuralNetwork(2, 6, 2);
        ArrayList<Double> point = new ArrayList<Double>();
        point.add(0.0);
        point.add(1.0);
        ArrayList<Double> point2 = new ArrayList<Double>();
        point2.add(1.0);
        point2.add(0.0);
        ArrayList<Double> point3 = new ArrayList<Double>();
        point3.add(1.0);
        point3.add(1.0);
        ArrayList<Double> point4 = new ArrayList<Double>();
        point4.add(0.0);
        point4.add(0.0);
        ArrayList<Double> label = new ArrayList<Double>();
        label.add(1.0);
        label.add(0.0);
        ArrayList<Double> label2 = new ArrayList<Double>();
        label2.add(1.0);
        label2.add(0.0);
        ArrayList<Double> label3 = new ArrayList<Double>();
        label3.add(0.0);
        label3.add(1.0);
        ArrayList<Double> label4 = new ArrayList<Double>();
        label4.add(0.0);
        label4.add(1.0);
        Datum D1 = new Datum(point, label);
        Datum D2 = new Datum(point2, label2);
        Datum D3 = new Datum(point3, label3);
        Datum D4 = new Datum(point4, label4);
        List<Datum> trainingData = new ArrayList<Datum>();
        trainingData.add(D1);
        trainingData.add(D2);
        trainingData.add(D3);
        trainingData.add(D4);
        network.backpropagation(trainingData, 10000);
        double errorRate = network.networkError(trainingData);
        System.out.println(errorRate);
        return;
    }
}
