package nl.workshop1.controller;

import java.util.ArrayList;
import nl.workshop1.model.Adres;
import nl.workshop1.model.AdresType;
import nl.workshop1.menu.AdresChangeMenu;
import nl.workshop1.menu.KlantChangeMenu;
import nl.workshop1.model.Klant;
import nl.workshop1.view.AdresChangeMenuView;
import nl.workshop1.view.KlantChangeMenuView;

/**
 *
 * @author FeniksBV
 */
public class KlantChangeMenuController extends MenuController {

    private KlantChangeMenu klantChangeMenu;
    private ArrayList<Adres> InitialAdresList = new ArrayList<>();
    private KlantChangeMenuView klantChangeMenuView;
    private int klantMode;

    public KlantChangeMenuController(int mode, KlantChangeMenu klantChangeMenu, KlantChangeMenuView klantChangeMenuView) {
        this.klantChangeMenu = klantChangeMenu;
        this.klantChangeMenuView = klantChangeMenuView;
        this.klantMode = mode;
        for (int i = 0; i < klantChangeMenu.getKlant().getAdresList().size(); i++) {
            Adres adres = (Adres) klantChangeMenu.getKlant().getAdresList().get(i).clone();
            InitialAdresList.add(adres);
        }
    }

    @Override
    public void buildOptionsMenu() {
        klantChangeMenu.clearSubMenu();
        if (klantMode == MODE_NIEUW) {
            // New account may change the userName
            klantChangeMenu.addSubMenu(formatText("Email") + klantChangeMenu.getKlant().getEmail(), "1");
        } else {
            // Modifying an account, username is not allowed to change
            klantChangeMenu.addSubMenu(formatText("Email") + klantChangeMenu.getKlant().getEmail(), "99");
        }
        klantChangeMenu.addSubMenu(formatText("Voornaam") + klantChangeMenu.getKlant().getVoornaam(), "2");
        klantChangeMenu.addSubMenu(formatText("Tussenvoegsel") + klantChangeMenu.getKlant().getTussenvoegsel(), "3");
        klantChangeMenu.addSubMenu(formatText("Achternaam") + klantChangeMenu.getKlant().getAchternaam(), "4");
        klantChangeMenu.addSubMenu(formatText(AdresType.ADRES_POST.getDescription()) + displayAdres(AdresType.ADRES_POST), "5");
        klantChangeMenu.addSubMenu(formatText(AdresType.ADRES_FACTUUR.getDescription()) + displayAdres(AdresType.ADRES_FACTUUR), "6");
        klantChangeMenu.addSubMenu(formatText(AdresType.ADRES_BEZORG.getDescription()) + displayAdres(AdresType.ADRES_BEZORG), "7");
        klantChangeMenu.addSubMenu(formatText("Sortering") + klantChangeMenu.getKlant().getSortering(), "8");
        klantChangeMenu.addSubMenu("Opslaan", "9");
    }

