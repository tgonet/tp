package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's role in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidRole(String)}
 */
public class Role {
    public static final String MESSAGE_CONSTRAINTS = "Role should only be"
            + " student or parent";

    /*
     * Only allow the role to either be student or parent
     */
    public static final String VALIDATION_REGEX = "^(student|parent)$";

    public static final Role PARENT_ROLE = new Role("parent");
    public static final Role STUDENT_ROLE = new Role("student");

    public final String role;

    /**
     * Constructs a {@code Name}.
     *
     * @param role A valid name.
     */
    public Role(String role) {
        requireNonNull(role);
        role = role.toLowerCase();
        checkArgument(isValidRole(role), MESSAGE_CONSTRAINTS);
        this.role = role;
    }

    /**
     * Returns true if a given string is a valid role.
     */
    public static boolean isValidRole(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Returns true if this is a STUDENT role.
     */
    public boolean isStudent() {
        return role.equals("student");
    }

    /**
     * Returns true if this is a PARENT role.
     */
    public boolean isParent() {
        return role.equals("parent");
    }

    @Override
    public String toString() {
        return role;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Role)) {
            return false;
        }

        Role otherRole = (Role) other;
        return role.equals(otherRole.role);
    }

    @Override
    public int hashCode() {
        return role.hashCode();
    }
}
