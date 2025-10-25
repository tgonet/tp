package seedu.address.model.person;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents a Parent in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Parent extends Person {
    private final Set<Name> childrenNames = new HashSet<>();
    private final Set<Student> children = new HashSet<>();

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

    // Children related functions

    public boolean hasChildren() {
        return !childrenNames.isEmpty();
    }

    public void addChildName(Name name) {
        childrenNames.add(name);
    }

    public void removeChildName(Name name) {
        childrenNames.remove(name);
    }

    public void addChild(Student student) {
        children.add(student);
        childrenNames.add(student.getName());
    }

    public void removeChild(Student student) {
        children.remove(student);
        childrenNames.remove(student.getName());
    }

    public Set<Student> getChildren() {
        return Collections.unmodifiableSet(children);
    }

    public Set<Name> getChildrenNames() {
        return Collections.unmodifiableSet(childrenNames);
    }

    // ---------------------

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
