package src.main;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.layout.StackPane;
import src.mechanics.Board;
import src.mechanics.Frame;
import src.mechanics.Pool;
import src.mechanics.Square;
import src.user.Player;
import src.util.UI;


import java.util.ArrayList;
import java.util.Scanner;


public class MainActivity{

    static Scrabble GAME;

    public static void main(String[] args) {
        /*SETUP GAME*/
        GAME = new Scrabble();

        GAME.setBoard(new Board());

        //--SETUP--
        Application.launch(UI.class);

        //Read User Input
        Scanner SCAN = new Scanner(System.in);

        //Player Names
        String playerNameA, playerNameB;

        playerNameA = GAME.readIn("Player A Name: ", SCAN);
        playerNameB = GAME.readIn("Player B Name: ", SCAN);

        GAME.setPlayers(new Player[]{
                new Player(playerNameA, new Frame(new Pool())),
                new Player(playerNameB, new Frame(new Pool()))
        });

        //Play the game
        String playerInput;

        boolean GAME_FINISHED = false;
        boolean EXIT_GAME = false;

        //Todo: While(!GAME_EXIT)

        /*
        These variables hold the separate
        values for the coordinates, word
        and direction the user entered */

        int[] COORD = new int[2];
        String WORD;
        char DIR;

        //Did The User Make A Valid Move?
        //Can We Move Onto The Next Move?
        //validMoveMade answers both these q's
        boolean validMoveMade = true;

        String pName;
        Player currPlayer;

        while (!GAME_FINISHED) {
            //Which Player's Turn Is It?
            currPlayer = GAME.getCurrentPlayer();
            pName = currPlayer.nameP();

            //Show Player The Board
            if (validMoveMade) {
                //Print This Player's Name
                System.out.println("PLAYER " + pName);

                GAME.getBoard().DisplayBoard();
                System.out.print("\n\n\n");

                //Display the frame to player
                System.out.println("FRAME");
                currPlayer.getFrameP().displayFrame();
                System.out.print("\n\n\n");
            }

            //Player chooses words
            System.out.println(pName + ", Please Enter A Word.");
            playerInput = GAME.readIn("Use Format: <Coord> <Dir> <Word>: ", SCAN);

            //Parse Input
            WORD = GAME.getWord(playerInput);
            COORD = GAME.getCoord(playerInput);
            DIR = GAME.getDirection(playerInput);

            //True If User Places Valid Word
            validMoveMade = GAME.getBoard().tileSelection(currPlayer, COORD[0], COORD[1],
                    DIR, WORD, GAME.getWordsOnBoard());

            //RETRY MOVE
            if (!validMoveMade)
                continue;

            //Valid Move, Thus Find Out Word Score
            int wordScore = currPlayer.calculateScore(WORD, COORD[0], COORD[1], DIR);

            //Increase Player Score
            currPlayer.increaseScore(wordScore);

            //Print Out Score
            System.out.println(WORD + " Is Worth " + wordScore + " Points!");
            System.out.println(pName + "'s Score Is " + currPlayer.getScore() + "\n\n");

            System.out.println("Next Move! Hit Enter When Ready.");
            GAME.readIn("", SCAN);

            GAME.incrementTurn();
        }

    }

    public static class Tile extends StackPane {
        public Tile(String a, int i, int j) {

            Rectangle border = new Rectangle(25, 25);
            border.setFill(Color.GREEN);
            border.setStroke(Color.WHITE);

            GridPane.setRowIndex(border, i);
            GridPane.setColumnIndex(border, j);


            Text text = new Text(a);
            setAlignment(Pos.CENTER);
            getChildren().addAll(border, text);

        }
    }

    public static Parent createBoard() {
        TilePane gameBoard = new TilePane();

        gameBoard.setPrefSize(700, 700);
        gameBoard.setPrefRows(GAME.getBoard().rows());
        gameBoard.setPrefColumns(GAME.getBoard().cols());

        ArrayList<Tile> tiles = new ArrayList<Tile>();

        GAME.setBoard(new Board());

        Tile bt;

        for (int i = 1; i <= GAME.getBoard().rows(); i++) {
            for (int j = 1; j <= GAME.getBoard().cols(); j++) {

                Square sqr = GAME.getBoard().getSquare(i, j);

                String output;
                String Tile = " ";

                if (sqr.isOccupied()) {

                    char tile = sqr.getTile();
                    bt = new Tile(String.valueOf(tile), i, j);
                } else {
                    switch (sqr.getMultiplier()) {
                        case DL:
                            output = "DL";
                            break;
                        case DW:
                            output = "DW";
                            break;
                        case TL:
                            output = "TL";
                            break;
                        case TW:
                            output = "TW";
                            break;
                        default:
                            output = "  ";
                    }

                    bt = new Tile(String.valueOf(output), i, j);

                }

                tiles.add(bt);
            }
        }
        gameBoard.getChildren().addAll(tiles);

        return gameBoard;
    }


}

