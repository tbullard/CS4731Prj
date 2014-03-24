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

}
