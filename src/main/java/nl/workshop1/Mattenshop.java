package nl.workshop1;

import java.io.FileNotFoundException;
import java.io.IOException;
import nl.workshop1.controller.LoginController;
import nl.workshop1.controller.HoofdMenuController;
import nl.workshop1.view.HoofdMenu;
import nl.workshop1.view.LoginMenu;
import nl.workshop1.utility.Slf4j;
import nl.workshop1.view.LoginView;

/**
 *
 * @author FeniksBV
 */
public class Mattenshop {

    public static void main(String[] args) throws FileNotFoundException, IOException {
        // Slf4j Logger
        Slf4j.getLogger().info("Mattenshop started");
        
        // Start the login procedure
        LoginMenu loginMenu = new LoginMenu();
        LoginView loginView = new LoginView();
        LoginController loginCtrl = new LoginController(loginMenu, loginView);
        loginCtrl.runController();

        HoofdMenu hoofdMenu = new HoofdMenu(loginCtrl.getLoginAccount());
        HoofdMenuController hoofdmenuCtrl = new HoofdMenuController(hoofdMenu);
        hoofdmenuCtrl.runController();

    }
}
