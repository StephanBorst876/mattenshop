package nl.workshop1.controller;

import java.util.ArrayList;
import nl.workshop1.DAO.DAOFactory;
import nl.workshop1.model.Klant;
import nl.workshop1.view.Menu;
import nl.workshop1.view.MenuView;
import nl.workshop1.view.View;

/**
 *
 * @author FeniksBV
 */
public class KlantSearchController extends Controller {

    private Menu klantMenu;
    private MenuView klantMenuView;

    public KlantSearchController() {
        this.klantMenu = new Menu(View.TITEL_KLANTEN);
        this.klantMenuView = new MenuView(this.klantMenu);
    }

    @Override
    public void runController() {
        while (true) {

            klantMenu.buildSearchOnlySubMenuList();
            if (!klantMenu.isRecordSelected()) {
                searchWithFilter();
            }

            klantMenuView.setMenu(klantMenu);
            requestedAction = klantMenuView.runViewer();
            switch (requestedAction) {
                case "0":
                    return;

                case "2":
                    // Nieuw filter, doorloop searchWithFilter en menu-opbouw
                    break;

            }

            // Is er een record gekozen met de opties A, B, C, ..
            if (requestedAction.compareTo("A") >= 0 && requestedAction.compareTo("Z") <= 0) {
                return;
            }
        }
    }

    protected void searchWithFilter() {
        klantMenu.setRecordSelected(false);
        klantMenu.getRecordList().clear();
        ArrayList<Klant> klantList = DAOFactory.getKlantDAO().readKlantWithFilter(klantMenu.getFilter());
        for (int i = 0; i < klantList.size(); i++) {
            klantMenu.getRecordList().add(klantList.get(i));
        }
    }

    public Klant getKlantSelected() {
        if (klantMenu.isRecordSelected()) {
            return (Klant) klantMenu.getSelectedObject();
        }
        return null;
    }
}
