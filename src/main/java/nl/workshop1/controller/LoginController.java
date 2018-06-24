package nl.workshop1.controller;

import nl.workshop1.model.Account;
import nl.workshop1.menu.LoginMenu;
import nl.workshop1.utility.Validator;
import nl.workshop1.view.LoginView;
import nl.workshop1.view.OutputText;

/**
 *
 * @author FeniksBV
 */
public class LoginController extends MenuController {

    // The model for Login equals the Account Model !
    private Account loginAccount;
    private LoginMenu loginMenu;
    private LoginView loginView;

    public LoginController() {
        this.loginMenu = new LoginMenu();
        loginAccount = new Account();
        this.loginView = new LoginView(loginMenu);
    }

    @Override
    public void runController() {

        OutputText.showError("\n\n\nHello!! Welcome at workshop1 -> Mattenshop.");

        // Een menu met alleen een titel, dus eenmalig opbouwen
        loginView.drawMenu();

        while (true) {

            // TODO: Tijdens ontwikkeling, gebruik een default en auto login
            // Test met verschillende soorten (Roles) ccounts
            loginAccount.setUserName("boer@piet.nl");
            loginAccount.setWachtwoord("piet");
//        loginAccont.seunt.setUserName("stephan@borst.nl");
//        loginAccoutWachtwoord("stephan");
//        loginAccount.setUserName("klant@klant.nl");
//        loginAccount.setWachtwoord("klant");
            if (Validator.validLoginAccount(loginAccount)) {
                OutputText.showMessage("Autologin enabled");
                requestedAction = "1";
                return;
            }

            // Hier start de eigenlijke procedure !!
            OutputText.showMessage("Een lege gebruikersnaam zal de applicatie afsluiten.");
            String userName = loginView.getInputUsername(true);
            if (userName == null) {
                requestedAction = "0";
                return;
            }

            String wachtwoord = loginView.getInputWachtwoord();
            
            loginAccount.setUserName(userName);
            loginAccount.setWachtwoord(wachtwoord);

            if (Validator.validLoginAccount(loginAccount)) {
                requestedAction = "1";
                return;
            } else {
                OutputText.showError("Geen geldige inloggegevens. Probeer opnieuw.");
            }

        }
    }

    public Account getLoginAccount() {
        return loginAccount;
    }

}
