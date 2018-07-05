package nl.workshop1.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import nl.workshop1.DAO.DAOFactory;
import nl.workshop1.model.Account;
import nl.workshop1.model.Adres;
import nl.workshop1.model.Artikel;
import nl.workshop1.model.BestelRegel;
import nl.workshop1.model.Bestelling;
import nl.workshop1.model.Bestelstatus;
import nl.workshop1.model.Klant;
import nl.workshop1.model.Role;
import nl.workshop1.view.Menu;
import nl.workshop1.view.MenuView;
import nl.workshop1.view.UserInput;
import nl.workshop1.view.View;

/**
 *
 * @author FeniksBV
 */
public class MenuController extends Controller {

    private Menu menu;
    private MenuView menuView;

    public MenuController(String titel) {
        this.menu = new Menu(titel);
        this.menuView = new MenuView(this.menu);
    }

    /**
     * Bestellingen heeft bestelKlant nodig om te tonen
     *
     * @param bestelKlant
     */
    public void setBestelKlant(Klant bestelKlant) {
        this.menu.setBestelKlant(bestelKlant);
    }

    /**
     * Bestelregel heeft de bestelling nodig om te tonen
     *
     * @param bestelling
     */
    public void setBestelling(Bestelling bestelling) {
        this.menu.setBestelling(bestelling);
    }

    @Override
    public void runController() {

        while (true) {

            menu.buildGeneralSubMenuList();
            searchWithFilter();

            menuView.setMenu(menu);
            requestedAction = menuView.runViewer();
            switch (requestedAction) {
                case "0":
                    return;

                case "1":

                case "3":
                    startViewer(requestedAction);
                    menu.setRecordSelected(false);
                    break;

                case "2":
                    // Nieuw filter, doorloop searchWithFilter en menu-opbouw
                    break;

                case "4":
                    // Verwijder
                    verwijderRecord();
                    menu.setRecordSelected(false);
                    break;

            }
        }
    }

    protected void startViewer(String reqAction) {
        if (menu.getTitle().equals(View.TITEL_ACCOUNTS)) {

            Account account = null;
            Klant klant = null;
            if (requestedAction.equals("3")) {
                // WIJZIG
                Object object = menu.getSelectedObject();
                account = (Account) object;
                // Indien een account wijzigen en het betreft een Role.KLANT
                // dan initialiseren we de klant data
                if (account.getRole() == Role.Klant) {
                    klant = DAOFactory.getKlantDAO().readKlant(account.getKlantId());
                }
            }
            AccountViewController accountCtrl = new AccountViewController(account, klant);
            accountCtrl.runController();

        } else if (menu.getTitle().equals(View.TITEL_ARTIKELEN)) {

            Artikel artikel = null;
            if (requestedAction.equals("3")) {
                // WIJZIG
                Object object = menu.getSelectedObject();
                artikel = (Artikel) object;
            }
            ArtikelViewController artikelCtrl = new ArtikelViewController(artikel);
            artikelCtrl.runController();

        } else if (menu.getTitle().equals(View.TITEL_BESTELLINGEN)) {

            // Start de controller voor de bestelregels
            Bestelling bestelling = null;
            if (requestedAction.equals("1")) {
                bestelling = new Bestelling();
                bestelling.setBestelDatum(new Date());
                bestelling.setBestelstatus(Bestelstatus.Onderhanden);
                bestelling.setKlantId(menu.getBestelKlant().getId());
            } else if (requestedAction.equals("3")) {
                // WIJZIG
                bestelling = (Bestelling) menu.getSelectedObject();
            }
            MenuController bestelRegelCtrl = new MenuController(View.TITEL_BESTELREGELS);
            bestelRegelCtrl.setBestelKlant(menu.getBestelKlant());
            bestelRegelCtrl.setBestelling(bestelling);
            bestelRegelCtrl.runController();

        } else if (menu.getTitle().equals(View.TITEL_KLANTEN)) {

            Klant klant = null;
            ArrayList<Adres> adresList = new ArrayList<>();
            if (requestedAction.equals("3")) {
                // WIJZIG
                // Bepaal eerst Adres data uit de DB
                klant = (Klant) menu.getSelectedObject();
                adresList = DAOFactory.getAdresDAO().readAdresWithKlantId(klant.getId());
            }
            KlantViewController klantCtrl = new KlantViewController(klant, adresList);
            klantCtrl.runController();

        } else if (menu.getTitle().equals(View.TITEL_BESTELREGELS)) {

            int verschilGereserveerd = 0;
            BestelRegel bestelRegel;
            if (requestedAction.equals("1")) {
                // Nieuw
                bestelRegel = new BestelRegel();
            } else {
                // WIJZIG
                bestelRegel = (BestelRegel) menu.getSelectedObject();
                verschilGereserveerd = bestelRegel.getAantal();
            }
            BestelRegelViewController bestelRegelCtrl = new BestelRegelViewController(
                    menu.getBestelling(), bestelRegel);
            bestelRegelCtrl.runController();
            if (bestelRegelCtrl.getRequestedAction().equals("4")) {
                // DB actions voor bestelling
                if (menu.getBestelling().getId() == 0) {
                    // Insert bestelling
                    DAOFactory.getBestellingDAO().insertBestelling(menu.getBestelling());
                } else {
                    // update bestelling
                    DAOFactory.getBestellingDAO().updateBestelling(menu.getBestelling());
                }

                // DB actions voor regels
                bestelRegel.setBestellingId(menu.getBestelling().getId());
                if (bestelRegel.getId() == 0) {
                    // Insert regel
                    DAOFactory.getBestelRegelDAO().insertBestelRegel(bestelRegel);
                    // Gereserveerd moet met bestelRegel.getAantal worden opgehoogd
                    verschilGereserveerd = bestelRegel.getAantal();
                } else {
                    // update regel
                    DAOFactory.getBestelRegelDAO().updateBestelRegel(bestelRegel);
                    verschilGereserveerd = bestelRegel.getAantal() - verschilGereserveerd;
                }

                // DB actions voor artikelen
                if (verschilGereserveerd != 0) {
                    Artikel artikel = DAOFactory.getArtikelDAO().readArtikelByNaam(bestelRegel.getArtikelNaam());
                    if (artikel != null) {
                        artikel.setGereserveerd(artikel.getGereserveerd() + verschilGereserveerd);
                        DAOFactory.getArtikelDAO().updateArtikel(artikel);
                    }

                }
            }
        }
    }

