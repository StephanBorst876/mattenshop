package nl.workshop1.view;

import nl.workshop1.controller.Controller;
import nl.workshop1.model.Artikel;

/**
 *
 * @author FeniksBV
 */
public class ArtikelView extends View {

    private int artikelMode;
    private Menu artikelChangeMenu;
    private Artikel artikel;

    public ArtikelView(int mode, Menu artikelChangeMenu) {
        super(artikelChangeMenu);
        this.artikelMode = mode;
        this.artikelChangeMenu = artikelChangeMenu;
        artikel = new Artikel();
    }

    public ArtikelView(int mode, Menu artikelChangeMenu, Artikel artikel) {
        super(artikelChangeMenu);
        this.artikelMode = mode;
        this.artikelChangeMenu = artikelChangeMenu;
        this.artikel = (Artikel) artikel.clone();
    }

    @Override
    public String runViewer() {

        // Initially ask for input all datafields
        if (artikelMode == Controller.MODE_NIEUW) {
            if (artikel.getNaam().isEmpty()) {
                // Nu weet je zeker dat dit de eerste keer is
                artikel.setNaam(getInputArtikelnaam());
                artikel.setPrijs(getInputPrijs());
                artikel.setVoorraad(getInputPositiveInt("Voorraad"));
                artikel.setGereserveerd(getInputPositiveInt("Gereserveerd"));
                artikel.setSortering(getInputSortering());
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
                    //Artikelnaam
                    artikel.setNaam(getInputArtikelnaam());
                    break;
                case "2":
                    // Prijs
                    artikel.setPrijs(getInputPrijs());
                    break;
                case "3":
                    // Voorraad
                    artikel.setVoorraad(getInputPositiveInt("Voorraad"));
                    break;
                case "4":
                    // Gereserveerd
                    artikel.setGereserveerd(getInputPositiveInt("Gereserveerd"));
                    break;
                case "5":
                    // Sortering
                    artikel.setSortering(getInputSortering());
                    break;
                case "6":
                    return requestedAction;
            }
        }

    }

    protected void buildSubMenuList() {
        artikelChangeMenu.clearSubMenuList();
        artikelChangeMenu.addSubMenu("Artikelnaam", artikel.getNaam(), "1");
        artikelChangeMenu.addSubMenu("Prijs", artikel.getPrijs().toString(), "2");
        artikelChangeMenu.addSubMenu("Voorraad", String.valueOf(artikel.getVoorraad()), "3");
        artikelChangeMenu.addSubMenu("Gereserveerd", String.valueOf(artikel.getGereserveerd()), "4");
        artikelChangeMenu.addSubMenu("Sortering", String.valueOf(artikel.getSortering()), "5");
        artikelChangeMenu.addSubMenu("Opslaan", "6");
    }

    

    /**
     * @return the artikel
     */
    public Artikel getArtikel() {
        return artikel;
    }

}
