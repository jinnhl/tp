package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_INSTAGRAM;
import static seedu.address.logic.commands.CommandTestUtil.DESC_WHATSAPP;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_WHATSAPP;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_WHATSAPP;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showCompanyAtIndex;
import static seedu.address.testutil.TypicalCompanies.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_COMPANY;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_COMPANY;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditCommand.EditCompanyDescriptor;
import seedu.address.model.CompanyList;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.company.Company;
import seedu.address.testutil.CompanyBuilder;
import seedu.address.testutil.EditCompanyDescriptorBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditCommand.
 */
public class EditCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Company editedCompany = new CompanyBuilder().build();
        EditCommand.EditCompanyDescriptor descriptor = new EditCompanyDescriptorBuilder(editedCompany).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_COMPANY, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_COMPANY_SUCCESS, editedCompany);

        Model expectedModel = new ModelManager(new CompanyList(model.getCompanyList()), new UserPrefs());
        expectedModel.setCompany(model.getFilteredCompanyList().get(0), editedCompany);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastCompany = Index.fromOneBased(model.getFilteredCompanyList().size());
        Company lastCompany = model.getFilteredCompanyList().get(indexLastCompany.getZeroBased());

        CompanyBuilder companyInList = new CompanyBuilder(lastCompany);
        Company editedCompany = companyInList.withName(VALID_NAME_WHATSAPP).withPhone(VALID_PHONE_WHATSAPP)
                .withTags(VALID_TAG_HUSBAND).build();

        EditCompanyDescriptor descriptor = new EditCompanyDescriptorBuilder().withName(VALID_NAME_WHATSAPP)
                .withPhone(VALID_PHONE_WHATSAPP).withTags(VALID_TAG_HUSBAND).build();
        EditCommand editCommand = new EditCommand(indexLastCompany, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_COMPANY_SUCCESS, editedCompany);

        Model expectedModel = new ModelManager(new CompanyList(model.getCompanyList()), new UserPrefs());
        expectedModel.setCompany(lastCompany, editedCompany);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditCommand editCommand = new EditCommand(INDEX_FIRST_COMPANY, new EditCommand.EditCompanyDescriptor());
        Company editedCompany = model.getFilteredCompanyList().get(INDEX_FIRST_COMPANY.getZeroBased());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_COMPANY_SUCCESS, editedCompany);

        Model expectedModel = new ModelManager(new CompanyList(model.getCompanyList()), new UserPrefs());

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showCompanyAtIndex(model, INDEX_FIRST_COMPANY);

        Company companyInFilteredList = model.getFilteredCompanyList().get(INDEX_FIRST_COMPANY.getZeroBased());
        Company editedCompany = new CompanyBuilder(companyInFilteredList).withName(VALID_NAME_WHATSAPP).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_COMPANY,
                new EditCompanyDescriptorBuilder().withName(VALID_NAME_WHATSAPP).build());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_COMPANY_SUCCESS, editedCompany);

        Model expectedModel = new ModelManager(new CompanyList(model.getCompanyList()), new UserPrefs());
        expectedModel.setCompany(model.getFilteredCompanyList().get(0), editedCompany);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateCompanyUnfilteredList_failure() {
        Company firstCompany = model.getFilteredCompanyList().get(INDEX_FIRST_COMPANY.getZeroBased());
        EditCompanyDescriptor descriptor = new EditCompanyDescriptorBuilder(firstCompany).build();
        EditCommand editCommand = new EditCommand(INDEX_SECOND_COMPANY, descriptor);

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_COMPANY);
    }

    @Test
    public void execute_duplicateCompanyFilteredList_failure() {
        showCompanyAtIndex(model, INDEX_FIRST_COMPANY);

        // edit company in filtered list into a duplicate in address book
        Company companyInList = model.getCompanyList().getCompanyList().get(INDEX_SECOND_COMPANY.getZeroBased());
        EditCommand editCommand = new EditCommand(INDEX_FIRST_COMPANY,
                new EditCompanyDescriptorBuilder(companyInList).build());

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_COMPANY);
    }

    @Test
    public void execute_invalidCompanyIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredCompanyList().size() + 1);
        EditCommand.EditCompanyDescriptor descriptor =
                new EditCompanyDescriptorBuilder().withName(VALID_NAME_WHATSAPP).build();
        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_COMPANY_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidCompanyIndexFilteredList_failure() {
        showCompanyAtIndex(model, INDEX_FIRST_COMPANY);
        Index outOfBoundIndex = INDEX_SECOND_COMPANY;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getCompanyList().getCompanyList().size());

        EditCommand editCommand = new EditCommand(outOfBoundIndex,
                new EditCompanyDescriptorBuilder().withName(VALID_NAME_WHATSAPP).build());

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_COMPANY_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditCommand standardCommand = new EditCommand(INDEX_FIRST_COMPANY, DESC_INSTAGRAM);

        // same values -> returns true
        EditCommand.EditCompanyDescriptor copyDescriptor = new EditCompanyDescriptor(DESC_INSTAGRAM);
        EditCommand commandWithSameValues = new EditCommand(INDEX_FIRST_COMPANY, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_SECOND_COMPANY, DESC_INSTAGRAM)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_FIRST_COMPANY, DESC_WHATSAPP)));
    }

}
