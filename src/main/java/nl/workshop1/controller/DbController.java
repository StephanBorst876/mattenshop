package nl.workshop1.controller;

import nl.workshop1.DAO.DbConnection;
import nl.workshop1.view.Menu;
import nl.workshop1.view.SimpleMenuView;

/**
 *
 * @author FeniksBV
 */
public class DbController extends Controller {

    private Menu dbMenu;
    private SimpleMenuView dbView;

    public DbController() {
        this.dbMenu = new Menu(Menu.TITEL_DB_SELECTIE);
        this.dbView = new SimpleMenuView(dbMenu);
    }

    @Override
    public void runController() {

        dbMenu.clearSubMenuList();
        dbMenu.addSubMenu(DbConnection.DB_MYSQL, "1");
        dbMenu.addSubMenu(DbConnection.DB_MONGODB, "2");
        dbMenu.addSubMenu("Afsluiten", "0");

        while (true) {
            requestedAction = dbView.runViewer();
            return;
        }
    }

}
