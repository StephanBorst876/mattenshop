package nl.workshop1.view;

import nl.workshop1.controller.MenuController;
import nl.workshop1.menu.AccountChangeMenu;
import nl.workshop1.model.Account;
import nl.workshop1.model.Role;
import nl.workshop1.utility.Converter;
import nl.workshop1.utility.Validator;

/**
 *
 * @author FeniksBV
 */
public class AccountView extends MenuView {

    private int accountMode;
    private AccountChangeMenu accountChangeMenu;
    private Account account;

    public AccountView(int mode, AccountChangeMenu accountChangeMenu) {
        super(accountChangeMenu);
        this.accountMode = mode;
        this.accountChangeMenu = accountChangeMenu;
        account = new Account();
    }

    public AccountView(int mode, AccountChangeMenu accountChangeMenu, Account account) {
        super(accountChangeMenu);
        this.accountMode = mode;
        this.accountChangeMenu = accountChangeMenu;
        this.account = (Account) account.clone();
    }

    public Account getAccount() {
        return account;
    }

    @Override
    public String runViewer() {

        // Initially ask for input all datafields
        if (accountMode == MenuController.MODE_NIEUW) {
            if (account.getUserName().equals("")) {
                // Nu weet je zeker dat dit de eerste keer is
                account.setUserName(getInputUsername(false));
                account.setWachtwoord(getInputWachtwoord());
                account.setRole(getInputRole());
            }
        }

        while (true) {
            buildSubMenuList();
            drawMenu();
            String requestedAction = userChoice();
            switch (requestedAction) {
                case "0":
                    return requestedAction;
                case "1":
                    account.setUserName(getInputUsername(false));
                    break;
                case "2":
                    account.setWachtwoord(getInputWachtwoord());
                    break;
                case "3":
                    account.setRole(getInputRole());
                    break;
                case "4":
                    // Klant zoeken
                    return requestedAction;
                case "5":
                    // Opslaan
                    return requestedAction;
                case "99":
                    OutputText.showError("Wijzigen Username is NIET toegestaan!");
                    break;
            }
        }

    }

    protected void buildSubMenuList() {
        accountChangeMenu.clearSubMenuList();
        if (accountMode == MenuController.MODE_NIEUW) {
            // New account may change the userName
            accountChangeMenu.addSubMenu("Username", account.getUserName(), "1");
        } else {
            // Modifying an account, username is not allowed to change
            accountChangeMenu.addSubMenu("Username", account.getUserName(), "99");
        }
        accountChangeMenu.addSubMenu("Wachtwoord", account.getWachtwoord(), "2");
        accountChangeMenu.addSubMenu("Role", account.getRole().getDescription(), "3");

        if (account.getRole().equals(Role.ROLE_KLANT)) {
            String klantDisplay = "";
            if (account.getKlant() != null) {
                klantDisplay = account.getKlant().getFullName();
            }
            accountChangeMenu.addSubMenu("Klant", klantDisplay, "4");
        } else {
            // Always reset when not role=Klant
            account.setKlant(null);
        }

        if (Validator.validAccount(account)) {
            accountChangeMenu.addSubMenu("Opslaan", "5");
        } else {
            OutputText.showError("Bij een role=Klant moet ook een klant zijn gespecificeerd.");
        }

    }

    public Role getInputRole() {
        while (true) {
            OutputText.showMessage("Role: " + allOptions());
            String s = getInputChoice();
            if (s.length() == 1) {
                Role role = Converter.stringNaarRole(s);
                if (role != null) {
                    return role;
                }
            }
        }
    }

    protected String allOptions() {
        StringBuilder str = new StringBuilder();
        String s = Role.ROLE_ADMIN.getDescription();
        str.append(s.charAt(0)).append('=').append(s).append(",");
        s = Role.ROLE_MEDEWERKER.getDescription();
        str.append(s.charAt(0)).append('=').append(s).append(",");
        s = Role.ROLE_KLANT.getDescription();
        str.append(s.charAt(0)).append('=').append(s);
        return str.toString();
    }

}
