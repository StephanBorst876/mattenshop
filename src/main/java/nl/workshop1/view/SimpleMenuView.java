package nl.workshop1.view;

import nl.workshop1.menu.Menu;

/**
 *
 * @author FeniksBV
 */
public class SimpleMenuView extends MenuView {

    public <E extends Menu> SimpleMenuView(E menu) {
        super(menu);
    }

    @Override
    public String runViewer() {
        while (true) {
            drawMenu();
            return userChoice();
        }
    }

}
