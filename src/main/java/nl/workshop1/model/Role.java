package nl.workshop1.model;

/**
 *
 * @author FeniksBV
 */
public enum Role {
    ROLE_ADMIN("Admin"), ROLE_MEDEWERKER("Medewerker"), ROLE_KLANT("Klant");

    private String description;

    private Role(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static String allOptions() {
        StringBuilder str = new StringBuilder();
        str.append(Role.ROLE_ADMIN.description.charAt(0)).append('=').append(ROLE_ADMIN.description).append(",");
        str.append(Role.ROLE_MEDEWERKER.description.charAt(0)).append('=').append(ROLE_MEDEWERKER.description).append(",");
        str.append(Role.ROLE_KLANT.description.charAt(0)).append('=').append(ROLE_KLANT.description);
        return str.toString();

    }

    public static Role getRole(String role) {
        switch (role) {
            case "A":
                return Role.ROLE_ADMIN;
            case "M":
                return Role.ROLE_MEDEWERKER;
            case "K":
                return Role.ROLE_KLANT;
            default:
                return null;
        }
    }

}
