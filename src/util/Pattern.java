package src.util;

import java.util.ArrayList;
import java.util.Arrays;

public class Pattern {

    /*
    Our Final Array
    This Will Contain All Of Our Coordinates
    After A Pattern Has Been Created
     */
    ArrayList<int[]> pattern;

    /*
    Our pattern will be based on the coordinates
    that we input to the class. Each of these coordinates
    will be mapped onto different areas of the board based on the
    formula in our reflect1D function.

    This dramatically reduces the amount of coordinates one has to
    input for a multiplier tile in order to have it evenly spread across the
    board.
     */

    int[][] inputCoords;
    int boardSize;

    public Pattern(int BOARD_SIZE)
    {
        this.boardSize = BOARD_SIZE;
    }

    private int reflect1D(int x)
    {
        //Reflect a single scalar
        return (boardSize + 1) - x;
    }

    private int[][] reflect2D(int x, int y)
    {
        /*
        Reflect a 2D coordinate onto
        the four different sections
        of the board
        */

        return new int[][]{
                {x, y},  {reflect1D(x), y}, {reflect1D(x), reflect1D(y)},
                {y, x},  {x, reflect1D(y)}, {reflect1D(y), reflect1D(x)}
        };
    }

    public void setPatternCoords(int[][] pos)
    {
        this.inputCoords = pos;
    }

    public ArrayList<int[]> getPattern()
    {
        //Initialise our pattern
        pattern = new ArrayList<int[]>();

        //Variable To Hold Our Reflections/Rotations
        int[][] newCoords;
        int x,y;

        //For Each Input Coordinate
        for(int i = 0; i < inputCoords.length; i++)
        {
            //Separate Into It's Dimensions
            x = inputCoords[i][0];
            y = inputCoords[i][1];

            /*
            Reflect This Coordinate Onto
            Four Different Sections Of The
            Map
            */

            newCoords = reflect2D(x, y);


            //Add These New Coordinates To Our Output ArrayList
            pattern.addAll(Arrays.asList(newCoords));

        }

        return pattern;
    }

}
