package nl.workshop1.DAO;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import nl.workshop1.model.Bestelling;
import nl.workshop1.model.Bestelstatus;
import nl.workshop1.utility.Slf4j;

/**
 *
 * @author FeniksBV
 */
public class BestellingDAOImpl implements BestellingDAO {

    // Plaats altijd de meeste recente bestelling bovenaan (desc)
    private final String BESTELLING_LIKE
            = "SELECT id,klant_id,totaalprijs,besteldatum,bestelstatus "
            + "FROM bestelling "
            + "WHERE klant_id = ? "
            + "AND bestelstatus = \"Onderhanden\" "
            + "AND id like ? "
            + "ORDER BY besteldatum DESC ";

    private final String BESTELLING_DELETE
            = "delete from bestelling "
            + "where id = ?";

    private final String BESTELLING_INSERT = "insert into bestelling "
            + "(klant_id,totaalprijs,besteldatum,bestelstatus) "
            + "values (?,?,?,?)";

    private final String BESTELLING_UPDATE = "update bestelling "
            + "set totaalprijs = ?,besteldatum = ? "
            + "where id = ?";

    private ArrayList<Bestelling> selectBestelling(String query, int id, String filter) {

        Slf4j.getLogger().info(query + " " + filter + " " + id);

        ArrayList<Bestelling> bestellingList = new ArrayList<>();

        try (Connection connObj = DbConnection.getConnection();
                PreparedStatement pstmtObj = connObj.prepareStatement(query);) {

            pstmtObj.setInt(1, id);
            pstmtObj.setString(2, filter);

            try (ResultSet resultSet = pstmtObj.executeQuery()) {
                while (resultSet.next()) {

                    Bestelling bestelling = new Bestelling();
                    bestelling.setId(resultSet.getInt("id"));
                    bestelling.setKlantId(resultSet.getInt("klant_id"));

                    BigDecimal value = new BigDecimal(resultSet.getFloat("totaalprijs"));
                    bestelling.setTotaalprijs(value.setScale(2, RoundingMode.HALF_UP));

                    bestelling.setBestelDatum(resultSet.getTimestamp("besteldatum"));
                    bestelling.setBestelstatus(Bestelstatus.valueOf(resultSet.getString("bestelstatus")));

                    bestellingList.add(bestelling);
                }
            }

        } catch (Exception sqlException) {
            Slf4j.getLogger().error("SQL exception occurred ", sqlException);
        }
        return bestellingList;

    }

    @Override
    public ArrayList<Bestelling> readBestellingWithFilter(int klantId, String filter) {
        Slf4j.getLogger().info("readBestellingWithKlantId({} {})", klantId, filter);

        ArrayList<Bestelling> bestellingList = selectBestelling(BESTELLING_LIKE, klantId,
                "%" + filter + "%");

        return bestellingList;
    }

    @Override
    public void deleteBestelling(int bestellingId) {
        Slf4j.getLogger().info("deleteBestelling({})", bestellingId);

        try (Connection connObj = DbConnection.getConnection();
                PreparedStatement pstmtObj = connObj.prepareStatement(BESTELLING_DELETE);) {

            pstmtObj.setInt(1, bestellingId);
            pstmtObj.execute();
            Slf4j.getLogger().info("deleteBestelling() ended.");

        } catch (Exception sqlException) {
            Slf4j.getLogger().error("SQL exception occurred ", sqlException);
        }

    }

    @Override
    public void insertBestelling(Bestelling bestelling) {
        Slf4j.getLogger().info("insertBestelling({})", bestelling.getKlantId());

        try (Connection connObj = DbConnection.getConnection();
                PreparedStatement pstmtObj = connObj.prepareStatement(BESTELLING_INSERT,
                        PreparedStatement.RETURN_GENERATED_KEYS);) {

            pstmtObj.setInt(1, bestelling.getKlantId());
            pstmtObj.setFloat(2, bestelling.getTotaalprijs().floatValue());
            pstmtObj.setTimestamp(3, getSQLDate(bestelling.getBestelDatum()));
            pstmtObj.setString(4, bestelling.getBestelstatus().getDescription());

            pstmtObj.execute();

            // Using the getGeneratedKeys() method to retrieve
            // the key(s). In this case there is only one key column: klant.id
            ResultSet keyResultSet = pstmtObj.getGeneratedKeys();

            if (keyResultSet.next()) {
                int newBestellingId = (int) keyResultSet.getInt(1);
                // Zojuist gegenereerde id toekennen aan het Bestelling object;
                // Het kan dan worden gebruikt om aan de te inserten
                // regels toe tee kennen.
                bestelling.setId(newBestellingId);
            }

            Slf4j.getLogger().info("insertBestelling() ended.");

        } catch (Exception sqlException) {
            Slf4j.getLogger().error("SQL exception occurred ", sqlException);
        }
    }

    protected static Timestamp getSQLDate(java.util.Date date) {
        return new java.sql.Timestamp(date.getTime());
    }

    @Override
    public void updateBestelling(Bestelling bestelling) {

        Slf4j.getLogger().info("updateBestelling({})", bestelling.getId());

        try (Connection connObj = DbConnection.getConnection();
                PreparedStatement pstmtObj = connObj.prepareStatement(BESTELLING_UPDATE);) {

            pstmtObj.setFloat(1, bestelling.getTotaalprijs().floatValue());
            pstmtObj.setTimestamp(2, getSQLDate(bestelling.getBestelDatum()));
            pstmtObj.setInt(3, bestelling.getId());
            Slf4j.getLogger().info(pstmtObj.toString());

            pstmtObj.execute();

            Slf4j.getLogger().info("updateBestelling() ended.", pstmtObj.toString());

        } catch (Exception sqlException) {
            Slf4j.getLogger().error("SQL exception occurred ", sqlException);
        }
    }

}
