package nl.workshop1.controller;

import nl.workshop1.DAO.DAOFactory;
import nl.workshop1.model.Account;
import nl.workshop1.model.Klant;
import nl.workshop1.view.AccountView;
import nl.workshop1.view.Menu;
import nl.workshop1.view.OutputText;

/**
 *
 * @author FeniksBV
 */
public class AccountViewController extends Controller {

    private AccountView accountView;
    private Account initialAccount = null;

    public AccountViewController(Account account, Klant klant) {
        String titel;
        if (account != null) {
            initialAccount = (Account) account.clone();
            titel = "Account wijzigen";
        } else {
            titel = "Account toevoegen";
        }
        accountView = new AccountView(new Menu(titel), account);
        if (klant != null) {
            accountView.setKlant(klant);
        }
    }

    @Override
    public void runController() {

        while (true) {
            requestedAction = accountView.runViewer();
            switch (requestedAction) {
                case "0":
                    return;
                case "4":
                    // Selecteer een klant
                    KlantSearchController klantSearchCtrl = new KlantSearchController();
                    klantSearchCtrl.runController();
                    Klant klant = klantSearchCtrl.getKlantSelected();
                    if (klant != null) {
                        accountView.setKlant(klant);
                    }
                    break;
                case "5":
                    // update / insert
                    if (initialAccount == null) {
                        // Insert, nu ingevoerd account mag nog niet bestaan.
                        Account account = DAOFactory.getAccountDAO().readAccountByUserName(
                                accountView.getAccount().getUserName());
                        if (account == null) {
                            DAOFactory.getAccountDAO().insertAccount(accountView.getAccount());
                            return;
                        } else {
                            OutputText.showError("Een account met deze username bestaat al! Toevoegen niet toegestaan.");
                        }
                    } else {
                        DAOFactory.getAccountDAO().updateAccount(accountView.getAccount());
                        return;
                    }
                    

            }
        }
    }

}
