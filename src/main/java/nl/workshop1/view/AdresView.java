package nl.workshop1.view;

import nl.workshop1.controller.MenuController;
import nl.workshop1.menu.AdresChangeMenu;
import nl.workshop1.model.Adres;
import nl.workshop1.model.AdresType;

/**
 *
 * @author FeniksBV
 */
public class AdresView extends MenuView {

    private int adresMode;
    private AdresChangeMenu adresChangeMenu;
    private Adres adres;
    private AdresType adresType;

    public AdresView(int mode, AdresType adresType, AdresChangeMenu adresChangeMenu) {
        super(adresChangeMenu);
        this.adresMode = mode;
        this.adresType = adresType;
        this.adresChangeMenu = adresChangeMenu;
        adres = new Adres();
    }

    public AdresView(int mode, AdresType adresType, AdresChangeMenu adresChangeMenu, Adres adres) {
        super(adresChangeMenu);
        this.adresMode = mode;
        this.adresType = adresType;
        this.adresChangeMenu = adresChangeMenu;
        this.adres = (Adres) adres.clone();
    }

    public Adres getAdres() {
        return adres;
    }

    @Override
    public String runViewer() {

        // Initially ask for input all datafields
        if (adresMode == MenuController.MODE_NIEUW) {
            if (adres.getStraatNaam().equals("")) {
                // Dit is een nieuw adres, dus alles uitvragen
                adres.setAdresType(adresType);
                adres.setStraatNaam(getInputStraatnaam());
                adres.setHuisNummer(getInputHuisnummer());
                adres.setToevoeging(getInputToevoeging());
                adres.setPostcode(getInputPostcode());
                adres.setWoonplaats(getInputWoonplaats());
            }
            if (adresChangeMenu == null) {
                // Aangeroepen vanuit nieuwe klant invoeren.
                // Die kijkt niet naar de return-waarde.
                return "";
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
                    // straatnaam
                    adres.setStraatNaam(getInputStraatnaam());
                    break;
                case "2":
                    // huisnummer
                    adres.setHuisNummer(getInputHuisnummer());
                    break;
                case "3":
                    // toevoeging
                    adres.setToevoeging(getInputToevoeging());
                    break;
                case "4":
                    // postcode
                    adres.setPostcode(getInputPostcode());
                    break;
                case "5":
                    // woonplaats
                    adres.setWoonplaats(getInputWoonplaats());
                    break;
                case "6":
                    // opslaan
                    return requestedAction;
                case "7":
                    // verwijderen
                    return requestedAction;

            }
        }

    }

    protected void buildSubMenuList() {
        adresChangeMenu.clearSubMenuList();

        adresChangeMenu.addSubMenu("Straatnaam", adres.getStraatNaam(), "1");
        adresChangeMenu.addSubMenu("Huisnummer", String.valueOf(adres.getHuisNummer()), "2");
        adresChangeMenu.addSubMenu("Toevoeging", adres.getToevoeging(), "3");
        adresChangeMenu.addSubMenu("postcode", adres.getPostcode(), "4");
        adresChangeMenu.addSubMenu("Woonplaats", adres.getWoonplaats(), "5");

        adresChangeMenu.addSubMenu("OK", "6");
        // Postadres mag niet worden verwijderd
        if (adresType != AdresType.ADRES_POST) {
            adresChangeMenu.addSubMenu("Verwijderen", "7");
        }
    }

}
