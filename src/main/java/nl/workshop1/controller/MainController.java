package nl.workshop1.controller;

import nl.workshop1.DAO.DAOFactory;
import nl.workshop1.model.Account;
import nl.workshop1.model.Klant;
import nl.workshop1.model.Role;
import nl.workshop1.view.Menu;
import nl.workshop1.view.OutputText;
import nl.workshop1.view.SimpleMenuView;
import nl.workshop1.view.View;

/**
 *
 * @author FeniksBV
 */
public class MainController extends Controller {

    private Menu hoofdMenu;
    private SimpleMenuView hoofdMenuView;
    private Role role;
    private Klant inlogKlant = null;

    public MainController() {
        this.hoofdMenu = new Menu("Hoofdmenu");
        this.hoofdMenuView = new SimpleMenuView(this.hoofdMenu);
    }

    @Override
    public void runController() {

        // Eerst inloggen
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
                // Lees klantgegevens indien een inlogKlant is ingelogd
                Menu.loginName = login.getUserName();
                if (role.equals(Role.Klant)) {
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
        if (role.equals(Role.Admin) || role.equals(Role.Admin)) {
            hoofdMenu.addSubMenu("Klanten", "2");
        }
        if (role.equals(Role.Admin) || role.equals(Role.Admin)) {
            hoofdMenu.addSubMenu("Artikelen", "3");
        }
        if (role.equals(Role.Admin)) {
            hoofdMenu.addSubMenu("Accounts", "4");
        }

        // De main-loop die wordt doorlopen totdat de applicatie wordt afgesloten.
        while (true) {
            hoofdMenuView.drawMenu();
            switch (hoofdMenuView.userChoice()) {
                case "0":
                    // Afsluiten
                    AfsluitenController afsluitCtrl = new AfsluitenController();
                    afsluitCtrl.runController();
                    // Oeps. Pas hier op: Alle controllers/menu's geven optie=0 als zijnde
                    // terug, maar de afsluitController geeft 1 als Afsluiten.
                    if (afsluitCtrl.getRequestedAction().equals("1")) {
                        return;
                    }
                    break;
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
                    MenuController bestelMenuCtrl = new MenuController(View.TITEL_BESTELLINGEN);
                    bestelMenuCtrl.setBestelKlant(bestelKlant);
                    bestelMenuCtrl.runController();
                    break;
                case "2":
                    // Klanten
                    MenuController klantMenuCtrl = new MenuController(View.TITEL_KLANTEN);
                    klantMenuCtrl.runController();
                    break;
                case "3":
                    // Artikelen
                    MenuController artikelMenuCtrl = new MenuController(View.TITEL_ARTIKELEN);
                    artikelMenuCtrl.runController();
                    break;
                case "4":
                    // Accounts
                    MenuController accountMenuCtrl = new MenuController(View.TITEL_ACCOUNTS);
                    accountMenuCtrl.runController();
            }
        }
    }

}
