package nl.workshop1.DAO;

import java.util.ArrayList;
import nl.workshop1.model.BestelRegel;

/**
 *
 * @author FeniksBV
 */
public interface BestelRegelDAO {
    
    public ArrayList<BestelRegel> readRegelsWithFilter(
            int bestellingId, String  filter);
    
    public void deleteBestelRegel(int bestelRegelId);

    public void insertBestelRegel(BestelRegel bestelRegel);

    public void updateBestelRegel(BestelRegel bestelRegel);
}
