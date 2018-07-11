package nl.workshop1.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import nl.workshop1.DAO.DAOFactory;
import nl.workshop1.model.Artikel;
import nl.workshop1.model.BestelRegel;
import nl.workshop1.model.Bestelling;
import nl.workshop1.view.BestelRegelView;
import nl.workshop1.view.Menu;

/**
 *
 * @author FeniksBV
 */
public class BestelRegelController extends Controller {

    private Bestelling bestelling = null;
    private BestelRegel bestelRegel;
    private BestelRegelView bestelRegelView;

    public BestelRegelController(Bestelling bestelling, BestelRegel bestelRegel) {

        this.bestelling = bestelling;
        this.bestelRegel = bestelRegel;
        String titel;
        if (this.bestelRegel.getId() == 0) {
            titel = "bestelregel toevoegen";
        } else {
            titel = "bestelregel wijzigen";
        }
        bestelRegelView = new BestelRegelView(new Menu(titel), bestelRegel);
    }

    @Override
    public void runController() {

        ArrayList<BestelRegel> bestelRegelList = new ArrayList<>();
        if (bestelling.getId() != 0) {
            bestelRegelList = DAOFactory.getBestelRegelDAO().readRegelsWithFilter(bestelling.getId(), "");
        }

        if (bestelRegel.getId() == 0) {
            // Uitvragen bestelRegel
            ArtikelSearchController ctrl = new ArtikelSearchController();
            ArrayList<Artikel> excludeList = new ArrayList<>();
            for (int i = 0; i < bestelRegelList.size(); i++) {
                Artikel artikel = new Artikel();
                artikel.setId(bestelRegelList.get(i).getArtikelId());
                excludeList.add(artikel);
            }
            ctrl.setExcludedArtikelList(excludeList);
            ctrl.runController();
            Artikel artikel = ctrl.getArtikelSelected();
            if (artikel != null) {
                bestelRegel.setArtikelId(artikel.getId());
                bestelRegel.setArtikelNaam(artikel.getNaam());
                bestelRegel.setPrijs(artikel.getPrijs());
                // Aantal wordt default op 1 gezet
                bestelRegel.setAantal(1);
                bestelRegelView.setArtikel(artikel);
            } else {
                // Geen artikel gekozen, dus quit
                requestedAction = "0";
                return;
            }

        } else {
            // Lees het artikel
            Artikel artikel = DAOFactory.getArtikelDAO().readArtikelByNaam(
                    bestelRegel.getArtikelNaam());
            if (artikel != null) {
                bestelRegelView.setArtikel(artikel);
            }
        }

        while (true) {
            requestedAction = bestelRegelView.runViewer();
            switch (requestedAction) {
                case "0":
                    return;
                case "4":
                    // Bijwerken van toegevoegde of gewijzigde bestelRegel

                    BigDecimal totaalPrijs = new BigDecimal(0);
                    bestelRegel = bestelRegelView.getBestelRegel();
                    Boolean gewijzigd = false;
                    for (int i = 0; i < bestelRegelList.size(); i++) {
                        if (bestelRegelList.get(i).getArtikelId() == bestelRegel.getArtikelId()) {
                            bestelRegelList.set(i, bestelRegel);
                            gewijzigd = true;
                        }
                        BigDecimal aantalArtikel = new BigDecimal(bestelRegelList.get(i).getAantal());
                        totaalPrijs = totaalPrijs.add(bestelRegelList.get(i).getPrijs().multiply(aantalArtikel));
                    }

                    if (!gewijzigd) {
                        // blijkbaar een nieuwe bestelregel ingevoerd
                        BigDecimal aantalArtikel = new BigDecimal(bestelRegel.getAantal());
                        totaalPrijs = totaalPrijs.add(bestelRegel.getPrijs().multiply(aantalArtikel));
                    }
                    bestelling.setTotaalprijs(totaalPrijs);

                    return;

            }
        }
    }

}
