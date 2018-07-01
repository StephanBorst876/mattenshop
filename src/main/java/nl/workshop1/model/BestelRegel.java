package nl.workshop1.model;

import java.math.BigDecimal;

/**
 *
 * @author FeniksBV
 */
public class BestelRegel {

    private int id = 0;
    private int bestellingId = 0;
    private int artikelId = 0;
    private int aantal = 0;
    private BigDecimal prijs = new BigDecimal(0);

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
    public void setArtikel(int artikelId) {
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

    
}
