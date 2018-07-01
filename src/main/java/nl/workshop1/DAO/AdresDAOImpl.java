package nl.workshop1.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import nl.workshop1.model.Adres;
import nl.workshop1.model.AdresType;
import nl.workshop1.utility.Slf4j;

/**
 *
 * @author FeniksBV
 */
public class AdresDAOImpl implements AdresDAO {

    private final String ADRES_SELECT
            = "SELECT id,straatnaam,huisnummer,toevoeging,postcode,woonplaats,klant_id,adres_type "
            + "FROM adres "
            + "WHERE klant_id = ?";
    
    private final String ADRES_INSERT = "insert into adres "
            + "(straatnaam,huisnummer,toevoeging,postcode,woonplaats,klant_id,adres_type) "
            + "values (?,?,?,?,?,?,?)";

    private final String ADRES_UPDATE = "UPDATE adres "
            + "SET straatnaam=?,huisnummer=?,toevoeging=?,postcode=?,woonplaats=? "
            + "WHERE id = ?";

    private final String ADRES_DELETE
            = "DELETE FROM adres WHERE id = ?";

    @Override
    public void deleteAdres(int id) {
        Slf4j.getLogger().info("deleteAdres({})", id);

        try {

            Connection connObj = DbConnection.getConnection();
            PreparedStatement pstmtObj = connObj.prepareStatement(ADRES_DELETE);

            pstmtObj.setInt(1, id);
            pstmtObj.execute();

            Slf4j.getLogger().info("deleteAdres() ended.");

        } catch (Exception sqlException) {
            Slf4j.getLogger().error("SQL exception occurred ", sqlException);
        }
    }

    @Override
    public void insertAdres(Adres adres) {
        Slf4j.getLogger().info("insertAdres()");

        try {

            Connection connObj = DbConnection.getConnection();
            PreparedStatement pstmtObj = connObj.prepareStatement(ADRES_INSERT);

            pstmtObj.setString(1, adres.getStraatNaam());
            pstmtObj.setInt(2, adres.getHuisNummer());
            pstmtObj.setString(3, adres.getToevoeging());
            pstmtObj.setString(4, adres.getPostcode());
            pstmtObj.setString(5, adres.getWoonplaats());
            pstmtObj.setInt(6, adres.getKlantId());
            pstmtObj.setString(7, adres.getAdresType().getDescription());

            pstmtObj.execute();

            Slf4j.getLogger().info("insertAdres() ended.");

        } catch (Exception sqlException) {
            Slf4j.getLogger().error("SQL exception occurred ", sqlException);
        }
    }

    @Override
    public void updateAdres(Adres adres) {
        Slf4j.getLogger().info("updateAdres({})", adres.getId());

        try {

            Connection connObj = DbConnection.getConnection();
            PreparedStatement pstmtObj = connObj.prepareStatement(ADRES_UPDATE);

            pstmtObj.setString(1, adres.getStraatNaam());
            pstmtObj.setInt(2, adres.getHuisNummer());
            pstmtObj.setString(3, adres.getToevoeging());
            pstmtObj.setString(4, adres.getPostcode());
            pstmtObj.setString(5, adres.getWoonplaats());
            pstmtObj.setInt(6, adres.getId());
            pstmtObj.execute();

            Slf4j.getLogger().info("updateAdres() ended.");

        } catch (Exception sqlException) {
            Slf4j.getLogger().error("SQL exception occurred ", sqlException);
        }
    }

    @Override
    public ArrayList<Adres> readAdresWithKlantId(int klantId) {

        Slf4j.getLogger().info(ADRES_SELECT + " " + klantId);

        ArrayList<Adres> adresList = new ArrayList<>();

        try {

            Connection connObj = DbConnection.getConnection();
            PreparedStatement pstmtObj = connObj.prepareStatement(ADRES_SELECT);

            pstmtObj.setInt(1, klantId);
            try (ResultSet resultSet = pstmtObj.executeQuery()) {
                while (resultSet.next()) {
                    Adres adres = new Adres();
                    // id,straatnaam,huisnummer,toevoeging,postcode,woonplaats,klant_id,adres_type
                    adres.setId(resultSet.getInt("id"));
                    adres.setStraatNaam(resultSet.getString("straatnaam"));
                    adres.setHuisNummer(resultSet.getInt("huisnummer"));
                    adres.setToevoeging(resultSet.getString("toevoeging"));
                    adres.setPostcode(resultSet.getString("postcode"));
                    adres.setWoonplaats(resultSet.getString("woonplaats"));
                    adres.setKlantId(resultSet.getInt("klant_id"));
                    adres.setAdresType(AdresType.valueOf(resultSet.getString("adres_type")));
                                        
                    adresList.add(adres);
                }
            }

        } catch (Exception sqlException) {
            Slf4j.getLogger().error("SQL exception occurred", sqlException);
        }
        return adresList;
    }

}
