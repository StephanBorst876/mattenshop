package nl.workshop1.controller;

/**
 *
 * @author FeniksBV
 */
public abstract class MenuController {

    // KlantCotroller en ArtikelController kunnen door een ADMIN worden
    // beheerd (muteren/wijzigen/verwijderen), maar kunnen
    // door klanten ook worden gebruikt voor alleen zoeken/selecteren.
    public static final int CONTROLLER_MODE_ADMIN = 1;
    public static final int CONTROLLER_MODE_SEARCH = 2;

    // Een controller kan een Entity Nieuw aanmaken of Wijzigen
    public static final int MODE_NIEUW = 1;
    public static final int MODE_WIJZIG = 2;

    public String requestedAction;

    public MenuController() {
        this.requestedAction = "";
    }

    /**
     *  Uitvoeren van de controller
     */
    public abstract void runController();

    /**
     *  Wordt gebruikt bij zoeken met filter
     * @return  Het menu
     */
//    TODO : public abstract Menu getMenu();

    /**
     *
     * @return  Door de gebruiker gevraagde actie
     */
    public String getRequestedAction() {
        return requestedAction;
    }

}
