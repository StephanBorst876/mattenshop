package nl.workshop1.view;

/**
 *
 * @author FeniksBV
 */
public class KlantChangeMenuView extends MenuView {

    public String getInputVoornaam() {
        showInputText("Voornaam : ");
        return input.nextLine();
    }

    public String getInputTussenvoegsel() {
        showInputText("Tussenvoegsel : ");
        return input.nextLine();
    }

    public String getInputAchternaam() {
        showInputText("Achternaam : ");
        return input.nextLine();
    }

    public int getInputSortering() {
        while (true) {
            try {
                showInputText("Hoe lager de sortering, hoe hoger in de lijst.\n");
                showInputText("Sortering : ");
                String s = input.nextLine();
                int sortering =  Integer.parseInt(s);
                return sortering;
            } catch (NumberFormatException ex) {

            }
        }
    }

}
