package dk.itu.mario.level;


public class StraightBuilding extends Building {
	public StraightBuilding(int start, int length, int floor) {
		super(start,length,floor);
		buildingTool = new StraightBuildingTool();
	}

	public Building getCopy() {
		return new StraightBuilding(this.start,this.lenght,this.floor);
	}

	@Override
	public int build(MyLevel level) {
		return buildingTool.build(start, lenght, floor, level);
	}

}
