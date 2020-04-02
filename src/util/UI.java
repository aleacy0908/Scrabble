package src.util;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import src.main.Scrabble;
import src.mechanics.*;
import src.user.Player;

import java.util.ArrayList;
import java.util.Iterator;


public class UI extends Application {

    private Scrabble  GAME      = new Scrabble();
    private GameBoard gameBoard = new GameBoard();
    public  TextField input     = new TextField();
    public  Button    submit    = new Button();
    private Player    currPlayer;

    public static  TextArea  output = new TextArea();
    private static Stage     window;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage mainStage) throws Exception {

        //create the 'Enter Player Names' window
        VBox playerNameWindow = new VBox();
        playerNameWindow.setSpacing(10);
        playerNameWindow.setPadding(new Insets(10,10,10,10));

        //create the button for submitting the player names
        Button confirm = new Button("Confirm");

        Label instruction = new Label("Enter each players name below");
        Label player1Name = new Label("Player 1 Name: ");
        Label player2Name = new Label("Player 2 Name: ");
        TextField name1 = new TextField();
        TextField name2 = new TextField();

        //Add all the created elements into the playerNameWindow scene
        playerNameWindow.getChildren().addAll(instruction,player1Name,name1,player2Name,name2,confirm);

        //will launch the game when the players submit their names
        confirm.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                //if one or both of the player names isn't given, an error will appear
                if(name1.getText().trim().isEmpty() || name2.getText().trim().isEmpty()){
                    alert("Error","Both players must be given a name!");
                }else{
                    GAME.setPlayers(new Player[]{
                            new Player(name1.getText(), new Frame(new Pool())),
                            new Player(name2.getText(), new Frame(new Pool()))
                    });
                    runGame(mainStage);
                }
            }
        });

        //Set the title and alignment of the window then launch
        mainStage.setTitle("Scrabble by The Pintsmen");
        playerNameWindow.setAlignment(Pos.CENTER);
        mainStage.setScene(new Scene(playerNameWindow, 350,300));
        mainStage.show();

    }

    public void runGame(Stage mainStage){

        //Whenever a close request is made, consume the request and load closeGame method
        mainStage.setOnCloseRequest(e -> {
            e.consume();
            closeGame();
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
        quit.setOnAction(e -> closeGame());
        help.setOnAction(e -> helpMessage());
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

    /*
    This will allow a player to skip their turn
     */
    public void passTurn(){
        output.setText(output.getText() + "\n" + currPlayer.nameP() + " passed their turn!");

        //Increment the current turn of the game
        GAME.incrementTurn();

        //Update the current player
        currPlayer = GAME.getCurrentPlayer();
        output.setText(output.getText() + "\n" + currPlayer.nameP() + "'s Frame: " +
                currPlayer.getFrameP().getFrame());
    }

    /*
    This method will display the help message to the player
     */
    public void helpMessage(){
        String message = "How to use:\n" +
                "<GRID REF> <DIRECTION A(across)/D(downwards)> <WORD>\n(Example: A1 A HELLO)\n" +
                "QUIT: Close the game\n" +
                "HELP: Displays this message\n";

        alert("Help", message);
    }

    /*
    Whenever a player tries to close the game, this method
    is called upon. It simply prompts them with a message
    to ask if they are certain they'd like to close the
    program
     */
    public static void closeGame(){
        //A confirm box is created asking the players whether they want to close the program
        boolean answer = confirm("Exit Game","Are you sure you want to end the game?");

        //If they answered yes, then the program will close
        if(answer){
            Platform.exit();
            System.exit(0);
        }
    }

    /*
    This is a general method made so
    a prompt message can appear to inform the
    players of something they need to know
     */
    public static void alert(String title, String message){

        //The prompts stage is created and its limits are set
        window = new Stage();
        window.setTitle(title);
        window.setMaxWidth(350);
        window.setMinWidth(250);

        //Blocks the player from interacting with other windows until this alert is closed
        window.initModality(Modality.APPLICATION_MODAL);

        //A label element is created with the custom message passed into the method
        Label label = new Label();
        label.setText(message);

        //A button for the player to confirm that they have read the alert
        Button ok = new Button("Ok");

        //Closes the alert when the player presses the button
        ok.setOnAction(e -> window.close());

        //A scene with VBox layout is created
        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, ok);
        layout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(layout);

        window.setScene(scene);
        window.showAndWait();
    }

    static boolean answer;

    /*
    This is a general method made so
    a prompt message to the players can be easily
    made, asking them to confirm something.
     */
    public static boolean confirm(String title, String message){

        //Creating the stage
        window = new Stage();
        window.setTitle(title);
        window.setMaxWidth(350);
        window.setMinWidth(250);

        //This will block the players from interacting with other windows
        window.initModality(Modality.APPLICATION_MODAL);

        //Placing the passed in message into a Label element to be added to the scene
        Label label = new Label();
        label.setText(message);

        //Creating the buttons for the players to interact with
        Button yes = new Button("Yes");
        Button no = new Button("No");

        //If they answer yes, result is set to true and the prompt closes
        yes.setOnAction(e -> {
            answer = true;
            window.close();
        });

        //If they answer no, result is set to false and the prompt closes
        no.setOnAction(e -> {
            answer = false;
            window.close();
        });

        //The scene is created using a VBox layout
        VBox layout = new VBox(5);
        layout.getChildren().addAll(label, yes, no);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();

        return answer;
    }

}
