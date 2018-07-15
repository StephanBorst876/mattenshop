package nl.workshop1.view;

import java.util.ArrayList;
import nl.workshop1.model.Adres;
import nl.workshop1.model.AdresType;
import nl.workshop1.model.Klant;

/**
 *
 * @author FeniksBV
 */
public class KlantView extends View {

    private AdresType adresType;
    private Menu klantViewMenu;
    private Klant klant = null;
    private ArrayList<Adres> adresList = new ArrayList<>();

    public KlantView(Menu klantViewMenu, Klant klant, ArrayList<Adres> adresList) {
        super(klantViewMenu);
        this.klantViewMenu = klantViewMenu;
        if (klant == null) {
            this.klant = new Klant();
        } else {
            this.klant = (Klant) klant.clone();
            this.adresList = adresList;
        }
    }

    public Klant getKlant() {
        return klant;
    }

    public void setAdresList(ArrayList<Adres> adresList) {
        this.adresList = adresList;
    }

    public ArrayList<Adres> getAdresList() {
        return adresList;
    }

    public AdresType getAdresType() {
        return adresType;
    }

    @Override
    public String runViewer() {

        // Initially ask for input all datafields
        if (klant.getAchternaam() == null) {
            // Nu weet je zeker dat dit de eerste keer is
            klant.setEmail(getInputUsername(/*allowEmptyInput=*/false));
            klant.setVoornaam(getInputVoornaam());
            klant.setTussenvoegsel(getInputTussenvoegsel());
            klant.setAchternaam(getInputAchternaam());
            klant.setSortering(getInputSortering());

            // Postadres is verplicht, dus ook uitvragen
            AdresView adresView = new AdresView(AdresType.Postadres, null, null);
            adresView.runViewer();

            adresList.add(adresView.getAdres());
        }

        while (true) {
            buildSubMenuList();
            drawMenu();
            String requestedAction = userChoice();
            switch (requestedAction) {
                case "0":
                    return requestedAction;
                case "1":
                    klant.setEmail(getInputUsername(/*allowEmptyInput=*/false));
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
        klantViewMenu.clearSubMenuList();

        klantViewMenu.addSubMenu("Email", klant.getEmail(), "1");
        klantViewMenu.addSubMenu("Voornaam", klant.getVoornaam(), "2");
        klantViewMenu.addSubMenu("Tussenvoegsel", klant.getTussenvoegsel(), "3");
        klantViewMenu.addSubMenu("Achternaam", klant.getAchternaam(), "4");
        klantViewMenu.addSubMenu(AdresType.Postadres.getDescription(), displayAdres(AdresType.Postadres), "5");
        klantViewMenu.addSubMenu(AdresType.Factuuradres.getDescription(), displayAdres(AdresType.Factuuradres), "6");
        klantViewMenu.addSubMenu(AdresType.Bezorgadres.getDescription(), displayAdres(AdresType.Bezorgadres), "7");
        klantViewMenu.addSubMenu("Sortering", String.valueOf(klant.getSortering()), "8");
        klantViewMenu.addSubMenu("Opslaan", "9");
        klantViewMenu.addSubMenu("Terug", "0");
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
