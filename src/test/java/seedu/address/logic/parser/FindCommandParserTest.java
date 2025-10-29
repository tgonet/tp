package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindCommand;
import seedu.address.model.person.Name;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.RoleContainsKeywordsPredicate;
import seedu.address.model.person.TagContainsKeywordsPredicate;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // Test with name and role
        FindCommand expectedFindCommand = new FindCommand(
                new NameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob")),
                new RoleContainsKeywordsPredicate(Collections.singletonList("student")),
                new TagContainsKeywordsPredicate(Collections.emptyList())
        );
        assertParseSuccess(parser, PREFIX_NAME + "Alice Bob" + " " + PREFIX_ROLE + "student", expectedFindCommand);

        // Test with name, role and tag
        expectedFindCommand = new FindCommand(
                new NameContainsKeywordsPredicate(Collections.singletonList("Charlie")),
                new RoleContainsKeywordsPredicate(Collections.singletonList("parent")),
                new TagContainsKeywordsPredicate(Collections.singletonList("friends"))
        );
        assertParseSuccess(parser,
                PREFIX_NAME + "Charlie" + " " + PREFIX_ROLE + "parent" + " " + PREFIX_TAG + "friends",
                expectedFindCommand);

        // Test with only tag
        expectedFindCommand = new FindCommand(
                new NameContainsKeywordsPredicate(Collections.emptyList()),
                new RoleContainsKeywordsPredicate(Collections.emptyList()),
                new TagContainsKeywordsPredicate(Collections.singletonList("math"))
        );
        assertParseSuccess(parser, PREFIX_TAG + "math", expectedFindCommand);

        // Test with multiple whitespaces between keywords
        expectedFindCommand = new FindCommand(
                new NameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob")),
                new RoleContainsKeywordsPredicate(Collections.emptyList()),
                new TagContainsKeywordsPredicate(Collections.singletonList("friends"))
        );
        assertParseSuccess(parser,
                PREFIX_NAME + " \n Alice \n \t Bob  \t" + " " + PREFIX_TAG + "friends",
                expectedFindCommand);
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // No prefixes provided
        assertParseFailure(parser, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));

        // Only whitespace after prefix
        assertParseFailure(parser, PREFIX_NAME + " ",
                String.format(Name.MESSAGE_CONSTRAINTS));
    }
}
