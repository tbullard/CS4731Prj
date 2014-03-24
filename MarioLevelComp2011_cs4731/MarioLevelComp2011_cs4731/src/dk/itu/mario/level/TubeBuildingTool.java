package dk.itu.mario.level;

import dk.itu.mario.engine.sprites.Enemy;
import dk.itu.mario.engine.sprites.SpriteTemplate;

public class TubeBuildingTool extends Tool {

	@Override
	public int build(int start, int length, int floor, MyLevel level) {
        int xTube = start + 1 + level.random.nextInt(4);
        int tubeHeight = floor - level.random.nextInt(2) - 2;
        for (int x = start; x < start + length; x++)
        {
            if (x > xTube + 1)
            {
                xTube += 3 + level.random.nextInt(4);
                tubeHeight = floor - level.random.nextInt(2) - 2;
            }
            if (xTube >= start + length - 2) xTube += 10;

            if (x == xTube && level.random.nextInt(11) < level.difficulty + 1)
            {
            	level.setSpriteTemplate(x, tubeHeight, new SpriteTemplate(Enemy.ENEMY_FLOWER, false));
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
                    if ((x == xTube || x == xTube + 1) && y >= tubeHeight)
                    {
                        int xPic = 10 + x - xTube;

                        if (y == tubeHeight)
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
		return new TubeBuildingTool();
	}

}
