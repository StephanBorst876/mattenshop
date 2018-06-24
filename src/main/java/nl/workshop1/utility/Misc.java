package nl.workshop1.utility;

import java.util.ArrayList;
import nl.workshop1.model.Adres;
import nl.workshop1.model.AdresType;

/**
 *
 * @author FeniksBV
 */
public class Misc {

    public static Adres getAdresTypeFromList(AdresType adresType, ArrayList<Adres> adres) {
        for (int i = 0; i < adres.size(); i++) {
            if (adres.get(i).getAdresType().equals(adresType)) {
                return adres.get(i);
            }
        }
        return null;
    }
}
