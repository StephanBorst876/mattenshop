package nl.workshop1.controller;

import nl.workshop1.DAO.DAOFactory;
import nl.workshop1.model.Account;
import nl.workshop1.model.Klant;
import nl.workshop1.model.Role;
import nl.workshop1.view.AccountView;
import nl.workshop1.view.Menu;

/**
 *
 * @author FeniksBV
 */
public class AccountViewController extends Controller {

    private AccountView accountView;
    private Account initialAccount = null;

    public AccountViewController() {
        // Een nieuw account
        accountView = new AccountView(MODE_NIEUW, new Menu("Account toevoegen"));
    }

    public AccountViewController(Account account) {
        // bestaand account
        initialAccount = account;
        accountView = new AccountView(MODE_WIJZIG, new Menu("Account wijzigen"), account);
    }

    @Override
    public void runController() {

        // Indien een bestaand account wijzigen en het betreft een Role.KLANT
        // dan initialiseren we de klant data
        if (initialAccount != null) {
            if (initialAccount.getRole() == Role.Klant) {
                Klant klant = DAOFactory.getKlantDAO().readKlant(initialAccount.getKlantId());
                accountView.setKlant(klant);
            }
        }

        while (true) {
            requestedAction = accountView.runViewer();
            switch (requestedAction) {
                case "0":
                    return;
                case "4":
                    // Selecteer een klant
                    KlantMenuController klantMenuCtrl = new KlantMenuController(Controller.CONTROLLER_MODE_SEARCH);
                    klantMenuCtrl.runController();
                    Klant klant = klantMenuCtrl.getKlantSelected();
                    if (klant != null) {
                        accountView.setKlant(klant);
                    }
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

}
