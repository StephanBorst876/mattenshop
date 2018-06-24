package nl.workshop1.controller;

import java.util.ArrayList;
import nl.workshop1.DAO.DAOFactory;
import nl.workshop1.model.Klant;
import nl.workshop1.menu.KlantMenu;
import nl.workshop1.view.KlantMenuView;
import nl.workshop1.view.UserInput;

/**
 *
 * @author FeniksBV
 */
public class KlantMenuController extends MenuController {

    private int controllerMode;
    private KlantMenu klantMenu;
    private KlantMenuView klantMenuView;
    private ArrayList<Klant> klantList;

    KlantMenuController(int controllerMode) {
        // Deze constructor zal gebruikt worden door Klanten
        this.controllerMode = controllerMode;
        this.klantMenu = new KlantMenu();
        this.klantMenuView = new KlantMenuView(this.klantMenu);
        this.klantList = new ArrayList();
    }

    @Override
    public void runController() {
        KlantChangeMenuController klantCtrl;

        while (true) {

            klantMenu.clearSubMenuList();
            klantMenu.getRecordList().clear();
            klantMenu.buildGeneralSubMenuList();
            searchWithFilter();

            klantMenuView.setMenu(klantMenu);
            requestedAction = klantMenuView.runViewer();
            switch (requestedAction) {
                case "0":
                    return;
                    
                case "1":

                case "3":
                    if (requestedAction.equals("1")) {
                        // NIEUW
                        klantCtrl = new KlantChangeMenuController();
                    } else {
                        // WIJZIG
                        // Bepaal eerst Adres data uit de DB
                        Klant klant = klantList.get(klantMenu.getRecordSelectedIndex());
                        klant.setAdresList(DAOFactory.getAdresDAO().readAdresWithKlantId(klant.getId()));
                        klantCtrl = new KlantChangeMenuController(klant);
                    }
                    klantCtrl.runController();
                    klantMenu.setRecordSelected(false);
                    break;

                case "2":
                    // Nieuw filter, doorloop searchWithFilter en menu-opbouw
                    break;

                case "4":
                    // Verwijder
                    Klant delKlant = klantList.get(klantMenu.getRecordSelectedIndex());
                    if (UserInput.getInputAkkoord("\nVerwijderen account !!")) {
                        DAOFactory.getKlantDAO().deleteKlant(delKlant.getId());
                    }
                    klantMenu.setRecordSelected(false);
                    break;
            }
        }

    }

    protected void searchWithFilter() {
        klantMenu.setRecordSelected(false);
        klantList = DAOFactory.getKlantDAO().readKlantWithFilter(klantMenu.getFilter());
        for (int i = 0; i < klantList.size(); i++) {
            klantMenu.getRecordList().add(klantList.get(i));
        }
    }

    public Klant getKlantSelected() {
        if (klantMenu.isRecordSelected()) {
            return klantList.get(klantMenu.getRecordSelectedIndex());
        }
        return null;
    }

}
