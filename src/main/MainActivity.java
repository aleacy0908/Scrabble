package src.main;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import src.mechanics.Board;
import src.mechanics.Frame;
import src.mechanics.Pool;
import src.user.Player;
import src.util.UI;
import src.mechanics.Square;


import java.util.Scanner;

public class MainActivity {

    static Scrabble GAME;

    public static void main(String[] args)
    {
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

        GAME.setPlayers(new Player[] {
                new Player(playerNameA, new Frame(new Pool())),
                new Player(playerNameB, new Frame(new Pool()))
        });

        //Play the game
        String playerInput;

        boolean GAME_FINISHED = false;
        boolean EXIT_GAME     = false;

        //Todo: While(!GAME_EXIT)

        /*
        These variables hold the separate
        values for the coordinates, word
        and direction the user entered */

        int[]  COORD = new int[2];
        String WORD;
        char   DIR;

        //Did The User Make A Valid Move?
        //Can We Move Onto The Next Move?
        //validMoveMade answers both these q's
        boolean validMoveMade = true;

        String pName;

        Player currPlayer;


        while(!GAME_FINISHED) {
            //Which Player's Turn Is It?
            currPlayer = GAME.getCurrentPlayer();
            pName      = currPlayer.nameP();

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
            WORD  = GAME.getWord(playerInput);
            COORD = GAME.getCoord(playerInput);
            DIR   = GAME.getDirection(playerInput);

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

    static int i,j;

    public static Parent createBoard () {


        GridPane gameBoard = new GridPane();
        gameBoard.setPrefSize(700, 700);

        String Tile = " ";
        for (i = 1; i < 16; i++) {
            for (j = 1; j < 16; j++) {
                Tile bt = new Tile(String.valueOf(Tile));

                Square sqr = GAME.getBoard().getSquare(i, j);

                if (sqr.isOccupied()) {

                    char tile = sqr.getTile();
                    bt = new Tile(String.valueOf(tile));

                } else {
                    String output;

                    switch (sqr.getMultiplier()) {
                        case DL:
                            output = "DL";
                            bt = new Tile(String.valueOf(output));
                            break;
                        case DW:
                            output = "DW";
                            bt = new Tile(String.valueOf(output));
                            break;
                        case TL:
                            output = "TL";
                            bt = new Tile(String.valueOf(output));
                            break;
                        case TW:
                            output = "TW";
                            bt = new Tile(String.valueOf(output));
                            break;
                        default:
                            output = "  ";
                            bt = new Tile(String.valueOf(output));

                            gameBoard.getChildren().add(bt);
                    }
                }
            }
        }
        return gameBoard;
    }

    public static class Tile extends StackPane {
        public Tile (String a) {

            Rectangle border = new Rectangle(50, 50);
            border.setFill(Color.GREEN);
            border.setStroke(Color.WHITE);

            GridPane.setRowIndex(border, i);
            GridPane.setColumnIndex(border, j);


            Text text = new Text(a);
            setAlignment(Pos.CENTER);
            getChildren().addAll(border, text);

        }
    }

}
