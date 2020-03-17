package tests.mechanics;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import src.mechanics.Pool;


public class PoolTest {

	@Test
	public void testGetTileValue() {
		Pool pl = new Pool();
		
		assert(pl.getTileValue('A') == 1);
		assert(pl.getTileValue('X') == 8);
		assert(pl.getTileValue('F') == 4);
	}
	
	@Test
	public void testAmountOfTileInPool() {
		Pool pl = new Pool();
		
		assert(pl.amountOfTileInPool('A') == 9);
		assert(pl.amountOfTileInPool('X') == 1);
		assert(pl.amountOfTileInPool('F') == 2);
	}
	
	@Test
	public void testDrawTileFromPool() {
		Pool pl = new Pool();
		
		int originalPoolSize = pl.size();
		
		char newTileOne = pl.drawTileFromPool();
		Assertions.assertEquals(pl.size(), originalPoolSize - 1);
		
		char newTileTwo = pl.drawTileFromPool();
		Assertions.assertEquals(pl.size(), originalPoolSize - 2);
		
		char newTileThree = pl.drawTileFromPool();
		Assertions.assertEquals(pl.size(), originalPoolSize - 3);
		
		Assertions.assertTrue(pl.isValidTile(newTileOne));
		Assertions.assertTrue(pl.isValidTile(newTileTwo));
		Assertions.assertTrue(pl.isValidTile(newTileThree));
		
	}
	
	@Test
	public void testResetPool() {
		
		Pool pl = new Pool();
		int originalPoolSize = pl.size();
		
		for(int i = 0; i < 10; i++)
		{
			pl.drawTileFromPool();
		}
		
		//Test pool size has been changed
		Assertions.assertEquals(pl.size(), originalPoolSize - 10);
		
		pl.resetPool();
		
		//Check its back to the original size
		Assertions.assertEquals(pl.size(), originalPoolSize);
	}
	
	@Test
	public void testSize() {
		
		Pool pl = new Pool();
		
		Assertions.assertEquals(pl.size(), 100);
	}
	
	@Test
	public void testIsEmpty() {
		
		Pool pl = new Pool();
		
		//No Tiles Drawn
		Assertions.assertFalse(pl.isEmpty());
		
		for(int i = 0; i < 50; i++)
		{
			pl.drawTileFromPool();
		}
		
		//50 Tiles Drawn, 50 Tiles Left
		Assertions.assertFalse(pl.isEmpty());
		
		for(int i = 0; i < 50; i++)
		{
			pl.drawTileFromPool();
		}
		
		//100 Tiles Drawn, No Tiles Left
		Assertions.assertTrue(pl.isEmpty());
	}
	
	
	
	
	

}
