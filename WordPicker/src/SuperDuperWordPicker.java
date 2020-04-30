import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Stack;

public class SuperDuperWordPicker {

    //yay a dictionary
    private DictionaryTree trie = new DictionaryTree();

    //This is a weird one lol we just kinda need it
    private int startFromPos = 0;

    //This contains every letter we remove from the frame
    private Stack<Character> removedTiles = new Stack<>();

    public SuperDuperWordPicker() {}

    /*

    You:

     */
    public ArrayList<Character> pickWord(char beginning, ArrayList<Character> frame)
    {
        //Root Node Of Dictionary
        Node ROOT = trie.getRoot();

        //Store The Word
        ArrayList<Character> output = new ArrayList<>();

        //Find a word
        if(findWord(beginning, ROOT, frame, output))
        {
            //Found A Word
            return output;
        }
        else
        {
            //Couldn't Find A Word
            return null;
        }
    }

    /*

    The Function She Tells You Not To Worry About:

     */

    private boolean findWord(char begin, Node node, ArrayList<Character> currFrame, ArrayList<Character> output)
    {
        //Can probably get rid of this step somehow
        if(node.isRoot())
        {
            //Start from layer one of the trie
            output.add(begin);
            return findWord(begin, node.getChild(begin), currFrame, output);
        }

        //Base Case: We've found a word !
        if(node.isEndOfWord())
            return true;


        while(true)
        {
            /*
            Example: We have C -> A
            Now we find a matching letter for 'A' that's in our
            frame and might lead to a word.

            We have 'G' in our frame!
            Word's exist that begin with C -> A -> G
            Therefore, letter = 'G'
             */
            Node letter = findMatchingLetter(node.getChildren(), currFrame, node.getNumChildren());

            /*
            If this is null, for example, we have
            C -> A

            but then no other letters in our frame will
            match with 'CA', thus we have to go back and
            reconsider from just 'C'
             */
            if(letter.isNull())
                return false;

            /*
            It's getting serious..
            We have a letter and we're removing it from
            the frame.
             */

            Character c = letter.getData();

            ArrayList<Character> newFrame = currFrame;

            //Fix the frame
            newFrame.remove(c);
            removedTiles.add(c);

            //Add the letter to our final word
            output.add(c);

            //Recursive Step to find next letter
            if(!findWord(begin, letter, newFrame, output))
            {
                //This was a dead-end :(

                //Add tiles back to frame
                newFrame.add(removedTiles.pop());

                //Go back to previous iteration of our word
                output.remove(c);
                continue;
            }
            else
            {
                //We found a word !
                return true;
            }
        }
    }

    /*
    Checks our frame for letters that have the potential to
    create words.
     */
    private Node findMatchingLetter(Node[] children, ArrayList<Character> currFrame, int numChildren)
    {
        Node child;

        /*
        startFromPos allows us to start from where
        we left off if a previous word didn't work
        out.
         */
        for(int i = startFromPos; i < numChildren; i++)
        {
            child = children[i];

            //We have a letter we can use
            if(isInFrame(child.getData(), currFrame))
            {
                startFromPos = i + 1;
                return child;
            }
        }

        //No possible letter
        return new Node();
    }

    //Can delete later when this is all implemented with API
    private boolean isInFrame(char c, ArrayList<Character> frame)
    {
        for(char f : frame)
        {
            if(c == f)
                return true;
        }

        return false;
    }

}
