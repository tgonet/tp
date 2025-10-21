package main.java.seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import seedu.address.model.person.Phone;

/**
 * Represents a Person's session in the address book.
 */
public class Session {
    String day;
    String time;

    public static final String DAY_MESSAGE_CONSTRAINTS =
            "Days should be one of the following: Mon, Tue, Wed, Thu, Fri, Sat, Sun";
    public static final String DAY_VALIDATION_REGEX = "^(Mon|Tue|Wed|Thu|Fri|Sat|Sun)$";

    public static final String TIME_MESSAGE_CONSTRAINTS =
            "Time should be in the format HH:MM in 24-hour format";
    public static final String TIME_VALIDATION_REGEX = "^([01]\\d|2[0-3]):([0-5]\\d)$";

    public final String value;

    /**
     * Constructs a {@code Session}.
     *
     * @param day A valid day.
     * @param time A valid time/
     * 
     */
    public Session(String day, String time) {
        requireNonNull(day, time);
        checkArgument(isValidDay(day), DAY_MESSAGE_CONSTRAINTS);
        checkArgument(isValidDay(time), TIME_MESSAGE_CONSTRAINTS);
        this.day = day;
        this.time = time;
    }

    /**
    * Returns true if a given string is a valid day.
    */
    public static boolean isValidDay(String test) {
        return test.matches(DAY_VALIDATION_REGEX);
    }

    /**
    * Returns true if a given string is a valid time.
    */
    public static boolean isValidTime(String test) {
        return test.matches(TIME_VALIDATION_REGEX);
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
        if (!(other instanceof Phone)) {
            return false;
        }

        Session otherSession = (Session) other;
        return value.equals(otherSession.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}