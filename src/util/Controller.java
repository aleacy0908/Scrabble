package src.util;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Controller {

    private static Stage window;
    public TextField input;
    public TextArea output;

    public static void alert(String title, String message){
        window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMaxWidth(350);
        window.setMinWidth(250);
        Label label = new Label();
        label.setText(message);

        Button ok = new Button("Ok");
        ok.setOnAction(e -> window.close());

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, ok);
        layout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(layout);

        window.setScene(scene);
        window.showAndWait();
    }

    static void closeGame(){
        boolean answer = confirm("Exit Game","Are you sure you want to end the game?");
        if(answer){
            Platform.exit();
        }
    }

    static boolean answer;

    public static boolean confirm(String title, String message){
        window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMaxWidth(350);
        window.setMinWidth(250);

        Label label = new Label();
        label.setText(message);

        Button yes = new Button("Yes");
        Button no = new Button("No");

        yes.setOnAction(e -> {
            answer = true;
            window.close();
        });

        no.setOnAction(e -> {
            answer = false;
            window.close();
        });

        VBox layout = new VBox(5);
        layout.getChildren().addAll(label, yes, no);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();

        return answer;
    }

    public void textEntered(ActionEvent actionEvent) {
        String option = input.getText();
        option = option.toUpperCase();

        if(option.equals("QUIT")){
            closeGame();
        }

        if(option.equals("PASS")){
        }

        if(option.equals("HELP")){
            output.setText(output.getText() + "\n" +"How to use:\n" +
                    "<GRID REF> <DIRECTION A(across)/D(downwards)> <WORD>\n(Example: A1 A HELLO)\n" +
                    "QUIT: Close the game\n" +
                    "HELP: Displays this message");
        }


    }
}
