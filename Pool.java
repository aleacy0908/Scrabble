import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

public class Pool {
	
	
	//--TILES--
	final char[] tileNames = {
			'A', 'B', 'C', 'D', 'E', 'F', 'G', 
			'H', 'I', 'J', 'K', 'L', 'M', 'N', 
			'O', 'P', 'Q', 'R', 'S', 'T', 'U', 
			'V', 'W', 'X', 'Y', 'Z', '_'};

	final int[] tilePointsValues = {
			1, 3, 3,  2, 1,  4, 2, 
			4, 1, 8,  5, 1,  3, 1, 
			1, 3, 10, 1, 1,  1, 1, 
			4, 4, 8,  4, 10, 0 };
	
	final int[] amountOfTilesInBeginning = {
			  9, 2, 2, 4, 12, 2, 3, 
			  2, 9, 1, 1, 4,  2, 6, 
			  8, 2, 1, 6, 4,  6, 4,
			  2, 2, 1, 2, 1,  2 };
	
	final int numTilesBeginning = 100;
	
	//Tile Pool 
	//TILE => AMOUNT OF TILES LEFT
	final Map<Character, int[]> tilePool;
	
	//Amount of tiles in the pool
	private int currPoolSize;
	
	Pool()
	{
		
		/* We want to create a map that links
		 * each individual tile to its value (in points)
		 * and the amount of the tile left in the pool
		 * 
		 * Maps make it much easier to retrieve information
		 * about each tile. We do not need to store our tiles in 
		 * any particular order thus maps are the better option
		 * as they can search for elements in O(1) time. 
		 * 
		 * i.e TILE => AMOUNT OF TILES LEFT
		 */
		
		tilePool = new HashMap<Character, int[]>();
				
		this.createPool();
	}
	
	//Allows the value of a tile to be queried
	public int getTileValue(char x)
	{
		//Error Check 
		if(!tilePool.containsKey(x))
			invalidTileException();
		
		//Return Value Of Tile
		return tilePool.get(x)[0];
	
	}
	
	
	public int amountOfTileInPool(char x)
	{
		//Error Check 
		if(!isValidTile(x))
			invalidTileException();
				
		//Return Amount Of Tiles
		return tilePool.get(x)[1];
	}
	
	//--Exceptions--
	private void invalidTileException()
	{
		throw new IllegalArgumentException("Invalid Tile");
	}
	
	
    //Creates the pool
	private void createPool()
	{
		char nameOfTile;
		int  pointsValOfTile, amountOfTilesLeft;
		
		int[] tileInfo;
		
		//For Each Tile
		for(int i = 0; i < tileNames.length; i++)
		{
			//Retrieve Tile Info
			nameOfTile        = tileNames[i];
			pointsValOfTile   = tilePointsValues[i];
			amountOfTilesLeft = amountOfTilesInBeginning[i];
			
			tileInfo = new int[] {pointsValOfTile, amountOfTilesLeft};
			
			//Add It To Our Map
			tilePool.put(nameOfTile, tileInfo);
		
		}
				
		//Set the initial size of the pool
		currPoolSize = numTilesBeginning;
	}
	
	public void resetPool()
	{
		//REMOVE all elements from the pool
		this.tilePool.clear();
		
		//Create the pool again
		this.createPool();
	}
	
	
	
	//Allows display of the number of tiles in the pool
	public int size()
	{
		return this.currPoolSize;
	}
	
	
	//Allows the pool to be checked to see if it is empty
	public boolean isEmpty()
	{
		return (currPoolSize == 0);
	}
	
	
	//Allows tiles to be drawn at random from the pool
	public char drawTileFromPool()
	{
		//Error Handling
		if(this.isEmpty())
			throw new RuntimeException("Pool Is Empty");
		
		//Tile Range
		int minIndx = 0;
		int maxIndx = tilePool.size() - 1;
		
		//Temporary Variables
		int rand;
		char t;
		
		//If there's none of a tile left, 
		//check for another one
		do
		{
			rand = (int)(Math.random() * ( (maxIndx-minIndx)+1) ) + minIndx;
			
			t = tileNames[rand];
			
		} while(this.amountOfTileInPool(t) == 0);
		
		//Change amount of tiles in pool
		this.removeTile(t);
		
		return t;
	}
	
	//Removes a tile from the pool
	private void removeTile(char x)
	{
		//Amount of this tile before removal
		int amountLeft = this.amountOfTileInPool(x);
		
		//Exception handling
		if(amountLeft == 0)
			invalidTileException();
		
		//Decrement the amount of this tile left
		setAmountOfTile(x, amountLeft--);
		
		//Adjust amount of total tiles
		currPoolSize--;
	}
	
	private void setAmountOfTile(char x, int amount)
	{
		//Exception handling
		if(!isValidTile(x))
				invalidTileException();
			
		int[] tileInfo = tilePool.get(x);
		
		//Set the amount of the tile left
		tileInfo[1] = amount;
		
		//Edit the tile pool
		tilePool.put(x, tileInfo);
	}
	
	public boolean isValidTile(char x)
	{
		return tilePool.containsKey(x);
	}
}

