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
    private int numWordsUsingTile = 0;

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

    public void incrementNumWordsUsingTile()
    {
        System.out.println();
        System.out.println("Incrementing " + letter);
        System.out.println("IsOccupied: " + isOccupied());
        System.out.println("numWordsUsingTile: " + numWordsUsingTile);
        System.out.println();

        numWordsUsingTile++;
    }

    public void decrementNumWordsUsingTile()
    {
        System.out.println();
        System.out.println("DecrementingI hav " + letter);
        System.out.println("IsOccupied: " + isOccupied());
        System.out.println("numWordsUsingTile: " + numWordsUsingTile);
        System.out.println();

        if(numWordsUsingTile != 0)
            numWordsUsingTile--;
    }

    public int getNumWordsUsingTile()
    {
        return this.numWordsUsingTile;
    }

    public void clearSquare()
    {
        if(isOccupied())
        {
            is_occupied = false;
            letter = " ";
        }

        numWordsUsingTile = 0;
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