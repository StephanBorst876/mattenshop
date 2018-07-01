package nl.workshop1.controller;

import java.util.ArrayList;
import nl.workshop1.DAO.DAOFactory;
import nl.workshop1.model.Bestelling;
import nl.workshop1.model.Klant;
import nl.workshop1.view.Menu;
import nl.workshop1.view.MenuView;
import nl.workshop1.view.UserInput;

/**
 *
 * @author FeniksBV
 */
public class BestellingMenuController extends Controller {

    private Menu bestellingMenu;
    private MenuView bestellingMenuView;
    private ArrayList<Bestelling> bestellingList;
    private Klant klant = null;

    public BestellingMenuController(Klant klant) {
        this.bestellingMenu = new Menu(TITEL_BESTELLINGEN);
        this.bestellingMenuView = new MenuView(this.bestellingMenu);
        this.bestellingList = new ArrayList();
        this.klant = klant;
        if (klant != null) {
            this.bestellingMenu.setKlant(klant);
        }
    }

    @Override
    public void runController() {

        // Indien NIET ingelogd als klant, dan eerst de klant kiezen
        // waar de bestelling voor geldt.
        if (klant == null) {
            KlantMenuController klantMenuCtrl = new KlantMenuController(Controller.CONTROLLER_MODE_SEARCH);
            klantMenuCtrl.runController();
            klant = klantMenuCtrl.getKlantSelected();
            if (klant == null) {
                return;
            } else {
                bestellingMenu.setKlant(klant);
            }
        }

        while (true) {

            bestellingMenu.buildGeneralSubMenuList();
            searchWithFilter();

            bestellingMenuView.setMenu(bestellingMenu);
            requestedAction = bestellingMenuView.runViewer();
            switch (requestedAction) {
                case "0":
                    return;

                case "1":

                case "3":
                    // Start de controller voor de bestelregels
                    BestelRegelMenuController bestelRegelCtrl;
                    if (requestedAction.equals("1")) {
                        // NIEUW
                        bestelRegelCtrl = new BestelRegelMenuController(klant);
                    } else {
                        // WIJZIG
                        bestelRegelCtrl = new BestelRegelMenuController(
                                klant, bestellingList.get(bestellingMenu.getRecordSelectedIndex()));
                    }
                    bestelRegelCtrl.runController();
                    break;

                case "2":
                    // Nieuw filter, doorloop searchWithFilter en menu-opbouw
                    break;

                case "4":
                    // Verwijder
                    Bestelling delBestelling = bestellingList.get(bestellingMenu.getRecordSelectedIndex());
                    if (UserInput.getInputAkkoord("\nVerwijderen bestelling !!")) {
                        DAOFactory.getBestellingDAO().deleteBestelling(delBestelling.getId());
                    }
                    bestellingMenu.setRecordSelected(false);
                    break;
            }

            // Is er een record gekozen met de opties A, B, C, ..
            if (requestedAction.compareTo("A") >= 0 && requestedAction.compareTo("Z") <= 0) {
                if (!bestellingMenu.isRecordSelected()) {
                    int selected = (int) (requestedAction.charAt(0) - 'A');
                    if (selected < bestellingMenu.getRecordList().size()) {
                        bestellingMenu.setRecordSelected(true);
                        bestellingMenu.setRecordSelectedIndex(selected);
                        bestellingMenu.clearSubMenuList();
                    }

                }
            }
        }

    }

    protected void searchWithFilter() {
        bestellingMenu.setRecordSelected(false);
        bestellingMenu.getRecordList().clear();
        bestellingList = DAOFactory.getBestellingDAO().readBestellingWithFilter(klant.getId(), 
                bestellingMenu.getFilter());
        for (int i = 0; i < bestellingList.size(); i++) {
            bestellingMenu.getRecordList().add(bestellingList.get(i));
        }
    }
}
