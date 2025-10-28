package seedu.address.model.session;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.DayOfWeek;
import java.time.LocalTime;

import org.junit.jupiter.api.Test;

/**
 * SessionSlot basics.
 */
public class SessionSlotTest {
    /**
     * toString shows day plus time.
     */
    @Test
    public void toString_containsDayAndTime() {
        SessionSlot s = new SessionSlot(DayOfWeek.MONDAY, LocalTime.of(8, 0));
        assertTrue(s.toString().contains("MONDAY"));
        assertTrue(s.toString().contains("08:00"));
    }

    /**
     * MAX_TIME constant set for sort sentinel.
     */
    @Test
    public void maxTime_constant_defined() {
        assertEquals(LocalTime.of(23, 59, 59), SessionSlot.MAX_TIME);
    }
}
