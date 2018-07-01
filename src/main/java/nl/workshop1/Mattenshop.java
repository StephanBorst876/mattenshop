package nl.workshop1;

import nl.workshop1.DAO.DAOFactory;
import nl.workshop1.controller.LoginController;
import nl.workshop1.controller.HoofdMenuController;
import nl.workshop1.model.Klant;
import nl.workshop1.model.Role;
import nl.workshop1.utility.Slf4j;
import nl.workshop1.view.Menu;
import nl.workshop1.view.OutputText;

/**
 *
 * @author FeniksBV
 */
public class Mattenshop {

    public static void main(String[] args) {
        Klant klant = null;
        
        // Slf4j Logger
        Slf4j.getLogger().info("Mattenshop started");

        // Start the login procedure
        LoginController loginCtrl = new LoginController();
        loginCtrl.runController();
        switch (loginCtrl.getRequestedAction()) {
            case "0":
                // Geen correcte login
                mattenshopAfsluiten();
                return;
            case "1":
                // een correcte Login.
                // Lees klantgegevens indien een klant is ingelogd
                Menu.loginName = loginCtrl.getLoginAccount().getUserName();
                if (loginCtrl.getLoginAccount().getRole().equals(Role.Klant)) {
                    klant = DAOFactory.getKlantDAO().readKlant(loginCtrl.getLoginAccount().getKlantId());
                    if (klant == null) {
                        OutputText.showError("Ontbrekende klantgegevens! Neem contact op met de beheerder.");
                        mattenshopAfsluiten();
                    }
                }
                break;
        }

        HoofdMenuController hoofdmenuCtrl;
        if(klant== null) {
            hoofdmenuCtrl = new HoofdMenuController(loginCtrl.getLoginAccount().getRole());
        }
        else {
            hoofdmenuCtrl = new HoofdMenuController(loginCtrl.getLoginAccount().getRole(), klant);
        }
        hoofdmenuCtrl.runController();
        if (hoofdmenuCtrl.getRequestedAction().equals("1")) {
            // Oeps. Pas hier op: Alle controllers/menu's geven optie=0 als zijnde
            // terug, maar de afsluitController geeft 1 als Afsluiten.
        }

        // Forceer correct afsluiten
        mattenshopAfsluiten();

    }

    protected static void mattenshopAfsluiten() {

        OutputText.showError("\n\nThanks for using Mattenshop. See you soon!");

        Slf4j.getLogger().info("Mattenshop ended");
        System.exit(0);
    }
}
