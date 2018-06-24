package nl.workshop1.controller;

import java.util.ArrayList;
import nl.workshop1.DAO.DAOFactory;
import nl.workshop1.model.Account;
import nl.workshop1.menu.AccountMenu;
import nl.workshop1.view.AccountMenuView;
import nl.workshop1.view.UserInput;

/**
 *
 * @author FeniksBV
 */
public class AccountMenuController extends MenuController {

    private AccountMenu accountMenu;
    private AccountMenuView accountMenuView;
    private ArrayList<Account> accountList;

    public AccountMenuController() {
        this.accountMenu = new AccountMenu();
        this.accountMenuView = new AccountMenuView(this.accountMenu);
        this.accountList = new ArrayList<>();
    }

    @Override
    public void runController() {
        AccountChangeMenuController accountCtrl;

        while (true) {

            accountMenu.clearSubMenuList();
            accountMenu.getRecordList().clear();
            accountMenu.buildGeneralSubMenuList();
            searchWithFilter();

            accountMenuView.setMenu(accountMenu);
            requestedAction = accountMenuView.runViewer();
            switch (requestedAction) {
                case "0":
                    return;

                case "1":

                case "3":
                    if (requestedAction.equals("1")) {
                        // NIEUW
                        accountCtrl = new AccountChangeMenuController();
                    } else {
                        // WIJZIG
                        accountCtrl = new AccountChangeMenuController(accountList.get(accountMenu.getRecordSelectedIndex()));
                    }
                    accountCtrl.runController();
                    accountMenu.setRecordSelected(false);
                    break;

                case "2":
                    // Nieuw filter, doorloop searchWithFilter en menu-opbouw
                    break;

                case "4":
                    // Verwijder
                    Account delAccount = accountList.get(accountMenu.getRecordSelectedIndex());
                    if (UserInput.getInputAkkoord("\nVerwijderen account !!")) {
                        DAOFactory.getAccountDAO().deleteAccount(delAccount.getUserName());
                    }
                    accountMenu.setRecordSelected(false);
                    break;

            }
        }
    }

    protected void searchWithFilter() {
        accountMenu.setRecordSelected(false);
        accountList = DAOFactory.getAccountDAO().readAccountWithFilter(accountMenu.getFilter());
        for (Account acc : accountList) {
            if (acc.getKlant() != null) {
                acc.setKlant(DAOFactory.getKlantDAO().readKlant(acc.getKlant().getId()));
            }
            accountMenu.getRecordList().add(acc);
        }
    }


}
