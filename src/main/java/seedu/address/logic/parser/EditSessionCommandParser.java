package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DAY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NEW_DAY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NEW_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditSessionCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Day;
import seedu.address.model.person.Time;

/**
 * Parses input arguments and creates a new {@link EditSessionCommand} object
 * based on the input provided by the user.
 *
 * @see EditSessionCommand
 */
public class EditSessionCommandParser implements Parser<EditSessionCommand> {

    /**
     * Parses input arguments and creates a new {@link EditSessionCommand} object
     * based on the input provided by the user.
     * The input should be in the format of {@code INDEX d/DAY ti/TIME d/NEW_DAY ti/NEW_TIME}.
     * If the input does not conform to the expected format, a ParseException will be thrown.
     *
     * @param args The input arguments provided by the user.
     * @return A new {@link EditSessionCommand} object based on the input provided by the user.
     * @throws ParseException If the input does not conform to the expected format.
     */
    public EditSessionCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_DAY, PREFIX_TIME,
                PREFIX_NEW_DAY, PREFIX_NEW_TIME);

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EditSessionCommand.MESSAGE_USAGE), pe);
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_DAY, PREFIX_TIME, PREFIX_NEW_DAY, PREFIX_NEW_TIME);

        Day day = ParserUtil.parseDay(argMultimap.getValue(PREFIX_DAY)
                .orElseThrow(() -> new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        EditSessionCommand.MESSAGE_USAGE))));
        Time time = ParserUtil.parseTime(argMultimap.getValue(PREFIX_TIME)
                .orElseThrow(() -> new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        EditSessionCommand.MESSAGE_USAGE))));
        Day newDay = ParserUtil.parseDay(argMultimap.getValue(PREFIX_NEW_DAY)
                .orElseThrow(() -> new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        EditSessionCommand.MESSAGE_USAGE))));
        Time newTime = ParserUtil.parseTime(argMultimap.getValue(PREFIX_NEW_TIME)
                .orElseThrow(() -> new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        EditSessionCommand.MESSAGE_USAGE))));

        return new EditSessionCommand(index, day, time, newDay, newTime);
    }
}
