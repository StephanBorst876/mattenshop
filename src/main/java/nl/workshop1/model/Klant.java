package nl.workshop1.model;

import java.util.ArrayList;

/**
 *
 * @author FeniksBV
 */
public class Klant {

    private int id = 0;
    private String email = "";
    private String voornaam = "";
    private String achternaam = "'";
    private String tussenvoegsel = "";
    private ArrayList<Adres> adresList = new ArrayList<>();
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
        if (!tussenvoegsel.equals("")) {
            s.append(tussenvoegsel).append(" ");
        }
        s.append(achternaam);
        return s.toString();
    }

    public void addAdresList(Adres adres) {
        adresList.add(adres);
    }

    public Adres getAdres(AdresType adresType) {
        for (int i = 0; i < adresList.size(); i++) {
            if (adresList.get(i).getAdresType().equals(adresType)) {
                return adresList.get(i);
            }
        }
        return null;
    }
    
    public ArrayList<Adres> getAdresList() {
        return adresList;
    }
}
