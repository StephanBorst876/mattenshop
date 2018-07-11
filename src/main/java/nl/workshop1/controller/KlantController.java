package nl.workshop1.controller;

import java.util.ArrayList;
import nl.workshop1.DAO.DAOFactory;
import nl.workshop1.model.Klant;
import nl.workshop1.model.Adres;
import nl.workshop1.model.AdresType;
import nl.workshop1.view.KlantView;
import nl.workshop1.view.Menu;
import nl.workshop1.view.OutputText;

/**
 *
 * @author FeniksBV
 */
public class KlantController extends Controller {

    private KlantView klantView;
    private Klant klant = null;
    private ArrayList<Adres> initialAdresList = new ArrayList<>();
    private ArrayList<Adres> adresList = new ArrayList<>();

    public KlantController(Klant klant, ArrayList<Adres> adresList) {
        String titel;
        if (klant == null) {
            titel = "Klant toevoegen";
        } else {
            titel = "Klant wijzigen";
            this.klant = klant;
            this.adresList = adresList;
            for (int i = 0; i < adresList.size(); i++) {
                initialAdresList.add((Adres) adresList.get(i).clone());
            }
        }
        klantView = new KlantView(new Menu(titel), klant, adresList);
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
                    // Bovenstaande 3 opties kunnen we in een enkele afhandelen
                    // Een adres invoeren of wijzigen gaat via de AdresController.
                    adresList = klantView.getAdresList();
                    Adres adres = getAdresFromList(klantView.getAdresType(), adresList);
                    AdresController adresCtrl = new AdresController(klantView.getAdresType(), adres);
                    adresCtrl.runController();
                    switch (adresCtrl.getRequestedAction()) {
                        case "6":
                        // Opslaan: Adres toekennen aan list
                        // Beide actions 6 en 7 kunnen we gelijktijdig afhandelen
                        case "7":
                            // Adres verwijderen -> verwijder adresType uit bestaande lijst
                            Adres newAdres = adresCtrl.getAdres();
                            updateAdresList(klantView.getAdresType(), newAdres);
                            klantView.setAdresList(adresList);
                            break;
                    }
                    break;
                case "9":
                    // update / insert
                    Boolean klantAangepast = true;
                    adresList = klantView.getAdresList();
                    if (klant == null) {
                        Klant klant = DAOFactory.getKlantDAO().readKlantWithEmail(klantView.getKlant().getEmail());
                        if (klant == null) {
                            DAOFactory.getKlantDAO().insertKlant(klantView.getKlant());
                        } else {
                            OutputText.showError("Een klant met dit email bestaat al! Toevoegen niet toegestaan.");
                            klantAangepast = false;
                        }
                    } else {
                        DAOFactory.getKlantDAO().updateKlant(klantView.getKlant());
                    }

                    if (klantAangepast) {
                        // Adressen bijwerken
                        AdresBijwerkenInDB(AdresType.Postadres, adresList);
                        AdresBijwerkenInDB(AdresType.Factuuradres, adresList);
                        AdresBijwerkenInDB(AdresType.Bezorgadres, adresList);
                        return;
                    }

            }
        }
    }

    protected void updateAdresList(AdresType adresType, Adres newAdres) {
        // newAdres kan null zijn, dan request = verwijderen
        // Indien newAdres != null, dan overschrijven als in bestaande lijst
        // Indien newAdres != null en niet gevonden in huidige lijst, dan toevoegen
        boolean overschrevenInLijst = false;
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

    protected void AdresBijwerkenInDB(AdresType adresType, ArrayList<Adres> adresList) {
        // Als wel in initial, maar niet in huidige, dan delete de initial
        // Als in beide, dan update de huidige
        // Als niet in initial en wel in huidige, dan insert de huidige
        Adres initAdres = getAdresFromList(adresType, initialAdresList);
        Adres newAdres = getAdresFromList(adresType, adresList);
        if (initAdres == null) {
            if (newAdres != null) {
                // insert het nieuwe adres
                // Zorg ervoor dat dit adres bij deze kant hoort.
                newAdres.setKlantId(klantView.getKlant().getId());
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

    protected Adres getAdresFromList(AdresType adresType, ArrayList<Adres> adresList) {
        for (Adres adres : adresList) {
            if (adres.getAdresType().equals(adresType)) {
                return adres;
            }
        }
        return null;
    }

}
