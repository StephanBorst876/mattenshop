package nl.workshop1.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import nl.workshop1.model.Adres;
import nl.workshop1.model.Klant;
import nl.workshop1.utility.Slf4j;

/**
 *
 * @author FeniksBV
 */
public class KlantDAOImpl implements KlantDAO {

    private final String KLANT_SELECT
            = "SELECT k.id,k.email,k.voornaam,k.achternaam,k.tussenvoegsel,k.sortering,"
            + "a.id,a.straatnaam,a.huisnummer,a.toevoeging,a.postcode,a.woonplaats,a.klant_id,a.adres_type "
            + "FROM klant k, adres a "
            + "WHERE id = ? AND aktief <> 0 AND k.id = a.klant_id "
            + "ORDER BY k.sortering,k.id";

    private final String KLANT_LIKE
            = "SELECT k.id,k.email,k.voornaam,k.achternaam,k.tussenvoegsel,k.sortering,"
            + "a.id,a.straatnaam,a.huisnummer,a.toevoeging,a.postcode,a.woonplaats,a.klant_id,a.adres_type "
            + "FROM klant k, adres a "
            + "WHERE achternaam like ? AND aktief <> 0 AND k.id = a.klant_id "
            + "ORDER BY k.sortering,k.id";

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

        Slf4j.getLogger().info(query + " " + filter);

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
                int previousKlantId = -1;
                Klant klant = null;
                while (resultSet.next()) {
                    int currentKlantId = resultSet.getInt("k.id");
                    if (currentKlantId != previousKlantId) {
                        previousKlantId = currentKlantId;
                        if (klant != null) {
                            klantList.add(klant);
                        }
                        klant = new Klant();
                        klant.setId(currentKlantId);
                        klant.setEmail(resultSet.getString("k.email"));
                        klant.setVoornaam(resultSet.getString("k.voornaam"));
                        klant.setAchternaam(resultSet.getString("k.achternaam"));
                        klant.setTussenvoegsel(resultSet.getString("k.tussenvoegsel"));
                        klant.setSortering(resultSet.getInt("k.sortering"));
                    }

                    Adres adres = new Adres();
                    adres.setId(resultSet.getInt("a.id"));
                    adres.setStraatNaam(resultSet.getString("a.straatnaam"));
                    adres.setHuisNummer(resultSet.getInt("a.huisnummer"));
                    adres.setToevoeging(resultSet.getString("a.toevoeging"));
                    adres.setPostcode(resultSet.getString("a.postcode"));
                    adres.setWoonplaats(resultSet.getString("a.woonplaats"));
                    adres.setAdresType(resultSet.getString("a.adres_type"));
                    adres.setKlantId(resultSet.getInt("a.klant_id"));
                    if (klant != null) {
                        klant.addAdresList(adres);
                    }

                    if (resultSet.isLast()) {
                        klantList.add(klant);
                    }
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

            //
            // Nu doe de handling van de adressen
            //
            // Using the getGeneratedKeys() method to retrieve
            // the key(s). In this case there is only one key column: klant.id
            ResultSet keyResultSet = pstmtObj.getGeneratedKeys();

            int newKlantId = 0;
            if (keyResultSet.next()) {
                newKlantId = (int) keyResultSet.getInt(1);
            }

            // het door de DB gegenereerde klant.id toekennen aan alle adres records
            for (int i = 0; i < klant.getAdresList().size(); i++) {
                klant.getAdresList().get(i).setKlantId(newKlantId);
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
