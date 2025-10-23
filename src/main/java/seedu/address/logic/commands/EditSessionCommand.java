package seedu.address.logic.commands;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DAY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NEW_DAY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NEW_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

public class EditSessionCommand extends Command {
    public static final String COMMAND_WORD = "editsession";

    private final Index targetIndex;
    private final Day oldDay;
    private final Time oldTime;
    private final Day newDay;
    private final Time newTime;

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits a tuition session of a person. "
            + "by the index number used in the displayed person list.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_DAY + "DAY "
            + PREFIX_TIME + "TIME "
            + PREFIX_NEW_DAY + "NEW_DAY "
            + PREFIX_NEW_TIME + "NEW_TIME \n"
            + "Example: " + COMMAND_WORD + " "
            + "1 "
            + PREFIX_DAY + "Mon "
            + PREFIX_TIME + "12pm-1pm "
            + PREFIX_NEW_DAY + "Tue "
            + PREFIX_NEW_TIME + "1pm-2pm";

    private final String MESSAGE_SUCCESS = "Edited session of %s.";
    private final String MESSAGE_SESSION_NOT_FOUND = "Session not found.";

    /**
     * Creates an EditSessionCommand to edit a session of a specific person in the list.
     *
     * @param targetIndex the index of the person in the filtered list
     * @param oldDay   the day of the session before editing
     * @param oldTime  the time of the session before editing
     * @param newDay   the day of the session after editing
     * @param newTime  the time of the session after editing
     */
    public EditSessionCommand(Index targetIndex, Day oldDay, Time oldTime, Day newDay, Time newTime) {
        this.targetIndex = targetIndex;
        this.oldDay = oldDay;
        this.oldTime = oldTime;
        this.newDay = newDay;
        this.newTime = newTime;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireAllNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(targetIndex.getZeroBased());
        if (personToEdit instanceof Student student) {
            if (!student.hasSession(new Session(oldDay, oldTime))) {
                throw new CommandException(MESSAGE_SESSION_NOT_FOUND);
            }
            Person personUpdated = toCopy((Student) personToEdit, oldDay, oldTime, newDay, newTime);
            model.setPerson(personToEdit, personUpdated);
            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
            return new CommandResult(String.format(MESSAGE_SUCCESS, personToEdit.getName()));
        } else {
            throw new CommandException(Messages.MESSAGE_ONLY_STUDENT_COMMAND);
        }
    }

    private Person toCopy(Student personToEdit, Day oldDay, Time oldTime, Day newDay, Time newTime) {
        assert personToEdit != null;

        // create new session
        Session newSession = new Session(newDay, newTime);

        // remove old session from person's existing sessions
        Set<Session> updatedSessions = new HashSet<>(personToEdit.getSessions());
        updatedSessions.remove(new Session(oldDay, oldTime));
        updatedSessions.add(newSession);

        return new Student(
                personToEdit.getName(),
                personToEdit.getPhone(),
                personToEdit.getAddress(),
                personToEdit.getRemark(),
                personToEdit.getTags(),
                updatedSessions
        );
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditSessionCommand)) {
            return false;
        }

        EditSessionCommand otherEditSessionCommand = (EditSessionCommand) other;

        return targetIndex.equals(otherEditSessionCommand.targetIndex)
                && oldDay.equals(otherEditSessionCommand.oldDay)
                && oldTime.equals(otherEditSessionCommand.oldTime)
                && newDay.equals(otherEditSessionCommand.newDay)
                && newTime.equals(otherEditSessionCommand.newTime);
    }

    @Override
    public String toString() {
        return "EditSessionCommand{" +
                "targetIndex=" + targetIndex +
                ", oldDay=" + oldDay +
                ", oldTime=" + oldTime +
                ", newDay=" + newDay +
                ", newTime=" + newTime +
                '}';
    }


}
