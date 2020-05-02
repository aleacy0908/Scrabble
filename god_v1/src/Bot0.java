
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.Map.Entry;

public class Bot0 implements BotAPI {

    // The public API of Bot must not change
    // This is ONLY class that you can edit in the program
    // Rename Bot to the name of your team. Use camel case.
    // Bot may not alter the state of the game objects
    // It may only inspect the state of the board and the player objects

    private PlayerAPI me;
    private OpponentAPI opponent;
    private BoardAPI board;
    private UserInterfaceAPI info;
    private DictionaryAPI dictionary;
    private int turnCount;

    private final String COMMAND_NAME = "NAME pintsMen";
    private final String COMMAND_PASS = "PASS";

    private final String DIR_ACROSS = "A";
    private final String DIR_DOWN   = "D";

    //FINAL CHOICE FOR EACH MOVE
    String      WORD_CHOICE;
    String      DIRECTION_CHOICE;
    Coordinates COORD;

    Bot0(PlayerAPI me, OpponentAPI opponent, BoardAPI board, UserInterfaceAPI ui, DictionaryAPI dictionary) {
        this.me = me;
        this.opponent = opponent;
        this.board = board;
        this.info = ui;
        this.dictionary = dictionary;
        turnCount = 0;
    }

    //Turns num into a letter in the alphabet
    public char getLetter(int x)
    {
        return x > 0 && x < 15 ? (char) (x + 'A' - 1) : null;
    }

    public String formCommandString(int r, int c, String dir, String word)
    {
        //Convert Row Num To Alphabet Letter
        char letter = getLetter(c);

        System.out.println("Row Command: " + r);
        System.out.println("Col Command: " + c);

        //Command Output
        return (String.valueOf(letter) + r + " " + dir + " " + word);
    }

    /*
    TODO:
    This function needs to analyse the scores of each word at
    each coordinate and pick the best one.
     */
    public void chooseBestWord(HashMap<Coordinates, String> acrossWords,
                               HashMap<Coordinates, String> downWords)
    {

        if(acrossWords.size() != 0)
        {
            COORD = acrossWords.keySet().iterator().next();

            System.out.println("AFter row: " + COORD.getRow());
            System.out.println("AFter col: " + COORD.getCol());

            WORD_CHOICE = acrossWords.get(COORD);
            DIRECTION_CHOICE = DIR_ACROSS;
        }

        else if(downWords.size() != 0)
        {
            //These coordinates are the wrong way around
            //because we transposed the board in order to
            //find 'down' words
            Coordinates tmp = downWords.keySet().iterator().next();

            WORD_CHOICE = downWords.get(tmp);

            //Un-transpose Coords
            COORD = new Coordinates(tmp.getCol(), tmp.getRow());

            //Direction is down
            DIRECTION_CHOICE = DIR_DOWN;
        }

        else
        {
            DIRECTION_CHOICE = null;
            WORD_CHOICE  = null;
            COORD = null;
        }
    }

    public String getCommand() {
        //Reset previous move's choices
        DIRECTION_CHOICE = "";
        WORD_CHOICE = "";
        COORD = null;

        System.out.println("New Move!");


        God almightyWordChooser = new God();


        //Turn Our Frame Into A Regular String
        String FRAME = "";
        int len = me.getFrameAsString().length();
        String[] myFrame = me.getFrameAsString().substring(1, len - 2).split(", ");

        for(String s : myFrame)
            FRAME += s;

        /*
        //Set Our Name
        if (turnCount == 0)
        {
            turnCount++;
            return COMMAND_NAME;
        }*/

        String command;

        if(board.isFirstPlay())
        {
            ArrayList<String> firstMoveList =
                    almightyWordChooser.firstPlay(FRAME);

            //Find out which is longest / gets the most points

            if(firstMoveList.size() == 0)
                return COMMAND_PASS;

            //TEMPORARILY JUST CHOOSING FIRST OPTION
            String finalWord = firstMoveList.get(0);

            //Only sets words across on the first go (could make random)
            command = formCommandString(8, 8, DIR_ACROSS, finalWord);
        }
        else {

            //Across word options
            HashMap<Coordinates, String> wordMapAcross =
                    almightyWordChooser.choose(this.board, FRAME);

            //Down word options (we do this by transposing the board)
            TransposeBoard tpBoard = new TransposeBoard(this.board);

            HashMap<Coordinates, String> wordMapDown =
                    almightyWordChooser.choose(tpBoard, FRAME);

            /*
            Unfortunately, we have to pass if God
            can't find any scrabble words.
             */
            if (wordMapAcross.size() == 0 &&
                wordMapDown.size()   == 0)
                return COMMAND_PASS;


            chooseBestWord(wordMapAcross, wordMapDown);

            int row = COORD.getRow();
            int col = COORD.getCol();

            System.out.println("Word choice: " + WORD_CHOICE);

            command = formCommandString(row, col, DIRECTION_CHOICE, WORD_CHOICE);
        }

        turnCount++;
        return command;
    }
}



