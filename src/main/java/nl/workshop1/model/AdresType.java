package nl.workshop1.model;

/**
 *
 * @author FeniksBV
 */
public enum AdresType {
    Postadres("Postadres"),Factuuradres("Factuuradres"),Bezorgadres("Bezorgadres");
    // Postadres/Factuuradres/Bezorgadres
    
    private String description;

    private AdresType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
    
}
