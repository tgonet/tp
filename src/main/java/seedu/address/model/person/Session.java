package seedu.address.model.person;

/**
 * Represents a Person's session in the address book.
 */
public class Session {
    private Day day;
    private Time time;

    /**
     * Constructs a {@code Session}.
     *
     * @param day A valid day.
     * @param time A valid time/
     *
     */
    public Session(Day day, Time time) {
        this.day = day;
        this.time = time;
    }

    /**
     * Returns the day of this session.
     *
     * @return the {@link Day} object representing the day of the session
     */
    public Day getDay() {
        return day;
    }

    /**
     * Returns the time of this session.
     *
     * @return the {@link Time} object representing the time of the session
     */
    public Time getTime() {
        return time;
    }

    /**
     * Returns a string representation of this session.
     * The format is: DAY-[TIME], e.g., "Mon-[3pm-5pm]".
     *
     * @return a string representing the day and time of the session
     */
    @Override
    public String toString() {
        return day.toString() + "-" + "[" + time.toString() + "]";
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Session)) {
            return false;
        }

        Session otherSession = (Session) other;
        return day.equals(otherSession.day) && time.equals(otherSession.time);
    }

    @Override
    public int hashCode() {
        int result = day != null ? day.hashCode() : 0;
        result = 31 * result + (time != null ? time.hashCode() : 0);
        return result;
    }
}
