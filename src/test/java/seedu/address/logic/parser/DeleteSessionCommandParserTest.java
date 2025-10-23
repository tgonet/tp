package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.DAY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.DAY_DESC_EXTRA;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DAY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TIME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.TIME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.TIME_DESC_EXTRA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DAY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DAY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.DeleteSessionCommand;
import seedu.address.model.person.Day;
import seedu.address.model.person.Time;

public class DeleteSessionCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteSessionCommand.MESSAGE_USAGE);

    private DeleteSessionCommandParser parser = new DeleteSessionCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, DAY_DESC + TIME_DESC, MESSAGE_INVALID_FORMAT);

        // no day specified
        assertParseFailure(parser, "1" + TIME_DESC, MESSAGE_INVALID_FORMAT);

        // no time specified
        assertParseFailure(parser, "1" + DAY_DESC, MESSAGE_INVALID_FORMAT);

        // no fields specified
        assertParseFailure(parser, "1", MESSAGE_INVALID_FORMAT);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-1" + DAY_DESC + TIME_DESC, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + DAY_DESC + TIME_DESC, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_DAY_DESC + TIME_DESC, Day.MESSAGE_CONSTRAINTS); // invalid day
        assertParseFailure(parser, "1" + DAY_DESC + INVALID_TIME_DESC, Time.MESSAGE_CONSTRAINTS); // invalid time

        // invalid day and time, first invalid value captured
        assertParseFailure(parser, "1" + INVALID_DAY_DESC + INVALID_TIME_DESC, Day.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + DAY_DESC + TIME_DESC;

        DeleteSessionCommand expectedCommand = new DeleteSessionCommand(targetIndex,
                new Day(VALID_DAY), new Time(VALID_TIME));

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_failure() {
        // multiple days given
        assertParseFailure(parser,
                "1" + DAY_DESC + TIME_DESC + DAY_DESC_EXTRA,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_DAY));

        // multiple times given
        assertParseFailure(parser,
                "1" + DAY_DESC + TIME_DESC + TIME_DESC_EXTRA,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_TIME));

        // multiples days and times given
        assertParseFailure(parser,
                "1" + DAY_DESC + TIME_DESC + DAY_DESC_EXTRA + TIME_DESC_EXTRA,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_DAY, PREFIX_TIME));
    }
}
