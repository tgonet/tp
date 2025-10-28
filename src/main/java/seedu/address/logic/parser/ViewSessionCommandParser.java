package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DAY;

import java.time.DayOfWeek;
import java.util.Locale;

import seedu.address.logic.commands.ViewSessionCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parse args for day-only viewSession.
 */
public class ViewSessionCommandParser implements Parser<ViewSessionCommand> {
    @Override
    public ViewSessionCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap mm = ArgumentTokenizer.tokenize(args, PREFIX_DAY);

        if (!mm.getValue(PREFIX_DAY).isPresent() || !mm.getPreamble().isEmpty()) {
            throw new ParseException("invalid command format. " + ViewSessionCommand.MESSAGE_USAGE);
        }

        DayOfWeek day = parseDay(mm.getValue(PREFIX_DAY).get().trim());
        return new ViewSessionCommand(day);
    }

    private static DayOfWeek parseDay(String s) throws ParseException {
        String norm = s.toLowerCase(Locale.ROOT);
        switch (norm) {
        case "mon":
        case "monday":
            return DayOfWeek.MONDAY;
        case "tue":
        case "tues":
        case "tuesday":
            return DayOfWeek.TUESDAY;
        case "wed":
        case "wednesday":
            return DayOfWeek.WEDNESDAY;
        case "thu":
        case "thur":
        case "thurs":
        case "thursday":
            return DayOfWeek.THURSDAY;
        case "fri":
        case "friday":
            return DayOfWeek.FRIDAY;
        case "sat":
        case "saturday":
            return DayOfWeek.SATURDAY;
        case "sun":
        case "sunday":
            return DayOfWeek.SUNDAY;
        default:
            throw new ParseException("day not recognised; use Mon/Tue/... or full names");
        }
    }
}
