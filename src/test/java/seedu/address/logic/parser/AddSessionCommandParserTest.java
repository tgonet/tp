package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DAY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.AddSessionCommand;
import seedu.address.model.person.Day;
import seedu.address.model.person.Time;

public class AddSessionCommandParserTest {
    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddSessionCommand.MESSAGE_USAGE);

    private AddSessionCommandParser parser = new AddSessionCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, PREFIX_DAY + "Mon " + PREFIX_TIME + "12pm-1pm",
                MESSAGE_INVALID_FORMAT);

        // no day specified
        assertParseFailure(parser, "1 " + PREFIX_TIME + "12pm-1pm",
                MESSAGE_INVALID_FORMAT);

        // no time specified
        assertParseFailure(parser, "1 " + PREFIX_DAY + "Mon",
                MESSAGE_INVALID_FORMAT);

        // no fields specified
        assertParseFailure(parser, "1", MESSAGE_INVALID_FORMAT);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + PREFIX_DAY + "Mon " + PREFIX_TIME + "12pm-1pm",
                MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + PREFIX_DAY + "Mon " + PREFIX_TIME + "12pm-1pm",
                MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 s/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid day
        assertParseFailure(parser, "1 " + PREFIX_DAY + "Monday " + PREFIX_TIME + "12pm-1pm",
                Day.MESSAGE_CONSTRAINTS);

        // invalid time
        assertParseFailure(parser, "1 " + PREFIX_DAY + "Mon " + PREFIX_TIME + "25:00",
                Time.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only first invalid value is captured
        assertParseFailure(parser, "1 " + PREFIX_DAY + "Monday " + PREFIX_TIME + "25:00",
                Day.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsPresent_success() {
        Index targetIndex = INDEX_FIRST_PERSON;

        // basic test with all fields present
        String userInput = targetIndex.getOneBased() + " " + PREFIX_DAY + "Mon "
                + PREFIX_TIME + "12pm-1pm";
        AddSessionCommand expectedCommand = new AddSessionCommand(targetIndex,
                new Day("Mon"), new Time("12pm-1pm"));
        assertParseSuccess(parser, userInput, expectedCommand);

        // different valid day
        userInput = targetIndex.getOneBased() + " " + PREFIX_DAY + "Tue "
                + PREFIX_TIME + "12pm-1pm";
        expectedCommand = new AddSessionCommand(targetIndex,
                new Day("Tue"), new Time("12pm-1pm"));
        assertParseSuccess(parser, userInput, expectedCommand);

        // different valid time
        userInput = targetIndex.getOneBased() + " " + PREFIX_DAY + "Mon "
                + PREFIX_TIME + "3pm-4pm";
        expectedCommand = new AddSessionCommand(targetIndex,
                new Day("Mon"), new Time("3pm-4pm"));
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_failure() {
        String validInput = "1 " + PREFIX_DAY + "Mon " + PREFIX_TIME + "12pm-1pm";

        // multiple days
        assertParseFailure(parser, validInput + " " + PREFIX_DAY + "Tue",
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_DAY));

        // multiple times
        assertParseFailure(parser, validInput + " " + PREFIX_TIME + "2pm-3pm",
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_TIME));

        // multiple days and times
        assertParseFailure(parser, validInput + " " + PREFIX_DAY + "Tue " + PREFIX_TIME + "2pm-3pm",
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_DAY, PREFIX_TIME));
    }
}
