package nl.workshop1.view;

import nl.workshop1.model.Account;
import nl.workshop1.model.Klant;
import nl.workshop1.model.Role;
import nl.workshop1.utility.Password;

/**
 *
 * @author FeniksBV
 */
public class AccountView extends View {

    private Menu accountViewMenu;
    private Account initialAccount = null;
    private Account account = null;
    private Klant klant = null;

    /**
     *
     * @param accountViewMenu
     * @param account
     */
    public AccountView(Menu accountViewMenu, Account account) {
        super(accountViewMenu);
        this.accountViewMenu = accountViewMenu;
        if (account == null) {
            this.account = new Account();
        } else {
            this.account = (Account) account.clone();
            initialAccount = (Account) account.clone();
        }
    }

    public Account getAccount() {
        return account;
    }

    public void setKlant(Klant klant) {
        this.klant = klant;
    }

    @Override
    public String runViewer() {

        // Initially ask for input all datafields
        if (account.getUserName().isEmpty()) {
            // Nu weet je zeker dat dit de eerste keer is
            account.setUserName(getInputUsername(/*allowEmptyInput=*/false));
            account.setWachtwoord(getInputWachtwoord());
            account.setRole(getInputRole());

            if (account.getRole() == Role.Klant) {
                // Forceer dat er een klant wordt gekozen
                return "4";
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
                    account.setUserName(getInputUsername(/*allowEmptyInput=*/false));
                    break;
                case "2":
                    String wachtwoord = getInputWachtwoord();
                    byte[] salt = Password.getNextSalt();
                    byte[] hashed = Password.hash(wachtwoord.toCharArray(),salt);
                    account.setWachtwoord(new String(salt) + new String(hashed));
                    break;
                case "3":
                    account.setRole(getInputRole());
                    break;
                case "4":
                    // Klant zoeken, want Role:KLANT gekozen
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
        accountViewMenu.clearSubMenuList();
        if (initialAccount == null) {
            // New account may change the userName
            accountViewMenu.addSubMenu("Username", account.getUserName(), "1");
        } else {
            // Modifying an account, username is not allowed to change
            accountViewMenu.addSubMenu("Username", account.getUserName(), "99");
        }
        accountViewMenu.addSubMenu("Wachtwoord", "**********", "2");
        accountViewMenu.addSubMenu("Role", account.getRole().getDescription(), "3");

        if (account.getRole() == Role.Klant) {
            if (klant != null) {
                accountViewMenu.addSubMenu("Klant", klant.getFullName(), "4");
                account.setKlantId(klant.getId());
                accountViewMenu.addSubMenu("Opslaan", "5");
            } else {
                accountViewMenu.addSubMenu("Klant", "", "4");
            }
        } else {
            klant = null;
            accountViewMenu.addSubMenu("Opslaan", "5");
        }
        accountViewMenu.addSubMenu("Terug", "0");

    }

    protected Role getInputRole() {
        while (true) {
            OutputText.showMessage("Role: " + allRoleOptions());
            String s = getInputChoice();
            if (s.length() >= 1) {
                Role role = charNaarRole(s.charAt(0));
                if (role != null) {
                    return role;
                }
            }
        }
    }

    protected Role charNaarRole(char role) {
        switch (role) {
            case 'A':
                return Role.Admin;
            case 'M':
                return Role.Medewerker;
            case 'K':
                return Role.Klant;
            default:
                return null;
        }
    }

    protected String allRoleOptions() {
        StringBuilder str = new StringBuilder();
        String s = Role.Admin.getDescription();
        str.append(s.charAt(0)).append('=').append(s).append(",");
        s = Role.Medewerker.getDescription();
        str.append(s.charAt(0)).append('=').append(s).append(",");
        s = Role.Klant.getDescription();
        str.append(s.charAt(0)).append('=').append(s);
        return str.toString();
    }

}
