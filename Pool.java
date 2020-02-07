import java.util.Map;
import java.util.HashMap;

public class Pool {
	
	final char[] tiles = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 
					'H', 'I', 'J', 'K', 'L', 'M', 'N', 
					'O', 'P', 'Q', 'R', 'S', 'T', 'U', 
					'V', 'W', 'X', 'Y', 'Z', '_'};
			
	
	final int[] startingNumTiles = {
					  9, 2, 2, 4, 12, 2, 3, 
					  2, 9, 1, 1, 4,  2, 6, 
					  8, 2, 1, 6, 4,  6, 4,
					  2, 2, 1, 2, 1,  2 };
	
	final int[] values = {
			1, 3, 3,  2, 1,  4, 2, 
		    4, 1, 8,  5, 1,  3, 1, 
		    1, 3, 10, 1, 1,  1, 1, 
		    4, 4, 8,  4, 10, 0 };
	
	final int numTilesBeginning = 100;
	
	//Links tiles with their worth
	//Constant as tile worth never changes
	final Map<Character, Integer> tileValueMap;
	
	
	//Stores the tiles currently in the pool
	//Not constant as amount of tiles will change
	Map<Character, Integer> tilePool;
	
	//Amount of tiles in the pool
	int currPoolSize;
	
	Pool()
	{
		
		tileValueMap = new HashMap<Character, Integer>();
		tilePool     = new HashMap<Character, Integer>();
		
		//Initialize the pool
		
		//This creates a map for:
		//Tile <-> Number Of This Tile In The Pool
		this.reset();
		
		
		/*Create Map For:
		    Tile <-> Value
		    
		Will only ever need to be done once as
		this is constant and never changes so we 
		can hard-code it in the constructor*/
		
		for(int i = 0; i < tiles.length; i++)
			tileValueMap.put(tiles[i], values[i]);
		
	}
	
	//Allows the value of a tile to be queried
	public int getTileValue(char x)
	{
		//Error Check 
		if(!tileValueMap.containsKey(x))
			invalidTileException();
		
		//Return Value Of Tile
		return tileValueMap.get(x);
	
	}
	
	
	public int amountInPool(char x)
	{
		//Error Check 
		if(!tilePool.containsKey(x))
			invalidTileException();
				
		//Return Amount Of Tiles
		return tilePool.get(x);
	}
	
	//--Exceptions--
	private void invalidTileException()
	{
		throw new IllegalArgumentException("Invalid Tile");
	}
	
	
    //Allows the pool to be reset
	public void reset()
	{
		//Remove everything from the pool
		this.tilePool.clear();
				
		for(int i = 0; i < tiles.length; i++)
		{
			tilePool.put(tiles[i], startingNumTiles[i]);
		}
				
		//Set the initial size of the pool
		currPoolSize = numTilesBeginning;
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
	public char drawTile()
	{
		//Error Handling
		if(this.isEmpty())
			throw new RuntimeException("Pool Is Empty");
		
		//Tile Range
		int minIndx = 0;
		int maxIndx = tiles.length - 1;
		
		//Temporary Variables
		int rand;
		char t;
		
		//If there's none of a tile left, 
		//check for another one
		do
		{
			rand = (int)(Math.random() * ( (maxIndx-minIndx)+1) ) + minIndx;
			
			t = tiles[rand];
			
		} while(this.amountInPool(t) == 0);
		
		//Change amount of tiles in pool
		this.removeTile(t);
		
		
		return t;
	}
	
	//Removes a tile from the pool
	private void removeTile(char x)
	{
		if(this.amountInPool(x) == 0)
			invalidTileException();
		
		//Adjust the amount of tile x left in the pool
		int numLeftovers = tilePool.get(x) - 1;
		
		tilePool.put(x, numLeftovers);
		
		//Adjust amount of total tiles
		currPoolSize--;
	}
	
	
	public static void main(String[] args)
	{
		Pool p = new Pool();
		p.drawTile();
	}
}

