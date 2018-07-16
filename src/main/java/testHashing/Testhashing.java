package testHashing;

import nl.workshop1.DAO.DAOFactory;
import nl.workshop1.model.Account;
import nl.workshop1.model.Role;
import nl.workshop1.utility.Password;

/**
 *
 * @author FeniksBV
 */
public class Testhashing {

    public static void main(String[] args) {
        String ww = "piet";
        byte[] salt = Password.getNextSalt();

        Account account = new Account();
        account.setUserName("boer@piet.nl");
        account.setRole(Role.Admin);
        account.setKlantId(0);

        System.out.println("Password = " + ww);
        System.out.println("salt     = " + new String(salt));
        byte[] hashedPwd = Password.hash(ww.toCharArray(), salt);
        account.setWachtwoord(new String(salt) + new String(hashedPwd));

        System.out.println("hashed   = " + account.getWachtwoord());
        System.out.println("Expected = " + Password.isExpectedPassword(ww.toCharArray(),
                salt, hashedPwd));
        System.out.println();
        DAOFactory.getAccountDAO().updateAccount(account);

        Account newAccount = DAOFactory.getAccountDAO().readAccountByUserName(account.getUserName());
        System.out.println("Password = " + newAccount.getWachtwoord());

        String newSalt = newAccount.getWachtwoord().substring(0, 16);
        byte[] newHashedPwd = Password.hash(ww.toCharArray(), newSalt.getBytes());
        System.out.println("salt     = " + newSalt);
        System.out.println("salt     = " + newSalt);
        System.out.println("hashed   = " + new String(newHashedPwd));
        System.out.println("Expected = " + Password.isExpectedPassword(ww.toCharArray(),
                newSalt.getBytes(), newHashedPwd));

    }
}
