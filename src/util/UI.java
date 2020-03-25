package src.util;


import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;;
import src.mechanics.Board;
import src.mechanics.Square;
import javafx.scene.Node;



public class UI extends Application {

    public final short  B_ROWS = 15;
    public final short  B_COLS = 15;
    Board board;

    public Parent createBoard(Board b)
    {
        this.board = b;

        GridPane gameBoard = new GridPane();
        gameBoard.setPrefSize(700, 700);

        for(int i = 1; i < 16; i++)
        {
            for(int j = 1; j < 16; j++)
            {
                Rectangle border = new Rectangle(50, 50);
                border.setFill(Color.GREEN);
                border.setStroke(Color.WHITE);

                GridPane.setRowIndex(border, i);
                GridPane.setColumnIndex(border, j);

                gameBoard.getChildren().addAll(border);
            }
        }

        return gameBoard;
    }



    public Board getBoard()
    {
        return this.board;
    }

    public void setBoard(Board b)
    {
        this.board = b;
    }


    @Override
    public void start (Stage primaryStage) {

        primaryStage.setScene(new Scene(createBoard(this.board)));
        primaryStage.show();


    }


   /* public static void main (String[] args) {
        launch(args);
    }*/
}
