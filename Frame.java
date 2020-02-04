import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Frame {

    //Access to the pool class and the tilePool
    Pool pool = new Pool();

    private final Pattern p = Pattern.compile("[a-zA-Z_]+");

    //Stores the players frame during a game
    ArrayList<Character> frame;

    //This creates a players frame at the start of the game
    public Frame()
    {
        this.frame = addToFrame();
    }

    //Allows access to a players frame
    public ArrayList<Character> getFrame()
    {
        return this.frame;
    }

    /*
    This method allows a player to add tiles to their
    frame at the start of the game. It works by returning
    an ArrayList that consists of letters taken from the pool.
    letters from the pool are added until the size of the
    ArrayList reaches 7, which is the max tiles a player
    can have in their frame. This should only be used to
    create a players frame.
    */
    private ArrayList<Character> addToFrame()
    {
        ArrayList<Character> frame = new ArrayList<Character>();

        while(frame.size() != 7)
        {
            frame.add(pool.drawTile());
        }
        return frame;
    }

    /*
    This method will accept an array of characters chosen
    by the user to create a word and also accepts an int
    telling the method how many tiles are left in the poolbag.
    The letters present inside the array c will be removed from
    the frame. If the total num of tiles in the bag
    is greater than 0, then tiles will be added back into the
    frame until either the frame is full or the bag becomes
    empty.
    */
    public void removeFromFrame(ArrayList<Character> list)
    {
        for (Character c : list) {
            this.frame.remove(c);
        }

        if(!pool.isEmpty())
        {
            while(this.frame.size() != 7)
            {
                this.frame.add(pool.drawTile());

                //allows for the loop to break if the pool is emptied
                if(pool.isEmpty())
                {
                    break;
                }
            }
        }

    }

    /*
    This method simply just checks if the players
    frame is empty. It starts with assuming its not
    empty and changes if it finds that it is.
    */
    public boolean checkIfEmpty()
    {
        boolean isEmpty = false;

        if(this.frame.size() == 0)
        {
            isEmpty = true;
        }

        return isEmpty;
    }

    /*
    This method scans a players frame to check if
    there are any letter tiles present in a their frame.
    If none are found then like the checkIfEmpty method,
    a message is printed to state that the game is now
    over as a player has run out of letter tiles.
    */
    public boolean checkLettersInFrame()
    {
        boolean containsLetter = true;

        for(char c : this.frame){
            Matcher m = p.matcher(Character.toString(c));
            boolean letter = m.matches();

            if(!letter){
                containsLetter = false;
            }
        }

        return containsLetter;
    }

    //Allows for the frame to be displayed
    public void displayFrame()
    {
        for(char c : this.frame)
        {
            System.out.print(" | " + c + " | ");
        }
        System.out.println();
    }

}
