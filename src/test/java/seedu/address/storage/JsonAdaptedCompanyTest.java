package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedCompany.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.storage.JsonAdaptedRoleTest.INVALID_ROLE;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalCompanies.AMAZON;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.company.Address;
import seedu.address.model.company.CompanyName;
import seedu.address.model.company.Email;
import seedu.address.model.company.Phone;

public class JsonAdaptedCompanyTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_NAME = AMAZON.getName().toString();
    private static final String VALID_PHONE = AMAZON.getPhone().toString();
    private static final String VALID_EMAIL = AMAZON.getEmail().toString();
    private static final String VALID_ADDRESS = AMAZON.getAddress().toString();
    private static final List<JsonAdaptedTag> VALID_TAGS = AMAZON.getTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList());
    private static final List<JsonAdaptedRole> VALID_ROLES = AMAZON.getRoleManager().getFilteredRoleList().stream()
            .map(JsonAdaptedRole::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validCompanyDetails_returnsCompany() throws Exception {
        JsonAdaptedCompany company = new JsonAdaptedCompany(AMAZON);
        assertEquals(AMAZON, company.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedCompany company =
                new JsonAdaptedCompany(INVALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_TAGS, VALID_ROLES);
        String expectedMessage = CompanyName.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, company::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedCompany company = new JsonAdaptedCompany(null, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                VALID_TAGS, VALID_ROLES);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, CompanyName.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, company::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        JsonAdaptedCompany company =
                new JsonAdaptedCompany(VALID_NAME, INVALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                        VALID_TAGS, VALID_ROLES);
        String expectedMessage = Phone.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, company::toModelType);
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        JsonAdaptedCompany company = new JsonAdaptedCompany(VALID_NAME, null, VALID_EMAIL, VALID_ADDRESS,
                VALID_TAGS, VALID_ROLES);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, company::toModelType);
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        JsonAdaptedCompany company =
                new JsonAdaptedCompany(VALID_NAME, VALID_PHONE, INVALID_EMAIL, VALID_ADDRESS,
                        VALID_TAGS, VALID_ROLES);
        String expectedMessage = Email.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, company::toModelType);
    }

    @Test
    public void toModelType_nullEmail_throwsIllegalValueException() {
        JsonAdaptedCompany company = new JsonAdaptedCompany(VALID_NAME, VALID_PHONE, null, VALID_ADDRESS,
                VALID_TAGS, VALID_ROLES);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, company::toModelType);
    }

    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {
        JsonAdaptedCompany company = new JsonAdaptedCompany(VALID_NAME, VALID_PHONE, VALID_EMAIL, INVALID_ADDRESS,
                VALID_TAGS, VALID_ROLES);
        String expectedMessage = Address.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, company::toModelType);
    }

    @Test
    public void toModelType_nullAddress_throwsIllegalValueException() {
        JsonAdaptedCompany company = new JsonAdaptedCompany(VALID_NAME, VALID_PHONE, VALID_EMAIL, null,
                VALID_TAGS, VALID_ROLES);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, company::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
        JsonAdaptedCompany company = new JsonAdaptedCompany(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                invalidTags, VALID_ROLES);
        assertThrows(IllegalValueException.class, company::toModelType);
    }

    @Test
    public void toModelType_invalidRoles_throwsIllegalValueException() {
        List<JsonAdaptedRole> invalidRoles = new ArrayList<>();
        invalidRoles.add(INVALID_ROLE);
        JsonAdaptedCompany company =
                new JsonAdaptedCompany(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_TAGS, invalidRoles);
        assertThrows(IllegalValueException.class, company::toModelType);
    }

}
