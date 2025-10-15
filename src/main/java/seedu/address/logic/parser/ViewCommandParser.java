package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ViewCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ViewCommand object.
 */
public class ViewCommandParser implements Parser<ViewCommand> {

    // Local fallback because seedu.address.commons.core.Messages does not exist here
    private static final String MESSAGE_INVALID_COMMAND_FORMAT =
            "Invalid command format! \n%1$s";

    @Override
    public ViewCommand parse(String args) throws ParseException {
        requireNonNull(args);
        String trimmed = args.trim();
        if (trimmed.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE));
        }

        Index index = ParserUtil.parseIndex(trimmed);
        return new ViewCommand(index);
    }
}
