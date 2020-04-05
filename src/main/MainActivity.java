package src.main;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import src.UI.AlertBox;
import src.UI.GameWindow;
import src.UI.InputPromptBox;
import src.UI.PlayerNameBox;
import src.mechanics.*;
import src.user.Player;
import src.user.Score;

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

    String prevWord  = null;
    int  prevScore = 0;
    int  prevRow = 0;
    int  prevCol = 0;
    char prevDir = 'A';
    boolean nextPlayersTurnPassed = false;
    Player  invalidPlayer = new Player("Placeholder");
    boolean postChallengeMove = false;

    public void takeTurn(String playerInput){
        boolean PLAYER_FINISHED = false;
        boolean EXIT_GAME = false;

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
        boolean validMoveMade;

        String pName;

        while (!PLAYER_FINISHED) {
            //Which Player's Turn Is It?
            pName = currPlayer.nameP();

            /*
            A backup is made of the players frame before selecting
            a frame so it can be reverted if they fail any of the checks.
            Needed for when a player uses a "_" tile as that tile is
            replaced so it can work with the checks
            */
            ArrayList<Character> backup = new ArrayList<>(currPlayer.getFrameP().getFrame());

            playerInput = mainWindow.getInputBoxText();

            //If its a challenge and the challenge
            //is not made on the first turn of the game
            if(playerInput.equals("CHALLENGE") && prevWord != null)
            {
                boolean invalidChallenge = GAME.getDict().check(prevWord);

                if(invalidChallenge)
                {
                    mainWindow.setOutputText("INVALID CHALLENGE");
                    passTurn();
                }
                else
                {
                    //Remove word from board
                    GAME.decrementWordsOnBoard();
                    GAME.getGUIBoard().removeWord(prevWord, prevRow-1, prevCol-1, prevDir);

                    //Decrease score
                    GAME.getPreviousPlayer().decreaseScore(prevScore);

                    //Print Output
                    mainWindow.setOutputText(mainWindow.getOutputText() +
                            "VALID CHALLENGE\n" +
                            "Removing " + prevWord +
                            "\nDecreasing Player's Score By " + prevScore +
                            "\nPlease Continue Your Go");

                    //Next players turn passed
                    nextPlayersTurnPassed = true;
                    invalidPlayer = GAME.getPreviousPlayer();

                    //Prompt next player to choose their word
                    mainWindow.setOutputText(mainWindow.getOutputText() + "\n" + currPlayer.nameP() + " - Please enter your word");

                    //Show Player Their Frame
                    mainWindow.setOutputText(mainWindow.getOutputText() + "\n" + currPlayer.nameP() + "'s Frame: " + printFrame() + "\n");

                    postChallengeMove = true;

                    //Take turn again
                    return;
                }

            }
            else if(playerInput.equals("CHALLENGE") && prevWord == null)
            {
                mainWindow.setOutputText(mainWindow.getOutputText() + "\nCannot Challenge On First Round\n");
                return;
            }

            //Parse Input
            WORD  = GAME.getWord(playerInput);
            COORD = GAME.getCoord(playerInput);
            DIR   = GAME.getDirection(playerInput);

            //Allows the player to add a letter of their choice
            while (WORD.contains("_")) {
                InputPromptBox replaceChar = new InputPromptBox();
                replaceChar.showBox(WORD, currPlayer);
                WORD = replaceChar.getPlayersInput();
            }

            //Valid Move, Thus Find Out Word Score
            int wordScore = currPlayer.calculateScore(WORD, COORD[0], COORD[1], DIR);

            int additionalWordsScore = additionalWordsCheck(WORD,COORD[0],COORD[1],DIR,
                    GAME.getGUIBoard().getBoardMechanics());

            if(additionalWordsScore == -1){
                new AlertBox().showBox("Error", "An adjacent word is not within the dictionary");
                currPlayer.getFrameP().getFrame().clear();
                currPlayer.getFrameP().getFrame().addAll(backup);
                break;
            }

            //True If User Places Valid Word
            validMoveMade = GAME.getGUIBoard().getBoardMechanics().tileSelection(currPlayer, COORD[0]-1, COORD[1]-1,
                    DIR, WORD, GAME);

            if(validMoveMade)
            {
                GAME.setWordsOnBoard(GAME.getWordsOnBoard() + 1);
            }else{
                currPlayer.getFrameP().getFrame().clear();
                currPlayer.getFrameP().getFrame().addAll(backup);
                break;
            }

            //Increase Player Score
            currPlayer.increaseScore(wordScore+additionalWordsScore);

            //Print Out Score
            mainWindow.setOutputText(mainWindow.getOutputText() + "\n" + WORD + " is Worth " + wordScore + " Points!");
            if (additionalWordsScore > 0) {
                mainWindow.setOutputText(mainWindow.getOutputText() + "\n" + "Adjacent Words Are Worth " +
                        additionalWordsScore + " Points!");
            }
            mainWindow.setOutputText(mainWindow.getOutputText() + "\n" + pName + "'s Score Is " + currPlayer.getScore());

            //Increment current turn
            GAME.incrementTurn();

            currPlayer = GAME.getCurrentPlayer();

            //Relevant if the next player
            //challenges the previous word
            prevWord  = WORD;
            prevScore = wordScore;
            prevRow   = COORD[0];
            prevCol   = COORD[1];
            prevDir   = DIR;

            PLAYER_FINISHED = true;

            if(postChallengeMove)
            {
                GAME.incrementTurn();

                mainWindow.setOutputText(
                        mainWindow.getOutputText() +
                                "\n\nTurn Is Passed Due To Loss Of Challenge\n\n" +
                        GAME.getCurrentPlayer().nameP() +
                        ": Please Make Another Move: \n" +
                        printFrame() +
                        "\n\n");

                postChallengeMove = false;

                return;
            }
            else
            {
                //Prompt next player to choose their word
                mainWindow.setOutputText(mainWindow.getOutputText() + "\n" + currPlayer.nameP() + " - Please enter your word");

                //Show Player Their Frame
                mainWindow.setOutputText(mainWindow.getOutputText() + "\n" + currPlayer.nameP() + "'s Frame: " + printFrame() + "\n");


                mainWindow.setInputBoxText("");
            }
        }


    }

    //Simply used to print a players frame to the UI
    public ArrayList<Character> printFrame(){
        return currPlayer.getFrameP().getFrame();
    }

    //Checks if a new word is placed adjacent to another word, creating another word
    public int additionalWordsCheck(String word, int row, int col, char dir, Board board){

        int wordsFound = 0;
        int scoreFromAdditionalWords = 0;
        DictionaryTree dictionary = new DictionaryTree();
        System.out.println("running additional words check");

        for(char c : word.toCharArray())
        {
            //Place the next square the users word will occupy into this variable
            Square sqr = board.getSquare(row,col);
            boolean crossWordFound = false;

            /*
            Create temp variables of row and col so we can easily reset
            them back to the next square the users word will occupy
             */
            int rowTmp = row;
            int colTmp = col;

            //Move the current sqr backwards until the start of the adjacent word is reached
            while(sqr.isOccupied()){
                if (dir == 'A'){
                    if(board.getSquare(rowTmp-1, colTmp).isOccupied()){
                        sqr = board.getSquare(rowTmp--, colTmp);
                        crossWordFound = true;
                    }
                }else{
                    if(board.getSquare(rowTmp, colTmp-1).isOccupied()){
                        sqr = board.getSquare(rowTmp, colTmp--);
                        crossWordFound = true;
                    }
                }
            }

            //Check If a new word was found and find its score if its found in the dictionary
            if(crossWordFound){
                String crossword = crossWords(rowTmp,colTmp,sqr,dir,board);

                if(!dictionary.check(crossword)){
                    //return -1 to the calling method to tell it the new word wasn't found in the dictionary
                    scoreFromAdditionalWords = -1;
                    break;
                }
                wordsFound++;

                //If only one word is found, then it is ignored as this is the word the users new word is connected to
                if(wordsFound > 1){
                    //For each adjacent word found, find its score value and add it to the score total the user will get
                    scoreFromAdditionalWords += currPlayer.calculateScore(word,rowTmp,colTmp,dir);
                }
            }

            //Move To Next Square
            if     (dir == 'A') col++;
            else if(dir == 'D') row++;
        }

        System.out.println("didnt crash");
        return scoreFromAdditionalWords;
    }

    //Turns the newly found adjacent word into a string
    private String crossWords(int row, int col,Square sqr,char dir,Board board){
        StringBuilder newWord = new StringBuilder();
        System.out.println("running?");

        //While the current square has a letter within it, the loop will run
        while(sqr.isOccupied()){

            //If the next square is empty, break the loop
            if(!sqr.isOccupied()){
                break;
            }

            //Append each letter of the adjacent word to the string
            newWord.append(sqr.getLetter());

            /*
            If the word is going across, we need to get the downward adjacent word.
            If the word is going downwards, we need to get the adjacent word going across.
             */
            if(dir == 'A') row++;
            else           col++;

            //Iterate to the next square
            sqr = board.getSquare(row,col);
        }

        System.out.println("string created");
        //Return the adjacent word
        return newWord.toString();
    }

}