class God {

    private SuperDuperWordPicker wordPicker;
    private PlacementTable tableCreator;
    private Board playerBoard;

    private int BOARD_ROWS = 15;
    private int BOARD_COLS = 15;

    public God()
    {
        wordPicker = new SuperDuperWordPicker();
    }

    public ArrayList<String> firstPlay(String frame)
    {
        //Convert Frame String into Frame ArrayList
        ArrayList<Character> frameArr = new ArrayList<>();

        System.out.println("original: " + frame);

        for(char c : frame.toCharArray())
            frameArr.add(c);

        ArrayList<String> wordList = new ArrayList<>();

        StringBuilder wordBuilder;

            //Should correspond to the centre of the board
            Coordinates centre = new Coordinates(7, 7);

            for(char c : frameArr)
            {

                ArrayList<Character> cloneFrame = (ArrayList<Character>) frameArr.clone();

                System.out.println("FRAME ARR : " + frameArr.toString());
                wordBuilder = new StringBuilder();

                ArrayList<Character> word = chooseFirstWord(cloneFrame, c);

                //No Word Found
                if(word == null)
                    continue;

                for(char x : word)
                    wordBuilder.append(x);

                wordList.add(wordBuilder.toString());
            }

            return wordList;
    }

    public HashMap<Coordinates, String> choose(BoardAPI b, String frame)
    {
        //Convert Frame String into Frame ArrayList
        ArrayList<Character> frameArr = new ArrayList<>();

        for(char c : frame.toCharArray())
            frameArr.add(c);

        HashMap<Coordinates, String> wordMap = new HashMap<Coordinates, String>();
        StringBuilder wordBuilder;

        //Scan every board square
        for(int i = 0; i < BOARD_ROWS; i++)
        {
            for(int j = 0; j < BOARD_COLS; j++)
            {
                //Attempt to find a word at this location
                ArrayList<Character> word = chooseWordAtCoord(i, j, b, frameArr);

                //No word possible to place at location
                if(word == null)
                    continue;

                //The coordinate to place on the board will be use
                //1-15 and not the indexes of 0-14
                Coordinates coord = new Coordinates(i + 1, j + 1);

                //Turn ArrayList of the word's
                //chars into a String
                wordBuilder = new StringBuilder();

                for(Character c : word)
                    wordBuilder.append(c);

                System.out.println("Coord Row: " + coord.getRow());
                System.out.println("Coord Row: " + coord.getCol());
                System.out.println("Word: "  + wordBuilder.toString());

                //Place in wordmap
                wordMap.put(coord, wordBuilder.toString());
            }
        }

        return wordMap;
    }

    private ArrayList<Character> chooseFirstWord(ArrayList<Character> frame, char firstLetter)
    {
        if(!frame.contains(firstLetter))
            throw new IllegalArgumentException("Invalid First Letter Index");

        //New table creator
        tableCreator = new PlacementTable();

        frame.remove((Character) firstLetter);

        ArrayList<Character> word =
                wordPicker.pickWord(frame, tableCreator.getFirstMoveFormat(firstLetter));

        System.out.println("First Letter: " + firstLetter);
        System.out.println("Word Found: " + word);

        if(word == null)
            return null;
        else
            return word;
    }

