package nl.workshop1.view;

import nl.workshop1.controller.Controller;

/**
 *
 * @author FeniksBV
 */
public class MenuView extends View {

    private Menu menu;

    public MenuView(Menu menu) {
        super(menu);
        this.menu = menu;
    }

    @Override
    public String runViewer() {
        while (true) {
            drawMenu();
            String requestedAction = userChoice();
            if (requestedAction.equals("2")) {
                // filter ingeven
                getMenu().setFilter(getInputFilter());
                return requestedAction;
            }

            if (requestedAction.compareTo("0") >= 0 && requestedAction.compareTo("4") <= 0) {
                return requestedAction;
            }

            if (requestedAction.compareTo("A") >= 0 && requestedAction.compareTo("Z") <= 0) {
                if (menu.getTitle().equals(Controller.TITEL_KLANTEN)) {
                    // Direct teruggeven, want voor SEARCH-MODE moeten we iets aparts doen.
                    return requestedAction;
                } else {
                    // Dit kunnen we intern afhandelen
                    Menu menu = getMenu();
                    if (!menu.isRecordSelected()) {
                        int selected = (int) (requestedAction.charAt(0) - 'A');
                        if (selected < menu.getRecordList().size()) {
                            menu.setRecordSelected(true);
                            menu.setRecordSelectedIndex(selected);
                            menu.buildGeneralSubMenuList();
                        }
                    }
                }
            }
        }
    }

}
