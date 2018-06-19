package nl.workshop1.menu;

import nl.workshop1.model.Klant;

/**
 *
 * @author FeniksBV
 */
public class KlantMenu extends Menu {

    private String filter = "";

    public KlantMenu() {
        super("Klanten");
        setRecordHeader( String.format("     %-20s%-30s%-30s%-10s", "Achternaam","Volledige naam","Email","Sortering"));
    }

    public String display(Klant klant) {
        return String.format("%-20s%-30s%-30s%-5d", 
                klant.getAchternaam(), klant.getFullName(), klant.getEmail(),klant.getSortering() );
    }

}
