import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/* Score Multipliers:
    NONE  = None
    DL = Double Letter
    TL = Triple Letter
    DW = Double Word
    TW = Triple Word */

enum SCORE_MULT {NONE, DL, TL, DW, TW};

//Coordinate Class
class Coord
{
    private short x;
    private short y;

    Coord(short x, short y)
    {
        this.x = x;
        this.y = y;
    }

    short getX() { return this.x; }
    short getY() { return this.y; }

    void setX(short x) { this.x = x; }
    void setY(short y) { this.y = y; }
}

public class Board {

    private class Square
    {
        SCORE_MULT score_multiplier;

        //Does A Letter Sit On The Square
        boolean    is_occupied = false;
        char       letter;

        Square()
        {
            //Set The Square's Score Multiplier
            this.score_multiplier = SCORE_MULT.NONE;
        }

        Square(SCORE_MULT multiplier)
        {
            //Set The Square's Score Multiplier
            this.score_multiplier = multiplier;
        }

        void setTile(char t)
        {
            this.is_occupied = true;

            this.letter = t;
        }

        char getTile()
        {
            //Error Handling: Calling Func When There's No Tile
            if(!is_occupied)
                throw new RuntimeException("No Tile On Square");

            return this.letter;
        }

        SCORE_MULT getMultiplier() { return this.score_multiplier; }
        void       setMultiplier(SCORE_MULT m) { this.score_multiplier = m; }

    }

    //MAIN BOARD
    final short B_ROWS = 15;
    final short B_COLS = 15;

    private Square[][] BOARD = new Square[B_ROWS][B_COLS];

    Board()
    {
        //First, Fill The Board With Empty Squares
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                BOARD[i][j] = new Square();
            }
        }


        /*--Multipliers--
        The Scrabble board is filled with multipliers that have a pattern as to
        where they are placed. Instead of an array with all 52 multiplier coordinates,
        we have reduced that to an array of 13 using this pattern on the scrabble board.

        The Pattern class reflects a single coordinate across the four sections of the
        board. We still need to initialise a few coordinates for each multiplier type
        as they dictate where our pattern begins.
         */

        int[][][] multiplierCoords = {
                { {1, 4}, {8, 4}, {7, 7}, {3, 7}        },
                { {2, 2}, {3, 3}, {4, 4}, {5, 5}, {8, 8}},
                { {2, 6}, {6, 6}                        },
                { {1, 1}, {8, 1}                        }
        };

        /*
        Our pattern object will turn a single coordinate for
        one quarter section of the board into four for all four
        sections
         */
        Pattern p = new Pattern(B_ROWS);

        SCORE_MULT multID;

        for (int i = 0; i < 4; i++) {
            //Switch which multiplier we want to
            //place on the board

            switch (i) {
                case 1:
                    multID = SCORE_MULT.DW;
                    break;
                case 2:
                    multID = SCORE_MULT.TL;
                    break;
                case 3:
                    multID = SCORE_MULT.TW;
                    break;
                default:
                    multID = SCORE_MULT.DL;
            }

            //Generate A Pattern For Each Multiplier Type
            p.setPatternCoords(multiplierCoords[i]);

            //For Each Coordinate In Our Pattern
            for (int[] coord : p.getPattern()) {
                Square tmp = getSquare(coord[0], coord[1]);

                tmp.setMultiplier(multID);
            }
        }
    }

    private Square getSquare(int x, int y)
    {
        //Error Handle: Invalid Coordinate
        if(x < 1 || x > B_ROWS ||
                y < 1 || y > B_COLS)
        {
            throw new IllegalArgumentException("Invalid Coordinate");
        }

        return BOARD[x-1][y-1];
    }

    public void DisplayBoard ( ) {
        int i, j;

        System.out.println("");
        System.out.println("                                       SCRABBLE BOARD                                         ");


        for (i = B_ROWS; i > 0; i--) {
            System.out.println("");
            System.out.print("  ___________________________________________________________________________________________\n");

            for (j = B_COLS + 1; j > 0; j--) {
                System.out.print("  | " + " " + " ");


            }
        }
        System.out.println("");
        System.out.print("  ___________________________________________________________________________________________\n");


    }
}

class wordPlacement{

    int numOfWordsOnBoard = 0;

    public wordPlacement() {
    }

    void tileSelection(Player player){

        boolean playerFinished = false;
        Scanner playerSelection = new Scanner(System.in);
        Scanner finished = new Scanner(System.in);
        char done;
        String word = "";

        while(!playerFinished){
            System.out.println("Select the tiles you wish to create a word with:");
            player.getFrameP().displayFrame();
            word = playerSelection.next().toUpperCase();

            if(!necessaryLetters(word,player)){
                System.out.println("Invalid word picked");
                continue;
            }

            System.out.println("word chosen : " + word);
            System.out.println("Is this the word you would like to place on the board? Y/N");
            done = finished.next().toUpperCase().charAt(0);

            if(done == 'Y'){
                ArrayList<Character> tiles = new ArrayList<Character>();

                for(int i = 0; i<word.length(); i++){
                    tiles.add(word.charAt(i));
                }
                player.getFrameP().removeFromFrame(tiles);

                numOfWordsOnBoard++;
                System.out.println("word placed : " + word);
                player.getFrameP().displayFrame();
                playerFinished = true;
            }
        }

    }



    boolean necessaryLetters(String word, Player player){

        boolean hasLetters = false;

        for(int i = 0; i <= word.length()-1; i++){
            if(player.getFrameP().checkLettersInFrame(word.charAt(i))){
                hasLetters = true;
            }else
            {
                hasLetters = false;
            }
        }


        return hasLetters;
    }

}
