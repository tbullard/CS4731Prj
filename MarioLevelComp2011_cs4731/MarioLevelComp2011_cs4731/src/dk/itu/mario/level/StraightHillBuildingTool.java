package dk.itu.mario.level;


public class StraightHillBuildingTool extends Tool {

	public int build(int start, int length, int floor, MyLevel level) {
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

//        addEnemyLine(xo + 1, xo + length - 1, floor - 1);

        int h = floor;

        boolean keepGoing = true;

        boolean[] occupied = new boolean[length];
        
        while (keepGoing)
        {
            h += - 2 - level.random.nextInt(3);

            if (h <= 0)
            {
                keepGoing = false;
            }
            else
            {
                int l = level.random.nextInt(5) + 3;
//                int xxo = level.random.nextInt(length - l - 2) + start + 1;
                int xxo = level.random.nextInt(length - l - 2) + start + 1;

                if (occupied[xxo - start] || occupied[xxo - start + l] || occupied[xxo - start - 1] || occupied[xxo - start + l + 1])
                {
                    keepGoing = false;
                }
                else
                {
                    occupied[xxo - start] = true;
                    occupied[xxo - start + l] = true;
                    level.addEnemyLine(xxo, xxo + l, h - 1);
                    if (level.random.nextInt(4) == 0)
                    {
                    	level.decorate(xxo - 1, xxo + l + 1, h);
                        keepGoing = false;
                    }
                    for (int x = xxo; x < xxo + l; x++)
                    {
                        for (int y = h; y < floor; y++)
                        {
                            int xx = 5;
                            if (x == xxo) xx = 4;
                            if (x == xxo + l - 1) xx = 6;
                            int yy = 9;
                            if (y == h) yy = 8;

                            if (level.getBlock(x, y) == 0)
                            {
                            	level.setBlock(x, y, (byte) (xx + yy * 16));
                            }
                            else
                            {
                                if (level.getBlock(x, y) == MyLevel.HILL_TOP_LEFT)	level.setBlock(x, y, MyLevel.HILL_TOP_LEFT_IN);
                                if (level.getBlock(x, y) == MyLevel.HILL_TOP_RIGHT)	level.setBlock(x, y, MyLevel.HILL_TOP_RIGHT_IN);
                            }
                        }
                    }
                }
            }
        }

        return length;
	}

	public Tool clone() {
		return new StraightHillBuildingTool();
	}

}
