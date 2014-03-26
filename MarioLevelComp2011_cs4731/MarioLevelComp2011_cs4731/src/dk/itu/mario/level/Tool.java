package dk.itu.mario.level;



public abstract class Tool {
	public abstract int build(int start,int length, int floor, MyLevel level);
	public abstract Tool clone();
	
	public boolean firstTime = true;
	public abstract void copyParamaters(int[] paramaters);
}
