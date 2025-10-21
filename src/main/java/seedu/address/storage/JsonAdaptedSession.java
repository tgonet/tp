package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Day;
import seedu.address.model.person.Session;
import seedu.address.model.person.Time;

/**
 * Jackson-friendly version of {@link Session}.
 */
class JsonAdaptedSession {

    private final String day;
    private final String time;

    /**
     * Constructs a {@code JsonAdaptedSession} with the given {@code session}.
     */
    @JsonCreator
    public JsonAdaptedSession(@JsonProperty("day") String day, @JsonProperty("time") String time) {
        this.day = day;
        this.time = time;
    }

    /**
     * Converts a given {@code session} into this class for Jackson use.
     */
    public JsonAdaptedSession(Session source) {
        this.day = source.getDay().getValue();
        this.time = source.getTime().getValue();
    }

    public String getDay() {
        return this.day;
    }

    public String getTime() {
        return time;
    }

    /**
     * Converts this Jackson-friendly adapted Session object into the model's {@code Session} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted Session.
     */
    public Session toModelType() throws IllegalValueException{
        if (!Day.isValidDay(this.day)) {
            throw new IllegalValueException(Day.MESSAGE_CONSTRAINTS);
        }
        else if (!Time.isValidTime(this.time)) {
            throw new IllegalValueException(Time.MESSAGE_CONSTRAINTS);
        }
        return new Session(new Day(this.day), new Time(this.time));
    }

}
