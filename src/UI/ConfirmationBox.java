package src.UI;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/*
 * This is a general method made so
 * a prompt message to the players can be easily
 * made, asking them to confirm something.
 */

public class ConfirmationBox extends Stage
{
    Stage window;

    private Button yesBtn = new Button("Yes");
    private Button noBtn  = new Button("No");

    Label msgLabel;

    private boolean yesClicked = false;

    public ConfirmationBox() {}

    public void showBox(String title, String message, boolean defaultListeners)
    {
        //Creating the stage
        window = new Stage();
        window.setTitle(title);
        window.setMaxWidth(350);
        window.setMinWidth(250);

        //This will block the players from interacting with other windows
        window.initModality(Modality.APPLICATION_MODAL);

        //Placing the passed in message into a Label element to be added to the scene
        msgLabel = new Label();
        msgLabel.setText(message);

        if(defaultListeners)
            setDefaultEventActionListeners();

        //The scene is created using a VBox layout
        VBox layout = new VBox(5);
        layout.getChildren().addAll(msgLabel, yesBtn, noBtn);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();

    }

    public void setDefaultEventActionListeners()
    {
        //If they answer yes, result is set to true and the prompt closes
        yesBtn.setOnAction(e -> {
            yesClicked = true;
            window.close();
        });

        //If they answer no, result is set to false and the prompt closes
        noBtn.setOnAction(e -> {
            window.close();
        });
    }

    public Stage getWindow()
    {
        return this.window;
    }

    public Button getYesBtn()
    {
        return this.yesBtn;
    }

    public Button getNoBtn()
    {
        return this.noBtn;
    }

    private boolean isYes()
    {
        return this.yesClicked;
    }

}
