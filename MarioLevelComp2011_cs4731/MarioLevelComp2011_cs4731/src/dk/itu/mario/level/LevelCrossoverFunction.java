package dk.itu.mario.level;

import java.util.ArrayList;
import java.util.List;

import optimization.CrossoverFunction;
import optimization.Distribution;
import optimization.Individual;

public class LevelCrossoverFunction implements CrossoverFunction<MyLevel> {

	@Override
	public List<Individual<MyLevel>> mate(Individual<MyLevel> father, Individual<MyLevel> mother) {
		List<Individual<MyLevel>> offspring = new ArrayList<Individual<MyLevel>>();
		
		MyLevel child_A = new MyLevel(320,15), 
				child_B = new MyLevel(320,15);
		
		int buildingCount = ((MyLevel) father.getData()).buildings.size();
		int changePoint = ((MyLevel) father.getData()).random.nextInt(buildingCount);

		for(int i = 0; i < buildingCount; i++) {
			child_A.buildings.add((i < changePoint ? father : mother).getData().buildings.get(i).clone());
			child_B.buildings.add((i > changePoint ? father : mother).getData().buildings.get(i).clone());
		}
		
		offspring.add(child_A);
		offspring.add(child_B);
		return offspring;
	}

}
