package nl.workshop1.controller;

import nl.workshop1.DAO.DAOFactory;
import nl.workshop1.DAO.DbConnection;
import nl.workshop1.model.Account;
import nl.workshop1.model.Klant;
import nl.workshop1.model.Role;
import nl.workshop1.view.Menu;
import nl.workshop1.view.OutputText;
import nl.workshop1.view.SimpleMenuView;

/**
 *
 * @author FeniksBV
 */
public class MainController extends Controller {

    // Default voor role is Klant, tenzij anders ingelogd
    private static Role role = Role.Klant;
    
    private Menu hoofdMenu;
    private SimpleMenuView hoofdMenuView;
    private Klant inlogKlant = null;

    public MainController() {
        this.hoofdMenu = new Menu(Menu.TITEL_HOOFDMENU);
        this.hoofdMenuView = new SimpleMenuView(this.hoofdMenu);
    }

    @Override
    public void runController() {

        // Welke Db te gebruiken?
        DbController dbCtrl = new DbController();
        dbCtrl.runController();
        switch (dbCtrl.getRequestedAction()) {
            case "0":
                // Afsluiten
                return;
            case "1":
                DbConnection.setDbSelectie(DbConnection.DB_MYSQL);
                break;
            case "2":
                DbConnection.setDbSelectie(DbConnection.DB_MONGODB);
                break;
        }

        // Indien MySQL gekozen, dan is de default HikariCP.
        // Vraag of men JDBC wil gebruiken.
        if (DbConnection.getDbSelectie().equals(DbConnection.DB_MYSQL)) {
            ConnectionPoolController cpCtrl = new ConnectionPoolController();
            cpCtrl.runController();
            if (cpCtrl.getRequestedAction().equals("J")) {
                // Er is gekozen om JDBC te gebruiken
                DbConnection.setHikariCPenabled(false);
            }
        }

        // Start the login procedure
        LoginController loginCtrl = new LoginController();
        loginCtrl.runController();
        switch (loginCtrl.getRequestedAction()) {
            case "0":
                // Geen correcte login
                OutputText.showError("Geen juiste inlog!");
                return;
            case "1":
                // een correcte Login.
                Account login = loginCtrl.getLoginAccount();
                role = login.getRole();

                Menu.loginName = login.getUserName();
                if (getRole().equals(Role.Klant)) {
                    // Lees klantgegevens indien een inlogKlant is ingelogd
                    inlogKlant = DAOFactory.getKlantDAO().readKlant(login.getKlantId());
                    if (inlogKlant == null) {
                        // Wel de role Klant, maar geen bijbehorende klantobject
                        OutputText.showError("Ontbrekende klantgegevens! Neem contact op met de beheerder.");
                        return;
                    }
                }
                break;
        }

        // Bouw nu het menu
        hoofdMenu.clearSubMenuList();
        hoofdMenu.addSubMenu("Bestellingen", "1");
        if (getRole().equals(Role.Admin) || getRole().equals(Role.Admin)) {
            hoofdMenu.addSubMenu("Klanten", "2");
        }
        if (getRole().equals(Role.Admin) || getRole().equals(Role.Admin)) {
            hoofdMenu.addSubMenu("Artikelen", "3");
        }
        if (getRole().equals(Role.Admin)) {
            hoofdMenu.addSubMenu("Accounts", "4");
        }
        hoofdMenu.addSubMenu("Afsluiten", "0");

        // De main-loop die wordt doorlopen totdat de applicatie wordt afgesloten.
        while (true) {
            hoofdMenuView.drawMenu();
            switch (hoofdMenuView.userChoice()) {
                case "0":
                    // Afsluiten
                    return;
                case "1":
                    // Bestellingen

                    // Indien NIET ingelogd als inlogKlant, dan eerst de inlogKlant kiezen
                    // waar de bestelling voor geldt.
                    Klant bestelKlant;
                    if (inlogKlant == null) {
                        KlantSearchController klantSearchCtrl = new KlantSearchController();
                        klantSearchCtrl.runController();
                        bestelKlant = klantSearchCtrl.getKlantSelected();
                        if (bestelKlant == null) {
                            break;
                        }
                    } else {
                        bestelKlant = (Klant) inlogKlant.clone();
                    }
                    MenuController bestelMenuCtrl = new MenuController(Menu.TITEL_BESTELLINGEN);
                    bestelMenuCtrl.setBestelKlant(bestelKlant);
                    bestelMenuCtrl.runController();
                    break;
                case "2":
                    // Klanten
                    MenuController klantMenuCtrl = new MenuController(Menu.TITEL_KLANTEN);
                    klantMenuCtrl.runController();
                    break;
                case "3":
                    // Artikelen
                    MenuController artikelMenuCtrl = new MenuController(Menu.TITEL_ARTIKELEN);
                    artikelMenuCtrl.runController();
                    break;
                case "4":
                    // Accounts
                    MenuController accountMenuCtrl = new MenuController(Menu.TITEL_ACCOUNTS);
                    accountMenuCtrl.runController();
            }
        }
    }

    /**
     * @return the role
     */
    public static Role getRole() {
        return role;
    }

}
