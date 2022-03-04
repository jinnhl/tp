package seedu.address.model.role;

import seedu.address.model.company.Company;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static java.util.Objects.requireNonNull;

/**
 * Represents the list of all roles tagged to a Company found in the address book.
 */
public class RoleManager {
    private ArrayList<Role> roleList;

    public RoleManager() {
        this.roleList = new ArrayList<>();
    }

    public RoleManager(ArrayList<Role> roleList) {
        this.roleList = roleList;
    }

    /**
     * Obtains the list of roles tagged to a Company
     * @return <code>ArrayList</code> representing the list of roles
     */
    public ArrayList<Role> getRoles() {
        return this.roleList;
    }

    /**
     * Obtains the unmodifiable set of roles tagged to a Company
     * @return <code>Set</code> representing the set of roles
     */
    public Set<Role> getSetRoles() {
        Set<Role> roleSet = new HashSet<Role>(this.roleList);
        return Collections.unmodifiableSet(roleSet);
    }

    /**
     * Obtains the number of roles
     * @return Integer representing the number of roles
     */
    public int countRoles() {
        return this.roleList.size();
    }

    public void addRole(Role role) {
        this.roleList.add(role);
    }

    /**
     * Checks if this role exist in the roleList
     * @param role role to check if it is duplicated
     * @return Boolean representing if this role is already stored
     */
    public boolean hasRole(Role role) {
        requireNonNull(role);
        return roleList.contains(role);
    }

    public void deleteRole(int index) {
        this.roleList.remove(index);
    }
}
