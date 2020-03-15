import java.io.*;
import java.util.*;


public class Player extends Score {

    private String Player;
    private Frame  frame;

    //Constructor With Frame
    Player (String P, Frame f) {
        this.Player  = P;
        this.frame   = f;
    }

    //Constructor Without Frame
    Player (String P) {
        this.Player  = P;
    }

    //Reset Player
    public void reset()
    {
        //Re-Initialise Score
        this.setScore(0);

        //New Frame
        this.setFrame(new Frame(new Pool()));
    }

    //Modify Name
    public void setPlayer (String player)
    {
       this.Player = player;
    }

    //Access Frame
    public Frame getFrameP ( ) {
        // return frame of player one
        return frame;
    }

    //Modify Frame
    public void setFrame(Frame f)
    {
        this.frame = f;
    }

    //Display Player Name
    public String nameP()
    {
        // return name of player one
        return Player;
    }



}


