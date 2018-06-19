package nl.workshop1.DAO;

import nl.workshop1.model.Artikel;

/**
 *
 * @author FeniksBV
 */
public interface ArtikelDAO {

    public void deleteArtikel(int id);

    public int insertArtikel(Artikel artikel);

    public void updateArtikel(Artikel artikel);
}
