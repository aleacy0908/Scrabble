package src.UI;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;


public class PlayerNameBox extends VBox {

    private String mainMessage = "Enter Each Player's Name Below";
    private String p1NameMsg   = "Player 1 Name: ";
    private String p2NameMsg   = "Player 2 Name: ";
    private String btnText     = "Confirm";

    private String noTextError  = "Both players must be given a name!";

    private int spacing = 10;

    Button confirmBtn;
    Label instruction;
    Label player1Name;
    Label player2Name;
    TextField name1;
    TextField name2;

    private boolean namesEntered = false;

    public PlayerNameBox()
    {
        super.setSpacing(spacing);
        super.setPadding(new Insets(10,10,10,10));
        super.setAlignment(Pos.CENTER);

        //create the button for submitting the player names
        confirmBtn  = new Button(btnText);
        instruction = new Label(mainMessage);
        player1Name = new Label(p1NameMsg);
        player2Name = new Label(p2NameMsg);

        name1 = new TextField();
        name2 = new TextField();

        //Add all the created elements into the playerNameWindow scene
        getChildren().addAll(instruction, player1Name, name1,
                             player2Name, name2, confirmBtn);

    }

    public void invalidNamePopup()
    {
        new AlertBox("Error", noTextError);
    }

    public boolean validNamesEntered()
    {
        return !name1.getText().trim().isEmpty() &&
                !name2.getText().trim().isEmpty();
    }

    public Button getConfirmBtn()
    {
        return this.confirmBtn;
    }

    public String getPlayerOne()
    {
        return name1.getText().toString();
    }

    public String getPlayerTwo()
    {
        return name2.getText().toString();
    }

}
