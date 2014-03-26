package dk.itu.mario.level;


public class StraightHillBuilding extends Building {

	public StraightHillBuilding(int start, int lenght, int floor) {
		super(start, lenght, floor);
		buildingTool = new StraightHillBuildingTool();
	}
	
	public Building getCopy() {
		return new StraightHillBuilding(this.start,this.lenght,this.floor);
	}

	@Override
	public int build(MyLevel level) {
		return buildingTool.build(start, lenght, floor, level);
	}

	@Override
	public double getWeight() {
		return 2;
	}

	@Override
	public double getProfit() {
		return 1;
	}

	@Override
	public double[] getWeights() {
		double[] result = {1,1,1,1};
		return result;
	}

	@Override
	public double[] getProfits() {
		double[] result = {1,1,1,1};
		return result;
	}

	@Override
	public void mutateParameters() {
		int len = MyLevel.random.nextInt(5) + 3;
		int[] parameters = {len,MyLevel.random.nextInt(MyLevel.length - len - 2) + start + 1, MyLevel.random.nextInt(10)%3};
		buildingTool.copyParamaters(parameters);
	}


}
