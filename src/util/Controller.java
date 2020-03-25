package src.util;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Controller {

    private static Stage window;
    public TextField input;
    public TextArea output;

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

    /*
    Whenever a player tries to close the game, this method
    is called upon. It simply prompts them with a message
    to ask if they are certain they'd like to close the
    program
     */
    static void closeGame(){
        //A confirm box is created asking the players whether they want to close the program
        boolean answer = confirm("Exit Game","Are you sure you want to end the game?");

        //If they answered yes, then the program will close
        if(answer){
            Platform.exit();
        }
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

    /*
    This method will handle whatever the player inputs into
    the game's console.
     */
    public void textEntered(ActionEvent actionEvent) {

        //Creating variables to parse the input
        String option = input.getText();
        option = option.toUpperCase();
        String[] usage = option.split(" ");
        int x;
        int y;
        String word;
        char dir;

        //Quits the game
        if(option.equals("QUIT")){
            closeGame();
        }

        //Allows a player to pass their turn
        if(option.equals("PASS")){
        }

        //Tells the player the commands they can use in the console
        if(option.equals("HELP")){
            output.setText(output.getText() + "\n" +"How to use:\n" +
                    "<GRID REF> <DIRECTION A(across)/D(downwards)> <WORD>\n(Example: A1 A HELLO)\n" +
                    "QUIT: Close the game\n" +
                    "HELP: Displays this message\n");
        }

        /*
        Splits the grid ref, direction and word from each other so they can be easily passed back
        to the main scrabble class.
         */
        if(usage.length == 3 && usage[0].length() > 1){
            x = (usage[0].charAt(0) - 64);

            //Here the grid ref will be converted from the format 'A1' to '1 1' so it works with our code
            if(usage[0].length() == 2){
                y = Character.getNumericValue(usage[0].charAt(1));
            }else{
                String yTemp = Character.toString(usage[0].charAt(1)) + usage[0].charAt(2);
                y = Integer.parseInt(yTemp);
            }

            dir = usage[1].charAt(0);
            word = usage[2];

        }
    }
}
