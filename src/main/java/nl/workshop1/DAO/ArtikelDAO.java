package nl.workshop1.DAO;

import java.util.ArrayList;
import nl.workshop1.model.Artikel;

/**
 *
 * @author FeniksBV
 */
public interface ArtikelDAO {

    public void deleteArtikel(int id);

    public void insertArtikel(Artikel artikel);

    public void updateArtikel(Artikel artikel);

    public ArrayList<Artikel> readArtikelWithFilter(String filter);

    public Artikel readArtikelByNaam(String naam);

    public Artikel readArtikelById(int id);

}
