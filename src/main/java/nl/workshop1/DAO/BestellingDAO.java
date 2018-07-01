package nl.workshop1.DAO;

import java.util.ArrayList;
import nl.workshop1.model.Bestelling;

/**
 *
 * @author FeniksBV
 */
public interface BestellingDAO {
    
    public ArrayList<Bestelling> readBestellingWithFilter(int klantId, String filter);
    
    public void deleteBestelling(int bestellingId);

    public void insertBestelling(Bestelling bestelling);

    public void updateBestelling(Bestelling bestelling);
}
