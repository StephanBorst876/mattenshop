package nl.workshop1.DAO;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import nl.workshop1.model.BestelRegel;
import nl.workshop1.utility.Slf4j;

/**
 *
 * @author FeniksBV
 */
public class BestelRegelDAOImpl implements BestelRegelDAO {

    private final String BESTELREGEL_LIKE
            = "SELECT br.id,artikel_id,aantal,br.prijs,naam FROM bestel_regel br, artikel a "
            + "WHERE artikel_id = a.id and bestelling_id = ? "
            + "AND naam LIKE ? ";

    private final String BESTELREGEL_DELETE
            = "delete from bestel_regel where id = ?";

    private final String BESTELREGEL_INSERT
            = "insert into bestel_regel (bestelling_id,artikel_id,aantal,prijs) values (?, ?, ?, ?)";

    private final String BESTELREGEL_UPDATE
            = "update bestel_regel "
            + "set aantal=?,prijs=? "
            + "where id = ?";
    
    private ArrayList<BestelRegel> selectBestelRegel(String query, int id, String filter) {

        Slf4j.getLogger().info(query + " " + filter + " " + id);

        ArrayList<BestelRegel> bestelRegelList = new ArrayList<>();

        try {

            Connection connObj = DbConnection.getConnection();
            PreparedStatement pstmtObj = connObj.prepareStatement(query);
            pstmtObj.setInt(1, id);
            pstmtObj.setString(2, filter);

            try (ResultSet resultSet = pstmtObj.executeQuery()) {
                while (resultSet.next()) {

                    BestelRegel bestelRegel = new BestelRegel();
                    bestelRegel.setId(resultSet.getInt("br.id"));
                    bestelRegel.setArtikelId(resultSet.getInt("artikel_id"));
                    bestelRegel.setAantal(resultSet.getInt("aantal"));

                    BigDecimal value = new BigDecimal(resultSet.getFloat("br.prijs"));
                    bestelRegel.setPrijs(value.setScale(2, RoundingMode.HALF_UP));

                    bestelRegel.setArtikelNaam(resultSet.getString("naam"));

                    bestelRegelList.add(bestelRegel);
                }
            }

        } catch (Exception sqlException) {
            Slf4j.getLogger().error("SQL exception occurred ", sqlException);
        }
        return bestelRegelList;

    }

    public ArrayList<BestelRegel> readRegelsWithFilter(int bestellingId, String filter) {
        Slf4j.getLogger().info("readRegelsWithFilter({} {})", bestellingId, filter);

        ArrayList<BestelRegel> bestelRegelList = selectBestelRegel(BESTELREGEL_LIKE,
                bestellingId, "%" + filter + "%");

        return bestelRegelList;
    }

    @Override
    public void deleteBestelRegel(int bestelRegelId) {

        Slf4j.getLogger().info("deleteBestelRegel({})", bestelRegelId);

        try {

            Connection connObj = DbConnection.getConnection();
            PreparedStatement pstmtObj = connObj.prepareStatement(BESTELREGEL_DELETE);

            pstmtObj.setInt(1, bestelRegelId);
            pstmtObj.execute();
            Slf4j.getLogger().info("deleteBestelRegel() ended.");

        } catch (Exception sqlException) {
            Slf4j.getLogger().error("SQL exception occurred ", sqlException);
        }

    }

    @Override
    public void insertBestelRegel(BestelRegel bestelRegel) {
        Slf4j.getLogger().info("insertBestelRegel({})", bestelRegel.getArtikelNaam());

        try {

            Connection connObj = DbConnection.getConnection();
            PreparedStatement pstmtObj = connObj.prepareStatement(BESTELREGEL_INSERT);

            pstmtObj.setInt(1, bestelRegel.getBestellingId());
            pstmtObj.setInt(2, bestelRegel.getArtikelId());
            pstmtObj.setInt(3, bestelRegel.getAantal());
            pstmtObj.setFloat(4, bestelRegel.getPrijs().floatValue());

            pstmtObj.execute();
            Slf4j.getLogger().info("insertBestelRegel() ended.");

        } catch (Exception sqlException) {
            Slf4j.getLogger().error("SQL exception occurred ", sqlException);
        }
    }

    @Override
    public void updateBestelRegel(BestelRegel bestelRegel) {
        
        Slf4j.getLogger().info("updateBestelRegel({})", bestelRegel.getId());

        try {
            
            Connection connObj = DbConnection.getConnection();
            PreparedStatement pstmtObj = connObj.prepareStatement(BESTELREGEL_UPDATE);

            pstmtObj.setInt(1, bestelRegel.getAantal());
            pstmtObj.setFloat(2, bestelRegel.getPrijs().floatValue());
            pstmtObj.setInt(3, bestelRegel.getId());

            pstmtObj.execute();

            Slf4j.getLogger().info("updateBestelRegel() ended. {}", pstmtObj.toString());

        } catch (Exception sqlException) {
            Slf4j.getLogger().error("SQL exception occurred ", sqlException);
        }
    }

}
