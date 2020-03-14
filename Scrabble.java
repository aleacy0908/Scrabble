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

        int[] x = getCoord("HELLO 2 4");
        System.out.println(x[0]);
        System.out.println(x[1]);

        //Read User Input
        Scanner SCAN = new Scanner(System.in);

        //Player Names
        String playerNameA, playerNameB;

        playerNameA = readIn("Player A Name: ", SCAN);
        playerNameB = readIn("Player B Name: ", SCAN);

        //Create Two Players
        Player  playerA = new Player(playerNameA, 0),
                playerB = new Player(playerNameB, 0);

        //Create Two Pools
        Pool    poolA = new Pool(),
                poolB = new Pool();

        //Create Two Frames
        Frame[] frames = {
                new Frame(), new Frame()
        };

        //Create The Board

        Board BOARD = new Board();

        //Play the game
        String playerInput;

        boolean GAME_FINISHED = false;
        boolean EXIT_GAME     = false;

        //Todo: While(!GAME_EXIT)

        int P = 0;

        int[] COORD = new int[2];
        String WORD;


        while(!GAME_FINISHED)
        {
            System.out.println("PLAYER " + P);

            //Show Player The Board
            BOARD.DisplayBoard();
            System.out.print("\n\n\n");

            //Display the frame to player
            System.out.println("FRAME");
            frames[P].displayFrame();
            System.out.print("\n\n\n");

            //Player chooses words
            playerInput = readIn("Enter Word & Loc: ", SCAN);

            //Parse Input
            WORD  = getWord(playerInput);
            COORD = getCoord(playerInput);


            //If so, place on the board

            //Display on board
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
        /*Return First Word In String
          HELLO 0 5. HELLO is from index 0 => index of
          the space. */
        return s.substring(0, s.indexOf(' '));
    }

    public static int[] getCoord(String s)
    {
        int space = s.indexOf(' ');

        //Error Handle
        if(space == -1)
            throw new IllegalArgumentException("Invalid Input");

        //Find Two Coords
        char x = s.charAt(s.indexOf(' ') + 1);
        char y = s.charAt(s.indexOf(' ') + 3);

        return new int[] {
                Character.getNumericValue(x), Character.getNumericValue(y) };
    }
}
