package nl.workshop1.view;

/**
 *
 * @author FeniksBV
 */
public class AfsluitMenu extends Menu {

    public AfsluitMenu() {
        super("Afsluiten");
    }

    @Override
    public void buildMenu() {
        resetMenu();
        disableAddReturnOption();
        addSubMenu("Afsluiten", "1");
        addSubMenu("Doorgaan", "2");
    }

}
