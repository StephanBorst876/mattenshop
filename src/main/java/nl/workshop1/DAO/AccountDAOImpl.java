package nl.workshop1.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import nl.workshop1.model.Account;
import nl.workshop1.model.Role;
import nl.workshop1.utility.Slf4j;

/**
 *
 * @author FeniksBV
 */
public class AccountDAOImpl implements AccountDAO {

    private final String ACCOUNT_SELECT
            = "SELECT email, wachtwoord, salt, account_type, klant_id "
            + "FROM account "
            + "WHERE email = ?";

    private final String ACCOUNT_LIKE
            = "SELECT email, wachtwoord, salt, account_type, klant_id "
            + "FROM account "
            + "WHERE email like ?";

    private final String ACCOUNT_DELETE
            = "delete from account where email = ?";

    private final String ACCOUNT_INSERT
            = "insert into account (email, wachtwoord, salt, account_type,klant_id) values (?, ?, ?, ?, ?)";

    private final String ACCOUNT_UPDATE
            = "update account "
            + "set wachtwoord=?,salt=?,account_type=?,klant_id=? "
            + "where email = ?";

    protected ArrayList<Account> selectAccount(String query, String userName) {

        Slf4j.getLogger().info(query + " " + userName);

        ArrayList<Account> accountList = new ArrayList<>();

        try (Connection connObj = DbConnection.getConnection();
                PreparedStatement pstmtObj = connObj.prepareStatement(query);) {

            pstmtObj.setString(1, userName);
            try (ResultSet resultSet = pstmtObj.executeQuery()) {
                while (resultSet.next()) {
                    Account account = new Account();
                    account.setUserName(resultSet.getString("email"));
                    account.setWachtwoord(resultSet.getString("wachtwoord"));
                    account.setSalt(resultSet.getString("salt"));
                    account.setRole(Role.valueOf(resultSet.getString("account_type")));
                    account.setKlantId(resultSet.getInt("klant_id"));
                    accountList.add(account);
                }
            }

        } catch (Exception sqlException) {
            Slf4j.getLogger().error("SQL exception occurred", sqlException);
        }
        return accountList;

    }

    @Override
    public Account readAccountByUserName(String userName) {

        ArrayList<Account> accountList = selectAccount(ACCOUNT_SELECT, userName);
        if (accountList.size() == 1) {
            return accountList.get(0);
        }
        return null;
    }

    @Override
    public ArrayList<Account> readAccountWithFilter(String filter) {

        ArrayList<Account> accountList = selectAccount(ACCOUNT_LIKE, "%" + filter + "%");

        return accountList;
    }

    @Override
    public void deleteAccount(String userName) {

        Slf4j.getLogger().info("deleteAccount({})", userName);

        try (Connection connObj = DbConnection.getConnection();
                PreparedStatement pstmtObj = connObj.prepareStatement(ACCOUNT_DELETE);) {

            pstmtObj.setString(1, userName);
            pstmtObj.execute();
            Slf4j.getLogger().info("deleteAccount() ended.");

        } catch (Exception sqlException) {
            Slf4j.getLogger().error("SQL exception occurred ", sqlException);
        }
    }

    @Override
    public void insertAccount(Account account) {
        Slf4j.getLogger().info("insertAccount({})", account.getUserName());

        try (Connection connObj = DbConnection.getConnection();
                PreparedStatement pstmtObj = connObj.prepareStatement(ACCOUNT_INSERT);) {

            pstmtObj.setString(1, account.getUserName());
            pstmtObj.setString(2, account.getWachtwoord());
            pstmtObj.setString(3, account.getSalt());
            pstmtObj.setString(4, account.getRole().getDescription());
            // Indien ROLE_KLANT dan update het klantID, anders null

            if (account.getRole() == Role.Klant) {
                pstmtObj.setInt(5, account.getKlantId());
            } else {
                pstmtObj.setNull(5, Types.INTEGER);
            }

            pstmtObj.execute();
            Slf4j.getLogger().info("insertAccount() ended.");

        } catch (Exception sqlException) {
            Slf4j.getLogger().error("SQL exception occurred ", sqlException);
        }
    }

    @Override
    public void updateAccount(Account account
    ) {
        Slf4j.getLogger().info("updateAccount({})", account.getUserName());

        try (Connection connObj = DbConnection.getConnection();
                PreparedStatement pstmtObj = connObj.prepareStatement(ACCOUNT_UPDATE);) {

            pstmtObj.setString(1, account.getWachtwoord());
            pstmtObj.setString(2, account.getSalt());
            pstmtObj.setString(3, account.getRole().getDescription());
            if (account.getRole() == Role.Klant) {
                pstmtObj.setInt(4, account.getKlantId());
            } else {
                pstmtObj.setNull(4, Types.INTEGER);
            }
            pstmtObj.setString(5, account.getUserName());

            pstmtObj.execute();
            Slf4j.getLogger().info("updateAccount() ended.", pstmtObj.toString());

        } catch (Exception sqlException) {
            Slf4j.getLogger().error("SQL exception occurred ", sqlException);
        }
    }
}
