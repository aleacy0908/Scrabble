package src.UI;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import src.main.Scrabble;
import src.mechanics.*;
import src.user.Player;

import java.util.ArrayList;

public class UI extends Application {

    private Scrabble  GAME      = new Scrabble();
    private GameBoard gameBoard = new GameBoard();
    public  TextField input     = new TextField();
    public  Button    submit    = new Button();
    private Player    currPlayer;

    public static  TextArea  output = new TextArea();
    private static Stage     window;

    private String windowTitle = "Scrabble By The Pintsmen";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage mainStage) throws Exception {

        //create the 'Enter Player Names' window
        PlayerNameBox playerNameWindow = new PlayerNameBox();

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

    public void runGame(Stage mainStage){

        //Whenever a close request is made, consume the request and load closeGame method
        mainStage.setOnCloseRequest(e -> {
            e.consume();
            new CloseGameBox();
        });

        //Create the layout of the main game window
        HBox mainWindow = new HBox(10);

        //Console contains all the elements which the user can interact with
        VBox console = new VBox(10);

        //Contains all the elements which accept user input
        HBox inputSpace = new HBox();

        //Contains the buttons for user to interact with
        ButtonBar commandButtons = new ButtonBar();

        //Placed into commandButtons for easier access to console commands
        Button help = new Button("Help");
        Button pass = new Button("Pass");
        Button quit = new Button("Quit");

        //Set the size of the entire game window
        mainWindow.setPrefSize(700.0,400.0);
        mainWindow.setPadding(new Insets(10,10,10,10));

        //Set the size of the console section of the game window
        console.setPrefSize(321.0,500.0);

        //Disable user input for the TextArea and set its size in console
        output.setEditable(false);
        output.setPrefSize(448,520);

        //Set the size of inputSpace and add the input elements to it
        inputSpace.setPrefSize(200,100);
        input.setPrefSize(210,25);
        input.setPromptText("Enter text here");
        submit.setText("Submit");
        inputSpace.getChildren().addAll(input,submit);

        //Set the size for the button bar and add all buttons to it
        commandButtons.setPrefSize(235,25);
        commandButtons.getButtons().addAll(pass,help,quit);
        console.setPadding(new Insets(0,0,0,10));

        //Add all the panes to the main game window
        console.getChildren().addAll(output,inputSpace,commandButtons);

        //Create and add the game board to the UI
        //GAME BOARD CREATED HERE
        mainWindow.getChildren().addAll(console,gameBoard);
        GAME.setBoard(gameBoard);

        //Update the current player and print out their frame
        currPlayer = GAME.getCurrentPlayer();
        output.setText(output.getText() + "\n" + currPlayer.nameP() + "'s Frame: " + currPlayer.getFrameP().getFrame());

        //Set the commands each button will execute when pressed
        quit.setOnAction(e -> new CloseGameBox());
        help.setOnAction(e -> new HelpBox());
        pass.setOnAction(e -> passTurn());

        //EventListener: When Submit Button Is Clicked
        submit.setOnAction(e -> {
            String playerInp = input.getText().toUpperCase();
            takeTurn(playerInp);
        });

        //Disable allowing a user to manually change the game windows size
        mainStage.setResizable(false);
        mainStage.setScene(new Scene(mainWindow));
        mainStage.show();

    }

    /*
    This method will execute each time a player submits their input
    into the UI. It will allow players to interact with and play the game.
     */
    public void takeTurn(String playerInput){
        boolean PLAYER_FINISHED = false;
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
        boolean validMoveMade;

        String pName;

        while (!PLAYER_FINISHED) {
            //Which Player's Turn Is It?
            pName = currPlayer.nameP();

            playerInput = input.getText();

            //Parse Input
            WORD  = GAME.getWord(playerInput);
            COORD = GAME.getCoord(playerInput);
            DIR   = GAME.getDirection(playerInput);

            //True If User Places Valid Word
            validMoveMade = gameBoard.getBoard().tileSelection(currPlayer, COORD[0], COORD[1],
                    DIR, WORD, Scrabble.wordsOnBoard);

            if(validMoveMade)
            {
                //TESTING
                gameBoard.setWord(WORD, COORD[0]-1, COORD[1]-1, DIR);
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
            output.setText(output.getText() + "\n" + WORD + " is Worth " + wordScore + " Points!");
            output.setText(output.getText() + "\n" + pName + "'s Score Is " + currPlayer.getScore() + "\n\n");

            //Increment current turn
            GAME.incrementTurn();

            currPlayer = GAME.getCurrentPlayer();

            //Prompt next player to choose their word
            output.setText(output.getText() + "\n" + currPlayer.nameP() + " - Please enter your word");

            //Show Player Their Frame
            if (validMoveMade) {
                //Display the frame to player
                output.setText(output.getText() + "\n" + currPlayer.nameP() + "'s Frame: " + printFrame() + "\n");
            }

            input.setText("");

            PLAYER_FINISHED = true;
        }


    }

    //Simply used to print a players frame to the UI
    public ArrayList<Character> printFrame(){
        return currPlayer.getFrameP().getFrame();
    }


    //This will allow a player to skip their turn
    public void passTurn(){
        output.setText(output.getText() + "\n" + currPlayer.nameP() + " passed their turn!");

        //Increment the current turn of the game
        GAME.incrementTurn();

        //Update the current player
        currPlayer = GAME.getCurrentPlayer();
        output.setText(output.getText() + "\n" + currPlayer.nameP() + "'s Frame: " +
                currPlayer.getFrameP().getFrame());
    }

}
