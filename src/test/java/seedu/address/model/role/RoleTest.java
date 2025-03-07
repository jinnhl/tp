package seedu.address.model.role;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DEADLINE_SOFTWARE_ENGINEER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_SOFTWARE_ENGINEER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_SOFTWARE_ENGINEER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STATUS_SOFTWARE_ENGINEER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STIPEND_SOFTWARE_ENGINEER;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalRoles.ML_ENGINEER;
import static seedu.address.testutil.TypicalRoles.MOBILE_ENGINEER;
import static seedu.address.testutil.TypicalRoles.NETWORK_ENGINEER;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.RoleBuilder;

public class RoleTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                new Role(null, null, null, null, null));
    }

    @Test
    public void equals() {
        //same object -> returns true
        assertTrue(ML_ENGINEER.equals(ML_ENGINEER));

        //same values -> return true
        assertTrue(MOBILE_ENGINEER.equals((new RoleBuilder(MOBILE_ENGINEER)).build()));

        // different role -> return false
        assertFalse(ML_ENGINEER.equals(NETWORK_ENGINEER));

        // null -> returns false
        assertFalse(ML_ENGINEER.equals(null));

        // different type -> returns false
        assertFalse(MOBILE_ENGINEER.equals(1));

        // different name -> returns false
        Role editedNetworkEngineer = new RoleBuilder(NETWORK_ENGINEER).withName(VALID_NAME_SOFTWARE_ENGINEER).build();
        assertFalse(editedNetworkEngineer.equals(NETWORK_ENGINEER));

        // different status -> returns false
        editedNetworkEngineer = new RoleBuilder(NETWORK_ENGINEER).withStatus(VALID_STATUS_SOFTWARE_ENGINEER).build();
        assertFalse(editedNetworkEngineer.equals(NETWORK_ENGINEER));

        // different deadline -> returns false
        editedNetworkEngineer = new RoleBuilder(NETWORK_ENGINEER)
                .withDeadline(VALID_DEADLINE_SOFTWARE_ENGINEER).build();
        assertFalse(editedNetworkEngineer.equals(NETWORK_ENGINEER));

        // different stipend -> returns false
        editedNetworkEngineer = new RoleBuilder(NETWORK_ENGINEER)
                .withStipend(VALID_STIPEND_SOFTWARE_ENGINEER).build();
        assertFalse(editedNetworkEngineer.equals(NETWORK_ENGINEER));

        // different description -> returns false
        editedNetworkEngineer = new RoleBuilder(NETWORK_ENGINEER)
                .withDescription(VALID_DESCRIPTION_SOFTWARE_ENGINEER).build();
        assertFalse(editedNetworkEngineer.equals(NETWORK_ENGINEER));
    }
}
