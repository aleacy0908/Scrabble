package src.UI;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.geometry.Pos;

 /*
 This is a general class made so
 a prompt message can appear to inform the
 players of something they need to know */

public class AlertBox extends Stage {

    private String ok = "Ok";
    private String message;
    private String title;

    Stage  window   = new Stage();
    Label  msgLabel = new Label();

    Button okBtn;
    VBox   layout;
    Scene  scene;

    public AlertBox() {}

    public AlertBox(String title, String message)
    {
        //Set Title
        this.title = title;
        this.message = message;
    }

    public void showBox(String title, String message)
    {
        window = new Stage();
        window.setMaxWidth(350);
        window.setMinWidth(250);

        //Blocks the player from interacting with other windows until this alert is closed
        window.initModality(Modality.APPLICATION_MODAL);

        //A label element is created with the custom message passed into the method
        msgLabel.setText(message);

        //A button for the player to confirm that they have read the alert
        okBtn = new Button(ok);

        //Closes the alert when the player presses the button
        okBtn.setOnAction(e -> window.close());

        //A scene with VBox layout is created
        VBox layout = new VBox(10);
        layout.getChildren().addAll(msgLabel, okBtn);
        layout.setAlignment(Pos.CENTER);

        scene = new Scene(layout);

        window.setScene(scene);
        window.showAndWait();
    }

    //Get & Set Message
    public void setMessage(String msg)
    {
        this.message = msg;
    }

    public String getMessage()
    {
        return this.message;
    }
}