    @Override
    public void handleMenu() {
        // Initially ask for input all datafields
        if (klantMode == MODE_NIEUW) {
            klantChangeMenu.getKlant().setEmail(klantChangeMenuView.getInputUsername());
            klantChangeMenu.getKlant().setVoornaam(klantChangeMenuView.getInputVoornaam());
            klantChangeMenu.getKlant().setTussenvoegsel(klantChangeMenuView.getInputTussenvoegsel());
            klantChangeMenu.getKlant().setAchternaam(klantChangeMenuView.getInputAchternaam());
            klantChangeMenu.getKlant().setSortering(klantChangeMenuView.getInputSortering());

            // Postadres is verplicht, dus ook uitvragen
            AdresChangeMenuView adresChangeMenuView = new AdresChangeMenuView();
            Adres postAdres = new Adres();
            postAdres.setAdresType(AdresType.ADRES_POST);
            postAdres.setStraatNaam(adresChangeMenuView.getInputStraatnaam());
            postAdres.setHuisNummer(adresChangeMenuView.getInputHuisnummer());
            postAdres.setToevoeging(adresChangeMenuView.getInputToevoeging());
            postAdres.setPostcode(adresChangeMenuView.getInputPostcode());
            postAdres.setWoonplaats(adresChangeMenuView.getInputWoonplaats());

            klantChangeMenu.getKlant().addAdresList(postAdres);
        }
        while (true) {
            buildOptionsMenu();
            klantChangeMenu.drawMenu();
            switch (klantChangeMenu.userChoice()) {
                case "0":
                    return;
                case "1":
                    klantChangeMenu.getKlant().setEmail(klantChangeMenuView.getInputUsername());
                    break;
                case "2":
                    klantChangeMenu.getKlant().setVoornaam(klantChangeMenuView.getInputVoornaam());
                    break;
                case "3":
                    klantChangeMenu.getKlant().setTussenvoegsel(klantChangeMenuView.getInputTussenvoegsel());
                    break;
                case "4":
                    klantChangeMenu.getKlant().setAchternaam(klantChangeMenuView.getInputAchternaam());
                    break;
                case "5":
                    // Postadres
                    adresChange(AdresType.ADRES_POST);
                    break;
                case "6":
                    // factuuradres
                    adresChange(AdresType.ADRES_FACTUUR);
                    break;
                case "7":
                    // bezorgadres
                    adresChange(AdresType.ADRES_BEZORG);
                    break;
                case "8":
                    klantChangeMenu.getKlant().setSortering(klantChangeMenuView.getInputSortering());
                    break;
                case "9":
                    Klant klant = klantChangeMenu.getKlant();
                    if (klantMode == MODE_NIEUW) {
                        KlantDAOController.insertKlant(klant);
                        for (int i = 0; i < klant.getAdresList().size(); i++) {
                            Adres adres = klant.getAdresList().get(i);
                            AdresDAOController.insertAdres(adres);
                        }
                    } else {
                        KlantDAOController.updateKlant(klant);

                        // En nu de adressen correct afhandelen
                        adressenAfhandelen(InitialAdresList, klant, AdresType.ADRES_POST);
                        adressenAfhandelen(InitialAdresList, klant, AdresType.ADRES_FACTUUR);
                        adressenAfhandelen(InitialAdresList, klant, AdresType.ADRES_BEZORG);
                    }

                    return;
                case "99":
                    klantChangeMenu.getMenuView().showMessage("\nWijzigen email is NIET toegestaan!");
                    break;
            }
        }
    }

    protected void adressenAfhandelen(ArrayList<Adres> initial, Klant huidigeKlant, AdresType adresType) {
        // Als wel in initial, maar niet in huidige, dan delete de initial
        // Als in beide, dan update de huidige
        // Als niet in initial en wel in huidige, dan insert de huidige
        Adres initAdres = getAdres(adresType, initial);
        if (initAdres == null) {
            Adres huidigAdres = huidigeKlant.getAdres(adresType);
            if (huidigAdres != null) {
                // insert het huidige
                AdresDAOController.insertAdres(huidigAdres);
            }
        } else {
            Adres huidigAdres = huidigeKlant.getAdres(adresType);
            if (huidigAdres == null) {
                // delete het initial
                AdresDAOController.deleteAdres(initAdres.getId());
            } else {
                // update huidig
                AdresDAOController.updateAdres(huidigAdres);
            }
        }
    }

    protected Adres getAdres(AdresType adresType, ArrayList<Adres> adres) {
        for (int i = 0; i < adres.size(); i++) {
            if (adres.get(i).getAdresType().equals(adresType)) {
                return adres.get(i);
            }
        }
        return null;
    }

    public void adresChange(AdresType adresType) {
        AdresChangeMenuView adresChangeMenuView = new AdresChangeMenuView();
        AdresChangeMenu adresChangeMenu = new AdresChangeMenu(adresType.getDescription() + " toevoegen/wijzigen");
        AdresChangeMenuController adresCtrl = new AdresChangeMenuController(adresType,
                klantChangeMenu.getKlant().getAdresList(), adresChangeMenu, adresChangeMenuView);
        adresCtrl.runController();

        // Ieder adres moet wel bij de huidige klant behoren!
        for (int i = 0; i < klantChangeMenu.getKlant().getAdresList().size(); i++) {
            klantChangeMenu.getKlant().getAdresList().get(i).setKlantId(
                    klantChangeMenu.getKlant().getId());
        }
    }

    public String displayAdres(AdresType adresType) {
        Adres adres = klantChangeMenu.getKlant().getAdres(adresType);
        if (adres != null) {
            return adres.display();
        }
        return "";
    }
}
