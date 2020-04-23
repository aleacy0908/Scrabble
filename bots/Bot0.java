package bots;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

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

    Bot0(PlayerAPI me, OpponentAPI opponent, BoardAPI board, UserInterfaceAPI ui, DictionaryAPI dictionary) {
        this.me = me;
        this.opponent = opponent;
        this.board = board;
        this.info = ui;
        this.dictionary = dictionary;
        turnCount = 0;
    }

    public String getCommand() {
        // Add your code here to input your commands
        // Your code must give the command NAME <botname> at the start of the game
        String command = "";
        switch (turnCount) {
            case 0:
                command = "NAME pintsMen";
                break;
            case 1:
                command = "PASS";
                break;
            case 2:
                command = "HELP";
                break;
            case 3:
                command = "SCORE";
                break;
            case 4:
                command = "POOL";
                break;
            default:

                BoardSearch bs = new BoardSearch(board,me);
                try {
                    bs.generateWord();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                if (bs.noWordFound) command = "PASS";
                else{
                    command = bs.columnAsLetter + String.valueOf(bs.placeOnRow);
                    System.out.println("command: " + command + "\n");
                    command += bs.isHorizontal ? "A " : "D ";
                    System.out.println("command: " + command + "\n");
                    command += bs.word;
                }
                System.out.println("command: " + command + " noWordFound: "+ bs.noWordFound);

                break;
        }
        turnCount++;
        return command;
    }

}

class BoardSearch
{

    /*
    A inner class that will hold the coordinates of each * char in placementTable.
    Each instance will also contain the corresponding coordinates of the * char
    in the actual game board
     */
    private static class Coordinates{
        int row;
        int column;
        int rowGameBoard;
        int colGameBoard;

        public Coordinates(int r, int c, int rgb, int cgb){
            this.row = r;
            this.column = c;
            this.rowGameBoard = rgb;
            this.colGameBoard = cgb;
        }
    }

    private final int MAX_COORDS = 5;

    //Size Of The Board
    private int SIZE = 15;

    private int TABLE_ROWS = 10;
    private int TABLE_COLS = 10;

    //Array of coords allowing a valid word placement
    Coordinates[] positions;

    private BoardAPI board;
    private PlayerAPI me;
    private Dictionary dictionary;

    ArrayList<char[][]> list = new ArrayList<>();

    public String word = "";
    public boolean isHorizontal = false;
    public int placeOnRow;
    public char columnAsLetter;
    public boolean noWordFound = false;

    public BoardSearch(BoardAPI b, PlayerAPI m)
    {
        this.board = b;
        this.me = m;

        for(int i = 0; i < SIZE; i++)
        {
            for(int j = 0; j < SIZE; j++)
            {
                if(isValidLocation_ACROSS_FIRSTLETTER(i, j))
                {
                    //Create Table For Location i,j
                    char[][] placementTable = createPlacementTable(i, j);

                    //Add Table To List
                    list.add(placementTable);
                }
            }
        }

        System.out.println("Size: " + list.size());
    }

    public boolean isValidLocation_ACROSS_FIRSTLETTER(int r, int c)
    {
        //Get Square
        Square sqr = board.getSquareCopy(r, c);

        //Condition To Implement
        //Empty square adjacent to an occupied square

        Tile tl = sqr.getTile();

        //Empty squares to the right of A
        int emptySquares = numberOfEmptyTilesToTheRightOf(r,c);

        if(sqr.isOccupied()) {
            System.out.println("--");
            System.out.println("ltr: " + sqr.getTile().getLetter());
            System.out.println(sqr.isOccupied());
            System.out.println(isEmptyTileLeftOf(r, c));
            System.out.println(emptySquares >= 1);
            System.out.println("--\n");
        }

        /*
        Checks:
        -> Tile Is Not Blank (Starting Letter)
        -> Empty Tile Left Of Our Starting Tile
        -> There Are Empty Tiles To Be Filled */

        return sqr.isOccupied() && isEmptyTileLeftOf(r, c) && emptySquares > 2;

    }

    private char[][] createPlacementTable(int r, int c) {

        //Our Table
        char[][] placementTable = new char[TABLE_ROWS][TABLE_COLS];

        for(int i = 0; i < TABLE_ROWS; i++)
        {
            for(int j = 0; j < TABLE_COLS; j++)
            {
                placementTable[i][j] = 'X';
            }
        }

        //Tiles Left On The Board AFTER
        //Our First Tile (First Letter Of The Word)
        int tilesLeft = this.SIZE - r;

        //Are We In The Middle Of Creating A Word?
        //If So, We Might Need To Place A Tile There
        boolean creatingWord = false;

        //A Letter Might Need / Has Potential To Be Placed Here
        char potential = '*';

        //This Tile Isn't Relevant To Our Move
        char irrelevant = '-';

        //Placeholder Tile Variable
        char tl;

        //Location Of Where We
        //Are In The Table
        int tableRow = 0;
        int tableCol = 0;

        Square sqr;

        int divider = (TABLE_ROWS / 2) - 1;

        int validPositionCount = 0;

        for (int row = r - divider; row < r + divider; row++) {
            for (int col = c; col < SIZE; col++) {

                //If We've Gone Off The Board
                if(row < 1 || row >= 15 ||
                        tableRow >= TABLE_ROWS || tableCol >= TABLE_COLS)
                    continue;

                //Get Next Square
                sqr = board.getSquareCopy(row, col);

                if (row == r) {
                    tl = sqr.isOccupied() ? sqr.getTile().getLetter() : potential;
                } else {

                    //If there's a letter here and it's connected
                    //to our potential start row
                    tl = isConnected(row, col, r) && sqr.isOccupied() ?
                            sqr.getTile().getLetter() : irrelevant;
                }

                //When '*' is placed on the table, a record of its coords on the table is kept and
                //its corresponding place on the game board
                if (tl == potential) {
                    positions[validPositionCount] = new Coordinates(tableRow,tableCol,row,col);
                    validPositionCount++;
                    System.out.println("FOUND A FREE SQUARE");
                }

                //Set Character
                placementTable[tableRow][tableCol++] = tl;
            }

            tableRow++;
            tableCol = 0;
        }

        return placementTable;
    }

