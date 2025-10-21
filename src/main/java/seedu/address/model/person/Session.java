package seedu.address.model.person;

/**
 * Represents a Person's session in the address book.
 */
public class Session {
    Day day;
    Time time;

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

    public Day getDay() {
        return day;
    }

    public Time getTime() {
        return time;
    }

    @Override
    public String toString() {
        return day.toString() + " " + time.toString();
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