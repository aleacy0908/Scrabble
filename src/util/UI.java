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

    public final short B_ROWS = 15;
    public final short B_COLS = 15;
    Board board;

    int i, j;

    public class Tile extends StackPane {
        public Tile (String a) {

            Rectangle border = new Rectangle(50, 50);
            border.setFill(Color.GREEN);
            border.setStroke(Color.WHITE);

            GridPane.setRowIndex(border, i);
            GridPane.setColumnIndex(border, j);


            Text text = new Text(a);
            // text.setFill(Color.BLACK);
            setAlignment(Pos.CENTER);
            getChildren().addAll(border, text);

        }
    }


    public Parent createBoard (Board b) {
        this.board = b;

        Pane gameBoard = new Pane();
        gameBoard.setPrefSize(700, 700);

        for (i = 1; i < 16; i++) {
            for (j = 1; j < 16; j++) {
                Square sqr = board.getSquare(i, j);

                if (sqr.isOccupied()) {

                    char tile = sqr.getTile();
                    Tile bt = new Tile(String.valueOf(tile));

                } else {
                    String output;

                    switch (sqr.getMultiplier()) {
                        case DL:
                            output = "DL";
                            Tile bt = new Tile(String.valueOf(output));
                            break;
                        case DW:
                            output = "DW";
                            bt = new Tile(String.valueOf(output));
                            break;
                        case TL:
                            output = "TL";
                            bt = new Tile(String.valueOf(output));
                            break;
                        case TW:
                            output = "TW";
                            bt = new Tile(String.valueOf(output));
                            break;
                        default:
                            output = "  ";
                            bt = new Tile(String.valueOf(output));

                            gameBoard.getChildren().add(bt);
                    }
                }
            }
        }
        return gameBoard;
    }


    public Board getBoard ( ) {
        return this.board;
    }

    public void setBoard (Board b) {
        this.board = b;
    }


    @Override
    public void start (Stage primaryStage) {

        primaryStage.setScene(new Scene(createBoard(new Board())));
        primaryStage.show();


    }
}

/*
    public static void main (String[] args) {
        launch(args);
    }
}
*/