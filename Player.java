import java.io.*;
import java.util.*;


public class Player {

    private String Player1,Player2;


    private int P1_score;
    private int P2_score;

    String FrameP1;
    String FrameP2;


    // allows player data to be reset

    public void reset()
    {
        P1_score = 0;
        P2_score = 0;

        Player1 = "";
        Player2 = "";

    }



    // allows name of a player to be set

    public void setPlayer1 (String player1)

    {
        Player1 = player1;
    }


    public void setPlayer2 (String player2)

    {
        Player2 = player2;
    }


    // allows a player score to be increased

    public void increaseScore(int Player)
    {

        int score = 0;

        if (Player == 1)
        {
            score = getP1_score();
            P1_score += score;

        }

        if (Player == 2)
        {
            score = getP2_score();
            P2_score += score;

        }
    }



    // allows access to their score

    public int getP1_score ( ) {

        return P1_score;
    }

    public int getP2_score ( ) {

        return P2_score;
    }



    // allows access to a players frame

    public String getFrameP1 ( ) {

        return FrameP1;
    }

    public String getFrameP2 ( ) {

        return FrameP2;
    }




    // allows display of a players name

    public String nameP1()

    {
        return Player1;
    }

    public String nameP2()

    {
        return Player2;
    }



}


