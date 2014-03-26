package dk.itu.mario.level;


public class EndBuilding extends Building {

	public EndBuilding(int start, int lenght, int floor) {
		super(start, lenght, floor);
		buildingTool = new EndBuildingTool();
	}

	public Building getCopy() {
		return new EndBuilding(this.start,this.lenght,this.floor);
	}

	@Override
	public int build(MyLevel level) {
		return buildingTool.build(start, lenght, floor, level);
	}

	@Override
	public double getWeight() {
		return 1;
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
	public void mutateParameters() {}

}
