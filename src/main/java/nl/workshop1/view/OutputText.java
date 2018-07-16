package nl.workshop1.view;

/**
 *
 * @author FeniksBV
 */
public class OutputText {

//    private static final String ANSI_RESET = "\u001B[0m";
//    private static final String ANSI_BLUE = "\u001B[34m";
//    public static final String ANSI_RED = "\u001B[31m";

    private static final String ANSI_RESET = "";
    private static final String ANSI_BLUE = "";
    public static final String ANSI_RED = "";
    
        
    public static void showText(String msg) {
        System.out.print(msg);
    }
    
    public static void showMessage(String msg) {
        System.out.println(msg);
    }

    public static void showError(String msg) {
        showMessage("\n" + ANSI_RED + msg + ANSI_RESET);
    }

    public static void showRecordHeader(String msg) {
        showMessage(ANSI_BLUE + msg + ANSI_RESET);
    }

    public static void showTitle(String msg) {
        System.out.println("\n" + ANSI_BLUE + msg);
        for (int i = 0; i < msg.length(); i++) {
            System.out.print("=");
        }
        System.out.println(ANSI_RESET);
    }

    

}
