package nl.workshop1.controller;

import java.util.ArrayList;
import nl.workshop1.DAO.DAOFactory;
import nl.workshop1.menu.ArtikelMenu;
import nl.workshop1.model.Artikel;
import nl.workshop1.view.ArtikelMenuView;
import nl.workshop1.view.UserInput;

/**
 *
 * @author FeniksBV
 */
public class ArtikelMenuController extends MenuController {

    private ArtikelMenu artikelMenu;
    private ArtikelMenuView artikelMenuView;
    private ArrayList<Artikel> artikelList = new ArrayList<>();

    public ArtikelMenuController() {
        this.artikelMenu = new ArtikelMenu();
        this.artikelMenuView = new ArtikelMenuView(artikelMenu);
    }

    @Override
    public void runController() {
        ArtikelChangeMenuController artikelCtrl;

        while (true) {

            artikelMenu.clearSubMenuList();
            artikelMenu.getRecordList().clear();
            artikelMenu.buildGeneralSubMenuList();
            searchWithFilter();

            artikelMenuView.setMenu(artikelMenu);
            requestedAction = artikelMenuView.runViewer();
            switch (requestedAction) {
            case "0":
                    return;

                case "1":

                case "3":
                    if (requestedAction.equals("1")) {
                        // NIEUW
                        artikelCtrl = new ArtikelChangeMenuController();
                    } else {
                        // WIJZIG
                        artikelCtrl = new ArtikelChangeMenuController(artikelList.get(artikelMenu.getRecordSelectedIndex()));
                    }
                    artikelCtrl.runController();
                    artikelMenu.setRecordSelected(false);
                    break;

                case "2":
                    // Nieuw filter, doorloop searchWithFilter en menu-opbouw
                    break;

                case "4":
                    // Verwijder
                    Artikel delArtikel = artikelList.get(artikelMenu.getRecordSelectedIndex());
                    if (UserInput.getInputAkkoord("\nVerwijderen artikel !!")) {
                        DAOFactory.getArtikelDAO().deleteArtikel(delArtikel.getId());
                    }
                    artikelMenu.setRecordSelected(false);
                    break;

            }
        }
    }
        
    protected void searchWithFilter() {
        artikelMenu.setRecordSelected(false);
        artikelList = DAOFactory.getArtikelDAO().readArtikelWithFilter(artikelMenu.getFilter());
        for (int i = 0; i < artikelList.size(); i++) {
            artikelMenu.getRecordList().add(artikelList.get(i));
        }
    }

}
