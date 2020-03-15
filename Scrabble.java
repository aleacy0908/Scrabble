import java.util.Scanner;

public class Scrabble {

    // Allow two humans to play scrabble
    // Game should include scoring
    // Challenges & Dictionary Checks dealt with manually
    // i.e manually subtract the score of the successfully
    // challenged word at the end

    public static void main(String[] args)
    {
        //--SETUP--

        //Read User Input
        Scanner SCAN = new Scanner(System.in);

        //Player Names
        String playerNameA, playerNameB;

        playerNameA = readIn("Player A Name: ", SCAN);
        playerNameB = readIn("Player B Name: ", SCAN);

        //Create Two Players
        Player[] players = new Player[] {
                new Player(playerNameA, 0, new Frame(new Pool())),
                new Player(playerNameB, 0, new Frame(new Pool()))
        };

        //Create The Board
        Board BOARD = new Board();

        //Play the game
        String playerInput;

        boolean GAME_FINISHED = false;
        boolean EXIT_GAME     = false;

        //Todo: While(!GAME_EXIT)

        //Increments Each Turn
        int TURN = 0;

        //0 or 1 => Represents which player's
        //move it is
        int P = 0;

        /*
        These variables hold the separate
        values for the coordinates, word
        and direction the user entered */

        int[]  COORD = new int[2];
        String WORD;
        char   DIR;

        //Current # Of Words
        int wordsOnBoard = 0;

        /*
        New Move

        True: If game can move onto
        the next move

        False: If same user must enter
        a word again */

        boolean newMove = true;

        //Current Name Of Who's
        //Turn It Is
        String pName;


        while(!GAME_FINISHED)
        {
            //Which Player's Turn Is It?
            pName = (P == 0)? players[0].nameP() : players[1].nameP();

            //Show Player The Board
            if(newMove)
            {
                //Print This Player's Name
                System.out.println("PLAYER " + pName);

                BOARD.DisplayBoard();
                System.out.print("\n\n\n");

                //Display the frame to player
                System.out.println("FRAME");
                players[P].getFrameP().displayFrame();
                System.out.print("\n\n\n");
            }

            //Player chooses words
            System.out.println(pName + ", Please Enter A Word.");
            playerInput = readIn("Use Format: <Coord> <Dir> <Word>: ", SCAN);

            //Parse Input
            WORD  = getWord(playerInput);
            COORD = getCoord(playerInput);
            DIR   = getDirection(playerInput);

            //Place Tiles On Board
            newMove = BOARD.tileSelection(players[P], COORD[0], COORD[1], DIR, WORD, wordsOnBoard);

            //Check if the user needs to enter a word again
            if(newMove)
            {
                //Next Turn
                TURN++;

                //Switch Player (0 or 1)
                P = TURN % 2;
            }

        }
    }

    public static String readIn(String txt, Scanner inp)
    {
        //Store Input
        String inputString;

        //Print Instructions
        System.out.print(txt);

        //Read String Data
        inputString = inp.nextLine();

        //Newline
        System.out.println();

        return inputString;
    }

    public static String getWord(String s)
    {
        int[] spaces = getSpaces(s);

        return s.substring(spaces[2]+1);
    }

    public static char getDirection(String s)
    {
        int[] spaces = getSpaces(s);

        return s.charAt(spaces[1] + 1);
    }

    public static int[] getCoord(String s)
    {
        int[] spaces = getSpaces(s);

        String x = s.substring(0, spaces[0]);
        String y = s.substring(spaces[0] + 1, spaces[1]);

        return new int[] {
                Integer.parseInt(x), Integer.parseInt(y) };
    }

    public static int[] getSpaces(String s)
    {
        //Index Of Space Characters
        int spc  = s.indexOf(' ');
        int spc2 = s.indexOf(' ', spc + 1);
        int spc3 = s.indexOf(' ', spc2+1);

        //Error Handling
        if(spc == -1 || spc2 == -1 || spc3 == -1)
            throw new IllegalArgumentException("Invalid Input");

        return new int[]{spc, spc2, spc3};
    }
}
