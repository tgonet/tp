package seedu.address.model.person;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.tag.Tag;

/**
 * Represents a Student in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Student extends Person {
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     *
     * @param name
     * @param phone
     * @param address
     * @param remark
     * @param tags
     */
    public Student(Name name, Phone phone, Address address, Remark remark, Set<Tag> tags) {
        super(name, phone, address, new Role("student"), remark);
        this.tags.addAll(tags);
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Student)) {
            return false;
        }

        Student otherPerson = (Student) other;
        return name.equals(otherPerson.name)
                && phone.equals(otherPerson.phone)
                && address.equals(otherPerson.address)
                && role.equals(otherPerson.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, phone, address, role, remark, tags);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("address", address)
                .add("role", role)
                .add("remark", remark)
                .add("tags", tags)
                .toString();
    }
}
