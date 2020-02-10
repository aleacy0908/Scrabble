import java.io.*;
import java.util.*;


public class Player {

    private String Player1,Player2;


    private int P1_score;
    private int P2_score;
    
    private Frame frame1;
    private Frame frame2;

    Player (String P1,String P2,int P1s, int P2s) {

        this.Player1 = P1;
        this.Player2 = P2;
        this.P1_score = P1s;
        this.P2_score = P2s;
        this.frame1 = new Frame();
        this.frame2 = new Frame();

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
       this.Player1 = player1;
    }


    public void setPlayer2 (String player2)

    {
        this.Player2 = player2;
    }


    // allows a player score to be increased

    public void increaseScore(int Player, int score)
    {

        if (Player == 1)
        {
            P1_score += score;

        }

        if (Player == 2)
        {
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

    public Frame getFrameP1 ( ) {

        return frame1;
    }

    public  Frame getFrameP2 ( ) {

        return frame2;
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


