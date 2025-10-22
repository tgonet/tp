package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public abstract class Person {

    // Identity fields
    protected final Name name;
    protected final Phone phone;

    // Data fields
    protected final Address address;
    protected final Remark remark;
    protected final Role role;
    // Role might seem useless but is used for display purposes and storage

    /**
     * Used during create new Person object during add command
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Address address, Role role, Remark remark) {
        requireAllNonNull(name, phone, address);
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.role = role; //Remove ltr
        this.remark = remark;
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Address getAddress() {
        return address;
    }

    public Role getRole() {
        return role;
    }

    public Remark getRemark() {
        return remark;
    }

    /**
     * Returns true if both persons have the same name.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null
                && otherPerson.getName().equals(getName());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, address, role, remark);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("address", address)
                .add("role", role)
                .add("remark", remark)
                .toString();
    }
}
