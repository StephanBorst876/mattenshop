package nl.workshop1.controller;

import java.util.ArrayList;
import nl.workshop1.DAO.DAOFactory;
import nl.workshop1.model.Artikel;
import nl.workshop1.view.Menu;
import nl.workshop1.view.MenuView;
import nl.workshop1.view.View;

/**
 *
 * @author FeniksBV
 */
public class ArtikelSearchController extends Controller {

    private Menu artikelMenu;
    private MenuView artikelMenuView;
    private ArrayList<Artikel> excludedArtikelList;

    public ArtikelSearchController() {
        this.artikelMenu = new Menu(View.TITEL_ARTIKELEN);
        this.artikelMenuView = new MenuView(this.artikelMenu);
        this.excludedArtikelList = new ArrayList<>();
    }

    public void setExcludedArtikelList(ArrayList<Artikel> excludedArtikelList) {
        this.excludedArtikelList = excludedArtikelList;
    }

    @Override
    public void runController() {
        while (true) {

            artikelMenu.buildSearchOnlySubMenuList();
            if (!artikelMenu.isRecordSelected()) {
                searchWithFilter();
            }

            artikelMenuView.setMenu(artikelMenu);
            requestedAction = artikelMenuView.runViewer();
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
        artikelMenu.setRecordSelected(false);
        artikelMenu.getRecordList().clear();
        ArrayList<Artikel> artikelList = DAOFactory.getArtikelDAO().readArtikelWithFilter(artikelMenu.getFilter());
        for (int i = 0; i < artikelList.size(); i++) {
            // Alleen in menu tonen indien het artikelId niet in de excludedList staat.
            Boolean gevondenInExcludedList = false;
            for (int j = 0; j < excludedArtikelList.size(); j++) {
                if (excludedArtikelList.get(j).getId() == artikelList.get(i).getId()) {
                    gevondenInExcludedList = true;
                }
            }
            if (!gevondenInExcludedList) {
                artikelMenu.getRecordList().add(artikelList.get(i));
            }

        }
    }

    public Artikel getArtikelSelected() {
        if (artikelMenu.isRecordSelected()) {
            return (Artikel) artikelMenu.getSelectedObject();
        }
        return null;
    }
}
