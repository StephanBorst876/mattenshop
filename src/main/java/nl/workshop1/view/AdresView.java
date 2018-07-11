package nl.workshop1.view;

import nl.workshop1.model.Adres;
import nl.workshop1.model.AdresType;

/**
 *
 * @author FeniksBV
 */
public class AdresView extends View {

    private Menu adresViewMenu;
    private Adres adres;
    private AdresType adresType;

    public AdresView(AdresType adresType, Menu adresViewMenu, Adres adres) {
        super(adresViewMenu);
        this.adresType = adresType;
        this.adresViewMenu = adresViewMenu;
        if (adres == null) {
            this.adres = new Adres();
            this.adres.setAdresType(adresType);
        } else {
            this.adres = (Adres) adres.clone();
        }
    }

    public Adres getAdres() {
        return adres;
    }

    @Override
    public String runViewer() {

        // Initially ask for input all datafields
        if (adres.getStraatNaam() == null) {
            // Dit is een nieuw adres, dus alles uitvragen
            adres.setAdresType(adresType);
            adres.setStraatNaam(getInputStraatnaam());
            adres.setHuisNummer(getInputHuisnummer());
            adres.setToevoeging(getInputToevoeging());
            adres.setPostcode(getInputPostcode());
            adres.setWoonplaats(getInputWoonplaats());
        }
        if (adresViewMenu == null) {
            // Aangeroepen vanuit nieuwe klant invoeren.
            // Die kijkt niet naar de return-waarde.
            return "";
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
                    // ok
                    return requestedAction;
                case "7":
                    // verwijderen
                    return requestedAction;

            }
        }

    }

    protected void buildSubMenuList() {
        adresViewMenu.clearSubMenuList();

        adresViewMenu.addSubMenu("Straatnaam", adres.getStraatNaam(), "1");
        adresViewMenu.addSubMenu("Huisnummer", String.valueOf(adres.getHuisNummer()), "2");
        adresViewMenu.addSubMenu("Toevoeging", adres.getToevoeging(), "3");
        adresViewMenu.addSubMenu("postcode", adres.getPostcode(), "4");
        adresViewMenu.addSubMenu("Woonplaats", adres.getWoonplaats(), "5");

        adresViewMenu.addSubMenu("OK", "6");
        // Postadres mag niet worden verwijderd
        if (adresType != AdresType.Postadres) {
            adresViewMenu.addSubMenu("Verwijderen", "7");
        }
        adresViewMenu.addSubMenu("Terug", "0");
    }

}
