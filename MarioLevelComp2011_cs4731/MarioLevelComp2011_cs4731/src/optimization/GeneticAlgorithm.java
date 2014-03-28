package optimization;

import java.util.ArrayList;
import java.util.List;

public class GeneticAlgorithm<T> extends OptimizationAlgorithm<T> {

	private GeneticAlgorithmProblem<T> gap;
	
	private int populationSize;
	private int populationToMate;
	private int populationToMutate;
	
	private List<Individual<T>> population;

	double[] probabilities;
	double	 summedProbabilities;
	
	public GeneticAlgorithm(int size, double replacementRate, double mutationRate, OptimizationProblem<T> optimizationProblem) {
		gap	= (GeneticAlgorithmProblem<T>) optimizationProblem;
		populationSize		= size;
		populationToMate 	= (int) Math.round(replacementRate * populationSize);
		populationToMutate	= (int) Math.round(mutationRate * populationSize);
		population = new ArrayList<Individual<T>>();
		summedProbabilities = 0;
		probabilities = new double[populationSize];
		for(int i = 0; i < populationSize; i++) {
			Individual<T> individual = gap.createRandom();
			population.add(individual);
			probabilities[i] = gap.value(individual);
			summedProbabilities += probabilities[i];
		}

		normalizeProbabilities();
		
	}

	private void normalizeProbabilities() {
		summedProbabilities = 0;
		for (int i = 0; i < population.size(); i++) {
			summedProbabilities += probabilities[i];
        }
		for (int i = 0; i < population.size(); i++) {
            probabilities[i] /= summedProbabilities;
        }
	}

	public Individual<T> getOptimal() {
		 
		double bestFitness	= Double.MIN_VALUE;
		Individual<T> best	= null;
		
		for(Individual<T> individual : population) {
			double fitness = gap.value(individual);
			if(fitness > bestFitness){
				best = individual;
				bestFitness = fitness;
			}
		}
		return best;
	}

	public double train() {
		generateOffspring();
		mutate();
		selectPopulation();
//		System.out.println(gap.value(this.getOptimal()));
		return summedProbabilities;
	}

	private void selectPopulation() {
		List<Individual<T>> newPopulation = new ArrayList<Individual<T>>();
		
		newPopulation.addAll(Distribution.sample(population,probabilities,populationSize));

		population.clear();
		probabilities = new double[populationSize];

		for (int i = 0; i < populationSize; i++) {
			Individual<T> individual = newPopulation.get(i);
			population.add(individual);
			probabilities[i] = gap.value(individual);
			summedProbabilities += probabilities[i];
        }
		
		normalizeProbabilities();
	}

	private void mutate() {	
		double[] oldProbabilities = probabilities;
		double[] newProbabilities = new double[populationToMutate];

		List<Individual<T>> newMembers = new ArrayList<Individual<T>>();
		
		for(int i = 0; i < populationToMutate; i++) {
			Individual<T> mutated = gap.mutate(Distribution.sample(population,probabilities));
			newMembers.add(mutated);
			newProbabilities[i]	= gap.value(mutated);
		}

		probabilities = new double[population.size() + populationToMutate];
	    System.arraycopy(oldProbabilities, 0, probabilities, 0, oldProbabilities.length);
	    System.arraycopy(newProbabilities, 0, probabilities, oldProbabilities.length, newProbabilities.length);
		population.addAll(newMembers);
		normalizeProbabilities();
	}

	private List<Individual<T>> generateOffspring() {		
		double[] oldProbabilities = probabilities;
		double[] newProbabilities = new double[populationToMate];
				
		List<Individual<T>> newMembers = new ArrayList<Individual<T>>();
		for(int i = 0; i < populationToMate; i+=2) {
			List<Individual<T>> offspring = gap.mate(Distribution.sample(population, probabilities, 2));
			newProbabilities[i]	= gap.value(offspring.get(0));
			newProbabilities[i+1] = gap.value(offspring.get(1));
			newMembers.addAll(offspring);
		}

		probabilities = new double[population.size() + populationToMate];
	    System.arraycopy(oldProbabilities, 0, probabilities, 0, oldProbabilities.length);
	    System.arraycopy(newProbabilities, 0, probabilities, oldProbabilities.length, newProbabilities.length);
		population.addAll(newMembers);
		normalizeProbabilities();
		
		return newMembers;
	}

}
