package tests.mechanics;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import src.mechanics.Frame;
import src.mechanics.Pool;
import src.mechanics.Square;
import src.mechanics.Board;

import java.util.ArrayList;
import java.util.Arrays;

import src.mechanics.Square.*;
import src.user.Player;

import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {

    void testMessage(String s)
    {
        System.out.println(s);
    }

    @Test
    public void testGetSquare()
    {
        testMessage("Running testGetSquare");

        Board BRD = new Board();

        //Squares Containing Multipliers
        Square x = BRD.getSquare(1,1);
        Assertions.assertEquals (x.getMultiplier(), SCORE_MULT.TW);

        //A Square With Double Word Multiplier
        x = BRD.getSquare(8,8);
        x = BRD.getSquare(8,8);
        Assertions.assertEquals (x.getMultiplier(), SCORE_MULT.DW);

        //A Square With Triple Letter Multiplier
        x = BRD.getSquare(6,2);
        Assertions.assertEquals (x.getMultiplier(), SCORE_MULT.TL);

        //A Square With Double Letter Multiplier
        x = BRD.getSquare(7,3);
        Assertions.assertEquals(x.getMultiplier(), SCORE_MULT.DL);

        //Squares With No Multipliers
        Square noMult = new Square();

        noMult = BRD.getSquare(1,3);
        Assertions.assertEquals(noMult.getMultiplier(), SCORE_MULT.NONE);

        noMult = BRD.getSquare(2,5);
        Assertions.assertEquals(noMult.getMultiplier(), SCORE_MULT.NONE);

        noMult = BRD.getSquare(5,3);
        Assertions.assertEquals(noMult.getMultiplier(), SCORE_MULT.NONE);

    }

    //Test: Board's Square Class
    public Square w, x, y, z;
    public char[] example_tiles = {'A', 'W', 'Y', '_'};

    @Test
    public void testSetMultiplier()
    {
        testMessage("Running testGetMultiplier");

        Square x = new Square();

        //Set Double Letter
        x.setMultiplier(SCORE_MULT.DL);
        Assertions.assertEquals(x.getMultiplier(), SCORE_MULT.DL);

        //Set Double Word
        x.setMultiplier(SCORE_MULT.DW);
        Assertions.assertEquals(x.getMultiplier(), SCORE_MULT.DW);

        //Set Triple Letter
        x.setMultiplier(SCORE_MULT.TL);
        Assertions.assertEquals(x.getMultiplier(), SCORE_MULT.TL);

        //Set Triple Word
        x.setMultiplier(SCORE_MULT.TW);
        Assertions.assertEquals(x.getMultiplier(), SCORE_MULT.TW);

        //Set None
        x.setMultiplier(SCORE_MULT.NONE);
        Assertions.assertEquals(x.getMultiplier(), SCORE_MULT.NONE);

    }

    @Test
    public void testGetMultiplier()
    {
        testMessage("Running testGetMultiplier");

        w = new Square();
        x = new Square(SCORE_MULT.TL);
        y = new Square(SCORE_MULT.DL);
        z = new Square(SCORE_MULT.TW);

        //Get NONE
        Assertions.assertEquals(w.getMultiplier(), SCORE_MULT.NONE);

        //Get TL
        Assertions.assertEquals(x.getMultiplier(), SCORE_MULT.TL);

        //Get DL
        Assertions.assertEquals(y.getMultiplier(), SCORE_MULT.DL);

        //Get TW
        Assertions.assertEquals(z.getMultiplier(), SCORE_MULT.TW);
    }

    @Test
    public void testSetTile()
    {
        testMessage("Running testSetTile");

        Square x = new Square();

        x.setTile(example_tiles[0]);
        Assertions.assertEquals(x.getTile(), example_tiles[0]);

        x.setTile(example_tiles[1]);
        Assertions.assertEquals(x.getTile(), example_tiles[1]);

        x.setTile(example_tiles[0]);
        x.setTile(example_tiles[1]);

        assertFalse(x.getTile() == example_tiles[0]);
        assertTrue(x.getTile() == example_tiles[1]);

    }

    @Test
    public void testGetTile()
    {
        testMessage("Running testGetTile");

        Square x = new Square();
        x.setTile(example_tiles[0]);

        assertTrue(x.getTile() == example_tiles[0]);
        assertFalse(x.getTile() == example_tiles[1]);

        x.setTile(example_tiles[3]);

        Assertions.assertEquals(x.getTile(), example_tiles[3]);
    }

    @Test
    public void testSetSquare() {

        testMessage("Running testSetSquare");

        Board board = new Board();

        board.setSquare(8, 8, 'H');
        char letter = board.getSquare(8, 8).getTile();

        assertEquals(letter, 'H');
        assertNotEquals('U', letter);
    }

    @Test
    public void testFirstWord() {

        testMessage("Running testFirstWord");

        Board board = new Board();
        Player player = new Player("test");
        String word = "hello";
        int row = 5;
        int column = 4;
        char direction = 'D';

        Assertions.assertFalse(board.firstWord(row, column, word, direction));

        row = 8;
        column = 7;
        direction = 'A';
        Assertions.assertTrue(board.firstWord(row, column, word, direction));


    }

    @Test
    public void testConflicts() {

        testMessage("Running testConflicts");

        Board board = new Board();

        board.setSquare(8, 6, 'H');
        board.setSquare(8, 7, 'E');
        board.setSquare(8, 8, 'L');
        board.setSquare(8, 9, 'L');
        board.setSquare(8, 10, 'O');

        Assertions.assertTrue(board.conflicts(8, 7, "WORD", 'A'));
        Assertions.assertFalse(board.conflicts(9, 7, "WORD", 'A'));

    }

    @Test
    public void testConnectsToWord() {

        testMessage("Running testConnectsToWord");

        Board board = new Board();
        Player player = new Player("test");

        board.setSquare(8, 6, 'H');
        board.setSquare(8, 7, 'E');
        board.setSquare(8, 8, 'L');
        board.setSquare(8, 9, 'L');
        board.setSquare(8, 10, 'O');

        Assertions.assertTrue(board.connectsToWord(8, 6, "HELLO", 'D'));
        Assertions.assertFalse(board.connectsToWord(9, 6, "HELLO", 'D'));
    }

    @Test
    public void testWithinBoard(){

        testMessage("Running testWithinBoard");

        Board board = new Board();
        Player player = new Player("test");

        Frame f = new Frame(new Pool());
        f.setFrame(new ArrayList<Character>(Arrays.asList('W', 'O', 'R', 'S', 'D', 'E', 'F')));
        player.setFrame(f);


        player.getFrameP().clear();

        Assertions.assertFalse(board.withinBoard(12,12,'A',"HELLO"));
        Assertions.assertTrue(board.withinBoard(15,11,'A',"HELLO"));
        Assertions.assertFalse(board.withinBoard(13,3,'D',"HELLO"));
        Assertions.assertTrue(board.withinBoard(8,3,'D',"HELLO"));
    }

    @Test
    public void testFillSquare(){

        testMessage("Running testFillSquare");

        Board board = new Board();
        Player player = new Player("test", new Frame(new Pool()));

        player.getFrameP().clear();

        Frame f = new Frame(new Pool());

        f.setFrame(new ArrayList<Character>(Arrays.asList('W', 'O', 'R', 'S', 'D', 'E', 'F')));

        player.setFrame(f);

        board.tileSelection(player,8,8,'A',"WORD", 0);
        Assertions.assertEquals(board.getSquare(8,8).getTile(), 'W');
        Assertions.assertEquals(board.getSquare(8,9).getTile(), 'O');
        Assertions.assertEquals(board.getSquare(8,10).getTile(), 'R');
        Assertions.assertEquals(board.getSquare(8,11).getTile(), 'D');
    }
    
    @Test 
    public void TestDisplayBoard ( ) {

        testMessage("Running testDisplayBoard");

        Board board = new Board();

        board.DisplayBoard();

    }
    
    @Test
    public void TestResetBoard()
    {
        testMessage("Running testResetBoard");

        Board board = new Board();

        board.setSquare(9, 4, 'J');
        char letter = board.getSquare(9, 4).getTile();

        assertEquals(letter, 'J');

        board.ResetBoard();

        Square x = board.getSquare(9, 4);

        assertFalse(x.isOccupied());

    }




}
