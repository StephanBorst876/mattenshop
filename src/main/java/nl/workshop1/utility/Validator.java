package nl.workshop1.utility;

import nl.workshop1.DAO.DAOFactory;
import nl.workshop1.model.Account;
import org.apache.commons.validator.routines.EmailValidator;

/**
 *
 * @author FeniksBV
 */
public class Validator {

    /**
     *
     * @param userName
     * @param wachtwoord
     * @return loginAccount
     */
    public static Account validLogin(String userName, String wachtwoord) {
        Account account = DAOFactory.getAccountDAO().readAccountByUserName(userName);
        if (account != null) {
            if (account.getWachtwoord() != null) {
                if (Password.isExpectedPassword(wachtwoord, account.getWachtwoord())) {
                    return account;
                }
            }
        }
        return null;
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
