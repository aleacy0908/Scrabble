import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

class Node
{
    //Element Of Node
    private char      data;

    //Array Of Children
    private Node[]    children;

    //Number Of Children
    private int       numChildren;

    //Is This Node The End Of A Word
    private boolean   isEndOfWord = false;

    //Max Children One Can Have
    private final int MAX = 26;

    private boolean isNull = false;

    private int childID;

    public Node(char letter, char[] childArr)
    {
        //Char Array Contains Too Many Letters
        if(children.length > 26)
            throw new IllegalArgumentException("This isn't 1932. Too Many Children");

        //Create A Default Child Array
        children = new Node[MAX];

        //Set Node's Element
        this.data = letter;

        //Create Children
        for(int i = 0; i < numChildren; i++) {
            createChild(childArr[i]);
        }

    }

    public Node(char letter)
    {
        //Create A Default Child Array (EMPTY)
        children = new Node[MAX];

        //Set Node Letter
        this.data = letter;
    }

    public Node()
    {
        isNull = true;
    };

    public boolean isNull() { return isNull; }

    public boolean isRoot() { return this.data == '*'; }

    public int getChildID() { return this.childID; }

    //Get/Set Node's Element
    public char getData()       { return this.data; }
    public void setData(char d)
    {
        isNull = false;
        this.data = d;
    }

    //Get Children Info
    public Node[]  getChildren()    { return this.children;    }
    public int     getNumChildren() { return this.numChildren; }

    //Get/set If The Node Is The End Of A Word
    public boolean isEndOfWord()               { return this.isEndOfWord; }
    public void    setEndOfWord(boolean isEnd)
    {
        this.isEndOfWord = isEnd;
    }

    //Check If The Node Has A Particular Child / Children
    public boolean hasChild(char c) { return (getChild(c) != null); }
    public boolean hasChildren()    { return numChildren > 0; }

    //Retrieve a child from the node
    //If it doesn't exist, return null
    public Node getChild(char c)
    {
        Node child;

        for(int i = 0; i < getNumChildren(); i++)
        {
            child = children[i];

            //If We've Found The Child
            if(child.getData() == c)
                return child;
        }

        //Child Doesn't Exist
        return null;
    }

    //Add A Child/Letter To The Node
    public void createChild(char c)
    {
        //Error Handling
        if(hasChild(c))
            return;
        else if(numChildren == 26)
            throw new IllegalArgumentException("Too Many Children");

        Node child = new Node(c);

        //Add To Children & Increment Num Children
        children[numChildren++] = child;
    }

}

public class DictionaryTree {

    private Node     ROOT;
    private String[] words;
    private int      numWords;

    String dictionaryFile = "res/CollinsScrabbleWords(2019).txt";

    public DictionaryTree()
    {
        try
        {
            //Read In Dictionary
            List<String> words =
                    Files.readAllLines(Paths.get(dictionaryFile));

            //Set Number Of Words
            numWords = words.size();

            /* Our head begins with an asterisk
               as the head node will not be included
               in any words. Every word begins with the
               head's child nodes A-Z.
             */
            ROOT = new Node('*');

            //Store The Words From Dictionary File
            for(String word : words)
                storeWord(word, ROOT, 0);

        }
        catch(IOException ex)
        {
            System.out.println("Unable To Read In Dictionary.");
        }
    }

    public Node getRoot() { return ROOT; }

    /*
      Stores a word in the tree
      Node n -> The current node in the tree we're at
      int  i -> The current letter index in the word
    */
    private void storeWord(String word, Node n, int i)
    {
        //BASE CASE
        if(i == word.length())
        {
            /*Tell this node that it is at the end of a word.
              For example, C -> A -> T: We need to tell T
              that it is the end of a word, even though
              there will be more words after it
              such as C -> A -> T -> S
             */

            n.setEndOfWord(true);
            return;
        }
        //RECURSIVE CASE
        else
        {
            char c = word.charAt(i);

            //Create A Child Letter
            if(!n.hasChild(c))
                n.createChild(c);

            //Recursive Call To Next letter/Node
            storeWord(word, n.getChild(c), i+1);
        }

    }

    public boolean searchTree(String word, Node n, int indx)
    {
        boolean isLastLetter = (word.length() == indx);

        //Base Case: It Is A Word
        if(isLastLetter && n.isEndOfWord())
            return true;
        //Base Case: It Isn't A Word
        else if(isLastLetter && !n.isEndOfWord())
            return false;

        char letter = word.charAt(indx);

        //Base Case: It Isn't A Word
        if(!n.hasChild(letter))
            return false;

        //Recursive Case
        else
            return searchTree(word, n.getChild(letter), indx+1);


    }

    //An Internal Print Method For Testing
    private void print(Node n)
    {
        Node[] children = n.getChildren();

        for(int i = 0; i < n.getNumChildren(); i++)
        {
            Node child = children[i];
            System.out.println(child.getData());

            if(child.hasChildren())
                print(child);
        }
    }

    //Check's If A Word Is In The Dictionary
    public boolean check(String word)
    {
        return searchTree(word, ROOT, 0);
    }
}
