package nl.workshop1.controller;

import nl.workshop1.view.AfsluitMenu;
import nl.workshop1.utility.Slf4j;
import nl.workshop1.view.AfsluitView;

/**
 *
 * @author FeniksBV
 */
public class AfsluitMenuController extends MenuController {

    private AfsluitMenu afsluitMenu;
    private AfsluitView afsluitView;

    public AfsluitMenuController(AfsluitMenu afsluitMenu, AfsluitView afsluitView ) {
        this.afsluitMenu = afsluitMenu;
        this.afsluitView = afsluitView;
    }

    @Override
    public void buildOptionsMenu() {
        afsluitMenu.buildMenu();
    }

    @Override
    public void handleMenu() {
        while (true) {
            afsluitMenu.drawMenu();
            switch (afsluitMenu.userChoice()) {
                case "1":
                    mattenshopAfsluiten();
                    break;
                case "2":
                    return;
            }
        }
    }

    public static void mattenshopAfsluiten() {

        AfsluitView.showError("\n\nThanks for using Mattenshop. See you soon!");

        Slf4j.getLogger().info( "Mattenshop ended");
        System.exit(0);
    }
}
