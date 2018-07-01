package nl.workshop1.controller;

/**
 *
 * @author FeniksBV
 */
public abstract class Controller {
    // Titels voor de verschillende menus
    public static final String TITEL_ACCOUNTS = "Accounts";
    public static final String TITEL_ARTIKELEN = "Artikelen";
    public static final String TITEL_KLANTEN = "Klanten";
    public static final String TITEL_BESTELLINGEN = "Bestellingen";
    public static final String TITEL_BESTELREGELS = "Bestellingregels";
    
    // KlantController en ArtikelController kunnen door een ADMIN worden
    // beheerd (muteren/wijzigen/verwijderen), maar kunnen
    // door klanten ook worden gebruikt voor alleen zoeken/selecteren.
    public static final int CONTROLLER_MODE_ADMIN = 1;
    public static final int CONTROLLER_MODE_SEARCH = 2;

    // Een controller kan een Entity Nieuw aanmaken of Wijzigen
    public static final int MODE_NIEUW = 1;
    public static final int MODE_WIJZIG = 2;

    public String requestedAction;

    public Controller() {
        this.requestedAction = "";
    }

    /**
     *  Uitvoeren van de controller
     */
    public abstract void runController();


    /**
     *
     * @return  Door de gebruiker gevraagde actie
     */
    public String getRequestedAction() {
        return requestedAction;
    }

}
