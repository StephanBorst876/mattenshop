package nl.workshop1.controller;

import nl.workshop1.model.Account;
import nl.workshop1.view.Menu;
import nl.workshop1.view.LoginView;

/**
 *
 * @author FeniksBV
 */
public class LoginController extends Controller {

    private Menu loginMenu;
    private LoginView loginView;

    public LoginController() {
        this.loginMenu = new Menu("Inloggen");
        this.loginView = new LoginView(loginMenu);
    }

    @Override
    public void runController() {

        while (true) {
            requestedAction = loginView.runViewer();
            switch (requestedAction) {
                case "0":
                    // Geen correcte login
                    return;
                case "1":
                    // WEL een correcte login
                    return;
            }
                    
        }
    }

    public Account getLoginAccount() {
        return loginView.getLoginAccount();
    }

}
