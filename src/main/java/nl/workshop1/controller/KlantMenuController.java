package nl.workshop1.controller;

import java.util.ArrayList;
import nl.workshop1.view.KlantMenu;
import nl.workshop1.model.Klant;
import nl.workshop1.view.KlantChangeMenu;
import nl.workshop1.view.Menu;
import nl.workshop1.view.KlantChangeMenuView;

/**
 *
 * @author FeniksBV
 */
public class KlantMenuController extends MenuController {

    private int controllerMode = CONTROLER_MODE_ADMIN;
    private KlantMenu klantMenu;
    private ArrayList<Klant> klantList = new ArrayList();

    KlantMenuController(KlantMenu klantMenu) {
        // Deze constructor zal gebruikt worden door Admin/Medewerker
        this.klantMenu = klantMenu;
    }

    KlantMenuController(KlantMenu klantMenu, int controllerMode) {
        // Deze constructor zal gebruikt worden door Klanten
        this.klantMenu = klantMenu;
        this.controllerMode = controllerMode;
    }

    @Override
    public void buildOptionsMenu() {
        if (!klantMenu.isRecordSelected()) {
            klantMenu.resetMenu();
            searchWithFilter();
        } else {
            klantMenu.clearSubMenu();
        }
        if (controllerMode == CONTROLER_MODE_ADMIN) {
            klantMenu.buildMenu();
        } else {
            klantMenu.buildSearchMenu();
        }

    }

    @Override
    public void handleMenu() {
        KlantChangeMenu klantChangeMenu;
        KlantChangeMenuView klantChangeMenuView;

        while (true) {
            buildOptionsMenu();
            klantMenu.drawMenu();
            switch (klantMenu.userChoice()) {
                case "0":
                    return;
                case "1":
                    // NEW
                    klantChangeMenu = new KlantChangeMenu("Klant toevoegen", new Klant());
                    klantChangeMenuView = new KlantChangeMenuView();
                    KlantChangeMenuController klantNew = new KlantChangeMenuController(Menu.MODE_NEW, klantChangeMenu, klantChangeMenuView);
                    klantNew.runController();
                    klantMenu.setRecordSelected(false);
                    break;
                case "2":
                    // Nieuw filter ingeven
                    klantMenu.setFilter(klantMenu.getMenuView().getInputFilter());
                    klantMenu.setRecordSelected(false);
                    break;
                case "3":
                    // Wijzig
                    klantChangeMenu = new KlantChangeMenu("Klant wijzigen", klantList.get(klantMenu.getRecordSelectedIndex()));
                    klantChangeMenuView = new KlantChangeMenuView();
                    KlantChangeMenuController klantChange = new KlantChangeMenuController(Menu.MODE_CHANGE, klantChangeMenu, klantChangeMenuView);
                    klantChange.runController();
                    klantMenu.setRecordSelected(false);
                    break;
                case "4":
                    // Verwijder
                    Klant delKlant = klantList.get(klantMenu.getRecordSelectedIndex());
                    if (klantMenu.getMenuView().getInputAkkoord("\nVerwijderen klant: " + klantMenu.display(delKlant))) {
                        KlantDAOController.deleteKlant(delKlant.getId());
                    }
                    klantMenu.setRecordSelected(false);
                    break;
                default:
                    // In this case a char 'A' till 'Z' might be pressed.
                    if (controllerMode == CONTROLLER_MODE_SEARCH) {
                        if (klantMenu.isRecordSelected()) {
                            return;
                        }
                    }
                    break;
            }
        }
    }

    public Klant chooseKlant() {
        controllerMode = CONTROLLER_MODE_SEARCH;
        buildOptionsMenu();
        handleMenu();
        if (klantMenu.isRecordSelected()) {
            return klantList.get(klantMenu.getRecordSelectedIndex());
        }
        return null;
    }

    protected void searchWithFilter() {
        klantList = KlantDAOController.readKlantWithFilter(klantMenu.getFilter());
        for (int i = 0; i < klantList.size(); i++) {
            klantMenu.addRecordList(klantMenu.display(klantList.get(i)));
        }
    }
}
