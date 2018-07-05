package nl.workshop1.controller;

import nl.workshop1.DAO.DAOFactory;
import nl.workshop1.model.Artikel;
import nl.workshop1.view.ArtikelView;
import nl.workshop1.view.Menu;
import nl.workshop1.view.OutputText;

/**
 *
 * @author FeniksBV
 */
public class ArtikelViewController extends Controller {

    private ArtikelView artikelView;
    private Artikel initialArtikel = null;

    public ArtikelViewController(Artikel artikel) {
        String titel;
        if (artikel != null) {
            initialArtikel = (Artikel) artikel.clone();
            titel = "Artikel wijzigen";
        } else {
            titel = "Artikel toevoegen";
        }
        artikelView = new ArtikelView(new Menu(titel), artikel);
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
                        Artikel artikel = DAOFactory.getArtikelDAO().readArtikelByNaam(artikelView.getArtikel().getNaam());
                        if (artikel == null) {
                            DAOFactory.getArtikelDAO().insertArtikel(artikelView.getArtikel());
                            return;
                        } else {
                            OutputText.showError("Een artikel met deze naam bestaat al! Toevoegen niet toegestaan.");
                        }
                    } else {
                        DAOFactory.getArtikelDAO().updateArtikel(artikelView.getArtikel());
                        return;
                    }
            }
        }
    }

}
