package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class DayTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Day(null));
    }

    @Test
    public void constructor_invalidDay_throwsIllegalArgumentException() {
        String invalidDay = "Funday"; // not matching "Mon", "Tue", etc.
        assertThrows(IllegalArgumentException.class, () -> new Day(invalidDay));
    }

    @Test
    public void isValidDay() {
        // null day
        assertThrows(NullPointerException.class, () -> Day.isValidDay(null));

        // invalid days
        assertFalse(Day.isValidDay("Frii")); // typo
        assertFalse(Day.isValidDay("")); // empty string
        assertFalse(Day.isValidDay(" ")); // spaces only
        assertFalse(Day.isValidDay("Funday")); // not a real day

        // valid days (short forms)
        assertTrue(Day.isValidDay("Mon"));
        assertTrue(Day.isValidDay("Tue"));
        assertTrue(Day.isValidDay("Wed"));
        assertTrue(Day.isValidDay("Thur"));
        assertTrue(Day.isValidDay("Fri"));
        assertTrue(Day.isValidDay("Sat"));
        assertTrue(Day.isValidDay("Sun"));

        // valid days (full forms)
        assertTrue(Day.isValidDay("Monday"));
        assertTrue(Day.isValidDay("Tuesday"));
        assertTrue(Day.isValidDay("Wednesday"));
        assertTrue(Day.isValidDay("Thursday"));
        assertTrue(Day.isValidDay("Friday"));
        assertTrue(Day.isValidDay("Saturday"));
        assertTrue(Day.isValidDay("Sunday"));

        // valid days (case-insensitive)
        assertTrue(Day.isValidDay("monday"));
        assertTrue(Day.isValidDay("fri"));
        assertTrue(Day.isValidDay("SuN"));
    }

    @Test
    public void equals() {
        Day day = new Day("Mon");

        // same values -> returns true
        assertTrue(day.equals(new Day("Mon")));

        // equivalent full form -> should normalize to same short form
        assertTrue(day.equals(new Day("Monday")));

        // same object -> returns true
        assertTrue(day.equals(day));

        // null -> returns false
        assertFalse(day.equals(null));

        // different types -> returns false
        assertFalse(day.equals("Mon"));

        // different values -> returns false
        assertFalse(day.equals(new Day("Tue")));
    }

    @Test
    public void hashCode_sameForEqualObjects() {
        Day day1 = new Day("Fri");
        Day day2 = new Day("Fri");
        assertTrue(day1.hashCode() == day2.hashCode());
    }

    @Test
    public void toString_returnsValue() {
        Day day = new Day("Wed");
        assertTrue(day.toString().equals("Wed"));
    }
}
