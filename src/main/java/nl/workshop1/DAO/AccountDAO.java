package nl.workshop1.DAO;

import java.util.ArrayList;
import nl.workshop1.model.Account;

/**
 *
 * @author FeniksBV
 */
public interface AccountDAO {

    public Account readAccountByUserName(String userName);

    public ArrayList<Account> readAccountWithFilter(String filter);

    public void deleteAccount(String userName);

    public void insertAccount(Account account);

    public void updateAccount(Account account);
}
