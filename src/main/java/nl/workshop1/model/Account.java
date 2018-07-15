package nl.workshop1.model;

import java.util.Objects;
import nl.workshop1.utility.Slf4j;

/**
 *
 * @author FeniksBV
 */
public class Account implements Cloneable {

    private String userName;
    private String wachtwoord;
    private String salt;
    private Role role;
    private int klantId;

    public Account() {
        this("", "");
    }

    public Account(String userName, String wachtwoord) {
        this.userName = userName;
        this.wachtwoord = wachtwoord;
        this.role = Role.Klant;
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

    
    /**
     * @return the salt
     */
    public String getSalt() {
        return salt;
    }

    /**
     * @param salt the salt to set
     */
    public void setSalt(String salt) {
        this.salt = salt;
    }
    
    /**
     * @return the klantId
     */
    public int getKlantId() {
        return klantId;
    }

    /**
     * @param klantId the klantId to set
     */
    public void setKlantId(int klantId) {
        this.klantId = klantId;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public Object clone() {
        try {
            return (Account) super.clone();
        } catch (CloneNotSupportedException ex) {
            Slf4j.getLogger().error("Cloning Account exception", ex);
        }
        return null;
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