    private boolean isConnected(int r, int c, int connectionRow)
    {
        Square sqr = board.getSquareCopy(r, c);

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
            Square tile = board.getSquareCopy(r, c - 1);

            return !tile.isOccupied();
        }
    }

    //Check If Empty Tile Above
    private boolean isEmptyTileAbove(int r, int c)
    {
        return !(board.getSquareCopy(r - 1, c).isOccupied());
    }

    private int numberOfEmptyTilesToTheRightOf(int r, int c)
    {
        Square sqr;

        //Max Number Of Tiles To The Right Of This One
        int leftOnBoard = this.SIZE - c;

        //Count How Many Are Empty
        int counter = 0;

        for(int i = 0; i < leftOnBoard; i++)
        {
            //Move To Next Square
            sqr = board.getSquareCopy(r, c++);

            if(sqr.isOccupied())
                break;
            else
                counter++;
        }

        System.out.println("Left: " + counter);

        return counter;

    }

    private int numOfEmptyTilesBelow(int r, int c){
        Square sqr;

        //Max Number Of Tiles Below The Given Square
        int belowOnBoard = this.SIZE - r;

        //Count How Many Are Empty
        int counter = 0;

        for(int i = 0; i < belowOnBoard; i++)
        {
            //Move To Next Square
            sqr = board.getSquareCopy(r++, c);

            if(sqr.isOccupied())
                break;
            else
                counter++;
        }

        System.out.println("Below: " + counter);

        return counter;
    }



    public void generateWord() throws FileNotFoundException {

        //Import dictionary file
        String inputFileName = "src/csw.txt";
        File inputFile = new File(inputFileName);
        Scanner in = new Scanner(inputFile);

        String word = "";
        int positionRow;
        int positionCol;
        int spaceAcross = 0;
        int spaceDownwards = 0;
        boolean wordFound = false;

        System.out.println(positions.length+"dfvadfadfadafv");

        for (int i = 0; i < positions.length; i++){

            //Iterate through each coord where a '*' is present in the placementTable
            positionRow = positions[i].row;
            positionCol = positions[i].column;

            //The max length a word can be placed depending the direction its placed
            spaceAcross = numberOfEmptyTilesToTheRightOf(positionRow,positionCol);
            spaceDownwards = numOfEmptyTilesBelow(positionRow,positionCol);

            while(in.hasNextLine()){

                word = in.toString();

                if(word.length() <= spaceAcross){
                    isHorizontal = true;
                    wordFound = true;
                    placeOnRow = positions[i].rowGameBoard;
                    columnAsLetter = (char) (positions[i].colGameBoard + 65);
                    break;
                }
                else if(word.length() <= spaceDownwards){
                    isHorizontal = false;
                    wordFound = true;
                    placeOnRow = positions[i].rowGameBoard;
                    columnAsLetter = (char) (positions[i].colGameBoard + 65);
                    break;
                }
                else in.nextLine();
            }

            if (wordFound) break;
        }

        if (!wordFound) noWordFound = true;

    }

    public static void main(String[] args) throws FileNotFoundException {
//        Scrabble sc = new Scrabble();
//        sc.
//        sc.getCurrentPlayer().getFrameAsString();
//
//        Word w = new Word(5, 8, false, "MOTH");
//
//        sc.getBoard().place(new Frame(), w);
//
//        Word x = new Word(8,8, true, "H");
//        sc.getBoard().place(new Frame(), x);
//
//        Word y = new Word(5,12, false, "TEA");
//
//        sc.getBoard().place(new Frame(), y);
//
//        Word z = new Word(6, 7, true, "BOGGLE");
//
//        sc.getBoard().place(new Frame(), z);
//
//        Word a = new Word(1,1, false, "UNCUT");
//        Word b = new Word(3, 3, true, "GEMS");
//
//        sc.getBoard().place(new Frame(), a);
//        sc.getBoard().place(new Frame(), b);
//
//        for(int i = 0; i < 15; i++)
//        {
//            for(int j = 0; j < 15; j++)
//            {
//                //System.out.println(i + "," + j);
//
//                Square s = sc.getBoard().getSquareCopy(i,j);
//                if(s.isOccupied())
//                    System.out.print(" " + s.getTile().getLetter() + " ");
//                else
//                    System.out.print(" - ");
//            }
//
//            System.out.println();
//        }
//
//        BoardSearch bs = new BoardSearch(sc.getBoard());
//
//        char[][] table = bs.createPlacementTable(7,7);
//
//
//        for(int i = 0; i < table.length; i++)
//        {
//            for(int j = 0; j < table[0].length; j++)
//            {
//                System.out.print(table[i][j] + "  ");
//            }
//            System.out.println();
//        }
    }
}
