package src.util;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import src.main.MainActivity;
import src.main.Scrabble;
import src.mechanics.Board;
import src.mechanics.Square;
import javafx.scene.Node;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.URL;
import java.util.ResourceBundle;

import static src.util.Controller.closeGame;


public class UI extends Application {

    Stage gameWindow;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage mainStage) throws Exception {

        HBox root = FXMLLoader.load(getClass().getResource("UI.fxml"));
        gameWindow = mainStage;
        gameWindow.setTitle("Scrabble");

        gameWindow.setOnCloseRequest(e -> {
            e.consume();
            closeGame();
        });

        root.getChildren().add(MainActivity.createBoard());

        gameWindow.setResizable(false);
        gameWindow.setScene(new Scene(root));
        gameWindow.show();
    }

}

