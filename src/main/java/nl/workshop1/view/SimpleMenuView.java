package nl.workshop1.view;

/**
 *
 * @author FeniksBV
 */
public class SimpleMenuView extends View {

    public  SimpleMenuView(Menu menu) {
        super(menu);
    }

    /**
     *  Straight forward menu without changing menu-options
     *  All options are fixed-text
     * @return  selected option
     */
    @Override
    public String runViewer() {
        while (true) {
            drawMenu();
            return userChoice();
        }
    }

}
