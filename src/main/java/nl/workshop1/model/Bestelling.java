package nl.workshop1.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author FeniksBV
 */
public class Bestelling {
    private int id;
    private int klantId;
    private BigDecimal totaalprijs;
    private Date bestelDatum;
    private Bestelstatus bestelstatus;

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
     * @return the klantId
     */
    public int getKlantId() {
        return klantId;
    }

    /**
     * @param klantId the klantId to set
     */
    public void setKlantId(int klantId) {
        this.klantId = klantId;
    }

    /**
     * @return the totaalprijs
     */
    public BigDecimal getTotaalprijs() {
        return totaalprijs;
    }

    /**
     * @param totaalprijs the totaalprijs to set
     */
    public void setTotaalprijs(BigDecimal totaalprijs) {
        this.totaalprijs = totaalprijs;
    }

    /**
     * @return the bestelDatum
     */
    public Date getBestelDatum() {
        return bestelDatum;
    }

    /**
     * @param bestelDatum the bestelDatum to set
     */
    public void setBestelDatum(Date bestelDatum) {
        this.bestelDatum = bestelDatum;
    }

    /**
     * @return the bestelstatus
     */
    public Bestelstatus getBestelstatus() {
        return bestelstatus;
    }

    /**
     * @param bestelstatus the bestelstatus to set
     */
    public void setBestelstatus(Bestelstatus bestelstatus) {
        this.bestelstatus = bestelstatus;
    }
  
}
