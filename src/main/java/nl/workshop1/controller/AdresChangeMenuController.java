package nl.workshop1.controller;

import nl.workshop1.model.Adres;
import nl.workshop1.model.AdresType;
import nl.workshop1.menu.AdresChangeMenu;
import nl.workshop1.view.AdresView;

/**
 *
 * @author FeniksBV
 */
public class AdresChangeMenuController extends MenuController {

    private AdresView adresView;
    private Adres newAdres = null;

    public AdresChangeMenuController(AdresType adresType, Adres adres) {
        if (adres == null) {
            adresView = new AdresView(MODE_NIEUW, adresType, new AdresChangeMenu(adresType.getDescription() + " toevoegen"));
        } else {
            adresView = new AdresView(MODE_WIJZIG, adresType, new AdresChangeMenu(adresType.getDescription() + " wijzigen"), adres);
        }
    }

    public Adres getAdres() {
        return newAdres;
    }

    @Override
    public void runController() {

        while (true) {
            requestedAction = adresView.runViewer();
            switch (requestedAction) {
                case "0":
                    return;
                case "6":
                    // Opslaan
                    newAdres = adresView.getAdres();
                    return;
                case "7":
                    // Verwijderen
                    newAdres = null;
                    return;
            }
        }
    }

    
}
