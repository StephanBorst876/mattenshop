package nl.workshop1.controller;

import nl.workshop1.DAO.DAOFactory;
import nl.workshop1.menu.AccountChangeMenu;
import nl.workshop1.model.Account;
import nl.workshop1.model.Klant;
import nl.workshop1.model.Role;
import nl.workshop1.view.AccountView;

/**
 *
 * @author FeniksBV
 */
public class AccountChangeMenuController extends MenuController {

    private AccountView accountView;
    private Account initialAccount = null;

    public AccountChangeMenuController() {
        // Een nieuw account
        accountView = new AccountView(MODE_NIEUW, new AccountChangeMenu("Account toevoegen"));
    }

    public AccountChangeMenuController(Account account) {
        // bestaand account
        initialAccount = account;

        accountView = new AccountView(MODE_WIJZIG, new AccountChangeMenu("Account wijzigen"), account);
    }

    @Override
    public void runController() {
        if (initialAccount != null) {
            if (initialAccount.getRole() == Role.ROLE_KLANT) {
                // Zoek ook de klantgegevens op
                initialAccount.setKlant(selectKlant());
            }
        }

        while (true) {
            requestedAction = accountView.runViewer();
            switch (requestedAction) {
                case "0":
                    return;
                case "4":
                    // Zoek naar klantgegevens
                    break;
                case "5":
                    // update / insert
                    if (initialAccount == null) {
                        // Insert
                        DAOFactory.getAccountDAO().insertAccount(accountView.getAccount());
                    } else {
                        DAOFactory.getAccountDAO().updateAccount(accountView.getAccount());
                    }
                    return;

            }
        }
    }

    protected Klant selectKlant() {
        KlantMenuController klantMenuCtrl = new KlantMenuController(MenuController.CONTROLLER_MODE_SEARCH);
        klantMenuCtrl.runController();
        Klant klant = klantMenuCtrl.getKlantSelected();
        if (klant != null) {
            return klant;
        }
        return null;
    }

}
