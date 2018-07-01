package nl.workshop1.view;

import nl.workshop1.controller.Controller;
import nl.workshop1.model.Account;
import nl.workshop1.model.Klant;
import nl.workshop1.model.Role;

/**
 *
 * @author FeniksBV
 */
public class AccountView extends View {

    private int accountMode;
    private Menu accountChangeMenu;
    private Account account;
    private Klant klant = null;

    public AccountView(int mode, Menu accountChangeMenu) {
        super(accountChangeMenu);
        this.accountMode = mode;
        this.accountChangeMenu = accountChangeMenu;
        account = new Account();
    }

    public AccountView(int mode, Menu accountChangeMenu, Account account) {
        super(accountChangeMenu);
        this.accountMode = mode;
        this.accountChangeMenu = accountChangeMenu;
        this.account = (Account) account.clone();
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
        if (accountMode == Controller.MODE_NIEUW) {
            if (account.getUserName().isEmpty()) {
                // Nu weet je zeker dat dit de eerste keer is
                account.setUserName(getInputUsername(false));
                account.setWachtwoord(getInputWachtwoord());
                account.setRole(getInputRole());
                
                if (account.getRole() == Role.Klant){
                    // Forceer dat er een klant wordt gekozen
                    return "4";
                }
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
        accountChangeMenu.clearSubMenuList();
        if (accountMode == Controller.MODE_NIEUW) {
            // New account may change the userName
            accountChangeMenu.addSubMenu("Username", account.getUserName(), "1");
        } else {
            // Modifying an account, username is not allowed to change
            accountChangeMenu.addSubMenu("Username", account.getUserName(), "99");
        }
        accountChangeMenu.addSubMenu("Wachtwoord", account.getWachtwoord(), "2");
        accountChangeMenu.addSubMenu("Role", account.getRole().getDescription(), "3");

        if (account.getRole() == Role.Klant) {
            if (klant != null) {
                accountChangeMenu.addSubMenu("Klant", klant.getFullName(), "4");
                account.setKlantId(klant.getId());
                accountChangeMenu.addSubMenu("Opslaan", "5");
            } else {
               accountChangeMenu.addSubMenu("Klant", "", "4"); 
            }
        } else {
            klant = null;
            accountChangeMenu.addSubMenu("Opslaan", "5");
        }

    }

    public Role getInputRole() {
        while (true) {
            OutputText.showMessage("Role: " + allOptions());
            String s = getInputChoice();
            if (s.length() == 1) {
                Role role = stringNaarRole(s);
                if (role != null) {
                    return role;
                }
            }
        }
    }

    protected Role stringNaarRole(String role) {
        switch (role) {
            case "A":
                return Role.Admin;
            case "M":
                return Role.Medewerker;
            case "K":
                return Role.Klant;
            default:
                return null;
        }
    }
    
    protected String allOptions() {
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
