package nl.workshop1.menu;

import java.util.ArrayList;

/**
 *
 * @author FeniksBV
 */
/* 
Een menu is als volgt opgebouwd:

<title> bestaat uit menuTitle - loginName

<recordHeader>  optional

<recordList> optional
bijv:
A. boer@piet.nl
B. stef@borst.nl

<submenu>
1. Nieuw
2. Zoeken
0. Terug
Maak uw keuze : 

 */
public class Menu {

    // Titel van het menu
    private String title;
    public static String loginName = "";

    // Eventuele records (account/artikel/klant etc) die worden getoond
    private boolean recordSelected = false;
    private ArrayList<Object> recordList = null;
    private int recordSelectedIndex = -1;

    // De opties uit het subMenu en welke actie daarmee correspondeert
    private ArrayList<String> subMenuList = null;
    private ArrayList<String> actionList = null;

    private String filter = "";

    public Menu(String title) {
        this.title = title;
        subMenuList = new ArrayList<>();
        actionList = new ArrayList<>();
        recordList = new ArrayList<>();
    }

    public void clearSubMenuList() {
        getSubMenuList().clear();
        getActionList().clear();
    }

    //
    //      Opbouw submenu: Dat kan per regel
    //
    /**
     *
     * @param subMenuName
     * @param action
     */
    public void addSubMenu(String subMenuName, String action) {
        getSubMenuList().add(subMenuName);
        getActionList().add(action);
    }

    /**
     *
     * @param id Te kiezen optie
     * @param subMenuName De omschrijving
     * @param action Uit te voeren actie indien optie gekozen
     */
    public void addSubMenu(String id, String subMenuName, String action) {
        getSubMenuList().add(formatText(id) + subMenuName);
        getActionList().add(action);
    }

    protected String formatText(String text) {
        int maxLength = 14;
        StringBuilder s = new StringBuilder(text);
        while (s.length() < maxLength) {
            s.append(" ");
        }
        s.append(": ");
        return s.toString();
    }

    //
    //      Opbouw submenu: Dat kan ook per voorgedefinieerd submenu
    //
    /**
     * Used when ROLE.KLANT
     */
    public void buildSearchOnlySubMenuList() {
        addSubMenu("Zoek" + buildTextForFilter(), "2");
    }

    /**
     * Een algemeen gebruikte menu-opbouw
     */
    public void buildGeneralSubMenuList() {
        addSubMenu("Nieuw", "1");
        addSubMenu("Zoek" + buildTextForFilter(), "2");
        if (isRecordSelected()) {
            addSubMenu("Wijzig", "3");
            addSubMenu("Verwijder", "4");
        }
    }

    protected String buildTextForFilter() {
        if (!filter.equals("")) {
            StringBuilder s = new StringBuilder();
            s.append(" <").append(getFilter()).append(">");
            return s.toString();
        }
        return "";
    }

    /**
     * @return the recordSelected
     */
    public boolean isRecordSelected() {
        return recordSelected;
    }

    /**
     * @param recordSelected the recordSelected to set
     */
    public void setRecordSelected(boolean recordSelected) {
        this.recordSelected = recordSelected;
    }

    /**
     * @return the recordSelectedIndex
     */
    public int getRecordSelectedIndex() {
        return recordSelectedIndex;
    }

    /**
     * @param recordSelectedIndex the recordSelectedIndex to set
     */
    public void setRecordSelectedIndex(int recordSelectedIndex) {
        this.recordSelectedIndex = recordSelectedIndex;
    }

    /**
     * @return the filter
     */
    public String getFilter() {
        return filter;
    }

    /**
     * @param filter the filter to set
     */
    public void setFilter(String filter) {
        this.filter = filter;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @return the loginName
     */
    public static String getLoginName() {
        return loginName;
    }

    /**
     * @return the recordList
     */
    public ArrayList<Object> getRecordList() {
        return recordList;
    }

    /**
     * @return the subMenuList
     */
    public ArrayList<String> getSubMenuList() {
        return subMenuList;
    }

    /**
     * @return the actionList
     */
    public ArrayList<String> getActionList() {
        return actionList;
    }

}
