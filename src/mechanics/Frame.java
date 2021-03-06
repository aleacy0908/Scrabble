package src.mechanics;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Frame {

    //Access to the pool class and the tilePool
    Pool pool;

    private final Pattern p = Pattern.compile("[a-zA-Z_]+");

    //Stores the players frame during a game
    ArrayList<Character> frame;

    //This creates a players frame at the start of the game
    public Frame(Pool p)
    {
        this.frame = new ArrayList<Character>();
        this.pool  = p;

        addToFrame();
    }

    public int size()   { return frame.size(); }
    public void clear() { frame.clear();       }

    public void setFrame(ArrayList<Character> f) { this.frame = f; }
    public void setFrame(List<Character> f) { this.frame = (ArrayList<Character>)f; }

    //Allows access to a players frame
    public ArrayList<Character> getFrame()
    {
        return this.frame;
    }

    /*
    This method allows tiles to be added to a
    frame. It works by returning an ArrayList that consists
    of letters taken from the pool. Letters from the pool
    are added until the size of the frame reaches 7 or
    when the pool empties.
    */
    public void addToFrame()
    {
        while(this.frame.size() != 7 && pool.size() != 0)
        {
            this.frame.add(pool.drawTileFromPool());
        }

    }

    /*
    This method will accept an array of characters chosen
    by the user to create a word
    The letters present inside the array c will be removed from
    the frame. addToFrame is called to refill the frame.
    */
    public void removeFromFrame(String list)
    {
        for (int i = 0; i < list.length(); i++) {
            this.frame.remove(list.charAt(i));
        }

        addToFrame();
    }

    public void removeFromFrame(ArrayList<Character> list)
    {
        for(Character c : list)
        {
            this.frame.remove(c);
        }

        addToFrame();
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
    Like the checkIfEmpty method, it starts with assuming
    that letters are present and returns false when no letters
    are found.
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

    /*
    This is an overloaded function to allow a
    check for a specific letter in a players frame
    */
    public boolean checkLettersInFrame(String s)
    {
        boolean containsLetter = true;

        for(int i = 0; i < s.length(); i++)
        {
            if(!this.frame.contains(s.charAt(i))){
                containsLetter = false;
                break;
            }
        }

        return containsLetter;
    }

    public boolean checkLettersInFrame(Character c)
    {
        return this.frame.contains(c);
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

    //This method allows for other classes to access a tile in a frame
    public Character getTileFromFrame(int i){
        return this.frame.get(i);
    }

}