    private ArrayList<Character> chooseWordAtCoord(int r, int c, BoardAPI b, ArrayList<Character> frame)
    {
        //New table creator
        tableCreator = new PlacementTable(b);

        String finalWord;

        //Check if we can place a word here
        if(!tableCreator.isValidLocation(r, c))
            return null;
        else
        {
            //Create Placement Table
            tableCreator.create(r, c);

            //Choose Word Using Placement Table Formatting
            return wordPicker.pickWord(frame, tableCreator.getFinalFormat());
        }
    }
}

class PlacementTable
{
    //~Settings~

    //A Letter Might Need / Has Potential To Be Placed Here
    private char typePotential  = '*';

    //This Tile Isn't Relevant To Our Move
    private char typeIrrelevant = '-';

    //End Of Settings

    private Tile firstLetter;
    private int  spaceForWord;
    private int  primaryRow;

    char[][] currTable;

    //Size Of The Board
    private int SIZE = 15;

    private int TABLE_ROWS = 10;
    private int TABLE_COLS = 10;

    private BoardAPI playerBoard;

    ArrayList<char[][]> list = new ArrayList<>();

    public PlacementTable() {}

    public PlacementTable(BoardAPI b)
    {
        this.playerBoard = b;
    }

    public void setBoard(BoardAPI b)
    {
        this.playerBoard = b;
    }

    //Primary Function
    public char[][] create(int r, int c)
    {
        if(isValidLocation(r,c))
            return createPlacementTable(r,c);
        else
            return null;
    }

    public char getFirstLetter()
    {
        //Letter at the main coordinate
        //of our placement table
        return firstLetter.getLetter();
    }

    public int getSpaceForWord()
    {
        return this.spaceForWord;
    }

    public char[] getWordFormat()
    {
        return currTable[primaryRow];
    }

    public boolean isValidLocation(int r, int c)
    {
        //Get Square
        Square sqr = playerBoard.getSquareCopy(r, c);

        //Condition To Implement
        //Empty square adjacent to an occupied square

        Tile tl = sqr.getTile();

        //Empty squares to the right of A
        int emptySquares = numberOfEmptyTilesToTheRightOf(r,c);

        /*
        Checks:
        -> Tile Is Not Blank (Starting Letter)
        -> Empty Tile Left Of Our Starting Tile
        -> There Are Empty Tiles To Be Filled */

        return sqr.isOccupied() && isEmptyTileLeftOf(r, c) && emptySquares > 2;

    }

    private char[][] createPlacementTable(int r, int c) {

        this.firstLetter  = playerBoard.getSquareCopy(r, c).getTile();
        this.spaceForWord = (this.SIZE - r);
        this.primaryRow   = 4;

        //Fill Table With X's
        char[][] placementTable = createBlankTable(TABLE_ROWS, TABLE_COLS);

        System.out.println(placementTable);

        //Tiles Left On The Board AFTER
        //Our First Tile (First Letter Of The Word)
        int tilesLeft = SIZE - r;

        //Are We In The Middle Of Creating A Word?
        //If So, We Might Need To Place A Tile There
        boolean creatingWord = false;

        //Placeholder Tile Variable
        char tl;

        //Location Of Where We
        //Are In The Table
        int tableRow = 0;
        int tableCol = 0;

        Square sqr;

        int divider = (TABLE_ROWS / 2) - 1;

        for (int row = r - divider; row < r + divider; row++) {
            for (int col = c; col < SIZE; col++) {

                //If We've Gone Off The Board
                if(row < 1 || row >= 15 ||
                        tableRow >= TABLE_ROWS || tableCol >= TABLE_COLS)
                    continue;

                //Get Next Square
                sqr = playerBoard.getSquareCopy(row, col);

                if (row == r) {
                    tl = sqr.isOccupied() ? sqr.getTile().getLetter() : typePotential;
                } else {

                    //If there's a letter here and it's connected
                    //to our potential start row
                    tl = isConnected(row, col, r) && sqr.isOccupied() ?
                            sqr.getTile().getLetter() : typeIrrelevant;
                }

                //Set Character
                placementTable[tableRow][tableCol++] = tl;
            }

            tableRow++;
            tableCol = 0;
        }

        currTable = placementTable;

        return placementTable;
    }

