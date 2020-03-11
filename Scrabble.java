import java.util.Scanner;

public class Scrabble {

    // Allow two humans to play scrabble
    // Game should include scoring
    // Challenges & Dictionary Checks dealt with manually
    // i.e manually subtract the score of the successfully
    // challenged word at the end

    public static void main(String[] args)
    {
        //Read User Input
        Scanner scan = new Scanner(System.in);

        //Player Names
        String playerNameA, playerNameB;

        playerNameA = readIn("Player A Name: ", scan);
        playerNameB = readIn("Player B Name: ", scan);


        //Create two players
        Player  playerA = new Player(playerNameA, 0),
                playerB = new Player(playerNameB, 0);

        System.out.println(playerA.nameP());
        System.out.println(playerB.nameP());


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








}
