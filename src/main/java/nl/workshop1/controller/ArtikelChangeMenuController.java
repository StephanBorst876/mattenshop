package nl.workshop1.controller;

import nl.workshop1.DAO.DAOFactory;
import nl.workshop1.menu.ArtikelChangeMenu;
import nl.workshop1.model.Artikel;
import nl.workshop1.view.ArtikelView;

/**
 *
 * @author FeniksBV
 */
public class ArtikelChangeMenuController extends MenuController {

    private ArtikelChangeMenu artikelChangeMenu;
    private ArtikelView artikelView;
    private Artikel initialArtikel;

    public ArtikelChangeMenuController() {
        // Een nieuw account
        artikelView = new ArtikelView(MODE_NIEUW, new ArtikelChangeMenu("Artikel toevoegen"));
    }

    public ArtikelChangeMenuController(Artikel artikel) {
        // bestaand account
        initialArtikel = artikel;

        artikelView = new ArtikelView(MODE_WIJZIG, new ArtikelChangeMenu("Artikel wijzigen"), artikel);
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
