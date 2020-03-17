package tests.user;

import java.util.ArrayList;
import java.util.Arrays;


import static org.junit.jupiter.api.Assertions.*;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import src.mechanics.Frame;
import src.mechanics.Pool;
import src.user.Player;


class PlayerTest {

    int score = 34;

    @Test
    public void TestgetName ( ) {

        Player j = new Player("bill");


        assert (j.nameP().equals("bill"));
        Assertions.assertFalse(j.nameP().equals("john"));

        System.out.println("getName Test Succesful");
    }

    @Test
    public void TestsetName ( ) {

        String name = "john";
        String name2 = "bill";

        Player j = new Player("john");

        j.setPlayer(name);

        assert(j.nameP().equals("john"));

        System.out.println("setName Test Succesful");



    }

    @Test
    public void Testreset( ) {

        //String blank = "";
        Player j = new Player("john");

        assert(j.nameP().equals("john"));



        j.reset();

        assert(j.nameP().equals(""));

        System.out.println("reset Test Succesful");

    }

    @Test
    public void TestgetScore( ) {

        Player j = new Player("john");

        j.setScore(68);

        Assertions.assertEquals(68, j.getScore());

        System.out.println("getScore Test Succesful");

    }

    @Test
    public void TestgetFrame( ) {


        Frame frameP1 = new Frame(new Pool());
        Frame frameP2 = new Frame(new Pool());

        frameP1.setFrame(Arrays.asList('E', 'C', 'X', 'S', 'K','J'));
        frameP2.setFrame(Arrays.asList('S', 'W', 'Q', 'A', 'B','L'));

        Assertions.assertEquals(Arrays.asList('E', 'C', 'X', 'S', 'K','J'),frameP1.getFrame());
        Assertions.assertEquals(Arrays.asList('S', 'W', 'Q', 'A', 'B','L'),frameP2.getFrame());

        System.out.println("getFrame Test Succesful");


    }

    @Test
    public void TestincreaseScore() {


        Player j = new Player("john");

        j.setScore(57);

        Assertions.assertEquals(57, j.getScore());

        j.increaseScore(10);

        Assertions.assertEquals(67, j.getScore());

        System.out.println("getScore Test Successful");

    }


}


