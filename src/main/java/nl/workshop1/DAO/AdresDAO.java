package nl.workshop1.DAO;

import nl.workshop1.model.Adres;

/**
 *
 * @author FeniksBV
 */
public interface AdresDAO {
    
    public void deleteAdres(int id);

    public void insertAdres(Adres adres);

    public void updateAdres(Adres adres);
}
