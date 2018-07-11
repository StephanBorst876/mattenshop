package nl.workshop1.DAO;

/**
 *
 * @author FeniksBV
 */
public class DAOFactory {

    // MySQL
    private static AccountDAO accountDAO = new AccountDAOImpl();
    private static KlantDAO klantDAO = new KlantDAOImpl();
    private static AdresDAO adresDAO = new AdresDAOImpl();
    private static ArtikelDAO artikelDAO = new ArtikelDAOImpl();
    private static BestellingDAO bestellingDAO = new BestellingDAOImpl();
    private static BestelRegelDAO bestelRegelDAO = new BestelRegelDAOImpl();

    // MongoDB
    private static AccountDAO accountDAOmongo = new AccountDAOmongoImpl();
    private static KlantDAO klantDAOmongo = new KlantDAOmongoImpl();
    private static AdresDAO adresDAOmongo = new AdresDAOmongoImpl();
    private static ArtikelDAO artikelDAOmongo = new ArtikelDAOmongoImpl();
    private static BestellingDAO bestellingDAOmongo = new BestellingDAOmongoImpl();
    private static BestelRegelDAO bestelRegelDAOmongo = new BestelRegelDAOmongoImpl();

    public static AccountDAO getAccountDAO() {
        if (isMySQL()) {
            return accountDAO;
        } else {
            return accountDAOmongo;
        }
    }

    public static KlantDAO getKlantDAO() {
       if (isMySQL()) {
            return klantDAO;
        } else {
            return klantDAOmongo;
        }
    }

    public static AdresDAO getAdresDAO() {
        if (isMySQL()) {
            return adresDAO;
        } else {
            return adresDAOmongo;
        }
    }

    public static ArtikelDAO getArtikelDAO() {
        if (isMySQL()) {
            return artikelDAO;
        } else {
            return artikelDAOmongo;
        }
    }

    public static BestellingDAO getBestellingDAO() {
        if (isMySQL()) {
            return bestellingDAO;
        } else {
            return bestellingDAOmongo;
        }
    }

    public static BestelRegelDAO getBestelRegelDAO() {
        if (isMySQL()) {
            return bestelRegelDAO;
        } else {
            return bestelRegelDAOmongo;
        }
    }

    protected static boolean isMySQL() {
        return (DbConnection.getDbSelectie().equals(DbConnection.DB_MYSQL));
    }
}
