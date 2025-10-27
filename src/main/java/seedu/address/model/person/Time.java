package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a time in 12-hour format with an optional minute component and AM/PM.
 *
 * <p>The time must follow the format {@code HH[:MM]am-HH[:MM]pm} (case-insensitive),
 * e.g., "3pm-5pm", "09:30AM-11:45AM", or "12:00am-1:15pm".</p>
 *
 * <p>Hours must be between 1 and 12, and minutes, if present, must be between 00 and 59.</p>
 */
public class Time {
    public static final String MESSAGE_CONSTRAINTS =
        "Time should be in 12-hour format without leading zeros, e.g., 3pm-5pm or 9:30AM-11:45AM\n"
                + "Start time should not be greater than end time";

    public static final String VALIDATION_REGEX =
            "^([1-9]|1[0-2])(:[0-5][0-9])?(am|pm|AM|PM)-([1-9]|1[0-2])(:[0-5][0-9])?(am|pm|AM|PM)$";


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
        if (!test.matches(VALIDATION_REGEX)) {
            return false;
        }

        String[] timeParts = test.split("-");
        return isStartBeforeEnd(timeParts[0], timeParts[1]);
    }

    /**
     * Returns true if the end time is more than the start time
     */
    private static boolean isStartBeforeEnd(String start, String end) {
        int startTime = toMinutes(start);
        int endTime = toMinutes(end);
        return endTime > startTime;
    }

    /**
     * Converts a 12-hour time to minutes since midnight.
     */
    private static int toMinutes(String input) {
        String time = input.toLowerCase();
        boolean isPm = time.endsWith("pm");
        String updatedTime = time.replaceAll("(?i)(am|pm)", "");

        String[] parts = updatedTime.split(":");
        int hour = Integer.parseInt(parts[0]);
        int minute = 0;
        if (parts.length == 2) {
            minute = Integer.parseInt(parts[1]);
        }

        if (hour == 12) {
            if (!isPm) {
                hour = 0;
            }
        } else if (isPm) {
            hour += 12;
        }

        return hour * 60 + minute;
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
