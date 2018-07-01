package nl.workshop1.controller;

import java.util.ArrayList;
import nl.workshop1.DAO.DAOFactory;
import nl.workshop1.model.Adres;
import nl.workshop1.model.Klant;
import nl.workshop1.view.Menu;
import nl.workshop1.view.MenuView;
import nl.workshop1.view.UserInput;

/**
 *
 * @author FeniksBV
 */
public class KlantMenuController extends Controller {

    private int controllerMode;
    private Menu klantMenu;
    private MenuView klantMenuView;
    private ArrayList<Klant> klantList;

    KlantMenuController(int controllerMode) {
        // Deze constructor zal gebruikt worden door Klanten
        this.controllerMode = controllerMode;
        this.klantMenu = new Menu(TITEL_KLANTEN);
        this.klantMenuView = new MenuView(this.klantMenu);
        this.klantList = new ArrayList();
    }

    @Override
    public void runController() {
        KlantViewController klantCtrl;

        while (true) {

            if (controllerMode == CONTROLLER_MODE_SEARCH) {
                // Alleen de opties Zoek en Terug zijn toegestaan
                klantMenu.buildSearchOnlySubMenuList();
            } else {
                klantMenu.buildGeneralSubMenuList();
            }
            if (!klantMenu.isRecordSelected()) {
                searchWithFilter();
            }

            klantMenuView.setMenu(klantMenu);
            requestedAction = klantMenuView.runViewer();
            switch (requestedAction) {
                case "0":
                    return;

                case "1":

                case "3":
                    if (requestedAction.equals("1")) {
                        // NIEUW
                        klantCtrl = new KlantViewController();
                    } else {
                        // WIJZIG
                        // Bepaal eerst Adres data uit de DB
                        Klant klant = klantList.get(klantMenu.getRecordSelectedIndex());
                        ArrayList<Adres> adresList = DAOFactory.getAdresDAO().readAdresWithKlantId(klant.getId());
                        klantCtrl = new KlantViewController(klant, adresList);
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

            // Is er een record gekozen met de opties A, B, C, ..
            if (requestedAction.compareTo("A") >= 0 && requestedAction.compareTo("Z") <= 0) {
                if (!klantMenu.isRecordSelected()) {
                    int selected = (int) (requestedAction.charAt(0) - 'A');
                    if (selected < klantMenu.getRecordList().size()) {
                        klantMenu.setRecordSelected(true);
                        klantMenu.setRecordSelectedIndex(selected);
                        klantMenu.clearSubMenuList();
                        if (controllerMode == Controller.CONTROLLER_MODE_SEARCH) {
                            // Geef de waarde direct terug.
                            return;
                        }
                    }

                }
            }
        }

    }

    protected void searchWithFilter() {
        klantMenu.setRecordSelected(false);
        klantMenu.getRecordList().clear();
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
