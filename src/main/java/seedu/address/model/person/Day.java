package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's day for a session in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidDay(String)}
 */
public class Day {
    public static final String MESSAGE_CONSTRAINTS =
            "Day should be one of the following: Mon, Tue, Wed, Thur, Fri, Sat, Sun, or their full forms "
                    + "(e.g., Monday, Friday)";
    public static final String VALIDATION_REGEX =
            "^(?i)(mon(day)?|tue(sday)?|wed(nesday)?|thur(sday)?|fri(day)?|sat(urday)?|sun(day)?)$";

    private final String value;

    /**
     * Constructs a {@code Day}.
     *
     * @param day A valid day.
     */
    public Day(String day) {
        requireNonNull(day);
        String normalizedDay = day.trim();
        checkArgument(isValidDay(normalizedDay), MESSAGE_CONSTRAINTS);

        switch (normalizedDay.toLowerCase()) {
        case "monday":
        case "mon":
            this.value = "Mon";
            break;
        case "tuesday":
        case "tue":
            this.value = "Tue";
            break;
        case "wednesday":
        case "wed":
            this.value = "Wed";
            break;
        case "thursday":
        case "thur":
            this.value = "Thur";
            break;
        case "friday":
        case "fri":
            this.value = "Fri";
            break;
        case "saturday":
        case "sat":
            this.value = "Sat";
            break;
        case "sunday":
        case "sun":
            this.value = "Sun";
            break;
        default:
            throw new IllegalArgumentException(MESSAGE_CONSTRAINTS);
        }
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
