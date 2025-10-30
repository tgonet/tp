package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_DUPLICATE_FIELDS;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DAY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NEW_DAY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NEW_TIME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TIME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DAY_MON;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DAY_TUE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TIME_1PM;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TIME_2PM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DAY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NEW_DAY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NEW_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditSessionCommand;
import seedu.address.model.person.Day;
import seedu.address.model.person.Time;

public class EditSessionCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditSessionCommand.MESSAGE_USAGE);

    private EditSessionCommandParser parser = new EditSessionCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_DAY_MON + " " + PREFIX_TIME + VALID_TIME_1PM
                        + " " + PREFIX_NEW_DAY + VALID_DAY_TUE
                        + " " + PREFIX_NEW_TIME + VALID_TIME_2PM,
                MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + " " + PREFIX_DAY + VALID_DAY_MON
                        + " " + PREFIX_TIME + VALID_TIME_1PM
                        + " " + PREFIX_NEW_DAY + VALID_DAY_TUE
                        + " " + PREFIX_NEW_TIME + VALID_TIME_2PM,
                MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + " " + PREFIX_DAY + VALID_DAY_MON
                        + " " + PREFIX_TIME + VALID_TIME_1PM
                        + " " + PREFIX_NEW_DAY + VALID_DAY_TUE
                        + " " + PREFIX_NEW_TIME + VALID_TIME_2PM,
                MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string" + " " + PREFIX_DAY + VALID_DAY_MON
                        + " " + PREFIX_TIME + VALID_TIME_1PM
                        + " " + PREFIX_NEW_DAY + VALID_DAY_TUE
                        + " " + PREFIX_NEW_TIME + VALID_TIME_2PM,
                MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string" + " " + PREFIX_DAY + VALID_DAY_MON
                        + " " + PREFIX_TIME + VALID_TIME_1PM
                        + " " + PREFIX_NEW_DAY + VALID_DAY_TUE
                        + " " + PREFIX_NEW_TIME + VALID_TIME_2PM,
                MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid day
        assertParseFailure(parser, "1" + INVALID_DAY_DESC
                        + " " + PREFIX_TIME + VALID_TIME_1PM
                        + " " + PREFIX_NEW_DAY + VALID_DAY_TUE
                        + " " + PREFIX_NEW_TIME + VALID_TIME_2PM,
                Day.MESSAGE_CONSTRAINTS);

        // invalid time
        assertParseFailure(parser, "1" + " " + PREFIX_DAY + VALID_DAY_MON
                        + INVALID_TIME_DESC
                        + " " + PREFIX_NEW_DAY + VALID_DAY_TUE
                        + " " + PREFIX_NEW_TIME + VALID_TIME_2PM,
                Time.MESSAGE_CONSTRAINTS);

        // invalid new day
        assertParseFailure(parser, "1" + " " + PREFIX_DAY + VALID_DAY_MON
                        + " " + PREFIX_TIME + VALID_TIME_1PM
                        + INVALID_NEW_DAY_DESC
                        + " " + PREFIX_NEW_TIME + VALID_TIME_2PM,
                Day.MESSAGE_CONSTRAINTS);

        // invalid new time
        assertParseFailure(parser, "1" + " " + PREFIX_DAY + VALID_DAY_MON
                        + " " + PREFIX_TIME + VALID_TIME_1PM
                        + " " + PREFIX_NEW_DAY + VALID_DAY_TUE
                        + INVALID_NEW_TIME_DESC,
                Time.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + " " + PREFIX_DAY + VALID_DAY_MON
                + " " + PREFIX_TIME + VALID_TIME_1PM
                + " " + PREFIX_NEW_DAY + VALID_DAY_TUE
                + " " + PREFIX_NEW_TIME + VALID_TIME_2PM;

        EditSessionCommand expectedCommand = new EditSessionCommand(
                targetIndex,
                new Day(VALID_DAY_MON),
                new Time(VALID_TIME_1PM),
                new Day(VALID_DAY_TUE),
                new Time(VALID_TIME_2PM)
        );

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleSamePrefixes_failure() {
        // Multiple day prefixes
        assertParseFailure(parser, "1" + " " + PREFIX_DAY + VALID_DAY_MON
                        + " " + PREFIX_DAY + VALID_DAY_TUE
                        + " " + PREFIX_TIME + VALID_TIME_1PM
                        + " " + PREFIX_NEW_DAY + VALID_DAY_TUE
                        + " " + PREFIX_NEW_TIME + VALID_TIME_2PM,
                MESSAGE_DUPLICATE_FIELDS + PREFIX_DAY);
    }
}
