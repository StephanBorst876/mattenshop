package nl.workshop1.utility;

import nl.workshop1.DAO.DAOFactory;
import nl.workshop1.model.Account;
import nl.workshop1.model.Role;
import org.apache.commons.validator.routines.EmailValidator;

/**
 *
 * @author FeniksBV
 */
public class Validator {

    public static boolean validLoginAccount(Account loginAccount) {

        Slf4j.getLogger().info("validLoginAccount({})", loginAccount.getUserName());

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
        if (postcode.matches("/^[1-9][0-9]{3}?[a-z]{2}$/i")) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean validAccount(Account account) {
        // Alle data velden zijn gevalideerd bij input.
        // Controleer of voor role=Klant ook een klant is gespecificeerd
        if (account.getRole().equals(Role.ROLE_KLANT)) {
            if (account.getKlant() == null) {
                return false;
            }
        }
        return true;
    }
}
