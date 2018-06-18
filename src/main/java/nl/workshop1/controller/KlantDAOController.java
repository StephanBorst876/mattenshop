package nl.workshop1.controller;

import java.util.ArrayList;
import nl.workshop1.DAO.DAOFactory;
import nl.workshop1.model.Klant;

/**
 *
 * @author FeniksBV
 */
public class KlantDAOController {
   
    public static ArrayList<Klant> readKlantWithFilter(String filter) {
        return DAOFactory.getKlantDAO().readKlantWithFilter(filter);
    }
    
    public static Klant readKlant(int id) {
        return DAOFactory.getKlantDAO().readKlant(id);
    }

    public static void deleteKlant(int id) {
        DAOFactory.getKlantDAO().deleteKlant(id);
    }

    public static void insertKlant(Klant klant) {
        DAOFactory.getKlantDAO().insertKlant(klant);
    }
    
    public static void updateKlant(Klant klant) {
        DAOFactory.getKlantDAO().updateKlant(klant);
    }
}
