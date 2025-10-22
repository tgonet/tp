package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.ViewSessionCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.session.SessionSlot;

/**
 * Parses input arguments and creates a new ViewSessionCommand object.
 */
public class ViewSessionCommandParser implements Parser<ViewSessionCommand> {

    @Override
    public ViewSessionCommand parse(String args) throws ParseException {
        requireNonNull(args);
        String trimmed = args.trim();
        if (trimmed.isEmpty()) {
            throw new ParseException(ViewSessionCommand.MESSAGE_USAGE);
        }
        try {
            SessionSlot slot = SessionSlot.parse(trimmed);
            return new ViewSessionCommand(slot);
        } catch (IllegalArgumentException ex) {
            throw new ParseException("Invalid viewSession arguments. " + ViewSessionCommand.MESSAGE_USAGE);
        }
    }
}
