package dk.itu.mario.level;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import optimization.Individual;
import optimization.MultiKnapsackEvaluationFunction;
import dk.itu.mario.MarioInterface.GamePlay;
import dk.itu.mario.MarioInterface.LevelInterface;
import dk.itu.mario.engine.sprites.SpriteTemplate;
import dk.itu.mario.engine.sprites.Enemy;
import dk.itu.mario.level.generator.PlayerStyle;


public class MyLevel extends RandomLevel implements Individual<MyLevel>{
	//Store information about the level
	public   int ENEMIES = 0; //the number of enemies the level contains
	public   int BLOCKS_EMPTY = 0; // the number of empty blocks
	public   int BLOCKS_COINS = 0; // the number of coin blocks
	public   int BLOCKS_POWER = 0; // the number of power blocks
	public   int COINS = 0; //These are the coins in boxes that Mario collect
	
	public List<Building> buildings = new ArrayList<Building>();
	 
	private static Random levelSeedRandom = new Random();
	public static long lastSeed;
	
	public static Random random;
	  
	public int difficulty = 0;
	private int type;
	public  int gaps;
		
	static int count = 0;
	public static int length = 10;

		public MyLevel(int width, int height)
	    {
			super(width, height);
	        random = new Random();
	    }


		public MyLevel(int width, int height, long seed, int difficulty, int type, GamePlay playerMetrics)
	    {
	        this(width, height);
	        //TODO: getPlayerStyle from playerMetrics
	        PlayerStyle playerStyle = PlayerStyle.NEW;
//	        double[] ratios = {.25,.25,.25,.25}; //Kind of random
//	        double[] ratios = {0,.75,.25,0}; // SPEED
//	        double[] ratios = {0,.25,.75,0}; // KILLER
	        double[] ratios = {0,0,1,0}; // Pure-KILLER
//	        double[] ratios = {0,0,0,1}; // EXPLORER
	        
	        create(LevelGenerator.create(playerStyle, ratios));
	        double[] sW = {0,0,0,0}, sV = {0,0,0,0};
	        for(int i =0; i < this.buildings.size(); i++) {
	        	Building d = this.buildings.get(i);
	        	System.out.println(d);
	        	double[] w =  d.getWeights();
	        	double[] p = d.getProfits();
	        	for(int j=0;j < 4;j++) {
	        		sW[j] +=w[j];
	        		System.out.print(d.getWeights()[j]+",");
	        	}
	        	System.out.println("");
	        	for(int j=0;j < 4;j++) {
	        		sV[j] += p[j];
	        		System.out.print(d.getProfits()[j]+",");
	        	}
	        	System.out.println("");
	        }
	        System.out.println("MaxWeight,Weight,Value");
	        for(int i=0;i < 4;i++) System.out.println(MultiKnapsackEvaluationFunction.P[i]+","+sW[i]+","+sV[i]);
	        fixWalls();
	    }

		public static MyLevel createRandom(int width, int height) {
			MyLevel level		= new MyLevel(width,height);
			level.creat(count,0,0);
	        count++;
	        return level;
		}

		public MyLevel copy() {
			MyLevel level		= new MyLevel(this.width,this.height);
			level.ENEMIES		= this.ENEMIES; 
			level.BLOCKS_EMPTY	= this.BLOCKS_EMPTY; 
			level.BLOCKS_COINS	= this.BLOCKS_COINS; 
			level.BLOCKS_POWER	= this.BLOCKS_POWER; 
			level.COINS			= this.COINS; 
			level.type			= this.type;
			level.difficulty	= this.difficulty;
			
			level.buildings.clear();
			for(Building building : this.buildings) level.buildings.add(building.clone());
			return level;
		}

		private void create(MyLevel level) {
//			System.out.println("Creating lvl");
//			this.ENEMIES		= level.ENEMIES; 
//			this.BLOCKS_EMPTY	= level.BLOCKS_EMPTY; 
//			this.BLOCKS_COINS	= level.BLOCKS_COINS; 
//			this.BLOCKS_POWER	= level.BLOCKS_POWER; 
//			this.COINS			= level.COINS; 
//			this.type			= level.type;
//			this.difficulty		= level.difficulty;

			this.buildings.add(new StraightBuilding(0,10,height-1,true));
			for(Building building : level.buildings) this.buildings.add(building.clone());
	        this.buildings.add(new EndBuilding(width-10,width,height-1));
	        for(Building building : buildings) building.build(this);
		}
		
	    public void creat(long seed, int difficulty, int type)
	    {
	        this.type = type;
	        this.difficulty = difficulty;

	        lastSeed = seed;
	        
	        for(int i =10; i < width-10; i+=10) {
	        	switch(random.nextInt(10)) {
	        	case 0:
	        	case 1:
	        		this.buildings.add(new StraightBuilding(i,10,height-1));
	        		break;
	        	case 2:
	        	case 3:
	        		this.buildings.add(new StraightHillBuilding(i,10,height-1));
	        		break;
	        	case 4:
	        	case 5:
	        		this.buildings.add(new CannonBuilding(i,10,height-1));
	        		break;
	        	case 6:
	        	case 7:
	        		this.buildings.add(new TubeBuilding(i,10,height-1));
	        		break;
	        	case 8:
	        	case 9:
	        		this.buildings.add(new JumpBuilding(i,10,height-1));
	        		break;
	        	}
	        }
	    }

