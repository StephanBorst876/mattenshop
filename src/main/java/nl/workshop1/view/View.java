package nl.workshop1.view;

import java.util.ArrayList;
import nl.workshop1.model.Account;
import nl.workshop1.model.Adres;
import nl.workshop1.model.Artikel;
import nl.workshop1.model.BestelRegel;
import nl.workshop1.model.Bestelling;
import nl.workshop1.model.Klant;

/**
 *
 * @author FeniksBV
 */
public abstract class View extends UserInput {

    private static final int NUMBER_RECORDS_TO_DISPLAY = 26;

    private Menu menu;
    protected ArrayList<Character> validOptions;

    public View(Menu menu) {
        this.validOptions = new ArrayList<>();
        this.menu = menu;
    }

    public abstract String runViewer();

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    /**
     * Draw header and submenu-itens on the console.
     */
    public void drawMenu() {

        validOptions.clear();

        if (menu.getTitle() != null) {
            if (menu.getLoginName().isEmpty()) {
                OutputText.showTitle(menu.getTitle());
            } else {
                OutputText.showTitle(menu.getTitle() + " - " + menu.getLoginName());
            }
        }

        if (menu.getBestelKlant() != null) {
            // Alleen wanneer we in bestellingen zitten
            OutputText.showMessage("     Huidige klant      : " + menu.getBestelKlant().getFullName());
        }
        if (menu.getBestelling() != null) {
            OutputText.showMessage("     Huidige bestelling : " + menu.getBestelling().getBestelDatum());
        }

        if (menu.isRecordSelected()) {
            // display the header
            OutputText.showRecordHeader(formatTextHeader(menu.getSelectedObject()));
            OutputText.showMessage("  -> " + displayRecord(menu.getSelectedObject()));
            OutputText.showMessage("");
        } else {
            for (int i = 0; i < menu.getRecordList().size(); i++) {
                if (i == 0) {
                    // display the header
                    OutputText.showRecordHeader(formatTextHeader(menu.getRecordList().get(0)));
                }
                if (i < NUMBER_RECORDS_TO_DISPLAY) {
                    OutputText.showMessage(generateChoice('A', i) + displayRecord(menu.getRecordList().get(i)));
                } else {
                    int remainingRecords = menu.getRecordList().size() - NUMBER_RECORDS_TO_DISPLAY;
                    if (remainingRecords > 0) {
                        OutputText.showMessage("..nog extra regels (" + remainingRecords + ")");
                    }
                    break;
                }

            }
            // Add an empty line after shown records
            if ((menu.getRecordList().size() > 0)
                    || (menu.getInfoLine().length() > 0)) {
                OutputText.showMessage("");
            }
        }

        // Is er nog een extra infoLine
        if (menu.getInfoLine().length() > 0) {
            OutputText.showMessage("  " + menu.getInfoLine());
        }

        // Show all submenu
        for (int i = 0; i < menu.getSubMenuList().size(); i++) {
            // Optie 0 uit de submenus wordt apart afgehandeld
            if (menu.getActionList().get(i).equals("0")) {
                OutputText.showMessage(generateChoice('0', 0) + menu.getSubMenuList().get(i));
            } else {
                OutputText.showMessage(generateChoice('1', i) + menu.getSubMenuList().get(i));
            }
        }

    }

    protected String formatTextHeader(Object obj) {
        if (obj instanceof Account) {
            return String.format("     %-30s%-15s", "Gebruikersnaam", "Role");
        } else if (obj instanceof Artikel) {
            return String.format("     %-30s%10s%10s%10s%10s", "Artikelnaam", "Prijs/st",
                    "Voorraad", "Gereserv.", "Sortering");
        } else if (obj instanceof Klant) {
            return String.format("     %-20s%-30s%-30s%-10s", "Achternaam", "Volledige naam", "Email", "Sortering");
        } else if (obj instanceof Bestelling) {
            return String.format("     %-11s%-30s%10s %-30s", "Referentie", "Besteldatum", "Totaal", "Bestelstatus");
        } else if (obj instanceof BestelRegel) {
            return String.format("     %-30s%10s%10s", "Artikelnaam", "Aantal", "Prijs/st");
        }
        return "";
    }

