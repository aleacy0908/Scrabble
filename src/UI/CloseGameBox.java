package src.UI;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;

/*
    Whenever a player tries to close the game, this method
    is called upon. It simply prompts them with a message
    to ask if they are certain they'd like to close the
    program
     */

public class CloseGameBox extends ConfirmationBox {

    //Close Game Text
    private String closeGameTitle = "Exit Game";
    private String closeGameMsg   = "Are you sure you want to end the game?";

    //We want our own custom methods
    //for the yes/no buttons
    boolean useDefaultListeners = false;

    Stage window;

    public CloseGameBox()
    {

        //Custom Event Listener: NO BUTTON
        super.getNoBtn().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                close();
            }
        });

        //Custom Event Listener: YES BUTTON
        super.getYesBtn().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Platform.exit();
                System.exit(0);
            }
        });

        //Show Box
        super.showBox(closeGameTitle, closeGameMsg, useDefaultListeners);
    }
}