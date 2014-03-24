package dk.itu.mario.level;


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
}
