package seedu.address.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

/**
 * A utility class to help with building EditPersonDescriptor objects.
 */
public class EditPersonDescriptorBuilder {

    private EditPersonDescriptor descriptor;

    /**
     * Instantiates a new Edit person descriptor builder.
     */
    public EditPersonDescriptorBuilder() {

        descriptor = new EditPersonDescriptor();
    }

    /**
     * Instantiates a new Edit person descriptor builder.
     *
     * @param descriptor the descriptor
     */
    public EditPersonDescriptorBuilder(EditPersonDescriptor descriptor) {

        this.descriptor = new EditPersonDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditPersonDescriptor} with fields containing {@code person}'s details
     *
     * @param person the person
     */
    public EditPersonDescriptorBuilder(Person person) {

        descriptor = new EditPersonDescriptor();
        descriptor.setName(person.getName());
        descriptor.setPhone(person.getPhone());
        descriptor.setEmail(person.getEmail());
        descriptor.setAddress(person.getAddress());
        descriptor.setTags(person.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code EditPersonDescriptor} that we are building.
     *
     * @param name the name
     * @return the edit person descriptor builder
     */
    public EditPersonDescriptorBuilder withName(String name) {

        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code EditPersonDescriptor} that we are building.
     *
     * @param phone the phone
     * @return the edit person descriptor builder
     */
    public EditPersonDescriptorBuilder withPhone(String phone) {

        descriptor.setPhone(new Phone(phone));
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code EditPersonDescriptor} that we are building.
     *
     * @param email the email
     * @return the edit person descriptor builder
     */
    public EditPersonDescriptorBuilder withEmail(String email) {

        descriptor.setEmail(new Email(email));
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code EditPersonDescriptor} that we are building.
     *
     * @param address the address
     * @return the edit person descriptor builder
     */
    public EditPersonDescriptorBuilder withAddress(String address) {

        descriptor.setAddress(new Address(address));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditPersonDescriptor}
     * that we are building.
     *
     * @param tags the tags
     * @return the edit person descriptor builder
     */
    public EditPersonDescriptorBuilder withTags(String... tags) {

        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }


    /**
     * Builds edit person descriptor.
     *
     * @return the edit person descriptor
     */
    public EditPersonDescriptor build() {

        return descriptor;
    }

}
