package seedu.address.model.session;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalTime;

import org.junit.jupiter.api.Test;

import seedu.address.model.session.SessionSlot.Day;
import seedu.address.model.session.SessionSlot.TimeRange;

public class SessionSlotTest {

    @Test
    public void parse_validExamples_normalizeCorrectly() {
        SessionSlot s1 = SessionSlot.parse("Mon-[5pm-6pm]");
        assertEquals("Mon-[17:00-18:00]", s1.toString());
        assertEquals("Mon-[5pm-6pm]", s1.toUserFormat());

        SessionSlot s2 = SessionSlot.parse("Tue-[15:30-17:00]");
        assertEquals("Tue-[15:30-17:00]", s2.toString());
        assertEquals("Tue-[3:30pm-5pm]", s2.toUserFormat());

        SessionSlot s3 = SessionSlot.parse("  wed - [ 1pm - 2:15pm ] ");
        assertEquals("Wed-[13:00-14:15]", s3.toString());
        assertEquals("Wed-[1pm-2:15pm]", s3.toUserFormat());
    }

    @Test
    public void parse_invalidExamples_throw() {
        assertThrows(IllegalArgumentException.class, () -> SessionSlot.parse("Grog-[BLAH]"));
        assertThrows(IllegalArgumentException.class, () -> SessionSlot.parse("Mon-[]"));
        assertThrows(IllegalArgumentException.class, () -> SessionSlot.parse("Mon-[5pm]"));
        assertThrows(IllegalArgumentException.class, () -> SessionSlot.parse("Mon-[25:00-26:00]"));
        assertThrows(IllegalArgumentException.class, () -> SessionSlot.parse("Fri-[6pm-5pm]")); // end before start
    }

    @Test
    public void equals_hashCode_contract() {
        SessionSlot a = SessionSlot.parse("Sat-[09:00-10:00]");
        SessionSlot b = SessionSlot.parse("Sat-[9am-10am]");
        SessionSlot c = SessionSlot.parse("Sat-[10:00-11:00]");

        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
        assertNotEquals(a, c);
    }

    @Test
    public void timeRange_toString_andEquality() {
        TimeRange r1 = TimeRange.of(LocalTime.of(9, 0), LocalTime.of(10, 0));
        TimeRange r2 = TimeRange.of(LocalTime.of(9, 0), LocalTime.of(10, 0));
        TimeRange r3 = TimeRange.of(LocalTime.of(9, 30), LocalTime.of(10, 0));

        assertEquals("09:00-10:00", r1.toString());
        assertEquals(r1, r2);
        assertNotEquals(r1, r3);
    }

    @Test
    public void day_parse_andToString() {
        assertEquals(Day.MON, Day.parse("Mon"));
        assertEquals(Day.THU, Day.parse("thu"));
        assertEquals("Wed", Day.WED.toString());
    }
}
