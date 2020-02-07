import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class PoolTest {

	@Test
	void testGetTileValue() {
		Pool pl = new Pool();
		
		assert(pl.getTileValue('A') == 1);
		assert(pl.getTileValue('X') == 8);
		assert(pl.getTileValue('F') == 4);
	}
	
	@Test
	void testAmountOfTileInPool() {
		Pool pl = new Pool();
		
		assert(pl.amountOfTileInPool('A') == 9);
		assert(pl.amountOfTileInPool('X') == 1);
		assert(pl.amountOfTileInPool('F') == 2);
	}
	
	@Test
	void testDrawTileFromPool() {
		Pool pl = new Pool();
		
		int originalPoolSize = pl.size();
		
		char newTileOne = pl.drawTileFromPool();
		assertEquals(pl.size(), originalPoolSize - 1);
		
		char newTileTwo = pl.drawTileFromPool();
		assertEquals(pl.size(), originalPoolSize - 2);
		
		char newTileThree = pl.drawTileFromPool();
		assertEquals(pl.size(), originalPoolSize - 3);
		
		assertTrue(pl.isValidTile(newTileOne));
		assertTrue(pl.isValidTile(newTileTwo));
		assertTrue(pl.isValidTile(newTileThree));
		
	}
	
	@Test
	void testResetPool() {
		
		Pool pl = new Pool();
		int originalPoolSize = pl.size();
		
		for(int i = 0; i < 10; i++)
		{
			pl.drawTileFromPool();
		}
		
		//Test pool size has been changed
		assertEquals(pl.size(), originalPoolSize - 10);
		
		pl.resetPool();
		
		//Check its back to the original size
		assertEquals(pl.size(), originalPoolSize);
	}
	
	@Test
	void testSize() {
		
		Pool pl = new Pool();
		
		assertEquals(pl.size(), 100);
	}
	
	@Test
	void testIsEmpty() {
		
		Pool pl = new Pool();
		
		//No Tiles Drawn
		assertFalse(pl.isEmpty());
		
		for(int i = 0; i < 50; i++)
		{
			pl.drawTileFromPool();
		}
		
		//50 Tiles Drawn, 50 Tiles Left
		assertFalse(pl.isEmpty());
		
		for(int i = 0; i < 50; i++)
		{
			pl.drawTileFromPool();
		}
		
		//100 Tiles Drawn, No Tiles Left
		assertTrue(pl.isEmpty());
	}
	
	
	
	
	

}
