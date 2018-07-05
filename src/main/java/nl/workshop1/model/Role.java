package nl.workshop1.model;

/**
 *
 * @author FeniksBV
 */
public enum Role {
    Admin("Admin"), Medewerker("Medewerker"), Klant("Klant");

    private String description;

    private Role(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

}
