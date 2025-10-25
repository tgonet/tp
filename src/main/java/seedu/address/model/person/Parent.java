package seedu.address.model.person;

/**
 * Represents a Parent in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Parent extends Person {

    /**
     * Every field must be present and not null.
     *
     * @param name
     * @param phone
     * @param address
     * @param remark
     */
    public Parent(Name name, Phone phone, Address address, Remark remark) {
        super(name, phone, address, Role.parentRole, remark);
    }

    /**
     * Creates dummy Parent object for filtering
     *
     * @param name
     */
    public Parent(Name name) {
        super(name, Role.parentRole);
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Parent)) {
            return false;
        }

        Parent otherPerson = (Parent) other;
        return name.equals(otherPerson.name)
                && phone.equals(otherPerson.phone)
                && address.equals(otherPerson.address);
    }
}
