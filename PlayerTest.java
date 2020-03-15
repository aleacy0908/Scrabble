import java.util.ArrayList;
import java.util.Arrays;


import static org.junit.jupiter.api.Assertions.*;

import org.junit.Test;



class PlayerTest {

    int score = 34;

    @Test
    public void TestgetName ( ) {

        Player j = new Player("bill", 56);


        assert (j.nameP().equals("bill"));
        assertFalse(j.nameP().equals("john"));

        System.out.println("getName Test Succesful");
    }

    @Test
    public void TestsetName ( ) {

        String name = "john";
        String name2 = "bill";

        Player j = new Player("john",67);

        j.setPlayer(name);

        assert(j.nameP().equals("john"));

        System.out.println("setName Test Succesful");



    }

    @Test
    public void Testreset( ) {

        //String blank = "";
        Player j = new Player("john", 67);

        assert(j.nameP().equals("john"));



        j.reset();

        assert(j.nameP().equals(""));

        System.out.println("reset Test Succesful");

    }

    @Test
    public void TestgetScore( ) {

        Player j = new Player("john",68);

        j.getP_score();

        assertEquals(68, j.getP_score());

        System.out.println("getScore Test Succesful");

    }

    @Test
    public void TestgetFrame( ) {


        Frame frameP1 = new Frame(new Pool());
        Frame frameP2 = new Frame(new Pool());

        frameP1.frame = new ArrayList<Character>(Arrays.asList('E', 'C', 'X', 'S', 'K','J'));
        frameP2.frame = new ArrayList<Character>(Arrays.asList('S', 'W', 'Q', 'A', 'B','L'));

        assertEquals(Arrays.asList('E', 'C', 'X', 'S', 'K','J'),frameP1.getFrame());
        assertEquals(Arrays.asList('S', 'W', 'Q', 'A', 'B','L'),frameP2.getFrame());

        System.out.println("getFrame Test Succesful");


    }

    @Test
    public void TestincreaseScore() {


        Player j = new Player("john",57);


        j.getP_score();

        assertEquals(57, j.getP_score());

        j.increaseScore(10);


        assertEquals(67, j.getP_score());

        System.out.println("getScore Test Successful");

    }


}


