package nl.workshop1.controller;

import nl.workshop1.model.Account;
import nl.workshop1.view.LoginMenu;
import nl.workshop1.utility.Validator;
import nl.workshop1.view.LoginView;

/**
 *
 * @author FeniksBV
 */
public class LoginController extends MenuController {

    private Account loginAccount;
    private LoginMenu loginMenu;
    private LoginView loginView;

    // The model for Login equals the AccountModel !
    public LoginController(LoginMenu loginMenu, LoginView loginView) {
        this.loginMenu = loginMenu;
        this.loginView = loginView;
        loginAccount = new Account();
    }

    public Account getLoginAccount() {
        return loginAccount;
    }

    @Override
    public void buildOptionsMenu() {
        loginMenu.disableAddReturnOption();
    }

    @Override
    public void handleMenu() {
        int numberOfTries = 0;

        loginView.showError("\n\n\nHello!! Welcome at workshop1 -> Mattenshop.");

        // During development set a default account
        loginAccount.setUserName("boer@piet.nl");
        loginAccount.setWachtwoord("piet");
        if (Validator.validLoginAccount(loginAccount)) {
            return;
        }

        loginMenu.drawMenu();
        
        do {
            if (numberOfTries != 0) {
                loginView.showError("Geen geldige inloggegevens. Probeer opnieuw.");
            }
            numberOfTries++;
            loginView.showMessage("Een lege gebruikersnaam of wachtwoord zal de applicatie afsluiten.");
            String userName = loginView.getInputUsername();
            if (userName == null) {
                AfsluitMenuController.mattenshopAfsluiten();
            }

            String wachtwoord = loginView.getInputWachtwoord();
            if (wachtwoord.equals("")) {
                AfsluitMenuController.mattenshopAfsluiten();
            }
            loginAccount.setUserName(userName);
            loginAccount.setWachtwoord(wachtwoord);

        } while (!Validator.validLoginAccount(loginAccount));

    }
}
