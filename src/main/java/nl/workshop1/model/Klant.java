package nl.workshop1.model;

import nl.workshop1.utility.Slf4j;

/**
 *
 * @author FeniksBV
 */
public class Klant implements Cloneable {

    private int id = 0;
    private String email = "";
    private String voornaam = "";
    private String achternaam = "";
    private String tussenvoegsel = "";
    //private ArrayList<Adres> adresList = new ArrayList<>();
    private int sortering = 0;

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
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the voornaam
     */
    public String getVoornaam() {
        return voornaam;
    }

    /**
     * @param voornaam the voornaam to set
     */
    public void setVoornaam(String voornaam) {
        this.voornaam = voornaam;
    }

    /**
     * @return the achternaam
     */
    public String getAchternaam() {
        return achternaam;
    }

    /**
     * @param achternaam the achternaam to set
     */
    public void setAchternaam(String achternaam) {
        this.achternaam = achternaam;
    }

    /**
     * @return the tussenvoegsel
     */
    public String getTussenvoegsel() {
        return tussenvoegsel;
    }

    /**
     * @param tussenvoegsel the tussenvoegsel to set
     */
    public void setTussenvoegsel(String tussenvoegsel) {
        this.tussenvoegsel = tussenvoegsel;
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

    public String getFullName() {
        StringBuilder s = new StringBuilder();
        // voornaam en achternaam zijn verplichte velden
        s.append(voornaam).append(" ");
        if (!tussenvoegsel.isEmpty()) {
            s.append(tussenvoegsel).append(" ");
        }
        s.append(achternaam);
        return s.toString();
    }

//    public void setAdresList(ArrayList<Adres> adresList) {
//        this.adresList = adresList;
//    }
//
//    public ArrayList<Adres> getAdresList() {
//        return adresList;
//    }

    @Override
    public Object clone() {
        try {
            Klant klantClone = (Klant) super.clone();
//            klantClone.adresList = new ArrayList<>();
//            for (int i = 0; i < getAdresList().size(); i++) {
//                klantClone.adresList.add((Adres) getAdresList().get(i).clone());
//            }
            return klantClone;
        } catch (CloneNotSupportedException ex) {
            Slf4j.getLogger().error("Cloning Klant exception", ex);
        }
        return null;
    }
}
