package nl.workshop1.DAO;

/**
 *
 * @author FeniksBV
 */
public class DAOFactory {

    private static AccountDAO accountDAO = new AccountDAOImpl();
    private static KlantDAO klantDAO = new KlantDAOImpl();
    private static AdresDAO adresDAO = new AdresDAOImpl();
    private static ArtikelDAO artikelDAO = new ArtikelDAOImpl();

    public static AccountDAO getAccountDAO() {
        return accountDAO;
    }

    public static KlantDAO getKlantDAO() {
        return klantDAO;
    }

    public static AdresDAO getAdresDAO() {
        return adresDAO;
    }
    
    public static ArtikelDAO getArtikelDAO() {
        return artikelDAO;
    }
}
