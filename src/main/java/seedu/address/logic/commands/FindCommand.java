package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.person.RoleContainsKeywordsPredicate;

/**
 * Finds and lists all persons in address book whose name contains any of the
 * argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " alice bob charlie";

    private final NameContainsKeywordsPredicate namePredicate;
    private final RoleContainsKeywordsPredicate rolePredicate;

    /**
     * Creates a FindCommand to filter persons by the given name and role predicates.
     *
     * @param namePredicate predicate used to filter persons by name
     * @param rolePredicate predicate used to filter persons by role
     */
    public FindCommand(NameContainsKeywordsPredicate namePredicate, RoleContainsKeywordsPredicate rolePredicate) {
        this.namePredicate = namePredicate;
        this.rolePredicate = rolePredicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        Predicate<Person> predicate = person -> true;

        if (this.namePredicate != null) {
            predicate = predicate.and(this.namePredicate);
        }
        if (rolePredicate != null) {
            predicate = predicate.and(this.rolePredicate);
        }
        model.updateFilteredPersonList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FindCommand)) {
            return false;
        }

        FindCommand otherFindCommand = (FindCommand) other;

        boolean isNameEqual;
        if (this.namePredicate == null && otherFindCommand.namePredicate == null) {
            isNameEqual = true;
        } else if (this.namePredicate == null || otherFindCommand.namePredicate == null) {
            isNameEqual = false;
        } else {
            isNameEqual = this.namePredicate.equals(otherFindCommand.namePredicate);
        }

        boolean isRoleEqual;
        if (this.rolePredicate == null && otherFindCommand.rolePredicate == null) {
            isRoleEqual = true;
        } else if (this.rolePredicate == null || otherFindCommand.rolePredicate == null) {
            isRoleEqual = false;
        } else {
            isRoleEqual = this.rolePredicate.equals(otherFindCommand.rolePredicate);
        }

        return isNameEqual && isRoleEqual;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name predicate", this.namePredicate)
                .add("role predicate", this.rolePredicate)
                .toString();
    }
}