    public ArrayList<PotentialTile> getFinalFormat()
    {
        char[] mainRow = getWordFormat();
        char[][] table = currTable;

        ArrayList<PotentialTile> finalFormat = new ArrayList<>();

        for(int c = 0; c < mainRow.length; c++)
        {
            char character = mainRow[c];

            PotentialTile tile = new PotentialTile();

            if(character == 'X')
                break;

            if(character == '*')
            {
                //Restriction word
                String restrictionWord = findWordDirectlyAbove(primaryRow, c);

                boolean noRestrictions = (restrictionWord.equals("*"));

                //Create new restriction
                if(!noRestrictions)
                {
                    Restriction res = new Restriction(restrictionWord);
                    tile.setRestriction(res);
                }
            }
            else
            {
                //Set character at that position
                tile.setLetter(character);
            }

            finalFormat.add(tile);
        }
        return finalFormat;
    }

    public ArrayList<PotentialTile> getFirstMoveFormat(char firstLetter)
    {
        ArrayList<PotentialTile> firstMoveTable = new ArrayList<>();

        firstMoveTable.add(new PotentialTile(firstLetter));

        for(int i = 1; i < 7; i++)
            firstMoveTable.add(new PotentialTile());

        return firstMoveTable;
    }

    /*
    TODO:
    This needs to find words below the current letter
    as well.
     */
    private String findWordDirectlyAbove(int r, int c)
    {
        char[][] table = currTable;
        String word = "";

        if(r == 0)
            throw new IllegalArgumentException("Invalid Coord");

        for(int i = r; i > 0; i--)
        {
            char next = table[i][c];

            //At End Of Word
            if(next == 'X' || next == '-')
                break;

            //Next Character + Old Word
            word = String.valueOf(next).concat(word);
        }

        return word;
    }

    private boolean isConnected(int r, int c, int connectionRow)
    {
        Square sqr = playerBoard.getSquareCopy(r, c);

        //Two Base Case
        if(r == connectionRow)
            return true;
        else if(!sqr.isOccupied())
            return false;

            //Recursive Case
        else
            return isConnected(r + 1, c, connectionRow);
    }

    //Check If Empty Tile To Left
    private boolean isEmptyTileLeftOf(int r, int c)
    {
        //If we're at the left
        //edge of the board
        if(c == 0)
            return true;
        else
        {
            Square tile = playerBoard.getSquareCopy(r, c - 1);

            return !tile.isOccupied();
        }
    }

    private char[][] createBlankTable(int rows, int cols)
    {
        //Empty array
        char[][] placementTable = new char[rows][cols];

        //Fill with X's
        for(int i = 0; i < rows; i++)
        {
            for(int j = 0; j < cols; j++)
            {
                placementTable[i][j] = 'X';
            }
        }

        return placementTable;
    }

    //Check If Empty Tile Above
    private boolean isEmptyTileAbove(int r, int c)
    {
        return !(playerBoard.getSquareCopy(r - 1, c).isOccupied());
    }

    private boolean outOfBounds(int r, int c)
    {
        //return (r )
        //if(row < 1 || row >= 15 ||
        //        tableRow >= TABLE_ROWS || tableCol >= TABLE_COLS)
        return false;
    }

    private int numberOfEmptyTilesToTheRightOf(int r, int c)
    {
        Square sqr;
        r++;

        //Max Number Of Tiles To The Right Of This One
        int leftOnBoard = (SIZE - r);

        //Count How Many Are Empty
        int counter = 0;

        for(int i = 0; i < leftOnBoard; i++)
        {
            //Move To Next Square
            sqr = playerBoard.getSquareCopy(r++, c);

            if(sqr.isOccupied())
                break;
            else
                counter++;
        }

        return counter;

    }
}

class SuperDuperWordPicker {

    //yay a dictionary
    private DictionaryTree trie = new DictionaryTree();

    //This is a weird one lol we just kinda need it
    private int startFromPos = 0;

    //This contains every letter we remove from the frame
    private Stack<Character> removedTiles = new Stack<>();

