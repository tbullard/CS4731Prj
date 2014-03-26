package dk.itu.mario.level;



public class StraightBuildingTool extends Tool {


	private boolean isSafe;

	public int build(int start, int length, int floor, MyLevel level){
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
	    if(this.isSafe) level.decorate(start, start + length, floor);
	    return length;
	}

	public Tool clone() {
		int[] paramaters = {(this.isSafe?0:1)};
		Tool tool = new StraightBuildingTool();
		tool.copyParamaters(paramaters);
		return (tool);
	}

	@Override
	public void copyParamaters(int[] parameters) {
		this.isSafe = parameters[0] == 0;
	}

}
