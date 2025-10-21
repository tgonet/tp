package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's day for a session in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidDay(String)}
 */
public class Day {
    public static final String MESSAGE_CONSTRAINTS =
            "Day should be one of the following: Mon, Tue, Wed, Thur, Fri, Sat, Sun";
    public static final String VALIDATION_REGEX = "^(Mon|Tue|Wed|Thur|Fri|Sat|Sun)$";

    private final String value;

    /**
     * Constructs a {@code Day}.
     *
     * @param day A valid day.
     *
     */
    public Day(String day) {
        requireNonNull(day);
        checkArgument(isValidDay(day), MESSAGE_CONSTRAINTS);
        this.value = day;
    }

    public String getValue() {
        return value;
    }

    /**
    * Returns true if a given string is a valid day.
    */
    public static boolean isValidDay(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Day)) {
            return false;
        }

        Day otherDay = (Day) other;
        return value.equals(otherDay.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
