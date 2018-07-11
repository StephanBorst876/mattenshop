package nl.workshop1.DAO;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import nl.workshop1.model.Artikel;
import nl.workshop1.utility.Slf4j;

/**
 *
 * @author FeniksBV
 */
public class ArtikelDAOImpl implements ArtikelDAO {

    // Er wordt geen fysieke delete gedaan van het artikel record.
    private final String ARTIKEL_DELETE
            = "update artikel set aktief = 0"
            + " where id = ?";

    private final String ARTIKEL_INSERT = "insert into artikel "
            + "(naam,prijs,voorraad,gereserveerd,sortering) "
            + "values (?,?,?,?,?)";

    private final String ARTIKEL_UPDATE = "update artikel "
            + "set naam=?,prijs=?,voorraad=?,gereserveerd=?,sortering=? "
            + "where id = ?";

    private final String ARTIKEL_LIKE
            = "SELECT id, naam, prijs, voorraad, gereserveerd, sortering FROM artikel "
            + "WHERE naam like ? AND aktief <> 0 "
            + "ORDER BY sortering";
    
    private final String ARTIKEL_SELECT_NAAM
            = "SELECT id, naam, prijs, voorraad, gereserveerd, sortering FROM artikel "
            + "WHERE naam = ? ";

    private final String ARTIKEL_SELECT_ID
            = "SELECT id, naam, prijs, voorraad, gereserveerd, sortering FROM artikel "
            + "WHERE id = ? ";
    
    @Override
    public void deleteArtikel(int id) {
        Slf4j.getLogger().info("deleteArtikel({})", id);

        try {

            Connection connObj = DbConnection.getConnection();
            PreparedStatement pstmtObj = connObj.prepareStatement(ARTIKEL_DELETE);

            pstmtObj.setInt(1, id);
            pstmtObj.execute();

            Slf4j.getLogger().info("deleteArtikel() ended.");

        } catch (Exception sqlException) {
            Slf4j.getLogger().error("SQL exception occurred ", sqlException);
        }
    }

    @Override
    public void insertArtikel(Artikel artikel) {
        Slf4j.getLogger().info("insertArtikel({})", artikel.getNaam());

        try {

            Connection connObj = DbConnection.getConnection();
            PreparedStatement pstmtObj = connObj.prepareStatement(ARTIKEL_INSERT,
                    PreparedStatement.RETURN_GENERATED_KEYS);

            pstmtObj.setString(1, artikel.getNaam());
            pstmtObj.setFloat(2, artikel.getPrijs().floatValue());
            pstmtObj.setInt(3, artikel.getVoorraad());
            pstmtObj.setInt(4, artikel.getGereserveerd());
            pstmtObj.setInt(5, artikel.getSortering());

            pstmtObj.execute();

            ResultSet keyResultSet = pstmtObj.getGeneratedKeys();

            int newArtikelId = 0;
            if (keyResultSet.next()) {
                newArtikelId = (int) keyResultSet.getInt(1);
            }
            Slf4j.getLogger().info("insertArtikel({}) ended", newArtikelId);

            artikel.setId(newArtikelId);

        } catch (Exception sqlException) {
            Slf4j.getLogger().error("SQL exception occurred ", sqlException);
        }

    }

    @Override
    public void updateArtikel(Artikel artikel) {

        Slf4j.getLogger().info("updateArtikel({})", artikel.getId());

        try {

            Connection connObj = DbConnection.getConnection();
            PreparedStatement pstmtObj = connObj.prepareStatement(ARTIKEL_UPDATE);

            pstmtObj.setString(1, artikel.getNaam());
            pstmtObj.setFloat(2, artikel.getPrijs().floatValue());
            pstmtObj.setInt(3, artikel.getVoorraad());
            pstmtObj.setInt(4, artikel.getGereserveerd());
            pstmtObj.setInt(5, artikel.getSortering());
            pstmtObj.setInt(6, artikel.getId());

            pstmtObj.execute();

            Slf4j.getLogger().info("updateArtikel() ended.", pstmtObj.toString());

        } catch (Exception sqlException) {
            Slf4j.getLogger().error("SQL exception occurred ", sqlException);
        }

    }

    protected ArrayList<Artikel> selectArtikel(String query, String arg) {

        Slf4j.getLogger().info(query + " " + arg);

        ArrayList<Artikel> artikelList = new ArrayList<>();

        try {

            Connection connObj = DbConnection.getConnection();
            PreparedStatement pstmtObj = connObj.prepareStatement(query);

            pstmtObj.setString(1, arg);
            try (ResultSet resultSet = pstmtObj.executeQuery()) {
                while (resultSet.next()) {
                    Artikel artikel = new Artikel();
                    artikel.setId(resultSet.getInt("id"));
                    artikel.setNaam(resultSet.getString("naam"));

                    BigDecimal value = new BigDecimal(resultSet.getFloat("prijs"));
                    artikel.setPrijs(value.setScale(2, RoundingMode.HALF_UP));

                    artikel.setVoorraad(resultSet.getInt("voorraad"));
                    artikel.setGereserveerd(resultSet.getInt("gereserveerd"));
                    artikel.setSortering(resultSet.getInt("sortering"));

                    artikelList.add(artikel);
                }
            }

        } catch (Exception sqlException) {
            Slf4j.getLogger().error("SQL exception occurred", sqlException);
        }
        return artikelList;

    }

    @Override
    public ArrayList<Artikel> readArtikelWithFilter(String filter) {
        ArrayList<Artikel> artikelList = selectArtikel(ARTIKEL_LIKE, "%" + filter + "%");
        return artikelList;
    }

    @Override
    public Artikel readArtikelByNaam(String naam) {
        //
        // Er mogen geen 2 artikele met een gelijke naam in de tabel.
        //
        ArrayList<Artikel> artikelList = selectArtikel(ARTIKEL_SELECT_NAAM, naam);
        if (artikelList.size() == 1) {
            return artikelList.get(0);
        }
        return null;
    }

    
    protected ArrayList<Artikel> selectArtikelId(String query, int id) {

        Slf4j.getLogger().info(query + " " + id);

        ArrayList<Artikel> artikelList = new ArrayList<>();

        try {

            Connection connObj = DbConnection.getConnection();
            PreparedStatement pstmtObj = connObj.prepareStatement(query);

            pstmtObj.setInt(1, id);
            try (ResultSet resultSet = pstmtObj.executeQuery()) {
                while (resultSet.next()) {
                    Artikel artikel = new Artikel();
                    artikel.setId(resultSet.getInt("id"));
                    artikel.setNaam(resultSet.getString("naam"));

                    BigDecimal value = new BigDecimal(resultSet.getFloat("prijs"));
                    artikel.setPrijs(value.setScale(2, RoundingMode.HALF_UP));

                    artikel.setVoorraad(resultSet.getInt("voorraad"));
                    artikel.setGereserveerd(resultSet.getInt("gereserveerd"));
                    artikel.setSortering(resultSet.getInt("sortering"));

                    artikelList.add(artikel);
                }
            }

        } catch (Exception sqlException) {
            Slf4j.getLogger().error("SQL exception occurred", sqlException);
        }
        return artikelList;

    }
    
    @Override
    public Artikel readArtikelById(int id) {
        //
        // Er mogen geen 2 artikele met een gelijke naam in de tabel.
        //
        ArrayList<Artikel> artikelList = selectArtikelId(ARTIKEL_SELECT_ID, id);
        if (artikelList.size() == 1) {
            return artikelList.get(0);
        }
        return null;
    }

    
}
