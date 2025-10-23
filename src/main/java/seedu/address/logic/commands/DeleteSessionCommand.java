package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DAY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Day;
import seedu.address.model.person.Person;
import seedu.address.model.person.Session;
import seedu.address.model.person.Student;
import seedu.address.model.person.Time;

/**
 * Deletes a tutoring session for a student.
 */
public class DeleteSessionCommand extends Command {
    public static final String COMMAND_WORD = "deletesession";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes a tutoring session for a student "
            + "occurring in the given day and time.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_DAY + "DAY "
            + PREFIX_TIME + "TIME\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_DAY + "Mon "
            + PREFIX_TIME + "12pm-1pm";

    public static final String MESSAGE_DELETE_SESSION_SUCCESS = "Session has been deleted for %1$s.";
    public static final String MESSAGE_NONEXISTENT_SESSION = "This session does not exist for this student.";

    private final Index targetPersonIndex;
    private final Day day;
    private final Time time;

    /**
     * Creates a DeleteSessionCommand to delete a tutoring session for a person in the AddressBook.
     *
     * @param targetPersonIndex the index of the person in the filtered list
     * @param day the day of the session
     * @param time the time of the session
     */
    public DeleteSessionCommand(Index targetPersonIndex, Day day, Time time) {
        requireNonNull(targetPersonIndex);
        requireNonNull(day.getValue());
        requireNonNull(time.getValue());
        this.targetPersonIndex = targetPersonIndex;
        this.day = day;
        this.time = time;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetPersonIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(targetPersonIndex.getZeroBased());

        if (personToEdit instanceof Student student) {
            if (!student.hasSession(new Session(day, time))) {
                throw new CommandException(MESSAGE_NONEXISTENT_SESSION);
            }

            Student editedStudent = createEditedStudent(student, day, time);
            model.setPerson(personToEdit, editedStudent);
            model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);

            return new CommandResult(String.format(MESSAGE_DELETE_SESSION_SUCCESS, personToEdit.getName()));
        } else {
            throw new CommandException(Messages.MESSAGE_ONLY_STUDENT_COMMAND);
        }
    }

    /**
     * Creates and returns a {@code Student} with the corresponding session deleted.
     *
     * @param studentToEdit the student that will have a session remove
     * @param day the day of the session removed
     * @param time the time of the session removed
     * @return a new {@code Student} with the corresponding session deleted
     */
    private static Student createEditedStudent(Student studentToEdit, Day day, Time time) {
        assert studentToEdit != null;

        Set<Session> updatedSessions = new HashSet<>(studentToEdit.getSessions());
        updatedSessions.remove(new Session(day, time));

        return new Student(
                studentToEdit.getName(),
                studentToEdit.getPhone(),
                studentToEdit.getAddress(),
                studentToEdit.getRemark(),
                studentToEdit.getTags(),
                updatedSessions
        );
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteSessionCommand otherDeleteSessionCommand)) {
            return false;
        }

        return this.targetPersonIndex.equals(otherDeleteSessionCommand.targetPersonIndex)
                && this.day.equals(otherDeleteSessionCommand.day)
                && this.time.equals(otherDeleteSessionCommand.time);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("person index", targetPersonIndex)
                .add("day time", day + " " + time)
                .toString();
    }
}
