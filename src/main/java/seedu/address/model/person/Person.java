package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;
import java.util.Optional;

import seedu.address.model.session.SessionSlot;


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

    private final Optional<SessionSlot> sessionSlot;

    /**
     * Used during create new Person object during add command
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Address address, Role role, Remark remark,
                  Optional<SessionSlot> sessionSlot) {
        requireAllNonNull(name, phone, address);
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.role = role;
        this.remark = remark;
        this.sessionSlot = sessionSlot == null ? Optional.empty() : sessionSlot;
    }

    // OVERLOADED CONSTRUCTOR to maintain old call sites:
    public Person(Name name, Phone phone, Address address, Role role, Remark remark) {
        this(name, phone, address, role, remark, Optional.empty());
    }

    /**
     * Used during create new Person object for filtering in the address book
     */
    protected Person(Name name, Role role) {
        this.name = name;
        this.phone = null;
        this.address = null;
        this.role = role;
        this.remark = null;
        this.sessionSlot = Optional.empty();
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

    public Optional<SessionSlot> getSessionSlot() {
        return sessionSlot;
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
                && otherPerson.getName().equals(getName())
                && otherPerson.getRole().equals(getRole());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, address, role, remark);
    }

    @Override
    public String toString() {
        // Keep prior contract to satisfy PersonTest expectations.
        // Format: fully-qualified class name + field map in insertion order.
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getName()).append("{")
                .append("name=").append(name).append(", ")
                .append("phone=").append(phone).append(", ")
                .append("address=").append(address).append(", ")
                .append("role=").append(role).append(", ")
                .append("remark=").append(remark);
        if (sessionSlot != null && sessionSlot.isPresent()) {
            sb.append(", session=").append(sessionSlot.get());
        }
        sb.append("}");
        return sb.toString();
    }

}
