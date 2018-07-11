package nl.workshop1.view;

import nl.workshop1.model.Account;
import nl.workshop1.utility.Validator;

/**
 *
 * @author FeniksBV
 */
public class LoginView extends View {

    private Account loginAccount = new Account();

    public LoginView(Menu loginMenu) {
        super(loginMenu);
    }

    @Override
    public String runViewer() {

        OutputText.showError("\n\n\nHello!! Welcome at workshop1 -> Mattenshop.");
        
        // Een menu met alleen een titel, dus eenmalig opbouwen
        drawMenu();
        
        while (true) {
            // TODO: Tijdens ontwikkeling, gebruik een default en auto login
            // Test met verschillende soorten (Roles) ccounts
            loginAccount.setUserName("boer@piet.nl");
            loginAccount.setWachtwoord("piet");
//        loginAccount.seunt.setUserName("stephan@borst.nl");
//        loginAccoutWachtwoord("stephan");
//        loginAccount.setUserName("klant1@klant.nl");
//        loginAccount.setWachtwoord("klant1");
            if (Validator.validLoginAccount(loginAccount)) {
                OutputText.showMessage("Autologin enabled");
                return "1";
            }

            // Hier start de (productie) procedure !!
            OutputText.showMessage("Een lege gebruikersnaam zal de applicatie afsluiten.");
            loginAccount.setUserName(getInputUsername(true));
            if (loginAccount.getUserName() == null) {
                loginAccount = null;
                return "0";
            }
            
            loginAccount.setWachtwoord(getInputWachtwoord());

            if (Validator.validLoginAccount(loginAccount)) {
                return "1";
            } else {
                OutputText.showError("Geen geldige inloggegevens. Probeer opnieuw.");
            }
        }
    }

    /**
     * @return the loginAccount
     */
    public Account getLoginAccount() {
        return loginAccount;
    }

}
