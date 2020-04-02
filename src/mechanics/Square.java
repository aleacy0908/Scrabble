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
    private String     letter = " ";

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

    public void setLetter(String t)
    {
        this.is_occupied = true;

        this.letter = t;
    }

    public String getLetter()
    {
        return this.letter;
    }

    void clearTile()
    {
        if(this.isOccupied())
        {
            this.letter = " ";
            this.is_occupied = false;
        }
    }

    public String toString()
    {
        if (isOccupied())
            return String.valueOf(getLetter());

        String output = " ";

        switch (this.getMultiplier())
        {
            case DL:
                output = "DL";
                break;
            case DW:
                output = "DW";
                break;
            case TL:
                output = "TL";
                break;
            case TW:
                output = "TW";
                break;
        }

        return output;
    }

    public SCORE_MULT getMultiplier() { return this.score_multiplier; }
    public void       setMultiplier(SCORE_MULT m) { this.score_multiplier = m; }

    public boolean hasMultiplier() { return this.score_multiplier != SCORE_MULT.NONE; }

    public boolean isOccupied() { return this.is_occupied; }

}