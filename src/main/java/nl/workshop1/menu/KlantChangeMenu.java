package nl.workshop1.menu;

import nl.workshop1.model.Klant;

/**
 *
 * @author FeniksBV
 */
public class KlantChangeMenu extends Menu {

    private Klant klant;

    public KlantChangeMenu(String title, Klant initialKlant) {
        super(title);
        this.klant = initialKlant;
    }

    public Klant getKlant() {
        return klant;
    }

}
