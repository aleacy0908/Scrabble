package src.util;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import src.main.MainActivity;

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

        gameWindow.setResizable(true);
        gameWindow.setScene(new Scene(root));
        gameWindow.show();
    }

}

