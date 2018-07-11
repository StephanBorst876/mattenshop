package nl.workshop1.view;

import java.util.ArrayList;
import nl.workshop1.model.Bestelling;
import nl.workshop1.model.Klant;

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

    // Titels voor de verschillende menu's
    public static final String TITEL_HOOFDMENU = "Hoofdmenu";
    public static final String TITEL_INLOGGEN = "Inloggen";
    public static final String TITEL_DB_SELECTIE = "Database selectie";
    public static final String TITEL_ACCOUNTS = "Accounts";
    public static final String TITEL_ARTIKELEN = "Artikelen";
    public static final String TITEL_KLANTEN = "Klanten";
    public static final String TITEL_BESTELLINGEN = "Bestellingen";
    public static final String TITEL_BESTELREGELS = "Bestellingregels";
    public static final String TITEL_ZOEK_ARTIKELEN = "Zoek artikelen";
    public static final String TITEL_ZOEK_KLANTEN = "Zoek klanten";
    
    // Titel van het menu
    private String title;
    public static String loginName = "";

    // Voor bestellingen, toon de bestelKlant en de bestelling
    private Klant bestelKlant = null;
    private Bestelling bestelling = null;

    // Eventuele records (account/artikel/klant etc) die worden getoond
    private boolean recordSelected = false;
    private ArrayList<Object> recordList = null;
    private int recordSelectedIndex = -1;

    // De opties uit het subMenu en welke actie daarmee correspondeert
    private ArrayList<String> subMenuList = null;
    private ArrayList<String> actionList = null;

    private String filter = "";
    private String infoLine = "";

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
        subMenuList.clear();
        actionList.clear();
        addSubMenu("Zoek " + buildTextForFilter(), "2");
        addSubMenu("Terug", "0");
    }

    /**
     * Een algemeen gebruikte menu-opbouw
     */
    public void buildGeneralSubMenuList() {
        subMenuList.clear();
        actionList.clear();
        addSubMenu("Nieuw", "1");
        addSubMenu("Zoek " + buildTextForFilter(), "2");
        if (isRecordSelected()) {
            addSubMenu("Wijzig", "3");
            addSubMenu("Verwijder", "4");
        }
        addSubMenu("Terug", "0");
    }

    protected String buildTextForFilter() {
        StringBuilder s = new StringBuilder();
        if (title.equals(Menu.TITEL_ACCOUNTS)) {
            s.append("(gebruikersnaam)");
        } else if ((title.equals(Menu.TITEL_KLANTEN)) || (title.equals(Menu.TITEL_ZOEK_KLANTEN))) {
            s.append("(achternaam)");
        } else if ((title.equals(Menu.TITEL_ARTIKELEN)) || (title.equals(Menu.TITEL_ZOEK_ARTIKELEN))) {
            s.append("(artikelnaam)");
        } else if (title.equals(Menu.TITEL_BESTELLINGEN)) {
            s.append("(referentie)");
        } else if (title.equals(Menu.TITEL_BESTELREGELS)) {
            s.append("(artikelnaam)");
        }

        if (!filter.isEmpty()) {
            s.append(" <").append(getFilter()).append(">");
            return s.toString();
        }
        return s.toString();
    }

    public void setBestelKlant(Klant bestelKlant) {
        this.bestelKlant = bestelKlant;
    }

    public Klant getBestelKlant() {
        return bestelKlant;
    }

    public void setBestelling(Bestelling bestelling) {
        this.bestelling = bestelling;
    }

    public Bestelling getBestelling() {
        return bestelling;
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

    public Object getSelectedObject() {
        return recordList.get(recordSelectedIndex);
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

    /**
     * @return the infoLine
     */
    public String getInfoLine() {
        return infoLine;
    }

    /**
     * @param infoLine the infoLine to set
     */
    public void setInfoLine(String infoLine) {
        this.infoLine = infoLine;
    }

}
