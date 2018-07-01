package nl.workshop1.model;

import nl.workshop1.utility.Slf4j;

/**
 *
 * @author FeniksBV
 */
public class Adres implements Cloneable {

    private int id = 0;
    private String straatNaam = "";
    private int huisNummer = 0;
    private String toevoeging = "";
    private String postcode = "";
    private String woonplaats = "";
    private int klantId = 0;
    private AdresType adresType = AdresType.Postadres;

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
     * @return the straatNaam
     */
    public String getStraatNaam() {
        return straatNaam;
    }

    /**
     * @param straatNaam the straatNaam to set
     */
    public void setStraatNaam(String straatNaam) {
        this.straatNaam = straatNaam;
    }

    /**
     * @return the huisNummer
     */
    public int getHuisNummer() {
        return huisNummer;
    }

    /**
     * @param huisNummer the huisNummer to set
     */
    public void setHuisNummer(int huisNummer) {
        this.huisNummer = huisNummer;
    }

    /**
     * @return the toevoeging
     */
    public String getToevoeging() {
        return toevoeging;
    }

    /**
     * @param toevoeging the toevoeging to set
     */
    public void setToevoeging(String toevoeging) {
        this.toevoeging = toevoeging;
    }

    /**
     * @return the postcode
     */
    public String getPostcode() {
        return postcode;
    }

    /**
     * @param postcode the postcode to set
     */
    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    /**
     * @return the woonplaats
     */
    public String getWoonplaats() {
        return woonplaats;
    }

    /**
     * @param woonplaats the woonplaats to set
     */
    public void setWoonplaats(String woonplaats) {
        this.woonplaats = woonplaats;
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
     * @return the adresType
     */
    public AdresType getAdresType() {
        return adresType;
    }

    /**
     * @param adresType the adresType to set
     */
    public void setAdresType(AdresType adresType) {
        this.adresType = adresType;
    }

    /**
     * @param newAdresType the adresType to set
     */
    public void setAdresType(String newAdresType) {
        AdresType tmpAdresType = AdresType.getAdresType(newAdresType);
        if (tmpAdresType != null) {
            this.adresType = tmpAdresType;
        }
    }

    

    @Override
    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException ex) {
            Slf4j.getLogger().error("Cloning Adres exception", ex);
        }
        return null;
    }
}
