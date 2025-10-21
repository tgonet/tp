package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class TimeTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Time(null));
    }

    @Test
    public void constructor_invalidTime_throwsIllegalArgumentException() {
        String invalidTime1 = "25:00AM-26:00PM";
        assertThrows(IllegalArgumentException.class, () -> new Time(invalidTime1));

        String invalidTime2 = "3pm5pm";
        assertThrows(IllegalArgumentException.class, () -> new Time(invalidTime2));

        String invalidTime3 = "13pm-2pm";
        assertThrows(IllegalArgumentException.class, () -> new Time(invalidTime3));
    }

    @Test
    public void isValidTime() {
        // null string
        assertThrows(NullPointerException.class, () -> Time.isValidTime(null));

        // invalid times
        assertFalse(Time.isValidTime("")); // empty
        assertFalse(Time.isValidTime(" ")); // spaces only
        assertFalse(Time.isValidTime("3pm5pm")); // missing dash
        assertFalse(Time.isValidTime("13am-2pm")); // invalid hour
        assertFalse(Time.isValidTime("0:00am-1:00pm")); // invalid hour

        // valid times
        assertTrue(Time.isValidTime("3pm-5pm"));
        assertTrue(Time.isValidTime("12am-1pm"));
        assertTrue(Time.isValidTime("1:30pm-2:45pm"));
        assertTrue(Time.isValidTime("09:00AM-11:15AM"));
        assertTrue(Time.isValidTime("10:00am-12:00pm"));
    }

    @Test
    public void equals() {
        Time time = new Time("3pm-5pm");

        // same values -> returns true
        assertTrue(time.equals(new Time("3pm-5pm")));

        // same object -> returns true
        assertTrue(time.equals(time));

        // null -> returns false
        assertFalse(time.equals(null));

        // different type -> returns false
        assertFalse(time.equals("3pm-5pm"));

        // different values -> returns false
        assertFalse(time.equals(new Time("4pm-6pm")));
    }

    @Test
    public void hashCode_sameForEqualObjects() {
        Time time1 = new Time("1pm-2pm");
        Time time2 = new Time("1pm-2pm");
        assertTrue(time1.hashCode() == time2.hashCode());
    }

    @Test
    public void toString_returnsValue() {
        Time time = new Time("10am-12pm");
        assertTrue(time.toString().equals("10am-12pm"));
    }
}
