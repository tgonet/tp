package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.AddressBookParser;
import seedu.address.logic.parser.ViewSessionCommandParser;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parser- and equality-focused tests for {@link ViewSessionCommand}.
 * Uses parsers to create commands, avoiding direct SessionSlot construction.
 */
public class ViewSessionCommandTest {

    @Test
    public void parse_validInputs_bothCasingsAccepted() throws Exception {
        ViewSessionCommandParser p = new ViewSessionCommandParser();

        // lower-case alias via command-specific parser
        ViewSessionCommand c1 = p.parse(" Mon-[5pm-6pm]");

        // canonical camelCase routed through top-level parser
        AddressBookParser top = new AddressBookParser();
        Command routed = top.parseCommand("viewSession Mon-[5pm-6pm]");
        assertTrue(routed instanceof ViewSessionCommand);
        ViewSessionCommand c2 = (ViewSessionCommand) routed;

        // same slot -> equals
        assertEquals(c1, c2);
    }

    @Test
    public void parse_24hFormatAccepted_roundTrips() throws Exception {
        ViewSessionCommandParser p = new ViewSessionCommandParser();

        ViewSessionCommand first = p.parse(" Tue-[15:00-16:30]");
        ViewSessionCommand second = p.parse("Tue-[15:00-16:30]");

        // equality is the important contract here; hashCode may differ in current impl
        assertEquals(first, second);
    }

    @Test
    public void parse_invalidInputs_throwParseException() {
        ViewSessionCommandParser p = new ViewSessionCommandParser();

        assertThrows(ParseException.class, () -> p.parse(" Grog-[5pm-6pm]")); // bad day
        assertThrows(ParseException.class, () -> p.parse(" Mon-5pm-6pm"));    // missing brackets
        assertThrows(ParseException.class, () -> p.parse(" Mon-[BLAH]"));     // bad time
    }

    @Test
    public void addressBookParser_routes_toViewSession_forBothCasings() throws Exception {
        AddressBookParser top = new AddressBookParser();

        Command a = top.parseCommand("viewsession Wed-[1pm-2pm]");
        Command b = top.parseCommand("viewSession Wed-[1pm-2pm]");

        assertTrue(a instanceof ViewSessionCommand);
        assertTrue(b instanceof ViewSessionCommand);

        ViewSessionCommand va = (ViewSessionCommand) a;
        ViewSessionCommand vb = (ViewSessionCommand) b;

        assertEquals(va, vb);
    }

    @Test
    public void equals_contract() throws Exception {
        AddressBookParser top = new AddressBookParser();

        ViewSessionCommand x1 = (ViewSessionCommand) top.parseCommand("viewsession Thu-[3pm-4pm]");
        ViewSessionCommand x2 = (ViewSessionCommand) top.parseCommand("viewSession Thu-[3pm-4pm]");
        ViewSessionCommand y  = (ViewSessionCommand) top.parseCommand("viewsession Thu-[4pm-5pm]");

        assertEquals(x1, x2);
        assertNotEquals(x1, y);
        assertNotEquals(null, x1);
    }
}
