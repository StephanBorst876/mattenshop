package nl.workshop1.DAO;

import com.vogella.junit.first.sqlExecuteStatement;
import java.util.ArrayList;
import nl.workshop1.model.Account;
import nl.workshop1.model.Role;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author FeniksBV
 */
public class AccountDAOImplTest {
    
    public AccountDAOImplTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    // executed before each test
    public void setUp() {
        String query = "DELETE FROM account WHERE email = \"stephan@borst.nl\";";
        sqlExecuteStatement.executeStatement(query);
        query = "INSERT INTO account (email, wachtwoord, account_type) VALUES (\"stephan@borst.nl\", \"stephan\", \"Medewerker\");";
        sqlExecuteStatement.executeStatement(query);
    }

    @After
    // executed after each test
    public void tearDown() {
        setUp();
        String query = "DELETE FROM account WHERE email = \"a\";";
        sqlExecuteStatement.executeStatement(query);
    }

    /**
     * Test of readAccountByUserName method, of class AccountDAOImpl.
     */
    @Test
    public void testReadAccountByUserName() {
        String userName = "stephan@borst.nl";
        AccountDAOImpl instance = new AccountDAOImpl();
        Account result = instance.readAccountByUserName(userName);

        Account expResult = new Account(userName, "stephan");
        expResult.setRole(Role.Medewerker);
        assertEquals(expResult, result);
    }

    /**
     * Test of readAccountWithFilter method, of class AccountDAOImpl.
     */
    @Test
    public void testReadAccountWithFilter() {
        String filter = "stephan@borst";
        AccountDAOImpl instance = new AccountDAOImpl();
        ArrayList<Account> result = instance.readAccountWithFilter(filter);

        ArrayList<Account> expResult = new ArrayList<>();
        Account account = new Account("stephan@borst.nl", "stephan");
        account.setRole(Role.Medewerker);
        expResult.add(account);

        assertEquals(expResult, result);
    }

    /**
     * Test of deleteAccount method, of class AccountDAOImpl.
     */
    @Test
    public void testDeleteAccount() {
        String userName = "stephan@borst.nl";
        AccountDAOImpl instance = new AccountDAOImpl();
        instance.deleteAccount(userName);
    }

    /**
     * Test of insertAccount method, of class AccountDAOImpl.
     */
    @Test
    public void testInsertAccount() {
        Account account = new Account("a", "a");
        account.setRole(Role.Admin);
        AccountDAOImpl instance = new AccountDAOImpl();
        instance.insertAccount(account);
    }

    /**
     * Test of updateAccount method, of class AccountDAOImpl.
     */
    @Test
    public void testUpdateAccount() {
        Account account = new Account("a", "aaaa");
        account.setRole(Role.Medewerker);
        AccountDAOImpl instance = new AccountDAOImpl();
        instance.updateAccount(account);
    }
    
}
