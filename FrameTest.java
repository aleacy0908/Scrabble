import org.junit.Test;
import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class FrameTest {


    @Test
    void getFrame() {
        Frame frame1 = new Frame();
        frame1.frame = new ArrayList<Character>(Arrays.asList('A', 'B', 'C', '_', 'D', 'E', 'F'));

        System.out.println("---- getFrame test ----");
        System.out.println(frame1.getFrame());
        System.out.println("The frame is printed differently in this test as I called the function in" +
                " a println");

        assertEquals(Arrays.asList('A', 'B', 'C', '_', 'D', 'E', 'F'),frame1.getFrame());
    }

    @Test
    void addToFrame() {
        Frame frame1 = new Frame();
        frame1.frame = new ArrayList<Character>(Arrays.asList('A', 'B', 'C'));

        System.out.println("---- addToFrame test ----");
        frame1.displayFrame();
        System.out.println("This frame only contains 3 tiles so the addToFrame function is called until" +
                " the frame contains 7 tiles");
        frame1.addToFrame();
        frame1.displayFrame();


        assertEquals(frame1.frame.size(),7);
    }

    @Test
    void removeFromFrame() {
        //an arraylist that contains letters for the class to remove
        ArrayList<Character> c = new ArrayList<Character>();
        Frame frame1 = new Frame();

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

        assertEquals(frame1.frame.size(),7);
    }

    @Test
    void checkIfEmpty() {
        Frame frame2 = new Frame();

        System.out.println("---- checkIfEmpty test ----");
        System.out.println("Current size of test frame 2 is: " + frame2.frame.size() + " So this test should return" +
                " false");
        System.out.println(frame2.checkIfEmpty());
        assertFalse(frame2.checkIfEmpty());

        System.out.println("After clearing all elements from the frame, the function should return true");

        frame2.frame.clear();

        System.out.println(frame2.checkIfEmpty());
        assertTrue(frame2.checkIfEmpty());
    }

    @Test
    void checkLettersInFrame() {
        Frame frame2 = new Frame();
        frame2.frame = new ArrayList<Character>(Arrays.asList('A', 'B', 'C', '_', 'D', 'E', 'F'));

        System.out.println("---- checkLettersInFrame test ----");

        frame2.displayFrame();

        System.out.println("This frame has been created with tiles and is checked for the letter 'A'. The function" +
                " should return true");
        System.out.println(frame2.checkLettersInFrame('A'));
        assertTrue(frame2.checkLettersInFrame('A'));

        System.out.println("If we test for the letter 'T' which is not in the frame, false should be returned");

        System.out.println(frame2.checkLettersInFrame('T'));
        assertFalse(frame2.checkLettersInFrame('T'));
    }

    @Test
    void getTileFromFrame() {

        System.out.println("---- getTileFromFrame test ----");

        ArrayList<Character> c = new ArrayList<Character>();
        Frame frame1 = new Frame();
        frame1.frame = new ArrayList<Character>(Arrays.asList('A', 'B', 'C', '_', 'D', 'E', 'F'));

        System.out.println("The complete frame:");
        frame1.displayFrame();

        for(int i = 0; i < 3; i++){
            c.add(frame1.getTileFromFrame(i));
        }

        System.out.println("The first three tiles of the frame " + c);

        assertEquals(c,Arrays.asList('A', 'B', 'C'));

    }
}