package dk.itu.mario.level;



public class EndBuildingTool extends Tool{

	public int build(int start, int length, int floor, MyLevel level) {
		level.xExit = length-2;
		level.yExit = floor;
		
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
	    
	    return (level.width - length);
	}

	public Tool clone() {
		return new EndBuildingTool();
	}

	@Override
	public void copyParamaters(int[] paramters) {}

}
