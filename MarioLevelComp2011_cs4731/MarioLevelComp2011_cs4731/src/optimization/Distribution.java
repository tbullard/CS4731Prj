package optimization;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Distribution<T> {
	public static <T> List<T> sample(List<T> collection, double[] probabilities, int samples) {
		int	length	=	collection.size();
		List<T> result = new ArrayList<T>();
		
		for(int sample = 0; sample < samples; sample++) {
			double rndValue = (new Random()).nextDouble();
			double accumulatedValue = 0;
			for(int i  = 0; i < length; i++) {
				accumulatedValue += probabilities[i];
				if(accumulatedValue > rndValue) {
					result.add(collection.get(i));
					break;
				}
			}
		}
		return result;
	}
	public static <T> T sample(List<T> collection, double[] probabilities) {
		int	length	=	collection.size();
		T result = null;
		
		double rndValue = (new Random()).nextDouble();
		double accumulatedValue = 0;
		for(int i  = 0; i < length; i++) {
			accumulatedValue += probabilities[i];
			if(accumulatedValue > rndValue) {
				result = collection.get(i);
				break;
			}
		}
		return result;
	}
}