    protected String displayRecord(Object obj) {
        if (obj instanceof Account) {
            Account account = (Account) obj;
            return String.format("%-30s%-15s", account.getUserName(), account.getRole().getDescription());
        } else if (obj instanceof Klant) {
            Klant klant = (Klant) obj;
            return String.format("%-20s%-30s%-30s%-5d", klant.getAchternaam(), klant.getFullName(),
                    klant.getEmail(), klant.getSortering());
        } else if (obj instanceof Artikel) {
            Artikel artikel = (Artikel) obj;
            return String.format("%-30s%10s%10d%10d%10d", artikel.getNaam(), currencyDisplay(artikel.getPrijs()),
                    artikel.getVoorraad(), artikel.getGereserveerd(), artikel.getSortering());
        } else if (obj instanceof Adres) {
            Adres adres = (Adres) obj;
            return String.format("%s %s %d %s %s", adres.getPostcode(), adres.getStraatNaam(),
                    adres.getHuisNummer(), adres.getToevoeging(), adres.getWoonplaats());
        } else if (obj instanceof Bestelling) {
            Bestelling bestelling = (Bestelling) obj;
            return String.format("%-11d%-30s%10s %-30s", bestelling.getId(),
                    bestelling.getBestelDatum(),
                    currencyDisplay(bestelling.getTotaalprijs()),
                    bestelling.getBestelstatus());
        } else if (obj instanceof BestelRegel) {
            BestelRegel bestelRegel = (BestelRegel) obj;
            return String.format("%-30s%10d%10s", bestelRegel.getArtikelNaam(),
                    bestelRegel.getAantal(),
                    currencyDisplay(bestelRegel.getPrijs()));
        }
        return "";
    }

    public static String currencyDisplay(Object obj) {
        return String.format("€%8.2f", obj);

//        return NumberFormat.getCurrencyInstance().format(obj);
    }

    /**
     * Maakt een geformateerde keuze optie
     *
     * @param offset '1' of 'A'
     * @param index oplopend van 0..1..2 etc
     * @return bijv: | 1. | of | A. |
     */
    protected String generateChoice(char offset, int index) {
        StringBuilder s = new StringBuilder();
        char option = (char) (offset + index);
        validOptions.add(option);
        return s.append("  ").append(option).append(". ").toString();
    }

    /**
     * Keeps asking the user for entering a key, until a valid key is pressed.
     *
     * @return : the selected key as a String
     */
    public String userChoice() {

        while (true) {
            String choice = getInputChoice();
            if (choice.length() == 1) {
                String value = validChoice(choice);
                if (value != null) {
                    return value;
                }
            }
        }
    }

    /**
     * Test of gebruikerskeuze wel valt in de range van mogelijke optie binnen
     * het submenu of de recordlist
     *
     * @param choice De ingegeven keuze
     * @return De keuze indien correct bevonden.
     */
    protected String validChoice(String choice) {
        // Indien in de range A-Z
        if (choice.charAt(0) >= 'A' && choice.charAt(0) <= 'Z') {
            if (!menu.isRecordSelected()) {
                int selected = (int) (choice.charAt(0) - 'A');
                if (selected <= menu.getRecordList().size()) {
                    return choice;
                }
            }
        }

        if (choice.charAt(0) == '0') {
            return "0";
        }

        // Indien in de range 1-9
        if (choice.charAt(0) >= '1' && choice.charAt(0) <= '9') {
            int index = Integer.valueOf(choice);
            // Is de gekozen optie aanwezig in het menu?
            for (int i = 0; i < validOptions.size(); i++) {
                if (choice.charAt(0) == validOptions.get(i)) {
                    return menu.getActionList().get(index - 1);
                }
            }
        }
        return null;
    }

}
