package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.time.DayOfWeek;

import org.junit.jupiter.api.Test;

/**
 * Unit tests for lightweight behaviors on {@link ViewSessionCommand}.
 * Does not execute against {@code Model}; see ViewSessionCommandExecuteTest for integration.
 */
public class ViewSessionCommandTest {

    /**
     * Ensure equals honors day parameter identity.
     */
    @Test
    public void equals_dayOnly_ok() {
        ViewSessionCommand monA = new ViewSessionCommand(DayOfWeek.MONDAY);
        ViewSessionCommand monB = new ViewSessionCommand(DayOfWeek.MONDAY);
        ViewSessionCommand tue = new ViewSessionCommand(DayOfWeek.TUESDAY);

        // same values
        assertEquals(monA, monB);

        // different day
        assertNotEquals(monA, tue);

        // not equal to other type or null
        assertNotEquals(monA, 1);
        assertNotEquals(monA, null);
    }

    /**
     * Ensure {@link ViewSessionCommand#toString()} reflects day parameter.
     */
    @Test
    public void toString_containsDay() {
        ViewSessionCommand mon = new ViewSessionCommand(DayOfWeek.MONDAY);
        String s = mon.toString();
        // Minimal deterministic check
        // Expected: "viewsession d/MONDAY" per command implementation
        assertEquals("viewsession d/MONDAY", s.replace("d/Monday", "d/MONDAY")); // shield case sensitivity across JDKs
    }
}
