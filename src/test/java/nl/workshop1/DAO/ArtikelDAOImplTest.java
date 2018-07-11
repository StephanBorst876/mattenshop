package nl.workshop1.DAO;

import com.vogella.junit.first.sqlExecuteStatement;
import java.math.BigDecimal;
import nl.workshop1.model.Artikel;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author FeniksBV
 */
public class ArtikelDAOImplTest {

    private int overallArtikelId = 0;
    
    public ArtikelDAOImplTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        String query = "DELETE FROM artikel WHERE naam = \"testArtikelStephan\";";
        sqlExecuteStatement.executeStatement(query);
    }

    @After
    public void tearDown() {
        setUp();
        String query = "DELETE FROM artikel WHERE naam = \"testArtikelStephan\";";
        sqlExecuteStatement.executeStatement(query);
    }

    /**
     * Test of deleteArtikel method, of class ArtikelDAOImpl.
     */
    @Test
    public void testDeleteArtikel() {
        int id = 0;
        ArtikelDAOImpl instance = new ArtikelDAOImpl();
        instance.deleteArtikel(id);
    }

    /**
     * Test of insertArtikel method, of class ArtikelDAOImpl.
     */
    @Test
    public void testInsertArtikel() {
        Artikel artikel = new Artikel();
        artikel.setNaam("testArtikelStephan");
        artikel.setPrijs(new BigDecimal(12.12));
        artikel.setVoorraad(100);
        artikel.setGereserveerd(0);
        artikel.setSortering(0);
        ArtikelDAOImpl instance = new ArtikelDAOImpl();
        instance.insertArtikel(artikel);
//        assertEquals(expResult, result);
    }

    /**
     * Test of updateArtikel method, of class ArtikelDAOImpl.
     */
    @Test
    public void testUpdateArtikel() {
        Artikel artikel = new Artikel();
        artikel.setNaam("testArtikelStephan");
        artikel.setPrijs(new BigDecimal(12.12));
        artikel.setVoorraad(200);
        artikel.setGereserveerd(0);
        artikel.setSortering(0);
        ArtikelDAOImpl instance = new ArtikelDAOImpl();
        instance.updateArtikel(artikel);
    }

}
