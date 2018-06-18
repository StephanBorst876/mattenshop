package nl.workshop1.controller;

import java.util.ArrayList;
import nl.workshop1.DAO.DAOFactory;
import nl.workshop1.model.Account;

/**
 *
 * @author FeniksBV
 */
public class AccountDAOController {

    public static ArrayList<Account> readAccountWithFilter(String filter) {
        return DAOFactory.getAccountDAO().readAccountWithFilter(filter);
    }

    public static Account readAccountByUserName(String userName) {
        return DAOFactory.getAccountDAO().readAccountByUserName(userName);
    }

    public static void deleteAccount(String userName) {
        DAOFactory.getAccountDAO().deleteAccount(userName);
    }

    public static void insertAccount(Account account) {
        DAOFactory.getAccountDAO().insertAccount(account);
    }
    
    public static void updateAccount(Account account) {
        DAOFactory.getAccountDAO().updateAccount(account);
    }
}
