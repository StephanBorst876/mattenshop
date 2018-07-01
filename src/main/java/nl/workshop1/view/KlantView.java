package nl.workshop1.view;

import java.util.ArrayList;
import nl.workshop1.controller.Controller;
import nl.workshop1.model.Adres;
import nl.workshop1.model.AdresType;
import nl.workshop1.model.Klant;

/**
 *
 * @author FeniksBV
 */
public class KlantView extends View {

    private int klantMode;
    private AdresType adresType = null;
    private Menu klantChangeMenu;
    private Klant klant;
    private ArrayList<Adres> adresList = null;

    public KlantView(int mode, Menu klantChangeMenu) {
        super(klantChangeMenu);
        this.klantMode = mode;
        this.klantChangeMenu = klantChangeMenu;
        klant = new Klant();
        adresList = new ArrayList<>();
    }

    public KlantView(int mode, Menu klantChangeMenu, Klant klant, ArrayList<Adres> adresList) {
        super(klantChangeMenu);
        this.klantMode = mode;
        this.klantChangeMenu = klantChangeMenu;
        this.klant = (Klant) klant.clone();
        this.adresList = adresList;
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
        if (klantMode == Controller.MODE_NIEUW) {
            if (klant.getAchternaam().isEmpty()) {
                // Nu weet je zeker dat dit de eerste keer is
                klant.setEmail(getInputUsername(false));
                klant.setVoornaam(getInputVoornaam());
                klant.setTussenvoegsel(getInputTussenvoegsel());
                klant.setAchternaam(getInputAchternaam());
                klant.setSortering(getInputSortering());

                // Postadres is verplicht, dus ook uitvragen
                AdresView adresView = new AdresView(klantMode, AdresType.Postadres, null);
                adresView.runViewer();

                adresList.add(adresView.getAdres());
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
                    adresType = AdresType.Postadres;
                    return requestedAction;
                case "6":
                    // factuuradres
                    adresType = AdresType.Factuuradres;
                    return requestedAction;
                case "7":
                    // bezorgadres
                    adresType = AdresType.Bezorgadres;
                    return requestedAction;
                case "8":
                    // Sortering
                    klant.setSortering(getInputSortering());
                    break;
                case "9":
                    // Opslaan
                    return requestedAction;
            }
        }

    }

    protected void buildSubMenuList() {
        klantChangeMenu.clearSubMenuList();
        
        klantChangeMenu.addSubMenu("Email", klant.getEmail(), "1");
        klantChangeMenu.addSubMenu("Voornaam", klant.getVoornaam(), "2");
        klantChangeMenu.addSubMenu("Tussenvoegsel", klant.getTussenvoegsel(), "3");
        klantChangeMenu.addSubMenu("Achternaam", klant.getAchternaam(), "4");
        klantChangeMenu.addSubMenu(AdresType.Postadres.getDescription(), displayAdres(AdresType.Postadres), "5");
        klantChangeMenu.addSubMenu(AdresType.Factuuradres.getDescription(), displayAdres(AdresType.Factuuradres), "6");
        klantChangeMenu.addSubMenu(AdresType.Bezorgadres.getDescription(), displayAdres(AdresType.Bezorgadres), "7");
        klantChangeMenu.addSubMenu("Sortering", String.valueOf(klant.getSortering()), "8");
        klantChangeMenu.addSubMenu("Opslaan", "9");
    }

    protected String displayAdres(AdresType adresType) {
        for (Adres adres : adresList) {
            if (adres.getAdresType().equals(adresType)) {
                return displayRecord(adres);
            }
        }
        return "";

    }

}
