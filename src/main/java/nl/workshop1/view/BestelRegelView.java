package nl.workshop1.view;

import nl.workshop1.controller.MainController;
import nl.workshop1.model.Artikel;
import nl.workshop1.model.BestelRegel;
import nl.workshop1.model.Role;

/**
 *
 * @author FeniksBV
 */
public class BestelRegelView extends View {

    Artikel artikel = null;
    BestelRegel bestelRegel = null;
    Menu bestelRegelViewMenu;

    public BestelRegelView(Menu bestelRegelViewMenu, BestelRegel bestelRegel) {
        super(bestelRegelViewMenu);
        this.bestelRegelViewMenu = bestelRegelViewMenu;
        this.bestelRegel = bestelRegel;
    }

    public BestelRegel getBestelRegel() {
        return bestelRegel;
    }

    public void setBestelRegel(BestelRegel bestelRegel) {
        this.bestelRegel = bestelRegel;
    }

    public void setArtikel(Artikel artikel) {
        this.artikel = artikel;
    }

    @Override
    public String runViewer() {

        while (true) {
            buildSubMenuList();
            drawMenu();
            String requestedAction = userChoice();
            switch (requestedAction) {
                case "0":
                    bestelRegel = null;
                    return requestedAction;
                case "2":
                    int maxAantal = artikel.getVoorraad() - artikel.getGereserveerd();
                    while (true) {
                        int newAantal = getInputPositiveInt("Aantal", 1);
                        if (newAantal <= maxAantal) {
                            bestelRegel.setAantal(newAantal);
                            break;
                        } else {
                            OutputText.showError("Huidige voorraad is niet voldoende, aanwezig = " + maxAantal);
                        }
                    }
                    break;
                case "3":
                    bestelRegel.setPrijs(getInputPrijs());
                    break;
                case "4":
                    // Opslaan
                    return requestedAction;
                case "99":
                    // Negeren, geen actie
                    OutputText.showError("Deze data mag NIET worden gewijzigd!");
                    break;
            }
        }
    }

    protected void buildSubMenuList() {
        bestelRegelViewMenu.clearSubMenuList();

        // Artikel Id/Naam mag niet worden gewijzigd
        bestelRegelViewMenu.addSubMenu("Artikel", bestelRegel.getArtikelNaam(), "99");
        bestelRegelViewMenu.addSubMenu("Aantal", String.valueOf(bestelRegel.getAantal()), "2");
        // De Role Klant mag de prijs niet wijzigen
        if (MainController.getRole().equals(Role.Klant)) {
            // Deze mogen de prijs NIET wijzigen
            bestelRegelViewMenu.addSubMenu("Prijs/st", currencyDisplay(bestelRegel.getPrijs()), "99");
        } else {
            bestelRegelViewMenu.addSubMenu("Prijs/st", currencyDisplay(bestelRegel.getPrijs()), "3");
        }
        bestelRegelViewMenu.addSubMenu("Opslaan", "4");
        bestelRegelViewMenu.addSubMenu("Terug", "0");
    }
}
