package src.main;
import src.mechanics.GameBoard;

import src.mechanics.DictionaryTree;
import src.user.Player;
import java.util.Scanner;

public class Scrabble {

    private GameBoard BOARD;
    private Player[]  players;
    public static int wordsOnBoard = 0;
    private int       numPlayers   = 0;
    private int       playerTurn   = 0;

    private DictionaryTree dict;

    public Scrabble()
    {
        dict = new DictionaryTree();
    }

    //Get Dictionary
    public DictionaryTree getDict()
    {
        return this.dict;
    }

    //How Many Words Are On The Board
    public int      getWordsOnBoard() { return this.wordsOnBoard; }

    public void setWordsOnBoard(int wordsOnBoard) {
        Scrabble.wordsOnBoard = wordsOnBoard;
    }

    //Return Player Who's Turn It Is
    public Player   getCurrentPlayer() { return this.getPlayer(playerTurn); }

    //Get/Set For Board
    public void         setGUIBoard(GameBoard b) { this.BOARD = b; }
    public GameBoard    getGUIBoard()        { return this.BOARD; }

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

        return s.substring(spaces[1]+1).toUpperCase();
    }

    public static char getDirection(String s)
    {
        int[] spaces = getSpaces(s);

        return s.toUpperCase().charAt(spaces[0] + 1);
    }

    public static int[] getCoord(String s)
    {
        int x = 0;
        int y = 0;
        int[] spaces = getSpaces(s);
        String coords = s.substring(0,spaces[0]).toUpperCase();

        if(coords.length() == 2){
            char yTmp = coords.charAt(0);
            char xTmp = coords.charAt(1);

            x = Character.getNumericValue(xTmp);
            y = yTmp - 64;

        }else if (coords.length() == 3){
            char yTmp = coords.charAt(0);
            String xTmp = Character.toString(coords.charAt(1)) + Character.toString(coords.charAt(2));

            x = Integer.parseInt(xTmp);
            y = yTmp - 64;
        }


        System.out.println(x +" "+ y + " FGDGSDGHDFHDFH");

        return new int[] {
                x, y };
    }

    public static int[] getSpaces(String s)
    {
        //Index Of Space Characters
        int spc  = s.indexOf(' ');
        int spc2 = s.indexOf(' ', spc + 1);
        //int spc3 = s.indexOf(' ', spc2+1);

        //Error Handling
        if(spc == -1 || spc2 == -1)
            throw new IllegalArgumentException("Invalid Input");

        return new int[]{spc, spc2};
    }
}