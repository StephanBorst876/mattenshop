package nl.workshop1.utility;

import nl.workshop1.DAO.DAOFactory;
import nl.workshop1.model.Account;
import org.apache.commons.validator.routines.EmailValidator;

/**
 *
 * @author FeniksBV
 */
public class Validator {

    public static boolean validLoginAccount(Account loginAccount) {
        Account account = DAOFactory.getAccountDAO().readAccountByUserName(loginAccount.getUserName());
        if (account != null) {
            if (account.getWachtwoord() != null) {
                if (loginAccount.getWachtwoord().equals(account.getWachtwoord())) {
                    loginAccount.setRole(account.getRole());
                    loginAccount.setKlantId(account.getKlantId());
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean validUsername(String gebruikersnaam) {
        EmailValidator emailvalidator = EmailValidator.getInstance();
        if (emailvalidator.isValid(gebruikersnaam)) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean validPostcode(String postcode) {
        // Postcode heeft het formaat 9999XX
        return postcode.matches("[1-9][0-9]{3}[A-Z]{2}");

    }

}
