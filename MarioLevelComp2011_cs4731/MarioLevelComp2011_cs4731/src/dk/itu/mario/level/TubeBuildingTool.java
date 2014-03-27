package dk.itu.mario.level;

import dk.itu.mario.engine.sprites.Enemy;
import dk.itu.mario.engine.sprites.SpriteTemplate;

public class TubeBuildingTool extends Tool {

	private int tubeHeight;

	@Override
	public int build(int start, int length, int floor, MyLevel level) {
        this.tubeHeight = floor - MyLevel.random.nextInt(2) - 2;
        int xTube = start + 1 + MyLevel.random.nextInt(4);
        for (int x = start; x < start + length; x++)
        {
            if (x > xTube + 1)
            {
                xTube += 3 + MyLevel.random.nextInt(4);
                this.tubeHeight = floor - MyLevel.random.nextInt(2) - 2;
            }
            if (xTube >= start + length - 2) xTube += 10;

            if (x == xTube && MyLevel.random.nextInt(11) < level.difficulty + 1)
            {
            	level.setSpriteTemplate(x, this.tubeHeight, new SpriteTemplate(Enemy.ENEMY_FLOWER, false));
            	level.ENEMIES++;
            }

            for (int y = 0; y < level.height; y++)
            {
                if (y >= floor)
                {
                	level.setBlock(x, y, MyLevel.GROUND);

                }
                else
                {
                    if ((x == xTube || x == xTube + 1) && y >= this.tubeHeight)
                    {
                        int xPic = 10 + x - xTube;

                        if (y == this.tubeHeight)
                        {
                        	//tube top
                        	level.setBlock(x, y, (byte) (xPic + 0 * 16));
                        }
                        else
                        {
                        	//tube side
                        	level.setBlock(x, y, (byte) (xPic + 1 * 16));
                        }
                    }
                }
            }
        }

        return length;
	}

	@Override
	public Tool clone() {
		int[] paramaters = {this.tubeHeight};
		Tool tool = new TubeBuildingTool();
		tool.copyParamaters(paramaters);
		return (tool);
	}

	@Override
	public void copyParamaters(int[] paramaters) {
		this.tubeHeight = paramaters[0];
	}

}
