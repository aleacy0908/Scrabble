public abstract class Score {

    int score = 0;

    GameInfo gi = new GameInfo();

    public void setScore      (int s) { this.score = s; }
    public void increaseScore (int s) { this.score += s; }
    public void decreaseScore (int s) { this.score -= s; }
    public int  getScore()            { return score;   }

    public int calculateScore(String word, int row, int col, char dir)
    {
        int wordMultiplier = 1,
                wordValue  = 0,
                tileValue;

        char tile;

        for(int i = 0; i < word.length(); i++)
        {
            tile = word.charAt(i);
            tileValue = gi.getTileValue(tile);

            SCORE_MULT m = gi.getMultiplier(row, col);

            /*
            If we have a Double Letter or a
            Triple Letter, we multiply the value of that
            letter by 2 or 3 respectively.

            If we have a Double Word or Triple Word,
            we save the multiplication until the very end.
            Thus we essentially store the amount we have to multiply
            the whole word by in the wordMultiplier
             */

            switch(m)
            {
                case DL:
                    tileValue *= 2;
                    break;
                case TL:
                    tileValue *= 3;
                    break;
                case DW:
                    wordMultiplier *= 2;
                    break;
                case TW:
                    wordMultiplier *= 3;
                    break;
            }

            //Add Value Of Each Tile
            wordValue += tileValue;

            //Move Onto Next Tile
            if(dir == 'A')
                col++;
            else
                row++;
        }

        //Multiply By Any Word Multipliers
        wordValue *= wordMultiplier;

        return wordValue;
    }


}
