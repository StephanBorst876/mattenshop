package nl.workshop1.controller;

import nl.workshop1.view.Menu;
import nl.workshop1.view.SimpleMenuView;

/**
 *
 * @author FeniksBV
 */
public class AfsluitenController extends Controller {

    private Menu afsluitMenu;
    private SimpleMenuView afsluitMenuView;

    public AfsluitenController() {
        this.afsluitMenu = new Menu("Afsluiten");
        this.afsluitMenuView = new SimpleMenuView(this.afsluitMenu);
    }

    @Override
    public void runController() {
        afsluitMenu.clearSubMenuList();
        afsluitMenu.addSubMenu("Afsluiten", "1");
        // TODO: automatisch afsluiten
        requestedAction = "1";
        return;
//        requestedAction = afsluitMenuView.runViewer();
    }

}
