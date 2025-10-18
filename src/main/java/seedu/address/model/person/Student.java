package seedu.address.model.person;

import seedu.address.model.tag.Tag;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Student extends Person {
//    private final Set<Tag> tags = new HashSet<>();

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
        super(name, phone, address, remark, tags);
    }

//    /**
//     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
//     * if modification is attempted.
//     */
//    public Set<Tag> getTags() {
//        return Collections.unmodifiableSet(tags);
//    }

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
        if (!(other instanceof Person)) {
            return false;
        }

        Person otherPerson = (Person) other;
        return name.equals(otherPerson.name)
                && phone.equals(otherPerson.phone)
                && address.equals(otherPerson.address)
                && tags.equals(otherPerson.tags);
    }
}
