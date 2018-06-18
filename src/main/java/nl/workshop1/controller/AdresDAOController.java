package nl.workshop1.controller;

import nl.workshop1.DAO.DAOFactory;
import nl.workshop1.model.Adres;

/**
 *
 * @author FeniksBV
 */
public class AdresDAOController {
    
    public static void deleteAdres(int id) {
        DAOFactory.getAdresDAO().deleteAdres(id);
    }

    public static void insertAdres(Adres adres) {
        DAOFactory.getAdresDAO().insertAdres(adres);
    }
    
    public static void updateAdres(Adres adres) {
        DAOFactory.getAdresDAO().updateAdres(adres);
    }
}
