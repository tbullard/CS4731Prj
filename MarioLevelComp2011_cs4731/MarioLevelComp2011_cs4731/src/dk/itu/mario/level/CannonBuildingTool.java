package dk.itu.mario.level;


public class CannonBuildingTool extends Tool {

	private int cannonHeight;
	private int xCannon;
	
	@Override
	public int build(int start, int length, int floor, MyLevel level) {
		if(firstTime) {
			int[] parameters = {floor - MyLevel.random.nextInt(4) - 1,start + 1 + MyLevel.random.nextInt(4)};
			copyParamaters(parameters);
		}
		
        for (int x = start; x < start + length; x++)
        {
            if (x > this.xCannon)
            {
            	this.xCannon += 2 + MyLevel.random.nextInt(4);
            }
            if (this.xCannon == start + length - 1) this.xCannon += 10;

            for (int y = 0; y < level.height; y++)
            {
                if (y >= floor)
                {
                	level.setBlock(x, y, MyLevel.GROUND);
                }
                else
                {
                    if (x == xCannon && y >= this.cannonHeight)
                    {
                        if (y == this.cannonHeight)
                        {
                        	level.setBlock(x, y, (byte) (14 + 0 * 16));
                        }
                        else if (y == this.cannonHeight + 1)
                        {
                        	level.setBlock(x, y, (byte) (14 + 1 * 16));
                        }
                        else
                        {
                        	level.setBlock(x, y, (byte) (14 + 2 * 16));
                        }
                    }
                }
            }
        }

        return length;
	}

	@Override
	public Tool clone() {
		int[] paramaters = {this.cannonHeight,this.xCannon};
		Tool tool = new CannonBuildingTool();
		tool.copyParamaters(paramaters);
		return (tool);
	}

	public void copyParamaters(int[] parameters) {
		this.cannonHeight	= parameters[0];
		this.xCannon 		= parameters[1];
	}

}
