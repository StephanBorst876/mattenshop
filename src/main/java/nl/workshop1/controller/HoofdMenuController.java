package nl.workshop1.controller;

import nl.workshop1.model.Role;
import nl.workshop1.menu.HoofdMenu;
import nl.workshop1.view.SimpleMenuView;

/**
 *
 * @author FeniksBV
 */
public class HoofdMenuController extends MenuController {

    private HoofdMenu hoofdMenu;
    private SimpleMenuView hoofdMenuView;
    private Role role;

    public HoofdMenuController(Role role) {
        // Hoofdmenu heeft de Role nodig, omdat dat de opties
        // in het menu bepaald.
        this.role = role;
        this.hoofdMenu = new HoofdMenu();
        this.hoofdMenuView = new SimpleMenuView(this.hoofdMenu);
    }

    @Override
    public void runController() {

        hoofdMenu.clearSubMenuList();
        hoofdMenu.addSubMenu("Bestellingen", "1");
        if (role.equals(Role.ROLE_ADMIN) || role.equals(Role.ROLE_MEDEWERKER)) {
            hoofdMenu.addSubMenu("Klanten", "2");
        }
        if (role.equals(Role.ROLE_ADMIN) || role.equals(Role.ROLE_MEDEWERKER)) {
            hoofdMenu.addSubMenu("Artikelen", "3");
        }
        if (role.equals(Role.ROLE_ADMIN)) {
            hoofdMenu.addSubMenu("Accounts", "4");
        }

        while (true) {
            hoofdMenuView.drawMenu();
            switch (hoofdMenuView.userChoice()) {
                case "0":
                    // Afsluiten
                    AfsluitMenuController afsluitCtrl = new AfsluitMenuController();
                    afsluitCtrl.runController();
                    if (afsluitCtrl.getRequestedAction() == "1") {
                        // Oeps. Pas hier op: Alle controllers/menu's geven optie=0 als zijnde
                        // terug, maar de afsluitController geeft 1 als Afsluiten.
                        return;
                    }
                case "1":
                    // Bestellingen
                    System.out.println("Bestellingen");
                    break;
                case "2":
                    // Klanten
                    KlantMenuController klantMenuCtrl = new KlantMenuController(MenuController.CONTROLLER_MODE_ADMIN);
                    klantMenuCtrl.runController();
                    break;
                case "3":
                    // Artikelen
                    ArtikelMenuController artikelMenuCtrl = new ArtikelMenuController();
                    artikelMenuCtrl.runController();
                    break;
                case "4":
                    // Accounts
                    AccountMenuController accountCtrl = new AccountMenuController();
                    accountCtrl.runController();
            }
        }
    }

}
