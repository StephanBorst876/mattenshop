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
     * @return  loginAccount
     */
    public static Account validLogin(String userName, String wachtwoord) {
        Account account = DAOFactory.getAccountDAO().readAccountByUserName(userName);
        if (account != null) {
            if (account.getWachtwoord() != null) {
                // Volgende NIET meer gebruiken !!
                //  if (loginAccount.getWachtwoord().equals(account.getWachtwoord())) {
                //
                String salt = account.getWachtwoord().substring(0, 16);
                String ww = account.getWachtwoord().substring(16);
                if (Password.isExpectedPassword(wachtwoord.toCharArray(),
                        salt.getBytes(), ww.getBytes())) {
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
