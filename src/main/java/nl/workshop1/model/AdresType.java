package nl.workshop1.model;

/**
 *
 * @author FeniksBV
 */
public enum AdresType {
    Postadres("Postadres"),Factuuradres("Factuuradres"),Bezorgadres("Bezorgadres");
    // Postadres/Factuuradres/Bezorgadres
    // Postadres is verplicht
    // Factuuradres kan Postadres overrulen
    // Bezorgadres kan Postadres overrulen
    
    private String description;

    private AdresType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
    
    public static AdresType getAdresType( String adresType ){
        switch (adresType) {
            case "P":
                return AdresType.Postadres;
            case "F":
                return AdresType.Factuuradres;
            case "B":
                return AdresType.Bezorgadres;
            default:
                return null;
        }
    }
}
