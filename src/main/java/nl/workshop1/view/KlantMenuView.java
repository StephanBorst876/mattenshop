package nl.workshop1.view;

import nl.workshop1.menu.Menu;

/**
 *
 * @author FeniksBV
 */
public class KlantMenuView extends MenuView{
    
    public <E extends Menu> KlantMenuView(E menu) {
        super(menu);
    }
    
    @Override
    public String runViewer() {
        while (true) {
            drawMenu();
            String requestedAction = userChoice();
            if (requestedAction.equals("2")) {
                // Filter ingeven
                getMenu().setFilter(getInputFilter());
            }

            if (requestedAction.compareTo("0") >= 0 && requestedAction.compareTo("4") <= 0) {
                return requestedAction;
            }

            if (requestedAction.compareTo("A") >= 0 && requestedAction.compareTo("Z") <= 0) {
                // Dit kunnen we intern afhandelen
                Menu menu = getMenu();
                if (!menu.isRecordSelected()) {
                    int selected = (int) (requestedAction.charAt(0) - 'A');
                    if (selected < menu.getRecordList().size()) {
                        menu.setRecordSelected(true);
                        menu.setRecordSelectedIndex(selected);
                        menu.clearSubMenuList();
                        menu.buildGeneralSubMenuList();
                    }

                }

            }
        }
    }
        
}
