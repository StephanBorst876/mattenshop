package nl.workshop1.model;

/**
 *
 * @author FeniksBV
 */
public enum Bestelstatus {
    Onderhanden("Onderhanden"), Afgehandeld("Afgehandeld");

    private String description;

    private Bestelstatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
