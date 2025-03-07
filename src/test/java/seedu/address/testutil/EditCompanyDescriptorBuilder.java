package seedu.address.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditCompanyDescriptor;
import seedu.address.model.company.Address;
import seedu.address.model.company.Company;
import seedu.address.model.company.CompanyName;
import seedu.address.model.company.Email;
import seedu.address.model.company.Phone;
import seedu.address.model.tag.Tag;

/**
 * A utility class to help with building EditCompanyDescriptor objects.
 */
public class EditCompanyDescriptorBuilder {

    private EditCompanyDescriptor descriptor;

    public EditCompanyDescriptorBuilder() {
        descriptor = new EditCommand.EditCompanyDescriptor();
    }

    public EditCompanyDescriptorBuilder(EditCommand.EditCompanyDescriptor descriptor) {
        this.descriptor = new EditCommand.EditCompanyDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditCompanyDescriptor} with fields containing {@code company}'s details
     */
    public EditCompanyDescriptorBuilder(Company company) {
        descriptor = new EditCommand.EditCompanyDescriptor();
        descriptor.setName(company.getName());
        descriptor.setPhone(company.getPhone());
        descriptor.setEmail(company.getEmail());
        descriptor.setAddress(company.getAddress());
        descriptor.setTags(company.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code EditCompanyDescriptor} that we are building.
     */
    public EditCompanyDescriptorBuilder withName(String name) {
        descriptor.setName(new CompanyName(name));
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code EditCompanyDescriptor} that we are building.
     */
    public EditCompanyDescriptorBuilder withPhone(String phone) {
        descriptor.setPhone(new Phone(phone));
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code EditCompanyDescriptor} that we are building.
     */
    public EditCompanyDescriptorBuilder withEmail(String email) {
        descriptor.setEmail(new Email(email));
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code EditCompanyDescriptor} that we are building.
     */
    public EditCompanyDescriptorBuilder withAddress(String address) {
        descriptor.setAddress(new Address(address));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditCompanyDescriptor} that we are
     * building.
     */
    public EditCompanyDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    public EditCommand.EditCompanyDescriptor build() {
        return descriptor;
    }
}
