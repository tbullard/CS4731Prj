package dk.itu.mario.level;


public class CannonBuilding extends Building {

	public CannonBuilding(int start, int lenght, int floor) {
		super(start, lenght, floor);
		buildingTool = new CannonBuildingTool();
	}

	public Building getCopy() {
		return new CannonBuilding(this.start,this.lenght,this.floor);
	}
	
	@Override
	public int build(MyLevel level) {
		return buildingTool.build(start, lenght, floor, level);
	}

	@Override
	public double getWeight() {
		return 15;
	}

	@Override
	public double getProfit() {
		return 11;
	}

	@Override
	public double[] getWeights() {
		double[] result = {3.75,3.75,3.75,3.75};
		return result;
	}

	@Override
	public double[] getProfits() {
		double[] result = {5,33,22,0};
		return result;
	}

	@Override
	public void mutateParameters() {
		int[] parameters = {floor - MyLevel.random.nextInt(4) - 1,start + 1 + MyLevel.random.nextInt(4)};
		buildingTool.copyParamaters(parameters);
	}

}