    protected void verwijderRecord() {
        Object object = menu.getSelectedObject();
        if (object instanceof Account) {
            if (UserInput.getInputAkkoord("\nVerwijderen account !!")) {
                DAOFactory.getAccountDAO().deleteAccount(((Account) object).getUserName());
            }
        } else if (object instanceof Artikel) {
            if (UserInput.getInputAkkoord("\nVerwijderen artikel !!")) {
                DAOFactory.getArtikelDAO().deleteArtikel(((Artikel) object).getId());
            }
        } else if (object instanceof Klant) {
            if (UserInput.getInputAkkoord("\nVerwijderen klant !!")) {
                DAOFactory.getKlantDAO().deleteKlant(((Klant) object).getId());
            }
        } else if (object instanceof Bestelling) {
            if (UserInput.getInputAkkoord("\nVerwijderen bestelling !!")) {
                DAOFactory.getBestellingDAO().deleteBestelling(((Bestelling) object).getId());

                // En pas voor alle artikelen de hoeveelheid voorraad en gereserveerd aan
                ArrayList<BestelRegel> bestelRegelList = DAOFactory.getBestelRegelDAO().readRegelsWithFilter(
                        ((Bestelling) object).getId(), "");
                for (int i = 0; i < bestelRegelList.size(); i++) {
                    Artikel artikel = DAOFactory.getArtikelDAO().readArtikelByNaam(
                            bestelRegelList.get(i).getArtikelNaam());
                    artikel.setVoorraad(artikel.getVoorraad() - bestelRegelList.get(i).getAantal());
                    artikel.setGereserveerd(artikel.getGereserveerd() - bestelRegelList.get(i).getAantal());
                    DAOFactory.getArtikelDAO().updateArtikel(artikel);
                }

            }
        } else if (object instanceof BestelRegel) {
            if (UserInput.getInputAkkoord("\nVerwijderen bestelregel !!")) {
                BestelRegel delRegel = (BestelRegel) menu.getSelectedObject();
                DAOFactory.getBestelRegelDAO().deleteBestelRegel(delRegel.getId());

                // Bestelling update
                BigDecimal totaalPrijs = new BigDecimal(menu.getBestelling().getTotaalprijs().toString());
                BigDecimal regelPrijs = new BigDecimal(delRegel.getPrijs().toString());
                menu.getBestelling().setTotaalprijs(totaalPrijs.subtract(regelPrijs));
                DAOFactory.getBestellingDAO().updateBestelling(menu.getBestelling());

                // Update artikel voor nieuw aantal gereserveerd
                Artikel artikel = DAOFactory.getArtikelDAO().readArtikelByNaam(
                        delRegel.getArtikelNaam());
                int newGereserveerd = artikel.getGereserveerd() - delRegel.getAantal();
                artikel.setGereserveerd(newGereserveerd);

                DAOFactory.getArtikelDAO().updateArtikel(artikel);
            }
        }
    }

    protected void searchWithFilter() {
        menu.setRecordSelected(false);
        menu.getRecordList().clear();
        if (menu.getTitle().equals(View.TITEL_ACCOUNTS)) {
            ArrayList<Account> tmpList = DAOFactory.getAccountDAO().readAccountWithFilter(menu.getFilter());
            for (Account account : tmpList) {
                menu.getRecordList().add(account);
            }
        } else if (menu.getTitle().equals(View.TITEL_ARTIKELEN)) {
            ArrayList<Artikel> tmpList = DAOFactory.getArtikelDAO().readArtikelWithFilter(menu.getFilter());
            for (Artikel artikel : tmpList) {
                menu.getRecordList().add(artikel);
            }
        } else if (menu.getTitle().equals(View.TITEL_KLANTEN)) {
            ArrayList<Klant> tmpList = DAOFactory.getKlantDAO().readKlantWithFilter(menu.getFilter());
            for (Klant klant : tmpList) {
                menu.getRecordList().add(klant);
            }
        } else if (menu.getTitle().equals(View.TITEL_BESTELLINGEN)) {
            ArrayList<Bestelling> tmpList = DAOFactory.getBestellingDAO().readBestellingWithFilter(
                    menu.getBestelKlant().getId(), menu.getFilter());
            for (Bestelling bestelling : tmpList) {
                menu.getRecordList().add(bestelling);
            }
        } else if (menu.getTitle().equals(View.TITEL_BESTELREGELS)) {
            if (menu.getBestelling().getId() != 0) {
                ArrayList<BestelRegel> tmpList = DAOFactory.getBestelRegelDAO().readRegelsWithFilter(
                        menu.getBestelling().getId(), menu.getFilter());
                for (BestelRegel bestelRegel : tmpList) {
                    menu.getRecordList().add(bestelRegel);
                }
            }
        }

    }

}
