package dk.itu.mario.level;


public class StraightHillBuildingTool extends Tool {

	private int l;
	private int xxo;
	private boolean hasEnemies;

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

        if(this.hasEnemies) level.addEnemyLine(start + 1, start + length - 1, floor - 1);

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
                this.l = level.random.nextInt(5) + 3;
                this.xxo = level.random.nextInt(length - this.l - 2) + start + 1;

                if (occupied[this.xxo - start] || occupied[this.xxo - start + this.l] || occupied[this.xxo - start - 1] || occupied[this.xxo - start + this.l + 1])
                {
                    keepGoing = false;
                }
                else
                {
                    occupied[this.xxo - start] = true;
                    occupied[this.xxo - start + this.l] = true;
                    if(this.hasEnemies) level.addEnemyLine(this.xxo, this.xxo + this.l, h - 1);
                    if (level.random.nextInt(4) == 0)
                    {
                    	level.decorate(this.xxo - 1, this.xxo + this.l + 1, h);
                        keepGoing = false;
                    }
                    for (int x = this.xxo; x < this.xxo + this.l; x++)
                    {
                        for (int y = h; y < floor; y++)
                        {
                            int xx = 5;
                            if (x == this.xxo) xx = 4;
                            if (x == this.xxo + this.l - 1) xx = 6;
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
		int[] paramaters = {this.l,this.xxo,(this.hasEnemies?0:1)};
		Tool tool = new StraightHillBuildingTool();
		tool.copyParamaters(paramaters);
		return (tool);
	}

	@Override
	public void copyParamaters(int[] paramaters) {
		this.l			= paramaters[0];
		this.xxo		= paramaters[1];
		this.hasEnemies	= paramaters[2] == 0;
	}

}
