package src.mechanics;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class DictionaryTree {

    private class Node
    {
        private char data;
        private Node[] children;

        private int MAX = 26;

        private int numChildren;

        private boolean isEndOfWord = false;

        public Node(char letter, char[] childArr)
        {
            //Error Handling
            if(children.length > 26)
                throw new IllegalArgumentException("This isn't 1932. Too Many Children");

            //Create A Default Child Array
            children = new Node[MAX];

            //Set Node Letter
            this.data = letter;

            for(int i = 0; i < numChildren; i++)
            {
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

        public char getData()       { return this.data; }
        public void setData(char d) { this.data = d;}

        public Node[]  getChildren()    { return this.children;    }
        public int     getNumChildren() { return this.numChildren; }
        public boolean isEndOfWord()    { return this.isEndOfWord; }

        public void    setEndOfWord(boolean isEnd)
        {
            this.isEndOfWord = isEnd;
        }

        public Node getChild(char c)
        {
            Node child;

            for(int i = 0; i < getNumChildren(); i++)
            {
                child = children[i];

                if(child.getData() == c)
                    return child;
            }

            return null;
        }

        public boolean hasChild(char c)
        {
            if(getChild(c) != null)
                return true;

            return false;
        }

        public boolean hasChildren()
        {
            return numChildren > 0;
        }

        public void createChild(char c)
        {
            if(hasChild(c))
                return;
            else if(numChildren == 26)
                throw new IllegalArgumentException("Too Many Children");

            Node child = new Node(c);

            children[numChildren++] = child;
        }

    }

    private Node head;
    private String[] words;
    private int numWords;

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
               head's child nodes.
             */
            head = new Node('*');

            //Store The Words From Dictionary File
            for(String word : words)
                storeWord(word, head, 0);

        }
        catch(IOException ex)
        {
            System.out.println(ex.getLocalizedMessage().toString());
        }
    }

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
            n.setEndOfWord(true);
            return;
        }
        //RECURSIVE CASE
        else
        {
            char c = word.charAt(i);

            if(!n.hasChild(c))
                n.createChild(c);

            storeWord(word, n.getChild(c), i+1);
        }

    }

    private boolean searchTree(String word, Node n, int indx)
    {
        boolean isLastLetter = (word.length() == indx);

        //Base Case
        if(isLastLetter && n.isEndOfWord())
            return true;
            //Base Case
        else if(isLastLetter && !n.isEndOfWord())
            return false;

        char letter = word.charAt(indx);

        //Base Case
        if(!n.hasChild(letter))
            return false;

            //Recursive Case
        else
            return searchTree(word, n.getChild(letter), indx+1);


    }

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
        return searchTree(word, head, 0);
    }

    public void print() { print(head); }
}
