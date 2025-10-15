package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.NameContainsKeywordsPredicate;
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

        // ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_ROLE, PREFIX_TAG);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(" " + trimmedArgs, PREFIX_NAME, PREFIX_ROLE);
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_ROLE);

        List<String> name = argMultimap.getValue(PREFIX_NAME)
                .map(value -> Arrays.asList(value.split("\\s+")))
                .orElse(Collections.emptyList());
        NameContainsKeywordsPredicate namePredicate = name.isEmpty() ? null
                : new NameContainsKeywordsPredicate(name);

        List<String> role = argMultimap.getValue(PREFIX_ROLE)
                .map(value -> Arrays.asList(value.split("\\s+")))
                .orElse(Collections.emptyList());
        RoleContainsKeywordsPredicate rolePredicate = role.isEmpty() ? null
                : new RoleContainsKeywordsPredicate(role);

                System.out.println("args: [" + trimmedArgs + "]");
System.out.println("PREFIX_NAME: [" + PREFIX_NAME.getPrefix() + "]");
System.out.println("argMultimap.getValue(PREFIX_NAME): " + argMultimap.getValue(PREFIX_NAME));

        // List<String> tagList = argMultimap.getValue(PREFIX_TAG)
        //         .map(value -> Arrays.asList(value.split("\\s+")))
        //         .orElse(Collections.emptyList());

        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        return new FindCommand(namePredicate, rolePredicate);
    }

}
