package dk.itu.mario.level;


public class JumpBuildingTool extends Tool {

	private int jumpLength;
	private int jumpStart;
	private boolean hasStairs;

	public int build(int start, int length, int floor, MyLevel level) {
		level.gaps++;
    	//jl: jump length
    	//js: the number of blocks that are available at either side for free
    	int end = (start + length);
        this.jumpLength = level.random.nextInt(99)%3 + 1;
        this.jumpStart = start + 1 + level.random.nextInt(length-this.jumpLength-1);

        int startHole = this.jumpStart;
		int endHole = this.jumpStart + this.jumpLength;
		
		this.hasStairs = level.random.nextInt(3) == 0;
        int maxStairHeight = Math.min(start - startHole-1, endHole - end-1);
    	int stairHeight = (level.random.nextInt(3) + 1) % maxStairHeight;
    	
        this.hasStairs = true;
    	stairHeight = 3;
        int stairLeftBegin	= startHole - stairHeight;
        int stairRightEnd	= endHole + stairHeight;
		
      //run from the start x position, for the whole length
        for (int x = start; x < start+length; x++)
        {
			if ( x < startHole || x > endHole)
            {
                for (int y = level.height; y >= floor - stairHeight; y--)
                {	//paint ground up until the floor
                    if (y >= floor) level.setBlock(x, y, MyLevel.GROUND);
                    
                  //if it is above ground, start making stairs of rocks
                    else if (this.hasStairs)
                    {	//LEFT SIDE
                    	if((x < startHole ) && (x > stairLeftBegin)) {
                    		if(( Math.abs(x - stairLeftBegin) >= Math.abs(y - floor) )) level.setBlock(x, y, MyLevel.ROCK);
                    	}
                        //RIGHT SIDE
                    	else if((startHole < x) && (x < stairRightEnd)) {
                    		if((Math.abs(x - endHole) + Math.abs(y - floor) ) <= stairHeight) level.setBlock(x, y, MyLevel.ROCK);
                    	}
                    }
                }
            }
        }

        return length;
	}

	public Tool clone() {
		int[] paramaters = {this.jumpLength,this.jumpStart,(hasStairs?0:1)};
		Tool tool = new JumpBuildingTool();
		tool.copyParamaters(paramaters);
		return (tool);
	}

	@Override
	public void copyParamaters(int[] paramters) {
		this.jumpLength = paramters[0];
		this.jumpStart	= paramters[1];
		this.hasStairs	= paramters[2] == 0;
	}

}
