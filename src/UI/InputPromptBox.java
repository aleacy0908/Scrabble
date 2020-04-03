package src.UI;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import src.user.Player;

public class InputPromptBox {

    private Stage window;

    private Button yesBtn = new Button("Confirm");

    private TextField input = new TextField();

    private String playersInput;

    private Player currPlayer;

    Label msgLabel;

    public InputPromptBox() {}

    public void showBox(String playersWord, Player player)
    {
        playersInput = playersWord;
        currPlayer = player;

        //Creating the stage
        window = new Stage();
        window.setTitle("Replace '_'");
        window.setMaxWidth(350);
        window.setMinWidth(250);

        //This will block the players from interacting with other windows
        window.initModality(Modality.APPLICATION_MODAL);

        //Placing the passed in message into a Label element to be added to the scene
        msgLabel = new Label();
        msgLabel.setText("Enter a character to replace '_' within " + playersInput);

        //If they answer yes, result is set to true and the prompt closes
        yesBtn.setOnAction(e -> {
            replaceChar();
            window.close();
        });

        //The scene is created using a VBox layout
        VBox layout = new VBox(5);
        layout.getChildren().addAll(msgLabel, input, yesBtn);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();

    }

    public void replaceChar(){

        //Allows the player to add a letter of their choice
        String tmp = input.getText().toUpperCase();
        playersInput = playersInput.replaceFirst("_", tmp);
        currPlayer.getFrameP().getFrame().remove(Character.valueOf('_'));
        currPlayer.getFrameP().getFrame().add(tmp.charAt(0));

    }

    public String getPlayersInput() {
        return playersInput;
    }
}
