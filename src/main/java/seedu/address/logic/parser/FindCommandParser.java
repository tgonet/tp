package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Name;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Role;
import seedu.address.model.person.RoleContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the
     * FindCommand
     * and returns a FindCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {

        String trimmedArgs = args.trim();

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(" " + trimmedArgs, PREFIX_NAME, PREFIX_ROLE);
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_ROLE);

        List<String> name = argMultimap.getValue(PREFIX_NAME)
                .map(value -> Arrays.stream(value.split("\\s+"))
                        .filter(part -> !part.isEmpty())
                        .map(part -> {
                            try {
                                return ParserUtil.parseName(part).fullName;
                            } catch (ParseException e) {
                                throw new RuntimeException(e);
                            }
                        })
                        .toList())
                .orElse(Collections.emptyList());

        if (argMultimap.getValue(PREFIX_NAME).isPresent() && name.isEmpty()) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        NameContainsKeywordsPredicate namePredicate = new NameContainsKeywordsPredicate(name);

        List<String> role = argMultimap.getValue(PREFIX_ROLE)
                .map(value -> Arrays.stream(value.split("\\s+"))
                        .filter(part -> !part.isEmpty())
                        .map(part -> {
                            try {
                                return ParserUtil.parseRole(part).role;
                            } catch (ParseException e) {
                                throw new RuntimeException(e);
                            }
                        })
                        .toList())
                .orElse(Collections.emptyList());

        if (argMultimap.getValue(PREFIX_ROLE).isPresent() && role.isEmpty()) {
            throw new ParseException(Role.MESSAGE_CONSTRAINTS);
        }

        RoleContainsKeywordsPredicate rolePredicate = new RoleContainsKeywordsPredicate(role);

        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        return new FindCommand(namePredicate, rolePredicate);
    }

}
