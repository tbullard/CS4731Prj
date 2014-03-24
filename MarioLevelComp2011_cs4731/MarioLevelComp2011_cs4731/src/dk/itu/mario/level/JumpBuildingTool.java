package dk.itu.mario.level;

public class JumpBuildingTool extends Tool {

	public int build(int start, int length, int floor, MyLevel level) {
		level.gaps++;
    	//jl: jump length
    	//js: the number of blocks that are available at either side for free
    	int end = (start + length);
        int jumpLength = level.random.nextInt(99)%3 + 1;
        int jumpStart = start + level.random.nextInt(length-jumpLength);

        int startHole = jumpStart;
		int endHole = jumpStart + jumpLength;
//		System.out.println(start+","+end);
		System.out.println("hole: "+startHole+","+endHole);
		
		boolean hasStairs = level.random.nextInt(3) == 0;
        int maxStairHeight = Math.min(start - startHole, endHole - end);
    	int stairHeight = (level.random.nextInt(3) + 1) % maxStairHeight;
    	
        hasStairs = true;
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
                    else if (hasStairs)
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
		return new JumpBuildingTool();
	}

}
