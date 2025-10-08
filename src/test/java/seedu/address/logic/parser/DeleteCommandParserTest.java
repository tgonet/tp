package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the DeleteCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the DeleteCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class DeleteCommandParserTest {

    private DeleteCommandParser parser = new DeleteCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteCommand() {
        assertParseSuccess(parser, "1", new DeleteCommand(INDEX_FIRST_PERSON));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_leadingAndTrailingSpaces_success() throws Exception {
        DeleteCommand cmd = parser.parse("   1   ");
        assertEquals(new DeleteCommand(Index.fromOneBased(1)), cmd);
    }

    @Test
    public void parse_multipleInnerSpaces_success() throws Exception {
        DeleteCommand cmd = parser.parse("1"); // delete takes only an index; inner spaces are not applicable
        assertEquals(new DeleteCommand(Index.fromOneBased(1)), cmd);
    }

    @Test
    public void parse_zeroIndex_failure() {
        assertThrows(ParseException.class, () -> parser.parse("0"));
    }

    @Test
    public void parse_negativeIndex_failure() {
        assertThrows(ParseException.class, () -> parser.parse("-5"));
    }

    @Test
    public void parse_nonNumeric_failure() {
        assertThrows(ParseException.class, () -> parser.parse("one"));
        assertThrows(ParseException.class, () -> parser.parse("1.0"));
        assertThrows(ParseException.class, () -> parser.parse("1,000"));
    }

    @Test
    public void parse_veryLargeIndex_success() throws Exception {
        // Parser accepts syntactically valid positive integers; bounds are enforced in the command execution layer.
        DeleteCommand cmd = parser.parse("999999999");
        assertEquals(new DeleteCommand(Index.fromOneBased(999_999_999)), cmd);
    }

    @Test
    public void parse_empty_failure() {
        assertThrows(ParseException.class, () -> parser.parse(""));
        assertThrows(ParseException.class, () -> parser.parse("   "));
    }

}
