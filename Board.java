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

class Square
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
    
        void clearTile()
        {
            if(this.isOccupied())
             {
                 this.letter = ' ';
                 this.is_occupied = false;
             }
        }

        SCORE_MULT getMultiplier() { return this.score_multiplier; }
        void       setMultiplier(SCORE_MULT m) { this.score_multiplier = m; }

        boolean isOccupied() { return this.is_occupied; }

    }

public class Board {

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
        where they are placed. Instead of an array with all 62 multiplier coordinates,
        we have reduced that to an array of 15 using this pattern on the scrabble board.

        The Pattern class reflects a single coordinate across the four sections of the
        board. We still need to initialise a few coordinates for each multiplier type
        as they dictate where our pattern begins.
         */

        int[][][] multiplierCoords = {
                {{1, 4}, {8, 4}, {7,3}, {7, 7}, {3, 7}},
                {{2, 2}, {3, 3}, {4, 4}, {5, 5}, {8, 8}},
                {{2, 6}, {6, 6}, {10,2}},
                {{1, 1}, {8, 1}}
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

    void setSquare(int x, int y, char t){

        //Error Handle: Invalid Coordinate
        if(x < 1 || x > B_ROWS ||
                y < 1 || y > B_COLS)
        {
            throw new IllegalArgumentException("Invalid Coordinate");
        }

        BOARD[x-1][y-1].setTile(t);
    }


    public void DisplayBoard() {
        int i, j;

        System.out.println("");
        System.out.println("                                                SCRABBLE BOARD                " +
                "                         ");


        for (i = 1; i <= B_ROWS; i++) {
            System.out.println("");
            System.out.print("  ________________________________________________________" +
                    "__________________________________________________\n");

            for (j = 1; j <= B_COLS; j++) {

                Square sqr = getSquare(i,j);

                if(sqr.isOccupied())
                {
                    char tile = sqr.getTile();

                    System.out.print("  |  " + tile + " ");
                }
                else
                {
                    String output;

                    switch(sqr.getMultiplier())
                    {
                        case DL:
                            output = "DL";
                            break;
                        case DW:
                            output = "DW";
                            break;
                        case TL:
                            output = "TL";
                            break;
                        case TW:
                            output = "TW";
                            break;
                        default:
                            output = "  ";
                    }

                    System.out.print("  | " + output + " ");
                }

            }
            System.out.print("  |");
        }

        System.out.println("");
        System.out.print("  ______________________________________________________________________" +
                "____________________________________\n");


    }

     void ResetBoard()
    {
        BOARD = new Square[B_ROWS][B_COLS];
        DisplayBoard();
    }

    //This allows the game to keep track of the amount of words currently on the board
    int numOfWordsOnBoard = 0;

    /*
    This method is where the player is given
    the ability to place a word onto the
    scrabble board.
     */
    void tileSelection(Player player){

        /*
        A backup is made of the players frame before selecting
        a frame so it can be reverted if they fail any of the checks.
        Needed for when a player uses a "_" tile as that tile is
        replaced so it can work with the checks
         */
        ArrayList<Character> backup = new ArrayList<>(player.getFrameP().getFrame());

        boolean playerFinished = false;
        Scanner playerSelection = new Scanner(System.in);
        Scanner square = new Scanner(System.in);
        Scanner dir = new Scanner(System.in);
        Scanner letter = new Scanner(System.in);
        int x,y;
        char direction;
        String word;

        //Loop doesn't break until the player has selected and placed a word on the board
        while(!playerFinished)
        {
            System.out.println("Select the tiles you wish to create a word with:");
            player.getFrameP().displayFrame();
            word = playerSelection.next().toUpperCase();

            if(word.length() < 2){
                System.out.println("Word must have 2 letters or more");
                continue;
            }

            //Allows the player to add a letter of their choice
            if(word.contains("_")){
                for(int i=0; i < word.length(); i++)
                {
                    if(word.charAt(i) == '_'){
                        System.out.println("Please select a letter you would like to enter for '_':");
                        String tmp = letter.next().toUpperCase();
                        word = word.replaceFirst("_",tmp);
                        player.getFrameP().frame.remove(Character.valueOf('_'));
                        player.getFrameP().frame.add(tmp.charAt(0));
                    }
                }
            }

            System.out.println("Where would you like to place the word on the board? (ROW COLUMN)");
            x = square.nextInt();
            y = square.nextInt();

            //The player must enter chose A or D for whether they want
            //their word to go across or downwards on the board
            System.out.println("Which direction do you want the word to go? Across(A)/Downwards(D)");
            direction = dir.next().toUpperCase().charAt(0);

            //If they don't enter A or D, the player must try again
            if((direction != 'D') && (direction != 'A')){
                System.out.println("Invalid direction entered");
                player.getFrameP().frame.clear();
                player.getFrameP().frame.addAll(backup);
                continue;
            }

            /*
            This calls checks to ensure the player is
            using at least one letter from their frame
            rather than only using words from the board.
             */
            if(!necessaryLetters(word,player,x,y,direction))
            {
                System.out.println("Invalid word picked");
                player.getFrameP().frame.clear();
                player.getFrameP().frame.addAll(backup);
                continue;
            }

            //This makes sure the first word is placed at the centre of the board
            //If not the first word then it is checked to see if it connects to another
            //word on the board
            if(numOfWordsOnBoard == 0) {
                if (!firstWord(x,y,word,direction)) {
                    System.out.println("First played word must start at the centre of the board");
                    player.getFrameP().frame.clear();
                    player.getFrameP().frame.addAll(backup);
                    continue;
                }
            }else{
                if(!connectsToWord(x,y,word,direction)){
                    System.out.println("New word must be connected to another word");
                    player.getFrameP().frame.clear();
                    player.getFrameP().frame.addAll(backup);
                    continue;
                }
            }

            //Checks if the given word goes outside of the boards bounds
            //or if a invalid grid ref was given
            if(!withinBoard(x,y,direction,word))
            {
                System.out.println("Out of board bounds");
                player.getFrameP().frame.clear();
                player.getFrameP().frame.addAll(backup);
                continue;
            }


            //Checks if the given word conflicts with any other words on the board
            if(conflicts(x,y,word,direction)){
                System.out.println("New word on given grid ref conflicts with another word on the board");
                player.getFrameP().frame.clear();
                player.getFrameP().frame.addAll(backup);
                continue;
            }



            System.out.println("Word placed : " + word);

            /*
            Once the player has chosen their word and it has passed all the necessary checks,
            the word is converted from a string to an ArrayList and sent to the removeFromFrame
            method to get rid of the used tiles. The tiles that were used are then placed onto
            the board and the number of words on the board is incremented
             */
            ArrayList<Character> tiles = new ArrayList<>();

            for(int i = 0; i<word.length(); i++)
            {
                tiles.add(word.charAt(i));
            }
            fillSquare(word, x, y, direction, tiles);
            player.getFrameP().removeFromFrame(tiles);

            numOfWordsOnBoard++;
            playerFinished = true;

        }
    }

    /*
    If the player chooses to have a word be placed across on the board then
    the column value is incremented each time a letter is placed on the board.
    If the player chooses to have the word be place downwards on the board
    then the row value is incremented each time a letter is placed. If a letter
    is already present on the board, it is removed from the list of letters
    that are going to be removed from a players frame.
     */
    private void fillSquare(String word, int row, int column, char direction, ArrayList<Character> tiles){

        Square sqr;

        switch(direction){
            case 'D':
                for(int i = 0; i < word.length(); i++)
                {
                    sqr = getSquare(row,column);
                    if(sqr.is_occupied){
                        if(sqr.getTile() == word.charAt(i)){
                            tiles.remove(Character.valueOf(word.charAt(i)));
                        }
                    }
                    setSquare(row,column,word.charAt(i));
                    row++;

                }
                break;

            case 'A':
                for(int i = 0; i < word.length(); i++)
                {
                    sqr = getSquare(row,column);
                    if(sqr.is_occupied){
                        if(sqr.getTile() == word.charAt(i)){
                            tiles.remove(Character.valueOf(word.charAt(i)));
                        }
                    }
                    setSquare(row,column,word.charAt(i));
                    column++;

                }
                break;
        }
    }

    /*
    This method performs two checks. First whether the given grid ref
    is within the bounds of the scrabble board. If it passes that first
    check then the length of the word is added to the row/column value
    depending on the direction the word is going to be placed.
    If the total from adding the row/column number and the length of
    the word exceed the bounds of the board, the check fails.
     */
    private boolean withinBoard(int x, int y, char direction, String word){

        boolean withinBounds = true;
        int length;

        if( (x > 15 || x < 1) || (y > 15 || y < 1) )
        {
            withinBounds = false;
        }

        switch(direction){
            case 'D':
                length = (x + word.length()) - 1;
                if(length > 15){
                    withinBounds = false;
                }
                break;

            case 'A':
                length = (y + word.length()) - 1;
                if(length > 15){
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
    private boolean usesOnlyFromBoard(String word, int row, int column, char direction){
        boolean onlyUsingLettersOnBoard = false;
        Square sqr;
        int count = 0;

        switch(direction){
            case 'D':
                for(int i = 0; i < word.length(); i++){
                    sqr = getSquare(row++,column);
                    if(sqr.is_occupied){
                        if(sqr.getTile() == word.charAt(i)){
                            count++;
                        }
                    }
                }
                break;

            case 'A':
                for(int i = 0; i < word.length(); i++){
                    sqr = getSquare(row,column++);
                    if(sqr.is_occupied){
                        if(sqr.getTile() == word.charAt(i)){
                            count++;
                        }
                    }
                }
                break;
        }

        if(count == word.length()){
            onlyUsingLettersOnBoard = true;
        }

        return onlyUsingLettersOnBoard;
    }

    /*
    This method is used to make sure a player is using at least one letter from
    their frame. If a letter isn't in a players frame, its checked to see
    if its on the board already. If the letter isn't found on the board
    or in the players frame then the check fails.
     */
    private boolean necessaryLetters(String word, Player player, int row, int column, char direction){

        boolean usesLettersFromFrame = true;

        for(int i = 0; i <= word.length()-1; i++)
        {
            usesLettersFromFrame = player.getFrameP().checkLettersInFrame(word.charAt(i));
        }

        if(usesOnlyFromBoard(word,row,column,direction)){
            usesLettersFromFrame = false;
        }

        return usesLettersFromFrame;
    }

    /*
    This method is checking if a new word is going to connect
    to another word if its not the first word going to be placed
    onto the board.
     */
    private boolean connectsToWord(int x,int y, String word, char direction){
        Square sqr;
        boolean connectsToWord = false;

        switch(direction){
            case 'D':
                for(int i = 0; i < word.length(); i++){

                    sqr = getSquare(x++,y);
                    if(sqr.is_occupied){
                        connectsToWord = true;
                    }
                }
                break;

            case 'A':
                for(int i = 0; i < word.length(); i++){

                    sqr = getSquare(x,y++);
                    if(sqr.is_occupied){
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
    private boolean conflicts(int x,int y, String word, char direction){
        boolean result = false;
        Square sqr;

        switch(direction){
            case 'D':

                for(int i = 0; i < word.length(); i++){

                    sqr = getSquare(x++,y);
                    if(sqr.is_occupied){
                        if( (word.charAt(i) == sqr.getTile()) ){
                            result = false;
                        }else{
                            result = true;
                        }
                    }
                }
                break;

            case 'A':

                for(int i = 0; i < word.length(); i++) {

                    sqr = getSquare(x, y++);
                    if (sqr.is_occupied) {
                        if ((word.charAt(i) == sqr.getTile())) {
                            result = false;
                        } else {
                            result = true;
                        }
                    }
                }
                break;
        }

        return result;
    }

    /*
    This checks if the new word is the first one to be placed onto
    the board. If this word doesn't lie on the centre at any point,
    the check fails
     */
    private boolean firstWord(int row, int column, String word, char direction){

        boolean isOnCentre = false;

        switch(direction){
            case 'D':

                for(int i = 0; i < word.length(); i++){
                    if ((i + row) == 8) {
                        isOnCentre = true;
                        break;
                    }
                }
                break;

            case 'A':

                for(int i = 0; i < word.length(); i++){
                    if ((i + column) == 8) {
                        isOnCentre = true;
                        break;
                    }
                }
                break;
        }

        return isOnCentre;
    }
}
