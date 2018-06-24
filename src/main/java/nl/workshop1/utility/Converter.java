package nl.workshop1.utility;

import nl.workshop1.model.AdresType;
import nl.workshop1.model.Role;

/**
 *
 * @author FeniksBV
 */
public class Converter {

    public static Role stringNaarRole(String role) {
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

    public static AdresType stringNaarAdresType(String adresType) {
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
    
    public static <E extends Enum<E>> String EnumNaarDB(Enum<?> myEnum) {
        if (myEnum instanceof Role) {
            return ((Role) myEnum).getDescription().substring(0, 1);
        } else if (myEnum instanceof AdresType) {
            return ((AdresType) myEnum).getDescription().substring(0, 1);
        }
        return "";
    }
}
