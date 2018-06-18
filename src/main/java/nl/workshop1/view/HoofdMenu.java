package nl.workshop1.view;

import nl.workshop1.model.Role;
import nl.workshop1.model.Account;

/**
 *
 * @author FeniksBV
 */
public class HoofdMenu extends Menu{
   
    private Role role;
    
    public HoofdMenu( Account loginAccount ) {
        super("Hoofdmenu");
        super.setLoginName( loginAccount.getUserName());
        this.role = loginAccount.getRole();
    }
    
    public Role getRole() {
        return role;
    }
}
