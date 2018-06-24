package nl.workshop1.view;

import java.util.ArrayList;
import nl.workshop1.controller.MenuController;
import nl.workshop1.menu.KlantChangeMenu;
import nl.workshop1.model.Adres;
import nl.workshop1.model.AdresType;
import nl.workshop1.model.Klant;
import nl.workshop1.utility.Misc;

/**
 *
 * @author FeniksBV
 */
public class KlantView extends MenuView {

    private int klantMode;
    private AdresType adresType = null;
    private KlantChangeMenu klantChangeMenu;
    private Klant klant;

    public KlantView(int mode, KlantChangeMenu klantChangeMenu) {
        super(klantChangeMenu);
        this.klantMode = mode;
        this.klantChangeMenu = klantChangeMenu;
        klant = new Klant();
    }

    public KlantView(int mode, KlantChangeMenu klantChangeMenu, Klant klant) {
        super(klantChangeMenu);
        this.klantMode = mode;
        this.klantChangeMenu = klantChangeMenu;
        this.klant = (Klant) klant.clone();
    }

    public Klant getKlant() {
        return klant;
    }

    public AdresType getAdresType() {
        return adresType;
    }

    @Override
    public String runViewer() {

        // Initially ask for input all datafields
        if (klantMode == MenuController.MODE_NIEUW) {
            if (klant.getAchternaam().equals("")) {
                // Nu weet je zeker dat dit de eerste keer is
                klant.setEmail(getInputUsername(false));
                klant.setVoornaam(getInputVoornaam());
                klant.setTussenvoegsel(getInputTussenvoegsel());
                klant.setAchternaam(getInputAchternaam());
                klant.setSortering(getInputSortering());

                // Postadres is verplicht, dus ook uitvragen
                AdresView adresView = new AdresView(klantMode, AdresType.ADRES_POST, null);
                adresView.runViewer();

                ArrayList<Adres> adresList = new ArrayList<>();
                adresList.add(adresView.getAdres());

                klant.setAdresList(adresList);
            }
        }

        while (true) {
            buildSubMenuList();
            drawMenu();
            String requestedAction = userChoice();
            switch (requestedAction) {
                case "0":
                    return requestedAction;
                case "1":
                    klant.setEmail(getInputUsername(false));
                    break;
                case "2":
                    klant.setVoornaam(getInputVoornaam());
                    break;
                case "3":
                    klant.setTussenvoegsel(getInputTussenvoegsel());
                    break;
                case "4":
                    klant.setAchternaam(getInputAchternaam());
                    break;
                case "5":
                    // Postadres
                    adresType = AdresType.ADRES_POST;
                    return requestedAction;
                case "6":
                    // factuuradres
                    adresType = AdresType.ADRES_FACTUUR;
                    return requestedAction;
                case "7":
                    // bezorgadres
                    adresType = AdresType.ADRES_BEZORG;
                    return requestedAction;
                case "8":
                    // Sortering
                    klant.setSortering(getInputSortering());
                    break;
                case "9":
                // Opslaan

                case "99":
//                klantChangeMenu.getMenuView().showMessage("\nWijzigen email is NIET toegestaan!");
                    break;
            }
        }

    }

    protected void buildSubMenuList() {
        klantChangeMenu.clearSubMenuList();
        if (klantMode == MenuController.MODE_NIEUW) {
            // New account may change the userName
            klantChangeMenu.addSubMenu("Email", klant.getEmail(), "1");
        } else {
            // Modifying an account, username is not allowed to change
            klantChangeMenu.addSubMenu("Email", klant.getEmail(), "99");
        }
        klantChangeMenu.addSubMenu("Voornaam", klant.getVoornaam(), "2");
        klantChangeMenu.addSubMenu("Tussenvoegsel", klant.getTussenvoegsel(), "3");
        klantChangeMenu.addSubMenu("Achternaam", klant.getAchternaam(), "4");
        klantChangeMenu.addSubMenu(AdresType.ADRES_POST.getDescription(), displayAdres(AdresType.ADRES_POST), "5");
        klantChangeMenu.addSubMenu(AdresType.ADRES_FACTUUR.getDescription(), displayAdres(AdresType.ADRES_FACTUUR), "6");
        klantChangeMenu.addSubMenu(AdresType.ADRES_BEZORG.getDescription(), displayAdres(AdresType.ADRES_BEZORG), "7");
        klantChangeMenu.addSubMenu("Sortering", String.valueOf(klant.getSortering()), "8");
        klantChangeMenu.addSubMenu("Opslaan", "9");
    }

    protected String displayAdres(AdresType adresType) {
        Adres adres = Misc.getAdresTypeFromList(adresType, klant.getAdresList());
        if (adres != null) {
            return displayRecord(adres);
        }
        return "";
    }

}
