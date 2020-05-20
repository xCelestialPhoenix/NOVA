package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Person objects.
 */
public class PersonBuilder {

    /**
     * The constant DEFAULT_NAME.
     */
    public static final String DEFAULT_NAME = "Alice Pauline";
    /**
     * The constant DEFAULT_PHONE.
     */
    public static final String DEFAULT_PHONE = "85355255";
    /**
     * The constant DEFAULT_EMAIL.
     */
    public static final String DEFAULT_EMAIL = "alice@gmail.com";
    /**
     * The constant DEFAULT_ADDRESS.
     */
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";

    private Name name;
    private Phone phone;
    private Email email;
    private Address address;
    private Set<Tag> tags;

    /**
     * Instantiates a new Person builder.
     */
    public PersonBuilder() {

        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        tags = new HashSet<>();
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     *
     * @param personToCopy the person to copy
     */
    public PersonBuilder(Person personToCopy) {

        name = personToCopy.getName();
        phone = personToCopy.getPhone();
        email = personToCopy.getEmail();
        address = personToCopy.getAddress();
        tags = new HashSet<>(personToCopy.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     *
     * @param name the name
     * @return the person builder
     */
    public PersonBuilder withName(String name) {

        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Person} that we are building.
     *
     * @param tags the tags
     * @return the person builder
     */
    public PersonBuilder withTags(String... tags) {

        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Person} that we are building.
     *
     * @param address the address
     * @return the person builder
     */
    public PersonBuilder withAddress(String address) {

        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Person} that we are building.
     *
     * @param phone the phone
     * @return the person builder
     */
    public PersonBuilder withPhone(String phone) {

        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Person} that we are building.
     *
     * @param email the email
     * @return the person builder
     */
    public PersonBuilder withEmail(String email) {

        this.email = new Email(email);
        return this;
    }

    /**
     * Builds person.
     *
     * @return the person
     */
    public Person build() {

        return new Person(name, phone, email, address, tags);
    }

}
