package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.util.PersonViewFormatter;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Views details of a person identified by the index in the displayed list.
 */
public class ViewCommand extends Command {

    public static final String COMMAND_WORD = "view";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Views the details of the person identified "
            + "by the index number used in the displayed person list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    private static final String MESSAGE_INVALID_PERSON_DISPLAYED_INDEX =
            "The person index provided is invalid";

    private final Index targetIndex;

    public ViewCommand(Index targetIndex) {
        requireNonNull(targetIndex);
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        final List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        final Person personToView = lastShownList.get(targetIndex.getZeroBased());
        final String details = PersonViewFormatter.format(personToView);
        return new CommandResult(details);
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof ViewCommand
                && targetIndex.equals(((ViewCommand) other).targetIndex));
    }
}
