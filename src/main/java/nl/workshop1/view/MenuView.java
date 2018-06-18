package nl.workshop1.view;

import java.util.Scanner;
import nl.workshop1.utility.Validator;

/**
 *
 * @author FeniksBV
 */
public class MenuView {

    public static Scanner input = new Scanner(System.in);

    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_RED = "\u001B[31m";

    public void showMessage() {
        showMessage("");
    }

    public void showMessage(String msg) {
        System.out.println(msg);
    }

    public void showInputText(String msg) {
        System.out.print(msg);
    }

    public void showTitle(String msg) {
        System.out.println("\n" + ANSI_BLUE + msg);
        for (int i = 0; i < msg.length(); i++) {
            System.out.print("=");
        }
        System.out.println(ANSI_RESET);
    }

    public void showRecordHeader(String msg) {
        System.out.println(ANSI_BLUE + msg + ANSI_RESET);
    }

    public static void showError(String msg) {
        System.out.println(ANSI_RED + msg + ANSI_RESET);
    }

    public String getInputChoice() {
        System.out.print("Maak uw keuze : ");
        return input.nextLine().toUpperCase();
    }

    public String getInputFilter() {
        System.out.print("Specificeer filter : ");
        return input.nextLine();
    }

    public boolean getInputAkkoord(String informatial) {
        if (!informatial.equals("")) {
            System.out.println(informatial);
        }
        do {
            System.out.print("Akkoord (J/N) ? : ");
            String akkoord = input.nextLine().toUpperCase();
            if (akkoord.equals("J")) {
                return true;
            }
            if (akkoord.equals("N")) {
                return false;
            }
        } while (true);
    }

    /**
     *
     * @return : gebruikersnaam (of null)
     */
    public String getInputUsername() {
        
        do {
            System.out.println("Een gebruikersnaam heeft de syntax van een emailadres.");
            System.out.print("Gebruikersnaam : ");
            String username = input.nextLine();

            if (username.equals("")) {
                return null;
            }
            
            if (Validator.validUsername(username)) {
                return username;
            } else {
                showError("Gebruikersnaam is ongeldig.");
            }
        } while (true);
    }

    public String getInputWachtwoord() {
        System.out.print("Wachtwoord     : ");
        return input.nextLine();
    }
}
