package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Name;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Role;
import seedu.address.model.person.RoleContainsKeywordsPredicate;
import seedu.address.model.tag.Tag;

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

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_ROLE, PREFIX_TAG);

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_ROLE);

        // Use .getValue() which returns Optional<String>, and handle missing values
        // Name keywords
        List<String> name = argMultimap.getValue(PREFIX_NAME)
                .map(value -> Arrays.asList(value.split(","))) // split if present
                .orElse(Collections.emptyList()); // if not present, return empty list

        // Role keywords
        List<String> role = argMultimap.getValue(PREFIX_ROLE)
                .map(value -> Arrays.asList(value.split(",")))
                .orElse(Collections.emptyList());

        // Tag keywords
        List<String> tagList = argMultimap.getValue(PREFIX_TAG)
                .map(value -> Arrays.asList(value.split(",")))
                .orElse(Collections.emptyList());

        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        //String[] nameKeywords = trimmedArgs.split("\\s+");

        return new FindCommand(new NameContainsKeywordsPredicate(name), new RoleContainsKeywordsPredicate(role));
    }

}