	    //TODO: improve this to be part of the mutations
	    public void addEnemyLine(int x0, int x1, int y)
	    {
	    	int type = random.nextInt(4);
	    	difficulty = type;
	    	boolean hasWings = MyLevel.random.nextDouble() < 0.1;
	        setSpriteTemplate(x0+MyLevel.random.nextInt(x1)+1, y, new SpriteTemplate(type,hasWings));
	        ENEMIES++;
	    }

	    public void decorate(int xStart, int xLength, int floor)
	    {
	    	//if its at the very top, just return
	        if (floor < 1)
	        	return;

	        //        boolean coins = random.nextInt(3) == 0;
	        boolean rocks = true;

	        //add an enemy line above the box
//	        addEnemyLine(xStart + 1, xLength - 1, floor - 1);

	        int s = random.nextInt(4);
	        int e = random.nextInt(4);

	        if (floor - 2 > 0){
	            if ((xLength - 1 - e) - (xStart + 1 + s) > 1){
	                for(int x = xStart + 1 + s; x < xLength - 1 - e; x++){
	                    setBlock(x, floor - 2, COIN);
	                    COINS++;
	                }
	            }
	        }

	        s = random.nextInt(4);
	        e = random.nextInt(4);
	        
	        //this fills the set of blocks and the hidden objects inside them
	        if (floor - 4 > 0)
	        {
	            if ((xLength - 1 - e) - (xStart + 1 + s) > 2)
	            {
	                for (int x = xStart + 1 + s; x < xLength - 1 - e; x++)
	                {
	                    if (rocks)
	                    {
	                        if (x != xStart + 1 && x != xLength - 2 && random.nextInt(3) == 0)
	                        {
	                            if (random.nextInt(4) == 0)
	                            {
	                                setBlock(x, floor - 4, BLOCK_POWERUP);
	                                BLOCKS_POWER++;
	                            }
	                            else
	                            {	//the fills a block with a hidden coin
	                                setBlock(x, floor - 4, BLOCK_COIN);
	                                BLOCKS_COINS++;
	                            }
	                        }
	                        else if (random.nextInt(4) == 0)
	                        {
	                            if (random.nextInt(4) == 0)
	                            {
	                                setBlock(x, floor - 4, (byte) (2 + 1 * 16));
	                            }
	                            else
	                            {
	                                setBlock(x, floor - 4, (byte) (1 + 1 * 16));
	                            }
	                        }
	                        else
	                        {
	                            setBlock(x, floor - 4, BLOCK_EMPTY);
	                            BLOCKS_EMPTY++;
	                        }
	                    }
	                }
	            }
	        }
	    }

	    private void fixWalls()
	    {
	    	if (type == LevelInterface.TYPE_CASTLE || type == LevelInterface.TYPE_UNDERGROUND)
	    	{
	            int ceiling = 0;
	            int run = 0;
	            for (int x = 0; x < width; x++)
	            {
	                if (run-- <= 0 && x > 4)
	                {
	                    ceiling = random.nextInt(4);
	                    run = random.nextInt(4) + 4;
	                }
	                for (int y = 0; y < height; y++)
	                {
	                    if ((x > 4 && y <= ceiling) || x < 1)
	                    {
	                        this.setBlock(x, y, GROUND);
	                    }
	                }
	            }
	        }
	    	
	        boolean[][] blockMap = new boolean[width + 1][height + 1];

	        for (int x = 0; x < width + 1; x++)
	        {
	            for (int y = 0; y < height + 1; y++)
	            {
	                int blocks = 0;
	                for (int xx = x - 1; xx < x + 1; xx++)
	                {
	                    for (int yy = y - 1; yy < y + 1; yy++)
	                    {
	                        if (getBlockCapped(xx, yy) == GROUND){
	                        	blocks++;
	                        }
	                    }
	                }
	                blockMap[x][y] = blocks == 4;
	            }
	        }
	        blockify(this, blockMap, width + 1, height + 1);
	    }

