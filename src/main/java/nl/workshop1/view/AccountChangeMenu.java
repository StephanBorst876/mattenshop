package nl.workshop1.view;

import nl.workshop1.model.Account;

/**
 *
 * @author FeniksBV
 */
public class AccountChangeMenu extends Menu {

    private Account account;

    public AccountChangeMenu(String title, Account initialAccount) {
        super(title);
        this.account = initialAccount;
    }

    public Account getAccount() {
        return account;
    }

    
}
