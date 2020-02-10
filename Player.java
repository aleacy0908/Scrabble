import java.io.*;
import java.util.*;


public class Player {

    int score;

    private String Player;


    private int P_score;

    private Frame frame;


    Player (String P,int Ps) {

        this.Player = P;
        this.P_score = Ps;
        this.frame = new Frame();


    }


    // allows player data to be reset

    public void reset()
    {
        // sets player score back to zero
        P_score = 0;


        // erases player name
        Player = "";


    }



    // allows name of a player to be set

    public void setPlayer (String player)

    {
       this.Player = player;
    }


    // allows a player score to be increased

    public void increaseScore(int score)
    {
        P_score += score;
    }



    // allows access to their score

    public int getP_score ( ) {

        // return score of player one
        return P_score;
    }


    // allows access to a players frame

    public Frame getFrameP ( ) {

        // return frame of player one
        return frame;
    }





    // allows display of a players name

    public String nameP()

    {
        // return name of player one
        return Player;
    }



}


