package nl.workshop1.controller;

import nl.workshop1.DAO.DAOFactory;
import nl.workshop1.model.Artikel;
import nl.workshop1.view.ArtikelView;
import nl.workshop1.view.Menu;

/**
 *
 * @author FeniksBV
 */
public class ArtikelViewController extends Controller {

    private Menu artikelChangeMenu;
    private ArtikelView artikelView;
    private Artikel initialArtikel;

    public ArtikelViewController() {
        // Een nieuw account
        artikelView = new ArtikelView(MODE_NIEUW, new Menu("Artikel toevoegen"));
    }

    public ArtikelViewController(Artikel artikel) {
        // bestaand account
        initialArtikel = artikel;

        artikelView = new ArtikelView(MODE_WIJZIG, new Menu("Artikel wijzigen"), artikel);
    }

    @Override
    public void runController() {

        while (true) {
            requestedAction = artikelView.runViewer();
            switch (requestedAction) {
                case "0":
                    return;
                case "6":
                    // update / insert
                    if (initialArtikel == null) {
                        // Insert
                        DAOFactory.getArtikelDAO().insertArtikel(artikelView.getArtikel());
                    } else {
                        DAOFactory.getArtikelDAO().updateArtikel(artikelView.getArtikel());
                    }
                    return;
            }
        }
    }


}
