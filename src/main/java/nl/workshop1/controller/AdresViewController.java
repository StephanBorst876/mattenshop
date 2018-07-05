package nl.workshop1.controller;

import nl.workshop1.model.Adres;
import nl.workshop1.model.AdresType;
import nl.workshop1.view.AdresView;
import nl.workshop1.view.Menu;

/**
 *
 * @author FeniksBV
 */
public class AdresViewController extends Controller {

    private AdresView adresView;
    private Adres newAdres = null;

    public AdresViewController(AdresType adresType, Adres adres) {
        String titel;
        if (adres == null) {
            titel = adresType.getDescription() + " toevoegen";
        } else {
            titel = adresType.getDescription() + " wijzigen";
            newAdres = (Adres) adres.clone();
        }
        adresView = new AdresView(adresType, new Menu(titel), newAdres);
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
