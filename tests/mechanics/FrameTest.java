package tests.mechanics;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import src.mechanics.Frame;
import src.mechanics.Pool;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class FrameTest {


    @Test
    public void getFrameTest() {
        Frame frame1 = new Frame(new Pool());
        frame1.setFrame(new ArrayList<Character>(
                Arrays.asList('A', 'B', 'C', '_', 'D', 'E', 'F')));

        System.out.println("---- getFrame test ----");
        System.out.println(frame1.getFrame());
        System.out.println("The frame is printed differently in this test as I called the function in" +
                " a println");

        Assertions.assertEquals(Arrays.asList('A', 'B', 'C', '_', 'D', 'E', 'F'),frame1.getFrame());
    }

    @Test
    public void addToFrameTest() {
        Frame frame1 = new Frame(new Pool());

        System.out.println("---- addToFrame test ----");
        frame1.displayFrame();
        System.out.println("This frame only contains 3 tiles so the addToFrame function is called until" +
                " the frame contains 7 tiles");
        frame1.addToFrame();
        frame1.displayFrame();


        Assertions.assertEquals(frame1.size(),7);
    }

    @Test
    public void removeFromFrameTest() {
        //an arraylist that contains letters for the class to remove
        ArrayList<Character> c = new ArrayList<Character>();
        Frame frame1 = new Frame(new Pool());

        System.out.println("---- removeFromFrame test ----");
        System.out.println("Test Frame 1 before removing first 3 tiles: ");
        frame1.displayFrame();
        for(int i = 0; i < 3; i++){
            c.add(frame1.getTileFromFrame(i));
        }
        frame1.removeFromFrame(c);
        System.out.println("Test Frame 1 after removing first 3 tiles: " );
        frame1.displayFrame();
        System.out.println("For this test I simply removed the first three tiles and replaced them with new tiles");

        Assertions.assertEquals(frame1.size(),7);
    }

    @Test
    public void checkIfEmptyTest() {
        Frame frame2 = new Frame(new Pool());

        System.out.println("---- checkIfEmpty test ----");
        System.out.println("Current size of test frame 2 is: " + frame2.size() + " So this assert should return" +
                " false");
        System.out.println(frame2.checkIfEmpty());
        Assertions.assertFalse(frame2.checkIfEmpty());

        System.out.println("After clearing all elements from the frame, the function should return true");

        frame2.clear();

        System.out.println(frame2.checkIfEmpty());
        Assertions.assertTrue(frame2.checkIfEmpty());
    }

    @Test
    public void checkLettersInFrameTest() {
        Frame frame2 = new Frame(new Pool());
        frame2.setFrame(new ArrayList<Character>(Arrays.asList('A', 'B', 'C', '_', 'D', 'E', 'F')));

        System.out.println("---- checkLettersInFrame test ----");

        frame2.displayFrame();

        System.out.println("This frame has been created with tiles and is checked for the letter 'A'. The function" +
                " should return true");
        System.out.println(frame2.checkLettersInFrame('A'));
        Assertions.assertTrue(frame2.checkLettersInFrame('A'));

        System.out.println("If we test for the letter 'T' which is not in the frame, false should be returned");

        System.out.println(frame2.checkLettersInFrame('T'));
        Assertions.assertFalse(frame2.checkLettersInFrame('T'));
    }

    @Test
    public void getTileFromFrameTest() {

        System.out.println("---- getTileFromFrame test ----");

        ArrayList<Character> c = new ArrayList<Character>();
        Frame frame1 = new Frame(new Pool());
        frame1.setFrame(new ArrayList<Character>(Arrays.asList('A', 'B', 'C', '_', 'D', 'E', 'F')));

        System.out.println("The complete frame:");
        frame1.displayFrame();

        for(int i = 0; i < 3; i++){
            c.add(frame1.getTileFromFrame(i));
        }

        System.out.println("The first three tiles of the frame " + c);

        assertEquals(c,Arrays.asList('A', 'B', 'C'));

    }
}