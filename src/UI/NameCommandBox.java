package src.UI;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import javax.swing.*;


public class NameCommandBox<NameWindow> extends VBox {

    Button Done;
    TextField namep1;
    TextField namep2;
    Label p1_name,p2_name;

    private String p1txt = "Enter Player 1 Name: ";
    private String p2txt = "Enter Player 2 Name: ";
    private String buttonText  = "Done";

    public Button getDone()
    {
        return this.Done;
    }

    public String getp1_name()
    {
        return namep1.getText().toString();
    }

    public String getP2_name()
    {
        return namep2.getText().toString();
    }

    public NameCommandBox ( ) {

        super.setSpacing(10);
        super.setPadding(new Insets(10,10,10,10));
        super.setAlignment(Pos.CENTER);

        Done  = new Button(buttonText);
        p1_name = new Label(p1txt);
        p2_name = new Label(p2txt);
        namep1 = new TextField();
        namep2 = new TextField();

       Done.setOnAction(new EventHandler<ActionEvent>() {

            public void handle (ActionEvent event) {

                System.exit(0);
            }
           });

        getChildren().addAll(p1_name, namep1, p2_name, namep2, Done);

    }
    
}