    ArrayList<PotentialTile> wordFormat;

    public SuperDuperWordPicker() {}

    /*

    You:

     */
    public ArrayList<Character> pickWord(ArrayList<Character> frame, ArrayList<PotentialTile> formatting)
    {
        this.wordFormat = formatting;

        //Root Node Of Dictionary
        DictNode ROOT = trie.getRoot();

        //Store The Word
        ArrayList<Character> output = new ArrayList<>();

        //Find a word
        if(findWord(0, ROOT, frame, output, 0))
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

    private boolean findWord(int pos, DictNode node, ArrayList<Character> currFrame,
                             ArrayList<Character> output, int numFrameTilesUsed)
    {
        //We've gone past the word size limit
        if(pos >= wordFormat.size())
            return false;

        //Check our current tiles
        PotentialTile currTile = wordFormat.get(pos);

        //Base Case: We've found a word !
        if(node.isEndOfWord() && numFrameTilesUsed > 0)
            return true;

        boolean beenHereBefore = false;

        while(true)
        {

            System.out.println("NEw loop!");
            /*
            Example: We have C -> A
            Now we find a matching letter for 'A' that's in our
            frame and might lead to a word.

            We have 'G' in our frame!
            Word's exist that begin with C -> A -> G
            Therefore, letter = 'G'
             */

            DictNode letter;

            if(currTile.hasLetter())
            {

                char c = currTile.getLetter();

                if(node.hasChild(c) && !beenHereBefore)
                {
                    System.out.println(10);
                    beenHereBefore = true;
                    letter = node.getChild(c);
                }
                else
                {
                    System.out.println(20);
                    beenHereBefore = false;
                    return false;
                }
            }
            else
            {
                letter = findMatchingLetter(node.getChildren(), currFrame, node.getNumChildren());
            }

            /*
            If this is null, for example, we have
            C -> A

            but then no other letters in our frame will
            match with 'CA', thus we have to go back and
            reconsider from just 'C'
             */
            if(letter.isNull())
            {
                System.out.println(100);
                return false;
            }

            Character c = letter.getData();

            System.out.println("Testing: " + c);

            //Check our restrictions
            if(currTile.hasRestriction())
            {
                boolean valid = currTile.getRestriction().check(c, trie);

                if(!valid)
                    return false;
            }

            /*
            It's getting serious..
            We have a letter and we're removing it from
            the frame.
             */

            ArrayList<Character> newFrame = currFrame;

            //If we're using a letter
            //from the frame, remove it
            //from said frame
            if(!currTile.hasLetter())
            {
                newFrame.remove(c);
                numFrameTilesUsed++;
                removedTiles.add(c);
            }

            System.out.println(1);

            //Add the letter to our final word
            output.add(c);

            //Recursive Step to find next letter
            if(!findWord(pos + 1, letter, newFrame, output, numFrameTilesUsed))
            {

                System.out.println("DEAD END");

                //This was a dead-end :(

                //Add tiles back to frame, unless we
                //didn't use it from the frame
                if(!currTile.hasLetter())
                {
                    newFrame.add(removedTiles.pop());
                    numFrameTilesUsed--;
                }

                //Go back to previous iteration of our word
                output.remove(c);

                System.out.println("About to continue");

                continue;
            }
            else
            {
                System.out.println("Found The Word " + output);
                //We found a word !
                return true;
            }
        }
    }

    /*
    Checks our frame for letters that have the potential to
    create words.
     */
    private DictNode findMatchingLetter(DictNode[] children, ArrayList<Character> currFrame, int numChildren)
    {
        DictNode child;

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
        return new DictNode();
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

class PotentialTile {

    private char potentialTile = '*';

    private boolean hasRestriction = false;
    Restriction restriction;

    private boolean hasLetter = false;
    private char letter = potentialTile;

    public PotentialTile() {}

    public PotentialTile(Restriction r)
    {
        hasRestriction = true;
        this.restriction = r;
    }

    public PotentialTile(char letter)
    {
        hasLetter = true;
        this.letter = letter;
    }

    public char getLetter()
    {
        if(!hasLetter())
            throw new RuntimeException("No Letter");

        return letter;
    }

    public boolean hasLetter()
    {
        return hasLetter;
    }

    public void setLetter(char letter)
    {
        hasLetter = true;
        this.letter = letter;
    }

    public void setRestriction(Restriction r)
    {
        this.hasRestriction = true;
        this.restriction = r;
    }

    public Restriction getRestriction()
    {
        if(hasRestriction)
            return this.restriction;
        else
            return null;
    }

    public boolean hasRestriction()
    {
        return this.hasRestriction;
    }

    public char toChar()
    {
        if(hasLetter)
            return letter;
        else
            return '*';

    }
}

class Restriction {

    private String word;

    public Restriction(String word)
    {
        /*
        word => "TEA*"
        Therefore, any letter placed on a particular
        tile with this restriction, has to also be a
        word with that would make sense with "TEA*",
        the asterisk being the letter we replace.

        So for example if we place the word "FUMBLE",
        and the third letter has this restriction,
        this would be valid because

        F U (M) B L E => T E A (M)
         */

        this.word = word;

        char[] letters = word.toCharArray();

        //Error handling: Invalid Format
        if(!word.contains("*"))
            throw new IllegalArgumentException("Invalid Format");
    }

    public boolean check(char letter, DictionaryTree trie)
    {
        String testWord = word;

        //Replace
        testWord = testWord.replace('*', letter);

        //Check dictionary
        return trie.check(testWord);
    }
}

class DictNode
{
    //Element Of Node
    private char      data;

    //Array Of Children
    private DictNode[]    children;

    //Number Of Children
    private int       numChildren;

    //Is This Node The End Of A Word
    private boolean   isEndOfWord = false;

    //Max Children One Can Have
    private final int MAX = 26;

    private boolean isNull = false;

    private int childID;

    public DictNode(char letter, char[] childArr)
    {
        //Char Array Contains Too Many Letters
        if(children.length > 26)
            throw new IllegalArgumentException("This isn't 1932. Too Many Children");

        //Create A Default Child Array
        children = new DictNode[MAX];

        //Set Node's Element
        this.data = letter;

        //Create Children
        for(int i = 0; i < numChildren; i++) {
            createChild(childArr[i]);
        }

    }

    public DictNode(char letter)
    {
        //Create A Default Child Array (EMPTY)
        children = new DictNode[MAX];

        //Set Node Letter
        this.data = letter;
    }

    public DictNode()
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
    public DictNode[]  getChildren()    { return this.children;    }
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
    public DictNode getChild(char c)
    {
        DictNode child;

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

        DictNode child = new DictNode(c);

        //Add To Children & Increment Num Children
        children[numChildren++] = child;
    }

}

class DictionaryTree {

    private DictNode ROOT;
    private String[] words;
    private int      numWords;

    String dictionaryFile = "csw.txt";

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
            ROOT = new DictNode('*');

            //Store The Words From Dictionary File
            for(String word : words)
                storeWord(word, ROOT, 0);

        }
        catch(IOException ex)
        {
            System.out.println("Unable To Read In Dictionary.");
        }
    }

    public DictNode getRoot() { return ROOT; }

    /*
      Stores a word in the tree
      Node n -> The current node in the tree we're at
      int  i -> The current letter index in the word
    */
    private void storeWord(String word, DictNode n, int i)
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

    public boolean searchTree(String word, DictNode n, int indx)
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
    private void print(DictNode n)
    {
        DictNode[] children = n.getChildren();

        for(int i = 0; i < n.getNumChildren(); i++)
        {
            DictNode child = children[i];
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

class TransposeBoard implements BoardAPI
{

    BoardAPI original;

    public TransposeBoard(BoardAPI b)
    {
        this.original = b;
    }

    @Override
    public boolean isLegalPlay(Frame frame, Word word) {
        return original.isLegalPlay(frame, word);
    }

    //TRANSPOSES THE BOARD
    @Override
    public Square getSquareCopy(int row, int col) {
        return original.getSquareCopy(col, row);
    }

    @Override
    public boolean isFirstPlay() {
        return original.isFirstPlay();
    }
}

