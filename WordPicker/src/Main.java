import java.util.ArrayList;
import java.util.Collections;

public class Main {
    /*
    Doesn't know what to do with '_'
    characters yet.
     */
    public static void main(String[] args)
    {

        //~Tweak the settings~

        //NEEDS UPPERCASE
        ArrayList<Character> frame = new ArrayList<>();
        frame.add('E');
        frame.add('L');
        frame.add('T');
        frame.add('S');
        frame.add('H');
        frame.add('E');

        char firstLetter = 'G';




        //Create word picker
        SuperDuperWordPicker picker = new SuperDuperWordPicker();

        final long startTime = System.currentTimeMillis();

        //PICK WORD
        ArrayList<Character> word = picker.pickWord(firstLetter, frame);

        final long timeElapsed = System.currentTimeMillis() - startTime;

        //Print Out Result
        System.out.print("Found Word: ");
        for(char c : word) System.out.print(c);

        System.out.println("\nTime Taken: " + timeElapsed + "ms");
    }
}
