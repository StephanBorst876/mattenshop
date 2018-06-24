package nl.workshop1;

import nl.workshop1.DAO.DAOFactory;
import nl.workshop1.controller.LoginController;
import nl.workshop1.controller.HoofdMenuController;
import nl.workshop1.menu.Menu;
import nl.workshop1.model.Klant;
import nl.workshop1.model.Role;
import nl.workshop1.utility.Slf4j;
import nl.workshop1.view.OutputText;

/**
 *
 * @author FeniksBV
 */
public class Mattenshop {

    public static void main(String[] args) {
        // Slf4j Logger
        Slf4j.getLogger().info("Mattenshop started");

        // Start the login procedure
        LoginController loginCtrl = new LoginController();
        loginCtrl.runController();
        switch (loginCtrl.getRequestedAction()) {
            case "0":
                // Afsluiten
                mattenshopAfsluiten();
                break;
            case "1":
                // een valid Login.
                // Lees klantgegevens indien een klant is ingelogd
                String loginName = loginCtrl.getLoginAccount().getUserName();
                if (loginCtrl.getLoginAccount().getRole().equals(Role.ROLE_KLANT)) {
                    Klant klant = DAOFactory.getKlantDAO().readKlant(loginCtrl.getLoginAccount().getKlantId());
                    if (klant != null) {
                        loginCtrl.getLoginAccount().setKlant(klant);
                        loginName = loginCtrl.getLoginAccount().getKlant().getFullName();
                    } else {
                        OutputText.showError("Ontbrekende klantgegevens! Neem contact op met de beheerder.");
                        mattenshopAfsluiten();
                    }
                }
                Menu.loginName = loginName;
                break;
        }

        HoofdMenuController hoofdmenuCtrl = new HoofdMenuController(loginCtrl.getLoginAccount().getRole());
        hoofdmenuCtrl.runController();

        // Forceer correct afsluiten
        mattenshopAfsluiten();

    }

    protected static void mattenshopAfsluiten() {

        OutputText.showError("\n\nThanks for using Mattenshop. See you soon!");

        Slf4j.getLogger().info("Mattenshop ended");
        System.exit(0);
    }
}
