package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalCompanies.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_COMPANY;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_ROLE;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_COMPANY;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_ROLE;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.company.Company;
import seedu.address.model.role.Role;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteRoleCommand}.
 */
public class DeleteRoleCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexList_success() {
        Company company = model.getFilteredCompanyList().get(INDEX_FIRST_COMPANY.getZeroBased());
        Role roleToDelete = company.getRoleManager().getFilteredRoleList().get(INDEX_FIRST_COMPANY.getZeroBased());
        DeleteRoleCommand deleteRoleCommand = new DeleteRoleCommand(INDEX_FIRST_COMPANY, INDEX_FIRST_ROLE);

        String expectedMessage = String.format(DeleteRoleCommand.MESSAGE_DELETE_ROLE_SUCCESS, roleToDelete);
        ModelManager expectedModel = new ModelManager(model.getCompanyList(), new UserPrefs());
        assertCommandSuccess(deleteRoleCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexList_throwsCommandException() {
        // test invalid company index
        Index outOfCompanyBoundIndex = Index.fromOneBased(model.getFilteredCompanyList().size() + 1);
        DeleteRoleCommand deleteRoleCommand1 = new DeleteRoleCommand(outOfCompanyBoundIndex, INDEX_FIRST_ROLE);
        assertCommandFailure(deleteRoleCommand1, model, Messages.MESSAGE_INVALID_COMPANY_DISPLAYED_INDEX);

        // test invalid role index
        Company company = model.getFilteredCompanyList().get(INDEX_FIRST_COMPANY.getZeroBased());
        Index outOfRoleBoundIndex = Index.fromOneBased(company.getRoleManager().getFilteredRoleList().size() + 1);
        DeleteRoleCommand deleteRoleCommand2 = new DeleteRoleCommand(INDEX_FIRST_COMPANY, outOfRoleBoundIndex);
        assertCommandFailure(deleteRoleCommand2, model, Messages.MESSAGE_INVALID_ROLE_DISPLAYED_INDEX);

        // test both invalid company and role indexes
        DeleteRoleCommand deleteRoleCommand3 = new DeleteRoleCommand(outOfCompanyBoundIndex, outOfRoleBoundIndex);
        assertCommandFailure(deleteRoleCommand3, model, Messages.MESSAGE_INVALID_COMPANY_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeleteRoleCommand deleteRoleCommand1 = new DeleteRoleCommand(INDEX_FIRST_COMPANY, INDEX_FIRST_ROLE);
        DeleteRoleCommand deleteRoleCommand2 = new DeleteRoleCommand(INDEX_SECOND_COMPANY, INDEX_SECOND_ROLE);
        DeleteRoleCommand deleteRoleCommand3 = new DeleteRoleCommand(INDEX_SECOND_COMPANY, INDEX_FIRST_ROLE);

        // same object -> returns true
        assertTrue(deleteRoleCommand1.equals(deleteRoleCommand1));

        // same values -> returns true
        DeleteRoleCommand deleteRoleCommand1Copy = new DeleteRoleCommand(INDEX_FIRST_COMPANY, INDEX_FIRST_ROLE);
        assertTrue(deleteRoleCommand1.equals(deleteRoleCommand1Copy));

        // different types -> returns false
        assertFalse(deleteRoleCommand1.equals(1));

        // null -> returns false
        assertFalse(deleteRoleCommand1.equals(null));

        // different role -> returns false
        assertFalse(deleteRoleCommand1.equals(deleteRoleCommand2));

        // different company -> returns false
        assertFalse(deleteRoleCommand1.equals(deleteRoleCommand3));
    }
}
