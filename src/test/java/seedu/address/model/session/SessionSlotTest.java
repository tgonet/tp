package seedu.address.model.session;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class SessionSlotTest {

    @Test
    public void parse_valid_success() {
        assertEquals("Mon-[15:00-17:00]",
                SessionSlot.parse("Mon-[3pm-5pm]").toString());
        assertEquals("Tue-[09:00-10:30]",
                SessionSlot.parse("tue-[9-10:30]").toString());
        assertEquals("Sun-[13:00-14:00]",
                SessionSlot.parse("Sun-[13:00-14:00]").toString());
    }

    @Test
    public void parse_invalid_rejected() {
        assertThrows(IllegalArgumentException.class, () -> SessionSlot.parse("Monday-[3pm-5pm]"));
        assertThrows(IllegalArgumentException.class, () -> SessionSlot.parse("Mon-3pm-5pm"));
        assertThrows(IllegalArgumentException.class, () -> SessionSlot.parse("Mon-[5pm-3pm]")); // end before start
        assertThrows(IllegalArgumentException.class, () -> SessionSlot.parse("Foo-[BLAH]"));
    }
}
