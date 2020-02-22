import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {

    @Test
    public void testGetSquare()
    {
        Board BRD = new Board();

        //Squares Containing Multipliers
        Square x = BRD.getSquare(1,1);
        assertEquals (x.getMultiplier(), SCORE_MULT.TW);

        //A Square With Double Word Multiplier
        x = BRD.getSquare(8,8);
        assertEquals (x.getMultiplier(), SCORE_MULT.DW);

        //A Square With Triple Letter Multiplier
        x = BRD.getSquare(6,2);
        assertEquals (x.getMultiplier(), SCORE_MULT.TL);

        //A Square With Double Letter Multiplier
        x = BRD.getSquare(7,3);
        assertEquals(x.getMultiplier(), SCORE_MULT.DL);

        //Squares With No Multipliers
        Square noMult = new Square();

        noMult = BRD.getSquare(1,3);
        assertEquals(noMult.getMultiplier(), SCORE_MULT.NONE);

        noMult = BRD.getSquare(2,5);
        assertEquals(noMult.getMultiplier(), SCORE_MULT.NONE);

        noMult = BRD.getSquare(5,3);
        assertEquals(noMult.getMultiplier(), SCORE_MULT.NONE);

    }

    //Test: Board's Square Class
    public Square w, x, y, z;
    public char[] example_tiles = {'A', 'W', 'Y', '_'};

    @Test
    public void testSetMultiplier()
    {
        Square x = new Square();

        //Set Double Letter
        x.setMultiplier(SCORE_MULT.DL);
        assertEquals(x.getMultiplier(), SCORE_MULT.DL);

        //Set Double Word
        x.setMultiplier(SCORE_MULT.DW);
        assertEquals(x.getMultiplier(), SCORE_MULT.DW);

        //Set Triple Letter
        x.setMultiplier(SCORE_MULT.TL);
        assertEquals(x.getMultiplier(), SCORE_MULT.TL);

        //Set Triple Word
        x.setMultiplier(SCORE_MULT.TW);
        assertEquals(x.getMultiplier(), SCORE_MULT.TW);

        //Set None
        x.setMultiplier(SCORE_MULT.NONE);
        assertEquals(x.getMultiplier(), SCORE_MULT.NONE);

    }

    @Test
    public void testGetMultiplier()
    {
        w = new Square();
        x = new Square(SCORE_MULT.TL);
        y = new Square(SCORE_MULT.DL);
        z = new Square(SCORE_MULT.TW);

        //Get NONE
        assertEquals(w.getMultiplier(), SCORE_MULT.NONE);

        //Get TL
        assertEquals(x.getMultiplier(), SCORE_MULT.TL);

        //Get DL
        assertEquals(y.getMultiplier(), SCORE_MULT.DL);

        //Get TW
        assertEquals(z.getMultiplier(), SCORE_MULT.TW);
    }

    @Test
    public void testSetTile()
    {
        Square x = new Square();

        x.setTile(example_tiles[0]);
        assertEquals(x.getTile(), example_tiles[0]);

        x.setTile(example_tiles[1]);
        assertEquals(x.getTile(), example_tiles[1]);

        x.setTile(example_tiles[0]);
        x.setTile(example_tiles[1]);

        assertFalse(x.getTile() == example_tiles[0]);
        assertTrue(x.getTile() == example_tiles[1]);

    }

    @Test
    public void testGetTile()
    {
        Square x = new Square();
        x.setTile(example_tiles[0]);

        assertTrue(x.getTile() == example_tiles[0]);
        assertFalse(x.getTile() == example_tiles[1]);

        x.setTile(example_tiles[3]);

        assertEquals(x.getTile(), example_tiles[3]);
    }





}