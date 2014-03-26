package dk.itu.mario.level;


public class JumpBuilding extends Building {

	public JumpBuilding(int start, int lenght, int floor) {
		super(start, lenght, floor);
		buildingTool = new JumpBuildingTool();
	}

	public Building getCopy() {
		return new JumpBuilding(this.start,this.lenght,this.floor);
	}

	@Override
	public int build(MyLevel level) {
		return buildingTool.build(start, lenght, floor, level);
	}

	@Override
	public double getWeight() {
		return 5;
	}

	@Override
	public double getProfit() {
		return 3;
	}

	@Override
	public double[] getWeights() {
		double[] result = {1.25,1.25,1.25,1.25};
		return result;
	}

	@Override
	public double[] getProfits() {
		double[] result = {6,3,6,3};
		return result;
	}

	@Override
	public void mutateParameters() {
        int jumpLen = MyLevel.random.nextInt(99)%3 + 1;
		int[] parameters = {jumpLen,start + 1 + MyLevel.random.nextInt(MyLevel.length-jumpLen-1),MyLevel.random.nextInt(3)};
		buildingTool.copyParamaters(parameters);
	}


}
