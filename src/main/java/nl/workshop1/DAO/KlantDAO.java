package nl.workshop1.DAO;

import java.util.ArrayList;
import nl.workshop1.model.Klant;

/**
 *
 * @author FeniksBV
 */
public interface KlantDAO {
  
    public ArrayList<Klant> readKlantWithFilter(String filter);
    
    public Klant readKlant(int id);

    public void deleteKlant(int id);

    public void insertKlant(Klant klant);

    public void updateKlant(Klant klant);
}