	    private void blockify(Level level, boolean[][] blocks, int width, int height){
	        int to = 0;
	        if (type == LevelInterface.TYPE_CASTLE)
	        {
	            to = 4 * 2;
	        }
	        else if (type == LevelInterface.TYPE_UNDERGROUND)
	        {
	            to = 4 * 3;
	        }

	        boolean[][] b = new boolean[2][2];

	        for (int x = 0; x < width; x++)
	        {
	            for (int y = 0; y < height; y++)
	            {
	                for (int xx = x; xx <= x + 1; xx++)
	                {
	                    for (int yy = y; yy <= y + 1; yy++)
	                    {
	                        int _xx = xx;
	                        int _yy = yy;
	                        if (_xx < 0) _xx = 0;
	                        if (_yy < 0) _yy = 0;
	                        if (_xx > width - 1) _xx = width - 1;
	                        if (_yy > height - 1) _yy = height - 1;
	                        b[xx - x][yy - y] = blocks[_xx][_yy];
	                    }
	                }

	                if (b[0][0] == b[1][0] && b[0][1] == b[1][1])
	                {
	                    if (b[0][0] == b[0][1])
	                    {
	                        if (b[0][0])
	                        {
	                            level.setBlock(x, y, (byte) (1 + 9 * 16 + to));
	                        }
	                        else
	                        {
	                            // KEEP OLD BLOCK!
	                        }
	                    }
	                    else
	                    {
	                        if (b[0][0])
	                        {
	                        	//down grass top?
	                            level.setBlock(x, y, (byte) (1 + 10 * 16 + to));
	                        }
	                        else
	                        {
	                        	//up grass top
	                            level.setBlock(x, y, (byte) (1 + 8 * 16 + to));
	                        }
	                    }
	                }
	                else if (b[0][0] == b[0][1] && b[1][0] == b[1][1])
	                {
	                    if (b[0][0])
	                    {
	                    	//right grass top
	                        level.setBlock(x, y, (byte) (2 + 9 * 16 + to));
	                    }
	                    else
	                    {
	                    	//left grass top
	                        level.setBlock(x, y, (byte) (0 + 9 * 16 + to));
	                    }
	                }
	                else if (b[0][0] == b[1][1] && b[0][1] == b[1][0])
	                {
	                    level.setBlock(x, y, (byte) (1 + 9 * 16 + to));
	                }
	                else if (b[0][0] == b[1][0])
	                {
	                    if (b[0][0])
	                    {
	                        if (b[0][1])
	                        {
	                            level.setBlock(x, y, (byte) (3 + 10 * 16 + to));
	                        }
	                        else
	                        {
	                            level.setBlock(x, y, (byte) (3 + 11 * 16 + to));
	                        }
	                    }
	                    else
	                    {
	                        if (b[0][1])
	                        {
	                        	//right up grass top
	                            level.setBlock(x, y, (byte) (2 + 8 * 16 + to));
	                        }
	                        else
	                        {
	                        	//left up grass top
	                            level.setBlock(x, y, (byte) (0 + 8 * 16 + to));
	                        }
	                    }
	                }
	                else if (b[0][1] == b[1][1])
	                {
	                    if (b[0][1])
	                    {
	                        if (b[0][0])
	                        {
	                        	//left pocket grass
	                            level.setBlock(x, y, (byte) (3 + 9 * 16 + to));
	                        }
	                        else
	                        {
	                        	//right pocket grass
	                            level.setBlock(x, y, (byte) (3 + 8 * 16 + to));
	                        }
	                    }
	                    else
	                    {
	                        if (b[0][0])
	                        {
	                            level.setBlock(x, y, (byte) (2 + 10 * 16 + to));
	                        }
	                        else
	                        {
	                            level.setBlock(x, y, (byte) (0 + 10 * 16 + to));
	                        }
	                    }
	                }
	                else
	                {
	                    level.setBlock(x, y, (byte) (0 + 1 * 16 + to));
	                }
	            }
	        }
	    }

		public MyLevel getData() {
			return this;
		}


		@Override
		public double[] getVariables() {
			double[] variables = new double[this.buildings.size()];
			for(int i=0; i < variables.length; i++) variables[i] = 1.0;
			return variables;
		}


		@Override
		public double[] getWeight() {
			double[] weights = new double[this.buildings.size()];
			for(int i=0; i < weights.length; i++) {
				weights[i] = this.buildings.get(i).getWeight();
			}
			return weights;
		}


		@Override
		public double[] getProfit() {
			double[] profits = new double[this.buildings.size()];
			int previousFloor = this.buildings.get(0).floor;
			for(int i=0; i < profits.length; i++) {
				double adjust = (this.buildings.get(i).floor - previousFloor) > 3 ? 0.5 : 1.0;
				profits[i] = this.buildings.get(i).getProfit() * adjust;
			}
			return profits;
		}


		@Override
		public double[][] getWeights() {
			double[][] weights = new double[this.buildings.size()][];
			for(int i=0; i < weights.length; i++) {
				weights[i] = this.buildings.get(i).getWeights();
			}
			return weights;
		}


		@Override
		public double[][] getProfits() {
			double[][] profits = new double[this.buildings.size()][];
			for(int i=0; i < profits.length; i++) {
				profits[i] = this.buildings.get(i).getProfits();
			}
			return profits;
		}


		@Override
		public void createMutations() {
			for(Building building: buildings) {
				building.mutate(random, height);
			}
		}
}
