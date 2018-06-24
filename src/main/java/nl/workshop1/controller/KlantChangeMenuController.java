package nl.workshop1.controller;

import java.util.ArrayList;
import nl.workshop1.DAO.DAOFactory;
import nl.workshop1.model.Klant;
import nl.workshop1.menu.KlantChangeMenu;
import nl.workshop1.model.Adres;
import nl.workshop1.model.AdresType;
import nl.workshop1.utility.Misc;
import nl.workshop1.view.KlantView;

/**
 *
 * @author FeniksBV
 */
public class KlantChangeMenuController extends MenuController {

    private KlantView klantView;
    private Klant initialKlant = null;

    public KlantChangeMenuController() {
        // Een nieuwe klant
        klantView = new KlantView(MODE_NIEUW, new KlantChangeMenu("Klant toevoegen"));
    }

    public KlantChangeMenuController(Klant klant) {
        // bestaand account
        initialKlant = klant;

        klantView = new KlantView(MODE_WIJZIG, new KlantChangeMenu("Klant wijzigen"), klant);
    }

    @Override
    public void runController() {
        while (true) {
            requestedAction = klantView.runViewer();
            switch (requestedAction) {
                case "0":
                    return;
                case "4":
                    // Zoek naar klantgegevens
                    break;
                case "5":
                // Postadres
                case "6":
                // factuuradres
                case "7":
                    // bezorgadres
                    // Bovenstaande kunnen we in een enkele afhandelen
                    adresChange(klantView.getAdresType());
                    break;
                case "9":
                    // update / insert
                    if (initialKlant == null) {
                        DAOFactory.getKlantDAO().insertKlant(klantView.getKlant());
                    } else {
                        DAOFactory.getKlantDAO().updateKlant(klantView.getKlant());
                    }
                    // Adressen bijwerken
                    uitvoerenDbActie(AdresType.ADRES_POST, klantView.getKlant().getAdresList());
                    return;

            }
        }
    }

    protected void adresChange(AdresType adresType) {
        Adres adres = Misc.getAdresTypeFromList(adresType, klantView.getKlant().getAdresList());
        AdresChangeMenuController adresCtrl = new AdresChangeMenuController(adresType, adres);
        adresCtrl.runController();
        switch (adresCtrl.getRequestedAction()) {
            case "6":
            // Opslaan: Adres toekennen aan list
            // Beide actions kunnen we gelijktijdig afhandelen
            case "7":
                // Adres verwijderen -> verwijder adresType uit bestaande lijst
                Adres newAdres = adresCtrl.getAdres();
                updateKlantAdresList(adresType, newAdres);
                break;
        }

        // Ieder adres moet wel bij de huidige klant behoren!
        for (int i = 0; i < klantView.getKlant().getAdresList().size(); i++) {
            klantView.getKlant().getAdresList().get(i).setKlantId(klantView.getKlant().getId());
        }
    }

    protected void updateKlantAdresList(AdresType adresType, Adres newAdres) {
        // newAdres kan null zijn, dan request = verwijderen
        // Indien newAdres != null, dan overschrijven als in bestaande lijst
        // Indien newAdres != null en niet gevonden in huidige lijst, dan toevoegen
        boolean overschrevenInLijst = false;
        ArrayList<Adres> adresList = klantView.getKlant().getAdresList();
        for (int i = 0; i < adresList.size(); i++) {
            if (adresList.get(i).getAdresType().equals(adresType)) {
                if (newAdres == null) {
                    adresList.remove(i);
                } else {
                    adresList.set(i, newAdres);
                    overschrevenInLijst = true;
                }
            }
        }

        if (newAdres != null && !overschrevenInLijst) {
            adresList.add(newAdres);
        }

    }

    protected void uitvoerenDbActie(AdresType adresType, ArrayList<Adres> adresList) {
        // Als wel in initial, maar niet in huidige, dan delete de initial
        // Als in beide, dan update de huidige
        // Als niet in initial en wel in huidige, dan insert de huidige
        Adres initAdres = Misc.getAdresTypeFromList(adresType, initialKlant.getAdresList());
        Adres newAdres = Misc.getAdresTypeFromList(adresType, adresList);
        if (initAdres == null) {
            if (newAdres != null) {
                // insert het huidige
                DAOFactory.getAdresDAO().insertAdres(newAdres);
            }
        } else {
            if (newAdres == null) {
                // delete het initial
                DAOFactory.getAdresDAO().deleteAdres(initAdres.getId());
            } else {
                // update huidig
                DAOFactory.getAdresDAO().updateAdres(newAdres);
            }
        }
    }
}
