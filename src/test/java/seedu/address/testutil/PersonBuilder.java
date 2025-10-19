package seedu.address.testutil;

import seedu.address.model.person.Address;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Remark;

/**
 * A utility class to help with building Person objects.
 */
public abstract class PersonBuilder<T extends PersonBuilder<T>> {

    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";
    public static final String DEFAULT_REMARK = "She likes Math.";

    protected Name name;
    protected Phone phone;
    protected Address address;
    protected Remark remark;

    /**
     * Creates a {@code PersonBuilder} with the default details.
     */
    public PersonBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        address = new Address(DEFAULT_ADDRESS);
        remark = new Remark(DEFAULT_REMARK);
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public PersonBuilder(Person personToCopy) {
        name = personToCopy.getName();
        phone = personToCopy.getPhone();
        address = personToCopy.getAddress();
        remark = personToCopy.getRemark();
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public T withName(String name) {
        this.name = new Name(name);
        return self();
    }

    /**
     * Sets the {@code Address} of the {@code Person} that we are building.
     */
    public T withAddress(String address) {
        this.address = new Address(address);
        return self();
    }

    /**
     * Sets the {@code Phone} of the {@code Person} that we are building.
     */
    public T withPhone(String phone) {
        this.phone = new Phone(phone);
        return self();
    }

    /**
     * Sets the {@code Remark} of the {@code Person} that we are building.
     */
    public T withRemark(String remark) {
        this.remark = new Remark(remark);
        return self();
    }

    /**
     * Returns this builder instance, typed as the subclass type.
     */
    protected abstract T self();

    public abstract Person build();
}
