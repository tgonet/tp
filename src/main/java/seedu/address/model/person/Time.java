package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a time in 12-hour format with an optional minute component and AM/PM.
 * <p>
 * The time must follow the format {@code HH:MMam-HH:MMpm} (or AM/PM),
 * e.g., "3pm-5pm" or "09:30AM-11:45AM".
 * </p>
 */
public class Time {
    public static final String MESSAGE_CONSTRAINTS =
            "Time should be in the format HH:MM in 24-hour format";
    public static final String VALIDATION_REGEX =
            "^(1[0-2]|0?[1-9])(:[0-5][0-9])?(am|pm|AM|PM)-(1[0-2]|0?[1-9])(:[0-5][0-9])?(am|pm|AM|PM)$";

    private final String value;

    /**
     * Constructs a {@code Time}.
     *
     * @param time A valid time.
     *
     */
    public Time(String time) {
        requireNonNull(time);
        checkArgument(isValidTime(time), MESSAGE_CONSTRAINTS);
        this.value = time;
    }

    /**
     * Returns the string value of this object.
     *
     * @return the underlying string value
     */
    public String getValue() {
        return value;
    }

    /**
    * Returns true if a given string is a valid time.
    */
    public static boolean isValidTime(String test) {
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
        if (!(other instanceof Time)) {
            return false;
        }

        Time otherTime = (Time) other;
        return value.equals(otherTime.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
