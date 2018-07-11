package nl.workshop1.view;

/**
 *
 * @author FeniksBV
 */
public class MenuView extends View {

    public MenuView(Menu menu) {
        super(menu);
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
                Menu menu = getMenu();
                if (!menu.isRecordSelected()) {
                    int selected = (int) (requestedAction.charAt(0) - 'A');
                    if (selected < menu.getRecordList().size()) {
                        menu.setRecordSelected(true);
                        menu.setRecordSelectedIndex(selected);
                        menu.buildGeneralSubMenuList();
                        setMenu(menu);
                    }
                }
                
                if (menu.getTitle().equals(Menu.TITEL_ZOEK_KLANTEN)
                        || menu.getTitle().equals(Menu.TITEL_ZOEK_ARTIKELEN)) {
                    // Direct teruggeven, want voor SEARCH-MODE moeten we iets aparts doen.
                    return requestedAction;
                }
            }
        }
    }

}
