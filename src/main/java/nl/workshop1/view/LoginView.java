package nl.workshop1.view;

import nl.workshop1.model.Account;
import nl.workshop1.utility.Validator;

/**
 *
 * @author FeniksBV
 */
public class LoginView extends View {

    private Account loginAccount = new Account();
    private String userName;
    private String wachtwoord;

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
            // Test met verschillende soorten (Roles) accounts
            userName = "boer@piet.nl";
            wachtwoord = "piet";
//            userName = "stephan@borst.nl";
//             wachtwoord = "stephan";
//             userName = "klant1@klant.nl";
//             wachtwoord = "klant1";
            loginAccount = Validator.validLogin(userName, wachtwoord);
            if (loginAccount != null) {
                OutputText.showMessage("Autologin enabled for : " + loginAccount.getUserName());
                return "1";
            }

            // Hier start de (productie) procedure !!
            OutputText.showMessage("Een lege gebruikersnaam zal de applicatie afsluiten.");
            userName = getInputUsername(/*allowEmptyInput=*/true);
            if (userName == null) {
                loginAccount = null;
                return "0";
            }

            wachtwoord = getInputWachtwoord();

            loginAccount = Validator.validLogin(userName, wachtwoord);
            if (loginAccount != null) {
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
