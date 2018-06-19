package nl.workshop1.controller;

import nl.workshop1.model.Role;
import nl.workshop1.menu.HoofdMenu;
import nl.workshop1.menu.AccountMenu;
import nl.workshop1.menu.AfsluitMenu;
import nl.workshop1.menu.KlantMenu;
import nl.workshop1.menu.ProductMenu;
import nl.workshop1.view.AfsluitView;

/**
 *
 * @author FeniksBV
 */
public class HoofdMenuController extends MenuController {

    private HoofdMenu hoofdMenu;

    public HoofdMenuController(HoofdMenu hoofdMenu) {
        this.hoofdMenu = hoofdMenu;
    }

    @Override
    public void buildOptionsMenu() {
        hoofdMenu.resetMenu();
        hoofdMenu.addSubMenu("Bestellingen", "1");
        if (hoofdMenu.getRole().equals(Role.ROLE_ADMIN) || hoofdMenu.getRole().equals(Role.ROLE_MEDEWERKER)) {
            hoofdMenu.addSubMenu("Klanten", "2");
        }
        if (hoofdMenu.getRole().equals(Role.ROLE_ADMIN) || hoofdMenu.getRole().equals(Role.ROLE_MEDEWERKER)) {
            hoofdMenu.addSubMenu("Producten", "3");
        }
        if (hoofdMenu.getRole().equals(Role.ROLE_ADMIN)) {
            hoofdMenu.addSubMenu("Accounts", "4");
        }
    }

    @Override
    public void handleMenu() {
        while (true) {
            hoofdMenu.drawMenu();
            switch (hoofdMenu.userChoice()) {
                case "0":
                    AfsluitMenu afsluitMenu = new AfsluitMenu();
                    AfsluitView afsluitView = new AfsluitView();
                    AfsluitMenuController AfsluitCtrl = new AfsluitMenuController(afsluitMenu, afsluitView);
                    AfsluitCtrl.runController();
                    break;
                case "1":
                    System.out.println("Bestellingen");
                    break;
                case "2":
                    KlantMenu klantMenu = new KlantMenu();
                    KlantMenuController klantMenuCtrl = new KlantMenuController(klantMenu, MenuController.CONTROLER_MODE_ADMIN);
                    klantMenuCtrl.runController();
                    break;
                case "3":
                    ProductMenu productMenu = new ProductMenu();
                    ProductMenuController productMenuCtrl = new ProductMenuController(productMenu);
                    productMenuCtrl.runController();
                    break;
                case "4":
                    AccountMenu accountMenu = new AccountMenu();
                    AccountMenuController accountCtrl = new AccountMenuController(accountMenu);
                    accountCtrl.runController();
                    break;
            }
        }
    }

}
