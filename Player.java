import java.io.*;
import java.util.*;


public class Player {

    int score;

    private String Player;

    private int P_score;

    private Frame frame;

    //Constructor With Frame
    Player (String P,int Ps, Frame f) {
        this.Player = P;
        this.P_score = Ps;
        this.frame = f;
    }

    //Constructor Without Frame
    Player (String P,int Ps) {
        this.Player = P;
        this.P_score = Ps;
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


    //Allows access to a players frame
    public Frame getFrameP ( ) {
        // return frame of player one
        return frame;
    }

    public void setFrame(Frame f)
    {
        this.frame = f;
    }

    // allows display of a players name

    public String nameP()
    {
        // return name of player one
        return Player;
    }



}


