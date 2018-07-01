package nl.workshop1.controller;

import nl.workshop1.model.Klant;
import nl.workshop1.model.Role;
import nl.workshop1.view.Menu;
import nl.workshop1.view.SimpleMenuView;

/**
 *
 * @author FeniksBV
 */
public class HoofdMenuController extends Controller {

    private Menu hoofdMenu;
    private SimpleMenuView hoofdMenuView;
    private Role role;
    private Klant klant = null;

    public HoofdMenuController(Role role) {
        // Hoofdmenu heeft de Role nodig, omdat dat de opties
        // in het menu bepaald.
        this.role = role;
        this.hoofdMenu = new Menu("Hoofdmenu");
        this.hoofdMenuView = new SimpleMenuView(this.hoofdMenu);
    }

    public HoofdMenuController(Role role, Klant klant) {
        this(role);
        this.klant = klant;
    }

    @Override
    public void runController() {

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

        while (true) {
            hoofdMenuView.drawMenu();
            switch (hoofdMenuView.userChoice()) {
                case "0":
                    // Afsluiten
                    AfsluitenController afsluitCtrl = new AfsluitenController();
                    afsluitCtrl.runController();
                    if (afsluitCtrl.getRequestedAction().equals("1")) {
                        // Oeps. Pas hier op: Alle controllers/menu's geven optie=0 als zijnde
                        // terug, maar de afsluitController geeft 1 als Afsluiten.
                        return;
                    }
                    break;
                case "1":
                    // Bestellingen
                    BestellingMenuController bestelMenuCtrl;
                    bestelMenuCtrl = new BestellingMenuController(klant);
                    bestelMenuCtrl.runController();
                    break;
                case "2":
                    // Klanten
                    KlantMenuController klantMenuCtrl = new KlantMenuController(Controller.CONTROLLER_MODE_ADMIN);
                    klantMenuCtrl.runController();
                    break;
                case "3":
                    // Artikelen
                    MenuController artikelMenuCtrl = new MenuController(TITEL_ARTIKELEN);
                    artikelMenuCtrl.runController();
                    break;
                case "4":
                    // Accounts
                    MenuController accountCtrl = new MenuController(TITEL_ACCOUNTS);
                    accountCtrl.runController();
            }
        }
    }

}
