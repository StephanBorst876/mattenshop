package nl.workshop1.menu;

import java.util.ArrayList;
import nl.workshop1.view.MenuView;

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

    private static final int NUMBER_RECORDS_TO_DISPLAY = 5;

    // For input purposes (i.e. input.nextLine(), use MenuView()
    private MenuView menuView = new MenuView();

    // Titel van het menu
    private String title;
    private static String loginName = "";

    // Eventuele records (account/artikel/klant etc) die worden getoond
    private boolean recordSelected = false;
    private ArrayList<String> recordList = null;
    private int recordSelectedIndex = -1;
    private String recordHeader = "";

    // De opties uit het menu en welke switch-actie daarmee correspondeerd
    private ArrayList<String> subMenu = null;
    private ArrayList<String> switchValue = null;

    // Bevat dit menu de optie: 0. Terug
    private boolean addReturnOption = true;

    private String filter = "";

    public Menu() {
        this("");
    }

    public Menu(String title) {
        this.title = title;
        subMenu = new ArrayList<>();
        switchValue = new ArrayList<>();
        recordList = new ArrayList<>();
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public void setLoginName(String name) {
        this.loginName = name;
    }

    public void setRecordHeader(String header) {
        this.recordHeader = header;
    }

    public void clearSubMenu() {
        subMenu.clear();
        switchValue.clear();
    }

    public void clearRecordList() {
        recordList.clear();
    }

    public void clearRecordMenu() {
        recordSelected = false;
        recordList.clear();
        recordSelectedIndex = -1;
    }

    public void resetMenu() {

        clearSubMenu();

        clearRecordMenu();

        addReturnOption = true;
    }

    public int getRecordSelectedIndex() {
        return recordSelectedIndex;
    }

    public boolean isRecordSelected() {
        return recordSelected;
    }

    public void setRecordSelected(boolean value) {
        recordSelected = value;
    }

    public void addRecordList(String s) {
        recordList.add(s);
    }

    public boolean enableAddReturnOption() {
        addReturnOption = true;
        return addReturnOption;
    }

    public boolean disableAddReturnOption() {
        addReturnOption = false;
        return addReturnOption;
    }

    public void addSubMenu(String subMenuName, String value) {
        subMenu.add(subMenuName);
        switchValue.add(value);
    }

    public MenuView getMenuView() {
        return menuView;
    }

    /**
     * Draw header and submenu-itens on the console. The option "0. terug" is
     * added depending on addReturnOption
     */
    public void drawMenu() {

        if (title != null) {
            if (loginName.length() > 0) {
                menuView.showTitle(title + " - " + loginName);
            } else {
                menuView.showTitle(title);
            }
        }

        if (recordSelected) {
            menuView.showMessage(" -> " + recordList.get(recordSelectedIndex));
            menuView.showMessage();
        } else {
            for (int i = 0; i < recordList.size(); i++) {
                if (i == 0) {
                    // First display the header
                    menuView.showRecordHeader(recordHeader);
                }
                if (i < NUMBER_RECORDS_TO_DISPLAY) {
                    menuView.showMessage(generateChoice('A', i) + recordList.get(i));
                } else {
                    int remainingRecords = recordList.size() - NUMBER_RECORDS_TO_DISPLAY;
                    if (remainingRecords > 0) {
                        menuView.showMessage("..nog extra regels (" + remainingRecords + ")");
                    }
                    break;
                }

            }
            // Add an empty line after shown records
            if (recordList.size() > 0) {
                menuView.showMessage();
            }
        }

        // Show all submenu
        for (int i = 0; i < subMenu.size(); i++) {
            menuView.showMessage(generateChoice('1', i) + subMenu.get(i));
        }
        if (addReturnOption) {
            menuView.showMessage(generateChoice('0', 0) + "Terug");
        }
    }

    protected String generateChoice(char offset, int index) {
        StringBuilder s = new StringBuilder();
        s.append("  ");
        s.append((char) (offset + index));
        s.append(". ");
        return s.toString();
    }

    /**
     * Keeps asking the user for entering a key, until a valid key is pressed.
     *
     * @return : the selected key as a String
     */
    public String userChoice() {

        while (true) {
            String choice = menuView.getInputChoice();
            if (choice.length() > 0) {
                String value = validChoice(choice);
                if (value != null) {
                    return value;
                }
            }
        }
    }

    private String validChoice(String choice) {
        // Indien in de range A-Z
        if (choice.charAt(0) >= 'A' && choice.charAt(0) <= 'Z') {
            int selected = (int) (choice.charAt(0) - 'A');
            if (!recordSelected) {
                if (selected < recordList.size()) {
                    recordSelected = true;
                    recordSelectedIndex = selected;
                    return choice;
                }
            }
            return null;
        }

        if (addReturnOption) {
            if (choice.charAt(0) == '0') {
                return "0";
            }
        }

        // Indien in de range 1-9
        if (choice.charAt(0) >= '1' && choice.charAt(0) <= '9') {
            int index = Integer.valueOf(choice);
            // Is de gekozen optie aanwezig in het menu?
            if (index <= switchValue.size()) {
                return switchValue.get(index - 1);
            }
        }

        return null;
    }

    public void buildSearchMenu() {
        addSubMenu("Zoek" + displayTextForFilter(), "2");
    }

    /**
     * A set of general used option for the menu
     */
    public void buildMenu() {
        addSubMenu("Nieuw", "1");
        addSubMenu("Zoek" + displayTextForFilter(), "2");
        if (isRecordSelected()) {
            addSubMenu("Wijzig", "3");
            addSubMenu("Verwijder", "4");
        }
    }

    protected String displayTextForFilter() {
        if (!filter.equals("")) {
            StringBuilder s = new StringBuilder();
            s.append(" <").append(filter).append(">");
            return s.toString();
        }
        return "";
    }

}
