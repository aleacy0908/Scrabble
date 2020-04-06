package src.UI;

public class HelpBox extends AlertBox {

    private String helpTitle;
    private String helpMsg;

    public HelpBox() {

        helpTitle = "Help";

        helpMsg = "How to use:\n" +
                "<GRID REF> <DIRECTION A(across)/" +
                "D(downwards)> <WORD>\n" +
                "(Example: A1 A HELLO)\n" +
                "QUIT: Close the game\n"  +
                "HELP: Displays this message\n" +
                "CHALLENGE: Type CHALLENGE to challenge a players word";

        showBox(helpTitle, helpMsg);
    }
}
