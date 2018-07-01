package nl.workshop1.DAO;

import java.util.ArrayList;
import nl.workshop1.model.BestelRegel;

/**
 *
 * @author FeniksBV
 */
public class BestelRegelDAOImpl implements BestelRegelDAO{

    public ArrayList<BestelRegel> readRegelsWithBestellingId(int bestellingId){
        ArrayList<BestelRegel> bestelRegelList = new ArrayList<>();
        
        return bestelRegelList;
    }
    
    
    @Override
    public void deleteBestelRegel(int bestelRegelId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void insertBestelRegel(BestelRegel bestelRegel) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void updateBestelRegel(BestelRegel bestelRegel) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
