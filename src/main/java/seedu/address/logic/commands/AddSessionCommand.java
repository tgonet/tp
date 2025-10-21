package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DAY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

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
 * Adds a session to an existing person in the address book.
 */
public class AddSessionCommand extends Command {
    public static final String COMMAND_WORD = "addsession";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a tuition session to a person. "
            + "by the index number used in the displayed person list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_DAY + "DAY "
            + PREFIX_TIME + "TIME \n"
            + "Example: " + COMMAND_WORD + " "
            + "1 "
            + PREFIX_DAY + "Mon "
            + PREFIX_TIME + "12:00 ";

    public static final String MESSAGE_SUCCESS = "New session added for %1$s";
    public static final String MESSAGE_DUPLICATE_SESSION = "This session already exists for the person";

    private Index index;
    private final Day day;
    private final Time time;

    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public AddSessionCommand(Index index, Day day, Time time) {
        requireNonNull(day.toString(), time.toString());
        this.index = index;
        this.day = day;
        this.time = time;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());
        if (personToEdit instanceof Student) {
            Person personUpdated = toCopy((Student) personToEdit, day, time);
            model.setPerson(personToEdit, personUpdated);
            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
            return new CommandResult(String.format(MESSAGE_SUCCESS, personToEdit.getName()));
        }
        else {
            throw new CommandException(Messages.MESSAGE_ONLY_STUDENT_COMMAND);
        }
    }

    public Person toCopy(Student personToEdit, Day day, Time time) throws CommandException {
        assert personToEdit != null;

        // create new session
        Session newSession = new Session(day, time);

        // add session to person's existing sessions
        Set<Session> updatedSessions = new HashSet<>(personToEdit.getSessions());
        if (updatedSessions.contains(newSession)) {
            throw new CommandException(MESSAGE_DUPLICATE_SESSION);
        }
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
        if (!(other instanceof AddSessionCommand)) {
            return false;
        }

        AddSessionCommand otherAddSessionCommand = (AddSessionCommand) other;
        return this.day.equals(otherAddSessionCommand.day)
                && this.time.equals(otherAddSessionCommand.time);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("day time",day + " " + time)
                .toString();
    }
}
