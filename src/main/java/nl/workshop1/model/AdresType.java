package nl.workshop1.model;

/**
 *
 * @author FeniksBV
 */
public enum AdresType {
    ADRES_POST("Postadres"),ADRES_FACTUUR("Factuuradres"),ADRES_BEZORG("Bezorgadres");
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
                return AdresType.ADRES_POST;
            case "F":
                return AdresType.ADRES_FACTUUR;
            case "B":
                return AdresType.ADRES_BEZORG;
            default:
                return null;
        }
    }
}
