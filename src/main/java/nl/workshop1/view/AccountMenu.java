package nl.workshop1.view;

import nl.workshop1.model.Account;

/**
 *
 * @author FeniksBV
 */
public class AccountMenu extends Menu {

    private String filter = "";

    public AccountMenu() {
        super("Accounts");
        setRecordHeader( String.format("     %-30s%-15s", "Gebruikersnaam","Role"));
    }

    public String display(Account account) {
        return String.format("%-30s%-15s", account.getUserName(), account.getRoleDescription() );
    }

}
