package src.main;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;

import src.UI.GameWindow;
import src.UI.PlayerNameBox;
import src.mechanics.*;
import src.user.Player;

import java.util.ArrayList;

public class MainActivity extends Application {

    private Scrabble  GAME;
    private Player    currPlayer;

    private PlayerNameBox playerNameWindow;
    private GameWindow mainWindow;

    private String windowTitle = "Scrabble By The Pintsmen";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage mainStage) throws Exception {

        GAME = new Scrabble();
        GAME.setGUIBoard(new GameBoard());

        //create the 'Enter Player Names' window
        playerNameWindow = new PlayerNameBox();

        playerNameWindow.getConfirmBtn().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(playerNameWindow.validNamesEntered())
                {
                    String name1 = playerNameWindow.getPlayerOne();
                    String name2 = playerNameWindow.getPlayerTwo();

                    GAME.setPlayers(new Player[]{
                            new Player(name1, new Frame(new Pool())),
                            new Player(name2, new Frame(new Pool()))
                    });

                    mainWindow = new GameWindow(GAME.getGUIBoard());

                    runGame(mainStage);
                }
                else
                {
                    playerNameWindow.invalidNamePopup();
                }
            }
        });

        //Set the title and alignment of the window then launch
        mainStage.setTitle(windowTitle);
        mainStage.setScene(new Scene(playerNameWindow, 350,300));
        mainStage.show();

    }

    //This will allow a player to skip their turn
    public void passTurn(){
        String previousText = mainWindow.getOutputText();

        mainWindow.setOutputText(previousText + "\n" + currPlayer.nameP() + " passed their turn!");

        //Increment the current turn of the game
        GAME.incrementTurn();

        //Update the current player
        currPlayer = GAME.getCurrentPlayer();
        previousText = mainWindow.getOutputText();

        mainWindow.setOutputText(previousText + "\n" + currPlayer.nameP() + "'s Frame: " +
                currPlayer.getFrameP().getFrame());
    }

    public void runGame(Stage mainStage){

        currPlayer = GAME.getCurrentPlayer();

        mainWindow.setOutputText(mainWindow.getOutputText() + "\n" + currPlayer.nameP() +
                "'s Frame: " + currPlayer.getFrameP().getFrame());

        //EventListener: When Submit Button Is Clicked
        mainWindow.getSubmitButton().setOnAction(e -> {
            String playerInp = mainWindow.getInputBoxText().toUpperCase();
            takeTurn(playerInp);
        });

        //EventListener: When Pass Button is Clicked
        mainWindow.getPassButton().setOnAction(e -> {
            passTurn();
        });

    }

    /*
    This method will execute each time a player submits their input
    into the UI. It will allow players to interact with and play the game.
     */
    public void takeTurn(String playerInput){
        boolean PLAYER_FINISHED = false;
        boolean EXIT_GAME = false;

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
        boolean validMoveMade;

        String pName;

        while (!PLAYER_FINISHED) {
            //Which Player's Turn Is It?
            pName = currPlayer.nameP();

            playerInput = mainWindow.getInputBoxText();

            //Parse Input
            WORD  = GAME.getWord(playerInput);
            COORD = GAME.getCoord(playerInput);
            DIR   = GAME.getDirection(playerInput);

            //True If User Places Valid Word
            validMoveMade = GAME.getGUIBoard().getBoardMechanics().tileSelection(currPlayer, COORD[0], COORD[1],
                    DIR, WORD, Scrabble.wordsOnBoard);

            if(validMoveMade)
            {
                GAME.getGUIBoard().setWord(WORD, COORD[0]-1, COORD[1]-1, DIR);
            }

            //RETRY MOVE
            if (!validMoveMade) {
                PLAYER_FINISHED = true;
            }

            //Valid Move, Thus Find Out Word Score
            int wordScore = currPlayer.calculateScore(WORD, COORD[0], COORD[1], DIR);

            //Increase Player Score
            currPlayer.increaseScore(wordScore);

            //Print Out Score
            mainWindow.setOutputText(mainWindow.getOutputText() + "\n" + WORD + " is Worth " + wordScore + " Points!");
            mainWindow.setOutputText(mainWindow.getOutputText() + "\n" + pName + "'s Score Is " + currPlayer.getScore() + "\n\n");

            //Increment current turn
            GAME.incrementTurn();

            currPlayer = GAME.getCurrentPlayer();

            //Prompt next player to choose their word
            mainWindow.setOutputText(mainWindow.getOutputText() + "\n" + currPlayer.nameP() + " - Please enter your word");

            //Show Player Their Frame
            if (validMoveMade) {
                //Display the frame to player
                mainWindow.setOutputText(mainWindow.getOutputText() + "\n" + currPlayer.nameP() + "'s Frame: " + printFrame() + "\n");
            }

            mainWindow.setInputBoxText("");

            PLAYER_FINISHED = true;
        }


    }

    //Simply used to print a players frame to the UI
    public ArrayList<Character> printFrame(){
        return currPlayer.getFrameP().getFrame();
    }

}