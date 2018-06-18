package nl.workshop1.view;

import nl.workshop1.model.Role;

/**
 *
 * @author FeniksBV
 */
public class AccountChangeMenuView extends MenuView {

    public Role getInputRole() {
        while (true) {
            showMessage("Role: " + Role.allOptions());
            String s = getInputChoice();
            if (s.length() == 1) {
                Role role = Role.getRole(s);
                if (role != null) {
                    return role;
                }
            }
        }
    }
}
