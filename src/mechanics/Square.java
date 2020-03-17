package src.mechanics;

/* Score Multipliers:
    NONE  = None
    DL = Double Letter
    TL = Triple Letter
    DW = Double Word
    TW = Triple Word */

public class Square
{
        /*
        Score Multipliers are strongly related to each Square on
        the board, thus we can use them in other classes by statically
        referring to the square class.
        */

    public static enum SCORE_MULT {NONE, DL, TL, DW, TW};

    SCORE_MULT score_multiplier;

    //Does A Letter Sit On The Square
    private boolean    is_occupied = false;
    private char       letter;

    public Square()
    {
        //Set The Square's Score Multiplier
        this.score_multiplier = SCORE_MULT.NONE;
    }

    public Square(SCORE_MULT multiplier)
    {
        //Set The Square's Score Multiplier
        this.score_multiplier = multiplier;
    }

    public void setTile(char t)
    {
        this.is_occupied = true;

        this.letter = t;
    }

    public char getTile()
    {
        //Error Handling: Calling Func When There's No Tile
        if(!isOccupied())
            throw new RuntimeException("No Tile On Square");

        return this.letter;
    }

    void clearTile()
    {
        if(this.isOccupied())
        {
            this.letter = ' ';
            this.is_occupied = false;
        }
    }

    public SCORE_MULT getMultiplier() { return this.score_multiplier; }
    public void       setMultiplier(SCORE_MULT m) { this.score_multiplier = m; }

    public boolean isOccupied() { return this.is_occupied; }

}