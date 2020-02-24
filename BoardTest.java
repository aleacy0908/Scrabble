import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

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

    @Test
    public void testSetSquare() {
        Board board = new Board();

        System.out.println("---- setSquare test ----");

        board.setSquare(8, 8, 'H');
        char letter = board.getSquare(8, 8).letter;

        assertEquals(letter, 'H');
        assertNotEquals('U', letter);
    }

    @Test
    public void testFirstWord() {
        Board board = new Board();
        Player player = new Player("test", 0);
        String word = "hello";
        int row = 5;
        int column = 4;
        char direction = 'D';

        System.out.println("---- firstWord test ----");

        assertFalse(board.firstWord(row, column, word, direction));

        row = 8;
        column = 7;
        direction = 'A';
        assertTrue(board.firstWord(row, column, word, direction));


    }

    @Test
    public void testConflicts() {
        Board board = new Board();

        System.out.println("---- conflicts test ----");

        board.setSquare(8, 6, 'H');
        board.setSquare(8, 7, 'E');
        board.setSquare(8, 8, 'L');
        board.setSquare(8, 9, 'L');
        board.setSquare(8, 10, 'O');

        assertTrue(board.conflicts(8, 7, "WORD", 'A'));
        assertFalse(board.conflicts(9, 7, "WORD", 'A'));

    }

    @Test
    public void testConnectsToWord() {
        Board board = new Board();
        Player player = new Player("test", 0);

        System.out.println("---- connectsToWord test ----");

        board.setSquare(8, 6, 'H');
        board.setSquare(8, 7, 'E');
        board.setSquare(8, 8, 'L');
        board.setSquare(8, 9, 'L');
        board.setSquare(8, 10, 'O');

        assertTrue(board.connectsToWord(8, 6, "HELLO", 'D'));
        assertFalse(board.connectsToWord(9, 6, "HELLO", 'D'));
    }

    @Test
    public void testNecessaryLetters() {
        Board board = new Board();
        Player player = new Player("test", 0);
        player.getFrameP().frame.clear();
        player.getFrameP().frame = new ArrayList<>(Arrays.asList('W', 'O', 'R', '_', 'D', 'E', 'F'));

        System.out.println("---- necessaryLetters test ----");

        board.setSquare(8, 6, 'H');
        board.setSquare(8, 7, 'E');
        board.setSquare(8, 8, 'L');
        board.setSquare(8, 9, 'L');
        board.setSquare(8, 10, 'O');

        assertTrue(board.necessaryLetters("WORD",player,8,6,'A'));
        assertFalse(board.necessaryLetters("FALSE",player,8,6,'A'));
        assertTrue(board.necessaryLetters("DEW",player,7,6,'D'));
        assertFalse(board.necessaryLetters("TRUE",player,5,6,'D'));
    }

    @Test
    public void testWithinBoard(){
        Board board = new Board();
        Player player = new Player("test", 0);
        player.getFrameP().frame.clear();
        player.getFrameP().frame = new ArrayList<>(Arrays.asList('W', 'O', 'R', 'S', 'D', 'E', 'F'));

        System.out.println("---- withinBoard test ----");

        assertFalse(board.withinBoard(12,12,'A',"HELLO"));
        assertTrue(board.withinBoard(15,11,'A',"HELLO"));
        assertFalse(board.withinBoard(13,3,'D',"HELLO"));
        assertTrue(board.withinBoard(8,3,'D',"HELLO"));
    }

    @Test
    public void testFillSquare(){
        Board board = new Board();
        Player player = new Player("test", 0);
        player.getFrameP().frame.clear();
        player.getFrameP().frame = new ArrayList<>(Arrays.asList('W', 'O', 'R', 'S', 'D', 'E', 'F'));

        System.out.println("---- fillSquare test ----");

        board.tileSelection(player,8,8,'A',"WORD");
        assertEquals(board.getSquare(8,8).getTile(), 'W');
        assertEquals(board.getSquare(8,9).getTile(), 'O');
        assertEquals(board.getSquare(8,10).getTile(), 'R');
        assertEquals(board.getSquare(8,11).getTile(), 'D');
    }




}