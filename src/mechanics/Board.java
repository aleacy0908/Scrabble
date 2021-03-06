package src.mechanics;

import src.UI.AlertBox;
import src.UI.InputPromptBox;
import src.main.Scrabble;
import src.mechanics.Square.*;

import src.user.Player;
import src.util.Pattern;

import java.util.ArrayList;
import java.util.Scanner;

public class Board {

    //MAIN BOARD
    private final short B_ROWS = 15;
    private final short B_COLS = 15;

    private Square[][] BOARD = new Square[B_ROWS][B_COLS];

    public Board() {
        //First, Fill The Board With Empty Squares
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                BOARD[i][j] = new Square();
            }
        }


        /*--Multipliers--
        The Scrabble board is filled with multipliers that have a pattern as to
        where they are placed. Instead of an array with all 62 multiplier coordinates,
        we have reduced that to an array of 15 using this pattern on the scrabble board.

        The Pattern class reflects a single coordinate across the four sections of the
        board. We still need to initialise a few coordinates for each multiplier type
        as they dictate where our pattern begins.
         */

        int[][][] multiplierCoords = {
                {{1, 4}, {8, 4}, {7, 3}, {7, 7}, {3, 7}},
                {{2, 2}, {3, 3}, {4, 4}, {5, 5}, {8, 8}},
                {{2, 6}, {6, 6}, {10, 2}},
                {{1, 1}, {8, 1}}
        };

        /*
        Our pattern object will turn a single coordinate for
        one quarter section of the board into four for all four
        sections
         */
        Pattern p = new Pattern(B_ROWS);

        Square.SCORE_MULT multID;

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
                Square tmp = getSquare(coord[0]-1, coord[1]-1);

                tmp.setMultiplier(multID);
            }
        }
    }

    public Square getSquare(int x, int y) {
        //Error Handle: Invalid Coordinate
        if (x < 0 || x >= B_ROWS ||
                y < 0 || y >= B_COLS) {
            throw new IllegalArgumentException("Invalid Coordinate");
        }

        return BOARD[x][y];
    }

    public void setSquare(int x, int y, String t) {

        //Error Handle: Invalid Coordinate
        if (x < 0 || x >= B_ROWS ||
                y < 0 || y >= B_COLS) {
            throw new IllegalArgumentException("Invalid Coordinate");
        }

        BOARD[x][y].setLetter(t);
    }

    public void clearSquare(int x, int y)
    {
        //Error Handle: Invalid Coordinate
        if (x < 0 || x >= B_ROWS ||
                y < 0 || y >= B_COLS) {
            throw new IllegalArgumentException("Invalid Coordinate");
        }

        BOARD[x][y].clearSquare();
    }

    int i, j;

   public void ResetBoard()
    {
        for (i = 0; i < B_ROWS; i++) {

            for (j = 0; j < B_COLS; j++) {

                Square sqr = getSquare(i, j);

                if (sqr.isOccupied()) {
                    sqr.clearSquare();
                }

            }
        }

    }

    int numOfWordsOnBoard = 0;

    /*
    This method is where the player is given
    the ability to place a word onto the
    scrabble board.
     */
    public boolean tileSelection(Player player, int row, int column, char direction, String word, Scrabble GAME) {

        AlertBox alert = new AlertBox();
        int numOfWordsOnBoard = GAME.getWordsOnBoard();
        boolean playerFinished = false;

        //Loop doesn't break until the player has selected and placed a word on the board
        while (!playerFinished) {

            if(word.equals("CHALLENGE"))
            {
                return true;
            }

            if (word.length() < 2) {
                alert.showBox("Error", "Word must have 2 letters or more");
                return false;
            }

            //If the user didn't enter A or D, the player must try again
            if ((direction != 'D') && (direction != 'A')) {
                System.out.println("Direction: " + direction);
                alert.showBox("Error", "Invalid direction entered");
                return false;
            }

            /*
            This calls checks to ensure the player is
            using at least one letter from their frame
            rather than only using words from the board.
             */
            if (!necessaryLetters(word, player, row, column, direction)) {
                alert.showBox("Error", "Invalid word picked");
                return false;
            }

            //This makes sure the first word is placed at the centre of the board
            //If not the first word then it is checked to see if it connects to another
            //word on the board
            if (numOfWordsOnBoard == 0) {
                if (!firstWord(row, column, word, direction)) {
                    alert.showBox("Error", "First played word must start at the centre of the board");
                    return false;
                }
            } else {
                if (!connectsToWord(row, column, word, direction)) {
                    alert.showBox("Error", "New word must be connected to another word");
                    return false;
                }
            }

            //Checks if the given word goes outside of the boards bounds
            //or if a invalid grid ref was given
            if (!withinBoard(row, column, direction, word)) {
                alert.showBox("Error", "Out of board bounds");
                return false;
            }


            //Checks if the given word conflicts with any other words on the board
            if (conflicts(row, column, word, direction)) {
                alert.showBox("Error", "New word on given grid ref conflicts with another word on the board");
                return false;
            }


            /*
            Once the player has chosen their word and it has passed all the necessary checks,
            the word is converted from a string to an ArrayList and sent to the removeFromFrame
            method to get rid of the used tiles. The tiles that were used are then placed onto
            the board and the number of words on the board is incremented
             */
            ArrayList<Character> tiles = new ArrayList<>();

            for (int i = 0; i < word.length(); i++) {
                tiles.add(word.charAt(i));
            }

            isSquareOccupied(word,row,column,direction,tiles);
            GAME.getGUIBoard().setWord(word, row, column, direction);
            player.getFrameP().removeFromFrame(tiles);

            playerFinished = true;

        }

        return true;
    }

    /*
    If the player chooses to have a word be placed across on the board then
    the column value is incremented each time a letter is placed on the board.
    If the player chooses to have the word be place downwards on the board
    then the row value is incremented each time a letter is placed. If a letter
    is already present on the board, it is removed from the list of letters
    that are going to be removed from a players frame.
     */
    public ArrayList<Character> isSquareOccupied(String word, int row, int column, char direction, ArrayList<Character> tiles){

        Square sqr;

        for (char c : word.toCharArray()) {
            sqr = getSquare(row, column);
            if (sqr.isOccupied()) {
                if (sqr.getLetter().equals(Character.toString(c))) {
                    tiles.remove(Character.valueOf(c));
                }
            }

            if(direction == 'D'){row++;}
            else                {column++;}

        }

        return tiles;
    }

    /*
    This method performs two checks. First whether the given grid ref
    is within the bounds of the scrabble board. If it passes that first
    check then the length of the word is added to the row/column value
    depending on the direction the word is going to be placed.
    If the total from adding the row/column number and the length of
    the word exceed the bounds of the board, the check fails.
     */
    public boolean withinBoard(int x, int y, char direction, String word) {

        boolean withinBounds = true;
        int length;

        if ((x > 14 || x < 0) || (y > 14 || y < 0)) {
            withinBounds = false;
        }

        switch (direction) {
            case 'D':
                length = (x + word.length()) - 1;
                if (length > 14) {
                    withinBounds = false;
                }
                break;

            case 'A':
                length = (y + word.length()) - 1;
                if (length > 14) {
                    withinBounds = false;
                }
                break;
        }

        return withinBounds;
    }

    /*
    This method is used for making sure that if a word is being placed
    where a word already exists, then that new word should be using
    letters from both the players frame and the board. It is
    used to prevent a player from creating a word already
    on the board and placing it in the same place as the original
     */
    private boolean usesOnlyFromBoard(String word, int row, int column, char direction) {
        boolean onlyUsingLettersOnBoard = false;
        Square sqr = getSquare(row,column);
        int count = 0;

        for (char c : word.toCharArray()) {
            if (sqr.isOccupied()) {
                if (sqr.getLetter().equals(Character.toString(c))) {
                    count++;
                }
            }

            if(direction == 'D'){row++;}
            else                {column++;}

            sqr = getSquare(row, column);
        }

        if (count == word.length()) {
            onlyUsingLettersOnBoard = true;
        }

        return onlyUsingLettersOnBoard;
    }

    /*
    Used to test if a single letter is being used
    from the game board
     */
    boolean letterPresentOnBoard(char letter, int x, int y) {
        Square sqr = getSquare(x, y);

        if (sqr.isOccupied()) {
            return sqr.getLetter().equals(Character.toString(letter));
        }

        return false;
    }


    /*
    This method is used to make sure a player is using at least one letter from
    their frame. If a letter isn't in a players frame, its checked to see
    if its on the board already. If the letter isn't found on the board
    or in the players frame then the check fails.
     */
    boolean necessaryLetters(String word, Player player, int row, int column, char direction) {

        boolean usesLettersFromFrame = true;
        boolean onBoardAlready;

        for (int i = 0; i <= word.length()-1; i++) {

            //Check if the letter uses frame letters
            //or is relying on the letter already
            //being on the board

            usesLettersFromFrame = player.getFrameP().checkLettersInFrame(word.charAt(i));
            onBoardAlready = letterPresentOnBoard(word.charAt(i), row, column);

            if (!usesLettersFromFrame && !onBoardAlready) {
                return false;
            }
        }

        if (usesOnlyFromBoard(word, row, column, direction)) {
            usesLettersFromFrame = false;
        }

        return usesLettersFromFrame;
    }

    /*
    This method is checking if a new word is going to connect
    to another word if its not the first word going to be placed
    onto the board.
     */
    public boolean connectsToWord(int x, int y, String word, char direction) {
        Square sqr;
        boolean connectsToWord = false;

        switch (direction) {
            case 'D':
                for (int i = 0; i < word.length(); i++) {

                    sqr = getSquare(x++, y);
                    if (sqr.isOccupied()) {
                        connectsToWord = true;
                    }
                }
                break;

            case 'A':
                for (int i = 0; i < word.length(); i++) {

                    sqr = getSquare(x, y++);
                    if (sqr.isOccupied()) {
                        connectsToWord = true;
                    }
                }
                break;
        }

        return connectsToWord;
    }

    /*
    This checks if the new word to be placed is going to conflict with
    any other words on the board. Depending on the direction of the new
    word, all the squares the new word will occupy are checked to see if
    they are already occupied. If a square is occupied, its checked
    to see if the letter within that square matches the letter that would've
    been placed into that square.
     */
    public boolean conflicts(int x, int y, String word, char direction) {

        Square sqr;

        for(char c : word.toCharArray())
        {
            sqr = getSquare(x, y);
            String ltr = Character.toString(c);

            //Check If Conflict
            if(sqr.isOccupied() && !sqr.getLetter().equals(ltr))
                return true;

            //Move To Next Square
            if     (direction == 'A') y++;
            else if(direction == 'D') x++;
        }

        //No Conflicts
        return false;
    }

    /*
    This checks if the new word is the first one to be placed onto
    the board. If this word doesn't lie on the centre at any point,
    the check fails
     */
    public boolean firstWord(int row, int column, String word, char direction) {

        boolean isOnCentre = false;

        switch (direction) {
            case 'D':

                for (int i = 0; i < word.length(); i++) {
                    if ((i + row) == 7 && column == 7) {

                        isOnCentre = true;
                        break;
                    }
                }
                break;

            case 'A':

                for (int i = 0; i < word.length(); i++) {
                    if ((i + column) == 7 && row == 7) {
                        isOnCentre = true;
                        break;
                    }
                }
                break;
        }

        return isOnCentre;
    }

    public int rows() { return B_ROWS; }
    public int cols() { return B_COLS; }

}
