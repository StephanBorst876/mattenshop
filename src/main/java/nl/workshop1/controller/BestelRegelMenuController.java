package nl.workshop1.controller;

import java.util.ArrayList;
import nl.workshop1.DAO.DAOFactory;
import nl.workshop1.model.BestelRegel;
import nl.workshop1.model.Bestelling;
import nl.workshop1.model.Klant;
import nl.workshop1.view.Menu;
import nl.workshop1.view.MenuView;

/**
 *
 * @author FeniksBV
 */
public class BestelRegelMenuController extends Controller {

    private Menu bestelRegelMenu;
    private MenuView bestelRegelMenuView;
    private ArrayList<BestelRegel> bestelRegelList;
    private Klant klant = null;
    private Bestelling bestelling = null;

    public BestelRegelMenuController(Klant klant) {
        this.klant = klant;
        this.bestelRegelMenu = new Menu(TITEL_BESTELREGELS);
        bestelRegelMenu.setKlant(klant);
        this.bestelRegelMenuView = new MenuView(this.bestelRegelMenu);
        this.bestelRegelList = new ArrayList();
    }

    public BestelRegelMenuController(Klant klant, Bestelling bestelling) {
        this.klant = klant;
        this.bestelling = bestelling;
        this.bestelRegelMenu = new Menu(TITEL_BESTELREGELS);
        bestelRegelMenu.setKlant(klant);
        bestelRegelMenu.setBestelling(bestelling);
        this.bestelRegelMenuView = new MenuView(this.bestelRegelMenu);
        this.bestelRegelList = new ArrayList();
    }

    @Override
    public void runController() {

        while (true) {

            bestelRegelMenu.buildGeneralSubMenuList();
            searchWithFilter();

            bestelRegelMenuView.setMenu(bestelRegelMenu);
            requestedAction = bestelRegelMenuView.runViewer();
            switch (requestedAction) {
                case "0":
                    return;
            }
        }

    }

    protected void searchWithFilter() {
        // Zoek alle regels bij de bestelling
        bestelRegelMenu.setRecordSelected(false);
        bestelRegelMenu.getRecordList().clear();
        if (bestelling != null) {
            bestelRegelList = DAOFactory.getBestelRegelDAO().readRegelsWithBestellingId(bestelling.getId());
            for (int i = 0; i < bestelRegelList.size(); i++) {
                bestelRegelMenu.getRecordList().add(bestelRegelList.get(i));
            }
        }
    }
}
