package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DAY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteSessionCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Day;
import seedu.address.model.person.Time;

/**
 * Parses input arguments and creates a new DeleteSessionCommand object
 */
public class DeleteSessionCommandParser implements Parser<DeleteSessionCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of DeleteSessionCommand
     * and returns a DeleteSessionCommand object for execution
     * @throws ParseException if the user input does not conform to the expected format
     */
    @Override
    public DeleteSessionCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_DAY, PREFIX_TIME);

        Index personIndex;

        try {
            personIndex = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    DeleteSessionCommand.MESSAGE_USAGE), pe);
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_DAY, PREFIX_TIME);

        String dayString = argMultimap.getValue(PREFIX_DAY)
                .orElseThrow(() -> new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        DeleteSessionCommand.MESSAGE_USAGE)));
        Day day = ParserUtil.parseDay(dayString);

        String timeString = argMultimap.getValue(PREFIX_TIME)
                .orElseThrow(() -> new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        DeleteSessionCommand.MESSAGE_USAGE)));
        Time time = ParserUtil.parseTime(timeString);

        return new DeleteSessionCommand(personIndex, day, time);
    }
}
