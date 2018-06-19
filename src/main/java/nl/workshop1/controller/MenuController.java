package nl.workshop1.controller;

/**
 *
 * @author FeniksBV
 */
public abstract class MenuController {
    
    // This class can run in Admin or in search mode
    // Default will be admin
    public static final int CONTROLER_MODE_ADMIN = 1;
    public static final int CONTROLLER_MODE_SEARCH = 2;
   
    // Een controller kan een Entity Nieuw aanmaken of Wijzigen
    public static final int MODE_NIEUW = 1;
    public static final int MODE_WIJZIG = 2;

    public abstract void buildOptionsMenu();

    public abstract void handleMenu();
    
    public void runController() {
        buildOptionsMenu();
        handleMenu();
    }
    
    public String formatText(String text) {
        int maxLength = 14;
        StringBuilder s = new StringBuilder(text);
        while (s.length() < maxLength) {
            s.append(" ");
        }
        s.append(": ");
        return s.toString();
    }

}
