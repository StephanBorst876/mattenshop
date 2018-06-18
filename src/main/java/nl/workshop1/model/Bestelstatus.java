package nl.workshop1.model;

/**
 *
 * @author FeniksBV
 */
public enum Bestelstatus {
    IN_BEHANDELING("In behandeling"), AFGEHANDELD("Afgehandeld");

    private String description;

    private Bestelstatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
