package nl.workshop1.controller;

import nl.workshop1.view.AccountChangeMenu;
import nl.workshop1.view.Menu;
import nl.workshop1.view.AccountChangeMenuView;

/**
 *
 * @author FeniksBV
 */
public class AccountChangeMenuController extends MenuController {

    private AccountChangeMenu accountChangeMenu;
    private AccountChangeMenuView accountChangeMenuView;
    private int accountMode;

    public AccountChangeMenuController(int mode, AccountChangeMenu accountChangeMenu, AccountChangeMenuView accountChangeMenuView) {
        this.accountChangeMenu = accountChangeMenu;
        this.accountChangeMenuView = accountChangeMenuView;
        this.accountMode = mode;
    }

    @Override
    public void buildOptionsMenu() {
        accountChangeMenu.clearSubMenu();
        if (accountMode == Menu.MODE_NEW) {
            // New account may change the userName
            accountChangeMenu.addSubMenu(formatText("Username") + accountChangeMenu.getAccount().getUserName(), "1");
        } else {
            // Modifying an account, username is not allowed to change
            accountChangeMenu.addSubMenu(formatText("Username") + accountChangeMenu.getAccount().getUserName(), "99");
        }
        accountChangeMenu.addSubMenu(formatText("Wachtwoord") + accountChangeMenu.getAccount().getWachtwoord(), "2");
        accountChangeMenu.addSubMenu(formatText("Role") + accountChangeMenu.getAccount().getRoleDescription(), "3");
        accountChangeMenu.addSubMenu("Opslaan", "4");
    }

    @Override
    public void handleMenu() {
        // Initially ask for input all datafields
        if (accountMode == Menu.MODE_NEW) {
            accountChangeMenu.getAccount().setUserName(accountChangeMenuView.getInputUsername());
            accountChangeMenu.getAccount().setWachtwoord(accountChangeMenuView.getInputWachtwoord());
            accountChangeMenu.getAccount().setRole(accountChangeMenuView.getInputRole());
        }
        while (true) {
            buildOptionsMenu();
            accountChangeMenu.drawMenu();
            switch (accountChangeMenu.userChoice()) {
                case "0":
                    return;
                case "1":
                    accountChangeMenu.getAccount().setUserName(accountChangeMenuView.getInputUsername());
                    break;
                case "2":
                    accountChangeMenu.getAccount().setWachtwoord(accountChangeMenuView.getInputWachtwoord());
                    break;
                case "3":
                    accountChangeMenu.getAccount().setRole(accountChangeMenuView.getInputRole());
                    break;
                case "4":
                    if (accountMode == Menu.MODE_NEW) {
                        AccountDAOController.insertAccount(accountChangeMenu.getAccount());
                    } else {
                        AccountDAOController.updateAccount(accountChangeMenu.getAccount());
                    }
                    return;
                case "99":
                    accountChangeMenu.getMenuView().showMessage("\nWijzigen Username is NIET toegestaan!");
                    break;
            }
        }
    }

}
