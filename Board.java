import java.util.Arrays;

/* Score Multipliers:
    NONE  = None
    DL = Double Letter
    TL = Triple Letter
    DW = Double Word
    TW = Triple Word */

enum SCORE_MULT {NONE, DL, TL, DW, TW};

//Coordinate Class
class Coord
{
    private short x;
    private short y;

    Coord(short x, short y)
    {
        this.x = x;
        this.y = y;
    }

    short getX() { return this.x; }
    short getY() { return this.y; }

    void setX(short x) { this.x = x; }
    void setY(short y) { this.y = y; }
}

public class Board {

    private class Square
    {
        SCORE_MULT score_multiplier;

        //Does A Letter Sit On The Square
        boolean    is_occupied = false;
        char       letter;

        Square()
        {
            //Set The Square's Score Multiplier
            this.score_multiplier = SCORE_MULT.NONE;
        }

        Square(SCORE_MULT multiplier)
        {
            //Set The Square's Score Multiplier
            this.score_multiplier = multiplier;
        }

        void setTile(char t)
        {
            this.is_occupied = true;

            this.letter = t;
        }

        char getTile()
        {
            //Error Handling: Calling Func When There's No Tile
            if(!is_occupied)
                throw new RuntimeException("No Tile On Square");

            return this.letter;
        }

        SCORE_MULT getMultiplier() { return this.score_multiplier; }
        void       setMultiplier(SCORE_MULT m) { this.score_multiplier = m; }

    }

    //MAIN BOARD
    final short B_ROWS = 15;
    final short B_COLS = 15;

    private Square[][] BOARD = new Square[B_ROWS][B_COLS];

    Board()
    {
        //Fill The Board With Squares
        Square emptySquare = new Square();
        Arrays.fill(BOARD, emptySquare);
    }

    private Square getSquare(int x, int y)
    {
        //Error Handle: Invalid Coordinate
        if(x < 1 || x > B_ROWS ||
           y < 1 || y > B_COLS)
        {
            throw new IllegalArgumentException("Invalid Coordinate");
        }

        return BOARD[x-1][y-1];
    }

}
