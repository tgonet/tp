package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ViewSessionCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.session.SessionSlot;

public class ViewSessionCommandParserTest {

    private final ViewSessionCommandParser parser = new ViewSessionCommandParser();

    @Test
    public void parse_validArgs_returnsCommand() throws Exception {
        ViewSessionCommand cmd = parser.parse(" Mon-[3pm-5pm] ");
        assertEquals(new ViewSessionCommand(SessionSlot.parse("Mon-[3pm-5pm]")), cmd);
    }

    @Test
    public void parse_invalidArgs_throws() {
        assertThrows(ParseException.class, () -> parser.parse(" Grog-[BLAH] "));
        assertThrows(ParseException.class, () -> parser.parse(""));
    }
}
