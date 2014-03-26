package dk.itu.mario.level;



public class StraightBuildingTool extends Tool {


	private boolean hasEnemies;
	private boolean hasDecoration;
	private boolean isSafe;

	public int build(int start, int length, int floor, MyLevel level){
		if(firstTime) {
			int[] parameters = {MyLevel.random.nextInt(1),MyLevel.random.nextInt(3)};
			copyParamaters(parameters);
		}
	    //runs from the specified x position to the length of the segment
	    for (int x = start; x < start + length; x++)
	    {
	        for (int y = 0; y < level.height; y++)
	        {
	            if (y >= floor)
	            {
	            	level.setBlock(x, y, MyLevel.GROUND);
	            }
	        }
	    }
	    if(this.isSafe)			return length;
	    if(this.hasEnemies) 	level.addEnemyLine(start + 1, start + length - 1, floor - 1);
	    if(this.hasDecoration)	level.decorate(start, start + length, floor);
	    return length;
	}

	public Tool clone() {
		int[] paramaters = {(this.hasEnemies?0:1),(this.hasDecoration?0:1)};
		Tool tool = new StraightBuildingTool();
		tool.copyParamaters(paramaters);
		return (tool);
	}

	@Override
	public void copyParamaters(int[] parameters) {
		this.hasEnemies		= parameters[0] == 0;
		this.hasDecoration	= parameters[1] == 0;
	}
	
	public void setSafe(boolean safe) {
		this.isSafe = safe;
	}

}
