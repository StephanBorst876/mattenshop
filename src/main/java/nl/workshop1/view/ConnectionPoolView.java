package nl.workshop1.view;

/**
 *
 * @author FeniksBV
 */
public class ConnectionPoolView extends View {

    public ConnectionPoolView(Menu cpMenu) {
        super(cpMenu);
    }

    @Override
    public String runViewer() {
        // Een menu met alleen een titel, dus eenmalig opbouwen
        drawMenu();
        while (true) {
            OutputText.showError("Default wordt hikariCP gebruikt.\n");
            if (getInputAkkoord("Wilt u JDBC gebruiken?")) {
                OutputText.showMessage("\nJDBC zal worden gebruikt!");
                return "J";
            }
            return "N";
        }

    }

}
