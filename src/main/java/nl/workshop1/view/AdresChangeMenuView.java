package nl.workshop1.view;

/**
 *
 * @author FeniksBV
 */
public class AdresChangeMenuView extends MenuView {

    public String getInputStraatnaam() {
        showInputText("Straatnaam : ");
        return input.nextLine();
    }

    public int getInputHuisnummer() {
        while (true) {
            try {
                showInputText("Huisnummer : ");
                String s = input.nextLine();
                int huisnummer = Integer.parseInt(s);
                return huisnummer;
            } catch (NumberFormatException ex) {
            }
        }
    }

    public String getInputToevoeging() {
        showInputText("Toevoeging : ");
        return input.nextLine();
    }

    public String getInputPostcode() {
        showInputText("Postcode : ");
        return input.nextLine().toUpperCase();
    }

    public String getInputWoonplaats() {
        showInputText("Woonplaats : ");
        return input.nextLine();
    }

}
