package nl.workshop1.view;

import nl.workshop1.model.Artikel;

/**
 *
 * @author FeniksBV
 */
public class ArtikelView extends View {

    private Menu artikelViewMenu;
    private Artikel artikel = null;

    public ArtikelView(Menu artikelViewMenu, Artikel artikel) {
        super(artikelViewMenu);
        this.artikelViewMenu = artikelViewMenu;
        if (artikel == null) {
            this.artikel = new Artikel();
        } else {
            this.artikel = (Artikel) artikel.clone();
        }
    }

    @Override
    public String runViewer() {

        // Initially ask for input all datafields
        if (artikel.getNaam() == null) {
            artikel.setNaam(getInputArtikelnaam());
            artikel.setPrijs(getInputPrijs());
            artikel.setVoorraad(getInputPositiveInt("Voorraad",0));
            artikel.setGereserveerd(getInputPositiveInt("Gereserveerd",0));
            artikel.setSortering(getInputSortering());
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
                    artikel.setVoorraad(getInputPositiveInt("Voorraad",0));
                    break;
                case "4":
                    // Gereserveerd
                    artikel.setGereserveerd(getInputPositiveInt("Gereserveerd",0));
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
        artikelViewMenu.clearSubMenuList();
        artikelViewMenu.addSubMenu("Artikelnaam", artikel.getNaam(), "1");
        artikelViewMenu.addSubMenu("Prijs/st", currencyDisplay(artikel.getPrijs()), "2");
//        artikelViewMenu.addSubMenu("Prijs", artikel.getPrijs().toString(), "2");
        artikelViewMenu.addSubMenu("Voorraad", String.valueOf(artikel.getVoorraad()), "3");
        artikelViewMenu.addSubMenu("Gereserveerd", String.valueOf(artikel.getGereserveerd()), "4");
        artikelViewMenu.addSubMenu("Sortering", String.valueOf(artikel.getSortering()), "5");
        artikelViewMenu.addSubMenu("Opslaan", "6");
        artikelViewMenu.addSubMenu("Terug", "0");
    }

    /**
     * @return the artikel
     */
    public Artikel getArtikel() {
        return artikel;
    }

}
