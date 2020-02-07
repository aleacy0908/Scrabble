import java.io.*;
import java.util.*;


public class Player {

    private String Player1,Player2;


    private int P1_score;
    private int P2_score;

    Frame FrameP1;
    Frame FrameP2;

    Player (String P1,String P2,int P1s, int P2s,  Frame Frame_P1,  Frame Frame_P2 ) {

        this.Player1 = P1;
        this.Player2 = P2;
        this.P1_score = P1s;
        this.P2_score = P2s;
        this.FrameP1 = Frame_P1;
        this.FrameP2 = Frame_P2;

    }


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
        Player1 = "";
        Player1 = player1;
    }


    public void setPlayer2 (String player2)

    {
        Player2 = "";
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

    public  Frame getFrameP1 ( ) {

        return FrameP1;
    }

    public  Frame getFrameP2 ( ) {

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
