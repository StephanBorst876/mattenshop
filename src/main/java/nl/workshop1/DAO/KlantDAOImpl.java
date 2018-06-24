package nl.workshop1.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import nl.workshop1.model.Klant;
import nl.workshop1.utility.Slf4j;

/**
 *
 * @author FeniksBV
 */
public class KlantDAOImpl implements KlantDAO {

    private final String KLANT_SELECT
            = "SELECT id,email,voornaam,achternaam,tussenvoegsel,sortering "
            + "FROM klant "
            + "WHERE id = ? "
            + "AND aktief <> 0  "
            + "ORDER BY sortering";

    private final String KLANT_LIKE
            = "SELECT id,email,voornaam,achternaam,tussenvoegsel,sortering "
            + "FROM klant "
            + "WHERE achternaam like ? AND aktief <> 0  "
            + "ORDER BY sortering";

    // Er wordt geen fysieke delete gedaan van het klant record.
    private final String KLANT_DELETE
            = "update klant set aktief = 0"
            + " where id = ?";

    private final String KLANT_INSERT = "insert into klant "
            + "(email,voornaam,achternaam,tussenvoegsel,sortering) "
            + "values (?,?,?,?,?)";

    private final String KLANT_UPDATE = "update klant "
            + "set email = ?,voornaam = ?,achternaam = ?,tussenvoegsel=?,sortering=? "
            + "where id = ?";

    private ArrayList<Klant> selectKlant(String query, String filter, int id) {

        Slf4j.getLogger().info(query + " " + filter + id);

        ArrayList<Klant> klantList = new ArrayList<>();

        try {

            Connection connObj = DbConnection.getConnection();
            PreparedStatement pstmtObj = connObj.prepareStatement(query);
            if (filter != null) {
                pstmtObj.setString(1, filter);
            } else {
                pstmtObj.setInt(1, id);
            }

            try (ResultSet resultSet = pstmtObj.executeQuery()) {
                while (resultSet.next()) {

                    Klant klant = new Klant();
                    klant.setId(resultSet.getInt("id"));
                    klant.setEmail(resultSet.getString("email"));
                    klant.setVoornaam(resultSet.getString("voornaam"));
                    klant.setAchternaam(resultSet.getString("achternaam"));
                    klant.setTussenvoegsel(resultSet.getString("tussenvoegsel"));
                    klant.setSortering(resultSet.getInt("sortering"));

                    klantList.add(klant);
                }
            }

        } catch (Exception sqlException) {
            Slf4j.getLogger().error("SQL exception occurred ", sqlException);
        }
        return klantList;

    }

    @Override
    public ArrayList<Klant> readKlantWithFilter(String filter) {
        Slf4j.getLogger().info("readKlantWithFilter({})", filter);

        ArrayList<Klant> klantList = selectKlant(KLANT_LIKE, "%" + filter + "%", 0);

        return klantList;
    }

    @Override
    public Klant readKlant(int id) {

        Slf4j.getLogger().info("readKlant({})", id);

        ArrayList<Klant> klantList = selectKlant(KLANT_SELECT, null, id);
        if (klantList.size() == 1) {
            return klantList.get(0);
        }
        return null;
    }

    @Override
    public void deleteKlant(int id) {
        Slf4j.getLogger().info("deleteKlant({})", id);

        try {
            Connection connObj = DbConnection.getConnection();
            PreparedStatement pstmtObj = connObj.prepareStatement(KLANT_DELETE);

            pstmtObj.setInt(1, id);
            pstmtObj.execute();
            Slf4j.getLogger().info("deleteKlant() ended.");

        } catch (Exception sqlException) {
            Slf4j.getLogger().error("SQL exception occurred ", sqlException);
        }
    }

    @Override
    public void insertKlant(Klant klant) {
        Slf4j.getLogger().info("insertKlant({})", klant.getEmail());

        try {

            Connection connObj = DbConnection.getConnection();
            PreparedStatement pstmtObj = connObj.prepareStatement(KLANT_INSERT,
                    PreparedStatement.RETURN_GENERATED_KEYS);

            pstmtObj.setString(1, klant.getEmail());
            pstmtObj.setString(2, klant.getVoornaam());
            pstmtObj.setString(3, klant.getAchternaam());
            pstmtObj.setString(4, klant.getTussenvoegsel());
            pstmtObj.setInt(5, klant.getSortering());

            pstmtObj.execute();

            // Using the getGeneratedKeys() method to retrieve
            // the key(s). In this case there is only one key column: klant.id
            ResultSet keyResultSet = pstmtObj.getGeneratedKeys();

            int newKlantId = 0;
            if (keyResultSet.next()) {
                newKlantId = (int) keyResultSet.getInt(1);
                // Zojuist gegenereerde id toekennen aan het klant object;
                // Het kan dan worden gebruikt om aan de te inserten
                // adressen toe tee kennen.
                klant.setId(newKlantId);
            }

            Slf4j.getLogger().info("insertKlant() ended with id={}", newKlantId);

        } catch (Exception sqlException) {
            Slf4j.getLogger().error("SQL exception occurred ", sqlException);
        }

    }

    @Override
    public void updateKlant(Klant klant) {

        Slf4j.getLogger().info("updateKlant({})", klant.getId());

        try {

            Connection connObj = DbConnection.getConnection();
            PreparedStatement pstmtObj = connObj.prepareStatement(KLANT_UPDATE);

            pstmtObj.setString(1, klant.getEmail());
            pstmtObj.setString(2, klant.getVoornaam());
            pstmtObj.setString(3, klant.getAchternaam());
            pstmtObj.setString(4, klant.getTussenvoegsel());
            pstmtObj.setInt(5, klant.getSortering());
            pstmtObj.setInt(6, klant.getId());

            pstmtObj.execute();

            Slf4j.getLogger().info("updateKlant() ended.", pstmtObj.toString());

        } catch (Exception sqlException) {
            Slf4j.getLogger().error("SQL exception occurred ", sqlException);
        }
    }

}
