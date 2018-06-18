package nl.workshop1.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import nl.workshop1.model.Adres;
import nl.workshop1.utility.Slf4j;

/**
 *
 * @author FeniksBV
 */
public class AdresDAOImpl implements AdresDAO {

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
            pstmtObj.setString(7, adres.getAdresType().getDescription().substring(0, 1));

            pstmtObj.execute();

            Slf4j.getLogger().info("insertAdres() ended.");

        } catch (Exception sqlException) {
            Slf4j.getLogger().error("SQL exception occurred ", sqlException);
        }
    }

    @Override
    public void updateAdres(Adres adres) {
        Slf4j.getLogger().info("updateAdres()");

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

}
