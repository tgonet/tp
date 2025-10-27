package seedu.address.model.session;

import static java.util.Objects.requireNonNull;

import java.time.DayOfWeek;
import java.time.LocalTime;

/**
 * Immutable weekly slot with day plus start time.
 */
public final class SessionSlot {
    /** Sentinel used in sort to sink missing times. */
    public static final LocalTime MAX_TIME = LocalTime.of(23, 59, 59);

    private final DayOfWeek day;
    private final LocalTime start;

    /**
     * Create session slot.
     * @param day weekday; not null
     * @param start start time; not null
     */
    public SessionSlot(DayOfWeek day, LocalTime start) {
        requireNonNull(day);
        requireNonNull(start);
        this.day = day;
        this.start = start;
    }

    /** Day accessor. */
    public DayOfWeek getDay() {
        return day;
    }

    /** Start accessor. */
    public LocalTime getStart() {
        return start;
    }

    @Override
    public String toString() {
        return day + " " + start;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof SessionSlot)) {
            return false;
        }
        SessionSlot o = (SessionSlot) other;
        return day == o.day && start.equals(o.start);
    }

    @Override
    public int hashCode() {
        return day.hashCode() * 31 + start.hashCode();
    }
}
