package nl.workshop1.view;

import nl.workshop1.menu.Menu;
import nl.workshop1.model.Account;
import nl.workshop1.model.Adres;
import nl.workshop1.model.Artikel;
import nl.workshop1.model.Klant;

/**
 *
 * @author FeniksBV
 */
public abstract class MenuView extends UserInput {

    private static final int NUMBER_RECORDS_TO_DISPLAY = 5;

    private Menu menu;

    public <E extends Menu> MenuView(E menu) {
        this.menu = menu;
    }

    public abstract String runViewer();

    public <E extends Menu> void setMenu(E menu) {
        this.menu = menu;
    }

    /**
     * Draw header and submenu-itens on the console.
     */
    public void drawMenu() {

        if (menu.getTitle() != null) {
            if (menu.getLoginName().equals("")) {
                OutputText.showTitle(menu.getTitle());
            } else {
                OutputText.showTitle(menu.getTitle() + " - " + menu.getLoginName());
            }
        }

        if (menu.isRecordSelected()) {
            OutputText.showMessage(" -> " + displayRecord(menu.getRecordList().get(menu.getRecordSelectedIndex())));
            OutputText.showMessage("");
        } else {
            for (int i = 0; i < menu.getRecordList().size(); i++) {
                if (i == 0) {
                    // First display the header
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
            if (menu.getRecordList().size() > 0) {
                OutputText.showMessage("");
            }
        }

        // Show all submenu
        for (int i = 0; i < menu.getSubMenuList().size(); i++) {
            OutputText.showMessage(generateChoice('1', i) + menu.getSubMenuList().get(i));
        }
        if (menu.getSubMenuList().size() > 0) {
            OutputText.showMessage(generateChoice('0', 0) + "Terug");
        }

    }

    protected String formatTextHeader(Object obj) {
        if (obj instanceof Account) {
            return String.format("     %-30s%-15s", "Gebruikersnaam", "Role");
        } else if (obj instanceof Artikel) {
            return String.format("     %-30s%10s%10s%10s%10s", "Artikelnaam", "Prijs",
                    "Voorraad", "Gereserv.", "Sortering");
        } else if (obj instanceof Klant) {
            return String.format("     %-20s%-30s%-30s%-10s", "Achternaam", "Volledige naam", "Email", "Sortering");
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
            return String.format("%-30s%10.2f%10d%10d%10d", artikel.getNaam(), artikel.getPrijs(),
                    artikel.getVoorraad(), artikel.getGereserveerd(), artikel.getSortering());
        } else if (obj instanceof Adres) {
            Adres adres = (Adres) obj;
            return String.format("%s %s %d %s %s", adres.getPostcode(), adres.getStraatNaam(),
                    adres.getHuisNummer(), adres.getToevoeging(), adres.getWoonplaats());
        }
        return "";
    }

    /**
     *  Maakt een geformateerde keuze optie
     * @param offset    '1' of 'A'
     * @param index     oplopend van 0..1..2 etc
     * @return          bijv: |  1. | of |  A. |
     */
    protected String generateChoice(char offset, int index) {
        StringBuilder s = new StringBuilder();
        return s.append("  ").append((char) (offset + index)).append(". ").toString();
    }

    /**
     * Keeps asking the user for entering a key, until a valid key is pressed.
     *
     * @return : the selected key as a String
     */
    public String userChoice() {

        while (true) {
            String choice = getInputChoice();
            if (choice.length() > 0) {
                String value = validChoice(choice);
                if (value != null) {
                    return value;
                }
            }
        }
    }

    /**
     * Test of gebruikerskeuze wel valt in de range van mogelijke optie
     * binnen het submenu of de recordlist
     * @param choice    De ingegeven keuze
     * @return          De keuze indien correct bevonden.
     */
    protected String validChoice(String choice) {
        // Indien in de range A-Z
        if (choice.charAt(0) >= 'A' && choice.charAt(0) <= 'Z') {
            if (!menu.isRecordSelected()) {
                int selected = (int) (choice.charAt(0) - 'A');
                if (selected < menu.getRecordList().size()) {
                    return choice;
                }
            }
            return null;
        }

        if (choice.charAt(0) == '0') {
            return "0";
        }

        // Indien in de range 1-9
        if (choice.charAt(0) >= '1' && choice.charAt(0) <= '9') {
            int index = Integer.valueOf(choice);
            // Is de gekozen optie aanwezig in het menu?
            if (index <= menu.getActionList().size()) {
                return menu.getActionList().get(index - 1);
            }
        }

        return null;
    }

    public Menu getMenu() {
        return menu;
    }

}
