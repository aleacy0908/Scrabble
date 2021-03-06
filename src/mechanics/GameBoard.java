package src.mechanics;

import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class GameBoard extends GridPane
{
    private Board board = new Board();

    //Change Colour Of Tiles
    private String BACKGROUND_COLOUR = "#13db72";
    private String BORDER_COLOUR     = "black";

    //Rows & Columns On Board
    private int ROWS = board.rows();
    private int COLS = board.cols();

    public GameBoard()
    {
        super();

        Square sqr;
        String value;

        //Set Up The Board
        for (int i = 0; i < ROWS; i++)
        {
            for (int j = 0; j < COLS; j++)
            {
                //Get Board Information
                sqr = board.getSquare(i, j);
                value = sqr.toString();


                //Add A Tile To The Board
                //Using This Information
                this.addTile(value, i, j);
            }
        }

        refreshBoard();
    }

    public Board getBoardMechanics() { return board; }

    //Change Value Of Tile (IS Setting Letter)
    public void setLetter(String t, int i, int j) {
        board.setSquare(i, j, t);
        refreshBoard();
    }

    //Add Tile To Grid (NOT Setting Letter)
    private void addTile(String t, int i, int j)
    {
        Tile newTile = new Tile(t, BACKGROUND_COLOUR, BORDER_COLOUR);
        this.add(newTile, i, j);
    }

    //Places A Word On The Board
    public void setWord(String word, int r, int c, char dir)
    {
        String letter;

        for (char ltr : word.toCharArray())
        {
            letter = String.valueOf(ltr);

            System.out.println(r + " " + c);
            System.out.println(word);

            board.setSquare(r, c, letter);
            board.getSquare(r, c).incrementNumWordsUsingTile();

            if(dir == 'A') c++;
            else           r++;
        }

        refreshBoard();
    }

    public void removeWord(String word, int r, int c, char dir)
    {
        for (int i = 0; i < word.length(); i++)
        {
            Square sqr = board.getSquare(r, c);

            //If a tile is being used by more than
            //one word, then we dont want to remove it
            if(sqr.getNumWordsUsingTile() < 2)
                board.clearSquare(r, c);

            board.getSquare(r,c).decrementNumWordsUsingTile();

            if(dir == 'A') c++;
            else           r++;
        }

        refreshBoard();
    }

    //Refreshes The Board
    private void refreshBoard()
    {
        int i = 0;
        int j = 0;

        for(Node n : getChildren())
        {
            Square sqr = board.getSquare(i, j);

            String letter = sqr.toString();

            ((Tile)n).setLetter(String.valueOf(letter));

            if(i == COLS-1)
            {
                i = 0;
                j++;
            }
            else
                i++;
        }
    }
}

class Tile extends Pane
{
    Text letter = new Text();

    public Tile(String l, String backgroundColour, String borderColour)
    {
        //Sets Where The Letter Is In Relation To It's Tile
        letter.layoutXProperty().bind(this.widthProperty().subtract(23));
        letter.layoutYProperty().bind(this.heightProperty().subtract(7));

        String lStr = String.valueOf(l);

        letter.setText(lStr);

        this.getChildren().add(letter);

        setStyle("-fx-background-color: " + backgroundColour + ";" +
                 "-fx-border-color: "     + borderColour     + ";");

        this.setPrefSize(25, 25);

    }

    public void setLetter(String l)
    {
        letter.setText(l);
    }

    public String getLetter()
    {
        return this.letter.getText().toString();
    }
}
