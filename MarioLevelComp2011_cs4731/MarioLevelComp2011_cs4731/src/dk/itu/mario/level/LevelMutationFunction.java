package dk.itu.mario.level;

import java.util.ArrayList;
import java.util.List;

import optimization.Individual;
import optimization.MutationFunction;

public class LevelMutationFunction implements MutationFunction<MyLevel> {

	@Override
	public Individual<MyLevel> mutate(Individual<MyLevel> individual) {
		// TODO
		Individual<MyLevel> mutated = ((MyLevel) individual.getData()).copy();
		mutated.createMutations();
		return mutated;
	}

}
