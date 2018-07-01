package nl.workshop1.controller;

import java.util.ArrayList;
import nl.workshop1.DAO.DAOFactory;
import nl.workshop1.model.Account;
import nl.workshop1.model.Artikel;
import nl.workshop1.view.Menu;
import nl.workshop1.view.MenuView;
import nl.workshop1.view.UserInput;

/**
 *
 * @author FeniksBV
 */
public class MenuController extends Controller {

    private Menu menu;
    private MenuView menuView;

    public MenuController(String titel) {
        this.menu = new Menu(titel);
        this.menuView = new MenuView(this.menu);
    }

    @Override
    public void runController() {

        while (true) {

            menu.buildGeneralSubMenuList();
            searchWithFilter();

            menuView.setMenu(menu);
            requestedAction = menuView.runViewer();
            switch (requestedAction) {
                case "0":
                    return;

                case "1":

                case "3":
                    startViewer(requestedAction);
                    menu.setRecordSelected(false);
                    break;

                case "2":
                    // Nieuw filter, doorloop searchWithFilter en menu-opbouw
                    break;

                case "4":
                    // Verwijder
                    verwijderRecord();
                    menu.setRecordSelected(false);
                    break;

            }
        }
    }

    protected void startViewer(String reqAction) {
        if (menu.getTitle().equals(TITEL_ACCOUNTS)) {
            AccountViewController accountCtrl;
            if (requestedAction.equals("1")) {
                // NIEUW
                accountCtrl = new AccountViewController();
            } else {
                // WIJZIG
                Object object = menu.getRecordList().get(menu.getRecordSelectedIndex());
                accountCtrl = new AccountViewController((Account) object);
            }
            accountCtrl.runController();
        } else if (menu.getTitle().equals(TITEL_ARTIKELEN)) {
            ArtikelViewController artikelCtrl;
            if (requestedAction.equals("1")) {
                // NIEUW
                artikelCtrl = new ArtikelViewController();
            } else {
                // WIJZIG
                Object object = menu.getRecordList().get(menu.getRecordSelectedIndex());
                artikelCtrl = new ArtikelViewController((Artikel) object);
            }
            artikelCtrl.runController();
        } else if (menu.getTitle().equals(TITEL_BESTELLINGEN)) {
            // Start de controller voor de bestelregels
            BestelRegelMenuController bestelRegelCtrl;
            if (requestedAction.equals("1")) {
                // NIEUW
                bestelRegelCtrl = new BestelRegelMenuController(menu.getKlant());
            } else {
                // WIJZIG
                bestelRegelCtrl = new BestelRegelMenuController(
                        menu.getKlant(), menu.getBestelling());
            }
            bestelRegelCtrl.runController();
        }
    }

    protected void verwijderRecord() {
        Object object = menu.getRecordList().get(menu.getRecordSelectedIndex());
        if (object instanceof Account) {
            if (UserInput.getInputAkkoord("\nVerwijderen account !!")) {
                DAOFactory.getAccountDAO().deleteAccount(((Account) object).getUserName());
            }
        } else if (object instanceof Artikel) {
            if (UserInput.getInputAkkoord("\nVerwijderen artikel !!")) {
                DAOFactory.getArtikelDAO().deleteArtikel(((Artikel) object).getId());
            }
        }
    }

    protected void searchWithFilter() {
        menu.setRecordSelected(false);
        menu.getRecordList().clear();
        if (menu.getTitle().equals(TITEL_ACCOUNTS)) {
            ArrayList<Account> tmpList = DAOFactory.getAccountDAO().readAccountWithFilter(menu.getFilter());
            for (Account account : tmpList) {
                menu.getRecordList().add(account);
            }
        } else if (menu.getTitle().equals(TITEL_ARTIKELEN)) {
            ArrayList<Artikel> tmpList = DAOFactory.getArtikelDAO().readArtikelWithFilter(menu.getFilter());
            for (Artikel artikel : tmpList) {
                menu.getRecordList().add(artikel);
            }
        }
    }

}
