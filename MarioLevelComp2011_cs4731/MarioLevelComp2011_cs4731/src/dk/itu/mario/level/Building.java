package dk.itu.mario.level;

import java.util.Random;



public abstract class Building {
	public Tool buildingTool;
	public int	start;
	public int	lenght;
	public int	floor;
	
	Building(int start, int lenght, int floor) {
		this.start 	= start;
		this.lenght = lenght;
		this.floor	= floor;
	}
	
	public abstract int build(MyLevel level);
	
	public Building clone(){
		Building clone = this.getCopy();
		clone.buildingTool = this.buildingTool.clone();
		return clone;
	}

	public abstract Building getCopy();
	
	public abstract double getWeight();
	public abstract double getProfit();

	public abstract double[] getWeights();
	public abstract double[] getProfits();

	public abstract void mutateParameters();
//	public  void mutateParameters() {}

	public void mutate(Random random, int height) {
		mutateFloor(random, height);
		mutateParameters();
	}

	private void mutateFloor(Random random, int height) {
		this.floor = Math.min(this.floor + random.nextInt(3) - 1,height - 1);
	}
		
}
