package src.util;
import src.mechanics.Square.SCORE_MULT;

import src.mechanics.Board;
import src.mechanics.Board;

public class GameInfo {

    //--TILES--
    final char[] tileNames = {
            'A', 'B', 'C', 'D', 'E', 'F', 'G',
            'H', 'I', 'J', 'K', 'L', 'M', 'N',
            'O', 'P', 'Q', 'R', 'S', 'T', 'U',
            'V', 'W', 'X', 'Y', 'Z', '_'};

    final int[] tilePointsValues = {
            1, 3, 3,  2, 1,  4, 2,
            4, 1, 8,  5, 1,  3, 1,
            1, 3, 10, 1, 1,  1, 1,
            4, 4, 8,  4, 10, 0 };

    final int[] amountOfTilesInBeginning = {
            9, 2, 2, 4, 12, 2, 3,
            2, 9, 1, 1, 4,  2, 6,
            8, 2, 1, 6, 4,  6, 4,
            2, 2, 1, 2, 1,  2 };

    final int numTilesBeginning = 100;

    public GameInfo() {}

    //Basic Game Info
    public char[] getTileNames()         { return tileNames;                }
    public int [] getTilePointsValues()  { return tilePointsValues;         }
    public int [] getTilesInBeginning()  { return amountOfTilesInBeginning; }
    public int    getNumTilesBeginning() { return numTilesBeginning;        }

    //Specific Tile Values
    public int getTileValue(char x)
    {
        for(int i = 0; i < tileNames.length; i++)
        {
            if(tileNames[i] == x)
                return tilePointsValues[i];
        }

        return -1;
    }

    //Specific Square Multipliers
    public SCORE_MULT getMultiplier(int x, int y)
    {
        Board b = new Board();

        return b.getSquare(x,y).getMultiplier();
    }

}
