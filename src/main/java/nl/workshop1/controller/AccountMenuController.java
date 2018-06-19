package nl.workshop1.controller;

import java.util.ArrayList;
import nl.workshop1.model.Account;
import nl.workshop1.menu.AccountChangeMenu;
import nl.workshop1.menu.AccountMenu;
import nl.workshop1.view.AccountChangeMenuView;

/**
 *
 * @author FeniksBV
 */
public class AccountMenuController extends MenuController {

    private AccountMenu accountMenu;
    private ArrayList<Account> accountList = new ArrayList<>();

    public AccountMenuController(AccountMenu accountMenu) {
        this.accountMenu = accountMenu;
    }

    @Override
    public void buildOptionsMenu() {
        if (!accountMenu.isRecordSelected()) {
            accountMenu.resetMenu();
            searchWithFilter();
        } else {
            accountMenu.clearSubMenu();
        }
        accountMenu.buildMenu();
    }

    @Override
    public void handleMenu() {
        AccountChangeMenu accountChangeMenu;
        AccountChangeMenuView accountChangeMenuView;

        while (true) {
            buildOptionsMenu();
            accountMenu.drawMenu();
            switch (accountMenu.userChoice()) {
                case "0":
                    return;
                case "1":
                    // NEW
                    accountChangeMenu = new AccountChangeMenu("Account toevoegen", new Account());
                    accountChangeMenuView = new AccountChangeMenuView();
                    AccountChangeMenuController accountNew = new AccountChangeMenuController(MODE_NIEUW, accountChangeMenu, accountChangeMenuView);
                    accountNew.runController();
                    accountMenu.setRecordSelected(false);
                    break;
                case "2":
                    // Nieuw filter ingeven
                    accountMenu.setFilter(accountMenu.getMenuView().getInputFilter());
                    accountMenu.setRecordSelected(false);
                    break;
                case "3":
                    // Wijzig
                    accountChangeMenu = new AccountChangeMenu("Account wijzigen", accountList.get(accountMenu.getRecordSelectedIndex()));
                    accountChangeMenuView = new AccountChangeMenuView();
                    AccountChangeMenuController accountChange = new AccountChangeMenuController(MODE_WIJZIG, accountChangeMenu, accountChangeMenuView);
                    accountChange.runController();
                    accountMenu.setRecordSelected(false);
                    break;
                case "4":
                    // Verwijder
                    Account delAccount = accountList.get(accountMenu.getRecordSelectedIndex());
                    if (accountMenu.getMenuView().getInputAkkoord("\nVerwijderen account: " + accountMenu.display(delAccount))) {
                        AccountDAOController.deleteAccount(delAccount.getUserName());
                    }
                    accountMenu.setRecordSelected(false);
                    break;
                default:
                    // In this case a char 'A' till 'Z' might be pressed.
                    break;
            }
        }
    }

    protected void searchWithFilter() {
        accountList = AccountDAOController.readAccountWithFilter(accountMenu.getFilter());
        for (int i = 0; i < accountList.size(); i++) {
            accountMenu.addRecordList(accountMenu.display(accountList.get(i)));
        }
    }

}
