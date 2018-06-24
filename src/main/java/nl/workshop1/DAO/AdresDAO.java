package nl.workshop1.DAO;

import java.util.ArrayList;
import nl.workshop1.model.Adres;

/**
 *
 * @author FeniksBV
 */
public interface AdresDAO {
    
    public ArrayList<Adres> readAdresWithKlantId( int klantId );
    
    public void deleteAdres(int id);

    public void insertAdres(Adres adres);

    public void updateAdres(Adres adres);
}
