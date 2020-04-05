package src.UI;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import src.main.Scrabble;
import src.mechanics.GameBoard;
import src.user.Player;

public class GameWindow extends Stage {

    //GUI Elements
    public TextArea  output = new TextArea();
    public TextField input  = new TextField();
    public  Button   submit = new Button();

    /*---CUSTOM SETTINGS---*/

    //Main Window
    private double windowWidth  = 750.0;
    private double windowHeight = 400.0;

    //Button Settings
    private String helpTxt = "Help";
    private String passTxt = "Pass";
    private String quitTxt = "Quit";
    private String submitButton = "Submit";
    private String Nametxt = "Name";

    private double btnWidth  = 235;
    private double btnHeight = 25;

    //Player Input Area
    private String inputTextPrompt = "Enter Text Here";

    private double inputSpaceWidth  = 300;
    private double inputSpaceHeight = 100;

    private double inputAreaWidth  = 270;
    private double inputAreaHeight = 25;

    //Game Output Area
    private double  outputAreaWidth    = 448;
    private double  outputAreaHeight   = 520;
    private boolean outputAreaEditable = false;

    //Console Area
    private double consoleWidth  = 321.0;
    private double consoleHeight = 500.0;

    //Padding
    private Insets mainWindowPadding = new Insets(10,10,10,10);
    private Insets consolePadding    = new Insets(0,0,0,10);

    //Spacing
    private int mainSpacing    = 10;
    private int consoleSpacing = 10;

    //GUI Elements
    private HBox      mainWindow, inputSpace;
    private Button    help, quit, pass, name;
    private VBox      console;
    private ButtonBar commandButtons;

    private Player currPlayer;

    public GameWindow(GameBoard board)
    {

        //Whenever a close request is made, consume the request and load closeGame method
        setOnCloseRequest(e -> {
            e.consume();
            new CloseGameBox();
        });

        //Create the layout of the main game window
        mainWindow = new HBox(mainSpacing);

        //Console contains all the elements which the user can interact with
        console = new VBox(consoleSpacing);

        //Contains all the elements which accept user input
        inputSpace = new HBox();

        //Contains the buttons for user to interact with
        commandButtons = new ButtonBar();

        //Placed into commandButtons for easier access to console commands
        help = new Button(helpTxt);
        pass = new Button(passTxt);
        quit = new Button(quitTxt);
        name = new Button(Nametxt);

        //Set the size of the entire game window
        mainWindow.setPrefSize(windowWidth, windowHeight);
        mainWindow.setPadding(mainWindowPadding);

        //Set the size of the console section of the game window
        console.setPrefSize(consoleWidth,consoleHeight);

        //Disable user input for the TextArea and set its size in console
        output.setEditable(outputAreaEditable);
        output.setPrefSize(outputAreaWidth,outputAreaHeight);

        //Set the size of inputSpace and add the input elements to it
        inputSpace.setPrefSize(inputSpaceWidth,inputSpaceHeight);
        input.setPrefSize(inputAreaWidth,inputAreaHeight);

        input.setPromptText(inputTextPrompt);
        submit.setText(submitButton);

        inputSpace.getChildren().addAll(input,submit);

        //Set the size for the button bar and add all buttons to it
        commandButtons.setPrefSize(btnWidth,btnHeight);
        commandButtons.getButtons().addAll(pass,help,quit,name);

        console.setPadding(consolePadding);

        //Add all the panes to the main game window
        console.getChildren().addAll(output,inputSpace,commandButtons);

        //Create and add the game board to the UI
        mainWindow.getChildren().addAll(console, board);

        //Set the commands each button will execute when pressed
        quit.setOnAction(e -> new CloseGameBox());
        help.setOnAction(e -> new HelpBox());
        name.setOnAction((e -> new NameCommandBox()));

        //Disable allowing a user to manually change the game windows size
        setResizable(false);
        setScene(new Scene(mainWindow));
        show();
    }

    public Button getPassButton()
    {
        return pass;
    }

    public String getInputBoxText()
    {
        return this.input.getText().toString();
    }

    public void setInputBoxText(String text)
    {
        this.input.setText(text);
    }

    public String getOutputText()
    {
        return output.getText().toString();
    }

    public void setOutputText(String text)
    {
        output.setText(text);
    }

    public Button getSubmitButton()
    {
        return this.submit;
    }

    public ObservableList<Node> getChildren()
    {
        return mainWindow.getChildren();
    }

}
