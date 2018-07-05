package nl.workshop1.model;

import java.math.BigDecimal;
import nl.workshop1.utility.Slf4j;

/**
 *
 * @author FeniksBV
 */
public class BestelRegel implements Cloneable{

    private int id;
    private int bestellingId;
    private int artikelId;
    private String artikelNaam;
    private int aantal;
    private BigDecimal prijs;

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the bestellingId
     */
    public int getBestellingId() {
        return bestellingId;
    }

    /**
     * @param bestellingId the bestellingId to set
     */
    public void setBestellingId(int bestellingId) {
        this.bestellingId = bestellingId;
    }

    /**
     * @return the artikel
     */
    public int getArtikelId() {
        return artikelId;
    }

    /**
     * @param artikel the artikel to set
     */
    public void setArtikelId(int artikelId) {
        this.artikelId = artikelId;
    }

    /**
     * @return the aantal
     */
    public int getAantal() {
        return aantal;
    }

    /**
     * @param aantal the aantal to set
     */
    public void setAantal(int aantal) {
        this.aantal = aantal;
    }

    /**
     * @return the prijs
     */
    public BigDecimal getPrijs() {
        return prijs;
    }

    /**
     * @param prijs the prijs to set
     */
    public void setPrijs(BigDecimal prijs) {
        this.prijs = prijs;
    }

    /**
     * @return the artikelNaam
     */
    public String getArtikelNaam() {
        return artikelNaam;
    }

    /**
     * @param artikelNaam the artikelNaam to set
     */
    public void setArtikelNaam(String artikelNaam) {
        this.artikelNaam = artikelNaam;
    }

    @Override
    public Object clone() {
        try {
            return (BestelRegel) super.clone();
        } catch (CloneNotSupportedException ex) {
            Slf4j.getLogger().error("Cloning BestelRegel exception", ex);
        }
        return null;
    }
    
}
