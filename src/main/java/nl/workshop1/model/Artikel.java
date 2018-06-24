package nl.workshop1.model;

import java.math.BigDecimal;
import nl.workshop1.utility.Slf4j;

/**
 *
 * @author FeniksBV
 */
public class Artikel implements Cloneable{
  private int id = 0;
  private String naam = "";
  private BigDecimal prijs = new BigDecimal(0);
  private int voorraad = 0;
  private int gereserveerd = 0;
  private int sortering = 0;
  private boolean aktief = true;

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
     * @return the naam
     */
    public String getNaam() {
        return naam;
    }

    /**
     * @param naam the naam to set
     */
    public void setNaam(String naam) {
        this.naam = naam;
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
     * @return the voorraad
     */
    public int getVoorraad() {
        return voorraad;
    }

    /**
     * @param voorraad the voorraad to set
     */
    public void setVoorraad(int voorraad) {
        this.voorraad = voorraad;
    }

    /**
     * @return the gereserveerd
     */
    public int getGereserveerd() {
        return gereserveerd;
    }

    /**
     * @param gereserveerd the gereserveerd to set
     */
    public void setGereserveerd(int gereserveerd) {
        this.gereserveerd = gereserveerd;
    }

    /**
     * @return the sortering
     */
    public int getSortering() {
        return sortering;
    }

    /**
     * @param sortering the sortering to set
     */
    public void setSortering(int sortering) {
        this.sortering = sortering;
    }

    /**
     * @return the aktief
     */
    public boolean isAktief() {
        return aktief;
    }

    /**
     * @param aktief the aktief to set
     */
    public void setAktief(boolean aktief) {
        this.aktief = aktief;
    }
  
    @Override
    public Object clone() {
        try {
            return (Artikel) super.clone();
        } catch (CloneNotSupportedException ex) {
            Slf4j.getLogger().error("Cloning Artikel exception", ex);
        }
        return null;
    }
  
}
