import java.util.ArrayList;
import java.util.Arrays;


import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;



class PlayerTest {

    @Test
    public void TestgetName ( ) {


        Player j = new Player("bill", "john", 56, 67);


        assert (j.nameP1().equals("bill"));
        assert (j.nameP2().equals("john"));
    }

    @Test
    public void TestsetName ( ) {

        String name = "john";
        String name2 = "bill";

        Player j = new Player("john", "bill", 56, 67);

        j.setPlayer1(name);
        j.setPlayer2(name2);

        assert(j.nameP1().equals("john"));
        assert(j.nameP2().equals("bill"));


    }

    @Test
    public void Testreset( ) {

        //String blank = "";
        Player j = new Player("john", "bill", 56, 67);

        assert(j.nameP1().equals("john"));
        assert(j.nameP2().equals("bill"));


        j.reset();

        assert(j.nameP1().equals(""));
        assert(j.nameP2().equals(""));

    }

    @Test
    public void TestgetScore( ) {

        Player j = new Player("john", "bill", 57, 68);

       j.getP1_score();

        assertEquals(57, j.getP1_score());

        j.getP2_score();

        assertEquals(68, j.getP2_score());

    }

    @Test
    public void TestgetFrame( ) {


        Frame frameP1 = new Frame();
        Frame frameP2 = new Frame();

        frameP1.frame = new ArrayList<Character>(Arrays.asList('E', 'C', 'X', 'S', 'K'));
        frameP2.frame = new ArrayList<Character>(Arrays.asList('S', 'W', 'Q', 'A', 'B'));

        assertEquals(Arrays.asList('E', 'C', 'X', 'S', 'K'),frameP1.getFrame());
        assertEquals(Arrays.asList('S', 'W', 'Q', 'A', 'B'),frameP2.getFrame());


    }


    }
