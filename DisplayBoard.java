public class Board {


    public static void DisplayBoard ( ) {
        int i, j;

        System.out.println("");
        System.out.println("                                       SCRABBLE BOARD                                         ");


        for (i = 15; i > 0; i--) {
            System.out.println("");
            System.out.print("  ___________________________________________________________________________________________\n");

            for (j = 16; j > 0; j--) {
                System.out.print("  | " + " " + " ");


            }
        }
        System.out.println("");
        System.out.print("  ___________________________________________________________________________________________\n");


    }
    
 }

   
