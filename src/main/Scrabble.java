package src.main;

import javafx.*;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import src.mechanics.Board;
import src.mechanics.Frame;
import src.mechanics.Pool;
import src.user.Player;
import src.util.UI;

import javafx.*;

import java.util.Scanner;

public class Scrabble {

    private Board    BOARD;
    private Player[] players;
    private int      wordsOnBoard = 0;
    private int      numPlayers   = 0;
    private int      playerTurn   = 0;

    public          Scrabble() {}

    //How Many Words Are On The Board
    public int      getWordsOnBoard() { return this.wordsOnBoard; }

    //Return Player Who's Turn It Is
    public Player   getCurrentPlayer() { return this.getPlayer(playerTurn); }

    //Get/Set For Board
    public void     setBoard(Board b) { this.BOARD = b; }
    public Board    getBoard()        { return this.BOARD; }

    //Retrieve ALL Players
    public Player[] getPlayers() { return this.players; }

    //Set ALL Players
    public void setPlayers(Player[] p)
    {
        this.players = p;
        this.numPlayers = p.length;
    }

    //Move To Next Turn
    public void incrementTurn()
    {
        //Increment turn
        playerTurn++;

        //Make It Turn 0 or 1 for 2 players, etc
        playerTurn %= numPlayers;
    }

    //Get A Single Player By ID
    public Player getPlayer(int indx)
    {

        //Error Handle: Invalid Index
        if(numPlayers - (indx+1) < 0)
            throw new IllegalArgumentException("No Player At Index");

        return this.getPlayers()[indx];

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
