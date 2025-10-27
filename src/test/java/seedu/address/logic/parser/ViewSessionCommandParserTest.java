package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DayOfWeek;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ViewSessionCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parser tests for day-only view session.
 */
public class ViewSessionCommandParserTest {
    private final ViewSessionCommandParser parser = new ViewSessionCommandParser();

    /**
     * Accepts full day token.
     */
    @Test
    public void parse_validDay_success() throws Exception {
        ViewSessionCommand cmd = parser.parse(" d/Tuesday");
        assertEquals(new ViewSessionCommand(DayOfWeek.TUESDAY), cmd);
    }

    /**
     * Accepts short alias.
     */
    @Test
    public void parse_dayAliases_success() throws Exception {
        assertEquals(new ViewSessionCommand(DayOfWeek.THURSDAY),
                parser.parse(" d/Thurs"));
    }

    /**
     * Rejects unknown/extra input.
     */
    @Test
    public void parse_rejectsPreambleOrExtra() {
        assertThrows(ParseException.class, () -> parser.parse(" nonsense d/Mon"));
        assertThrows(ParseException.class, () -> parser.parse(" d/Mon extra"));
    }

    /**
     * Rejects bad day token.
     */
    @Test
    public void parse_invalidDay_failure() {
        assertThrows(ParseException.class, () -> parser.parse(" d/Funday"));
    }
}
