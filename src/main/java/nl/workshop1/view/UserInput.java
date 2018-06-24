package nl.workshop1.view;

import java.math.BigDecimal;
import java.util.Scanner;
import nl.workshop1.utility.Validator;

/**
 *
 * @author FeniksBV
 */
public abstract class UserInput {

    public static Scanner input = new Scanner(System.in);

    public String getInputChoice() {
        OutputText.showText("Maak uw keuze : ");
        return input.nextLine().toUpperCase();
    }

    /**
     *
     * @param emptyAllowed
     * @return : gebruikersnaam (of null)
     */
    public String getInputUsername(boolean emptyAllowed) {

        do {
            OutputText.showText("Een gebruikersnaam heeft de syntax van een emailadres.\n");
            OutputText.showText("Gebruikersnaam : ");
            String username = input.nextLine();

            if (emptyAllowed) {
                // Alleen bij inloggen
                if (username.equals("")) {
                    return null;
                }
            }

            if (Validator.validUsername(username)) {
                return username;
            } else {
                OutputText.showError("Gebruikersnaam voldoet niet aan de syntax.");
            }
        } while (true);
    }

    /**
     *
     * @return the wachtwoord
     */
    public String getInputWachtwoord() {
        do {
            OutputText.showText("Wachtwoord     : ");
            String wachtwoord = input.nextLine();
            if (!wachtwoord.equals("")) {
                return wachtwoord;
            }

        } while (true);
    }

    public static boolean getInputAkkoord(String informatial) {
        if (!informatial.equals("")) {
            OutputText.showMessage(informatial);
        }
        do {
            OutputText.showText("Akkoord (J/N) ? : ");
            String akkoord = input.nextLine().toUpperCase();
            if (akkoord.equals("J")) {
                return true;
            }
            if (akkoord.equals("N")) {
                return false;
            }
        } while (true);
    }

    public String getInputFilter() {
        OutputText.showText("Specificeer filter : ");
        return input.nextLine();
    }

    public int getInputSortering() {
        while (true) {
            try {
                OutputText.showText("Hoe lager de sortering, hoe hoger in de lijst.\n");
                OutputText.showText("Sortering : ");
                String s = input.nextLine();
                int sortering = Integer.parseInt(s);
                return sortering;
            } catch (NumberFormatException ex) {

            }
        }
    }

    public BigDecimal getInputPrijs() {
        while (true) {
            try {
                OutputText.showText("Prijs : ");
                String s = input.nextLine();
                BigDecimal prijs = new BigDecimal(s);
                if (prijs.longValue() >= 0.0 && prijs.longValue() <= 9999.99) {
                    return prijs;
                } else {
                    OutputText.showError("De prijs moet liggen tussen 0 en 9999.99\n");
                }
            } catch (NumberFormatException ex) {

            }
        }
    }

    public int getInputPositiveInt(String text) {
        while (true) {
            try {
                OutputText.showText(text + " : ");
                String s = input.nextLine();
                int positiveInt = Integer.parseInt(s);

                if (positiveInt >= 0 && positiveInt < Integer.MAX_VALUE) {
                    return positiveInt;
                } else {
                    OutputText.showError("Specificeer een getal >= 0 en < " + Integer.MAX_VALUE + "\n");
                }
            } catch (NumberFormatException ex) {

            }
        }
    }

    public String getInputArtikelnaam() {
        while (true) {
            OutputText.showText("Artikelnaam : ");
            String s = input.nextLine();
            if (s.length() >= 1) {
                return s;
            } else {
                OutputText.showError("Artikelnaam invullen aub!");
            }

        }
    }

    public String getInputVoornaam() {
        while (true) {
            OutputText.showText("Voornaam : ");
            String s = input.nextLine();
            if (s.length() >= 1) {
                return s;
            } else {
                OutputText.showError("Voornaam invullen aub!");
            }
        }
    }

    public String getInputTussenvoegsel() {
        OutputText.showText("Tussenvoegsel : ");
        return input.nextLine();
    }

    public String getInputAchternaam() {
        while (true) {
            OutputText.showText("Achternaam : ");
            String s = input.nextLine();
            if (s.length() >= 1) {
                return s;
            } else {
                OutputText.showError("Achternaam invullen aub!");
            }
        }
    }

    public String getInputStraatnaam() {
        OutputText.showText("Straatnaam : ");
        return input.nextLine();
    }

    public int getInputHuisnummer() {
        while (true) {
            try {
                OutputText.showText("Huisnummer : ");
                String s = input.nextLine();
                int huisnummer = Integer.parseInt(s);
                return huisnummer;
            } catch (NumberFormatException ex) {
                OutputText.showError("Huisnummer moet een getal zijn.");
            }
        }
    }

    public String getInputToevoeging() {
        OutputText.showText("Toevoeging : ");
        return input.nextLine();
    }

    public String getInputPostcode() {
        while (true) {
            OutputText.showText("Postcode : ");
            String s = input.nextLine().toUpperCase();
            if (Validator.validPostcode(s)) {
                return s;
            } else {
                OutputText.showError("Geef een waarde als 9999XX");
            }
        }
    }

    public String getInputWoonplaats() {
        while (true) {
            OutputText.showText("Woonplaats : ");
            String s = input.nextLine();
            if (s.length() >= 1) {
                return s;
            } else {
                OutputText.showError("Woonplaats invullen aub!");
            }
        }

    }
}
