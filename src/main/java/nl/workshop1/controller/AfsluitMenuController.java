package nl.workshop1.controller;

import nl.workshop1.menu.Menu;
import nl.workshop1.view.SimpleMenuView;

/**
 *
 * @author FeniksBV
 */
public class AfsluitMenuController extends MenuController {

    private Menu afsluitMenu;
    private SimpleMenuView afsluitMenuView;

    public AfsluitMenuController() {
        this.afsluitMenu = new Menu("Afsluiten");
        this.afsluitMenuView = new SimpleMenuView(this.afsluitMenu);
    }

    @Override
    public void runController() {
        afsluitMenu.clearSubMenuList();
        afsluitMenu.addSubMenu("Afsluiten", "1");
        while (true) {
            afsluitMenuView.drawMenu();
            requestedAction = afsluitMenuView.runViewer();
            
            // Oeps. Pas hier op: Alle menu's geven optie=0 als zijnde
            // terug, maar de afsluitController moet 1 geven als Afsluiten.
            
            return;
        }
    }
    

}
