package nl.workshop1.model;

import java.util.Objects;

/**
 *
 * @author FeniksBV
 */
public class Account implements Cloneable {

    private String userName;
    private String wachtwoord;
    private Role role;

    public Account() {
        this("","");
    }

    public Account(String userName, String wachtwoord) {
        this.userName = userName;
        this.wachtwoord = wachtwoord;
        this.role = Role.ROLE_KLANT;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getWachtwoord() {
        return wachtwoord;
    }

    public void setWachtwoord(String wachtwoord) {
        this.wachtwoord = wachtwoord;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setRole(String role) {
        Role newRole = Role.getRole(role);
        if (newRole != null) {
            this.role = newRole;
        }
    }

    public String getRoleDescription() {
        return role.getDescription();
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Account accountClone = (Account) super.clone();
        return accountClone;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + Objects.hashCode(this.role);
        hash = 59 * hash + Objects.hashCode(this.userName);
        hash = 59 * hash + Objects.hashCode(this.wachtwoord);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Account other = (Account) obj;
        if (!Objects.equals(this.role.getDescription(), other.role.getDescription())) {
            return false;
        }
        if (!Objects.equals(this.userName, other.userName)) {
            return false;
        }
        if (!Objects.equals(this.wachtwoord, other.wachtwoord)) {
            return false;
        }
        return true;
    }
}
