package nl.workshop1.controller;

import nl.workshop1.view.ConnectionPoolView;
import nl.workshop1.view.Menu;

/**
 *
 * @author FeniksBV
 */
public class ConnectionPoolController extends Controller{

    private Menu cpMenu;
    private ConnectionPoolView cpView;

    public ConnectionPoolController() {
        this.cpMenu = new Menu(Menu.TITEL_CONNECTION_POOL);
        this.cpView = new ConnectionPoolView(cpMenu);
    }
    @Override
    public void runController() {
        while (true) {
            requestedAction = cpView.runViewer();
            return;
        }
    }
    
}
