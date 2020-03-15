import java.util.Scanner;

enum Turn {TURN_A, TURN_B};

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
        int TURN = -1;

        //0 or 1 => Represents which player's
        //move it is
        int P;

        int[]  COORD = new int[2];
        String WORD;
        char   DIR;


        while(!GAME_FINISHED)
        {
            //Next Turn
            TURN++;

            //Switch Player (0 or 1)
            P = TURN % 2;

            System.out.println("PLAYER " + P);

            //Show Player The Board
            BOARD.DisplayBoard();
            System.out.print("\n\n\n");

            //Display the frame to player
            System.out.println("FRAME");
            players[P].getFrameP().displayFrame();
            System.out.print("\n\n\n");

            //Player chooses words
            System.out.println("Next Move!");
            playerInput = readIn("Format: <Coord> <Dir> <Word>: ", SCAN);

            //Parse Input
            WORD  = getWord(playerInput);
            COORD = getCoord(playerInput);
            DIR   = getDirection(playerInput);

            //Place Tiles On Board
            BOARD.tileSelection(players[P], COORD[0], COORD[1], DIR, WORD);

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
