package nl.workshop1.controller;

import java.util.ArrayList;
import nl.workshop1.model.Adres;
import nl.workshop1.model.AdresType;
import nl.workshop1.view.AdresChangeMenu;
import nl.workshop1.view.AdresChangeMenuView;

/**
 *
 * @author FeniksBV
 */
public class AdresChangeMenuController extends MenuController {

    private AdresType adresType;
    private ArrayList<Adres> adresList;
    private AdresChangeMenu adresChangeMenu;
    private AdresChangeMenuView adresChangeMenuView;

    protected Adres editAdres = null;

    /**
     *
     * @param adresType     Het adresType (Post, Factuur, Bezorg)
     * @param adresList     Lijst van bestaande adressen voor klant
     * @param adresChangeMenu       Het menu object voor adres
     * @param adresChangeMenuView   Het view object voor adres
     */
    public AdresChangeMenuController(AdresType adresType, ArrayList<Adres> adresList, AdresChangeMenu adresChangeMenu, AdresChangeMenuView adresChangeMenuView) {
        this.adresType = adresType;
        this.adresList = adresList;
        this.adresChangeMenu = adresChangeMenu;
        this.adresChangeMenuView = adresChangeMenuView;

        // Is er al een beoogd adresrecord?
        for (int i = 0; i < adresList.size(); i++) {
            if (adresList.get(i).getAdresType().equals(adresType)) {
                // Ja, het is er, dus die gaan we gebruiken
                editAdres = (Adres) adresList.get(i).clone();
            }
        }
        if (editAdres == null) {
            // Het beoogde adres bestaat nog niet, dus een nieuwe maken
            editAdres = new Adres();
            editAdres.setAdresType(adresType);
        }

    }

    @Override
    public void buildOptionsMenu() {
        adresChangeMenu.clearSubMenu();

        adresChangeMenu.addSubMenu(formatText("Straatnaam") + editAdres.getStraatNaam(), "1");
        adresChangeMenu.addSubMenu(formatText("Huisnummer") + editAdres.getHuisNummer(), "2");
        adresChangeMenu.addSubMenu(formatText("Toevoeging") + editAdres.getToevoeging(), "3");
        adresChangeMenu.addSubMenu(formatText("postcode") + editAdres.getPostcode(), "4");
        adresChangeMenu.addSubMenu(formatText("Woonplaats") + editAdres.getWoonplaats(), "5");

        adresChangeMenu.addSubMenu("OK", "6");
        if (adresType != AdresType.ADRES_POST) {
            // Postadres mag niet worden verwijderd
            adresChangeMenu.addSubMenu("Verwijderen", "7");
        }
    }

    @Override
    public void handleMenu() {
        // Initially ask for input all datafields
        if (editAdres.getStraatNaam().equals("")) {
            // Dit is een nieuw adres, dus alles uitvragen
            editAdres.setStraatNaam(adresChangeMenuView.getInputStraatnaam());
            editAdres.setHuisNummer(adresChangeMenuView.getInputHuisnummer());
            editAdres.setToevoeging(adresChangeMenuView.getInputToevoeging());
            editAdres.setPostcode(adresChangeMenuView.getInputPostcode());
            editAdres.setWoonplaats(adresChangeMenuView.getInputWoonplaats());
        }
        while (true) {
            buildOptionsMenu();
            adresChangeMenu.drawMenu();
            switch (adresChangeMenu.userChoice()) {
                case "0":
                    return;
                case "1":
                    // straatnaam
                    editAdres.setStraatNaam(adresChangeMenuView.getInputStraatnaam());
                    break;
                case "2":
                    // huisnummer
                    editAdres.setHuisNummer(adresChangeMenuView.getInputHuisnummer());
                    break;
                case "3":
                    // toevoeging
                    editAdres.setToevoeging(adresChangeMenuView.getInputToevoeging());
                    break;
                case "4":
                    // postcode
                    editAdres.setPostcode(adresChangeMenuView.getInputPostcode());
                    return;
                case "5":
                    // woonplaats
                    editAdres.setWoonplaats(adresChangeMenuView.getInputWoonplaats());
                    break;
                case "6":
                    // opslaan
                    // Verwijder eerst de huidige, dan de nieuwe toevoegen
                    for (int i = adresList.size() - 1; i >= 0; i--) {
                        if (adresList.get(i).getAdresType() == adresType) {
                            adresList.remove(i);
                            return;
                        }
                    }
                    adresList.add(editAdres);
                    return;
                case "7":
                    // verwijderen
                    for (int i = adresList.size() - 1; i >= 0; i--) {
                        if (adresList.get(i).getAdresType() == adresType) {
                            adresList.remove(i);
                            return;
                        }
                    }
                    return;
            }
        }
    }

}
