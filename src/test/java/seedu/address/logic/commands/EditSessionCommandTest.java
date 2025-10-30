package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SIXTH_PERSON_PARENT;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Day;
import seedu.address.model.person.Person;
import seedu.address.model.person.Session;
import seedu.address.model.person.Student;
import seedu.address.model.person.Time;
import seedu.address.testutil.StudentBuilder;

public class EditSessionCommandTest {
    private static final Day VALID_DAY = new Day("Mon");
    private static final Day NEW_DAY = new Day("Tue");
    private static final Time VALID_TIME = new Time("2pm-3pm");
    private static final Time NEW_TIME = new Time("3pm-4pm");
    private static final String VALID_NAME = "Ma Yirui";

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        EditSessionCommand editSessionCommand = new EditSessionCommand(
                outOfBoundIndex, VALID_DAY, VALID_TIME, NEW_DAY, NEW_TIME);

        assertThrows(CommandException.class,
                Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX, () -> editSessionCommand.execute(model));
    }

    @Test
    public void execute_nonStudentPerson_throwsCommandException() {
        // First person in the typical persons list is not a student
        EditSessionCommand editSessionCommand = new EditSessionCommand(
                INDEX_SIXTH_PERSON_PARENT, VALID_DAY, VALID_TIME, NEW_DAY, NEW_TIME);

        assertThrows(CommandException.class,
                Messages.MESSAGE_ONLY_STUDENT_COMMAND, () -> editSessionCommand.execute(model));
    }

    @Test
    public void execute_nonexistentSession_throwsCommandException() {
        // Create a student with no sessions
        Student student = new StudentBuilder().withName(VALID_NAME).build();
        model.addPerson(student);
        Index index = Index.fromOneBased(model.getFilteredPersonList().size());

        EditSessionCommand editSessionCommand = new EditSessionCommand(
                index, VALID_DAY, VALID_TIME, NEW_DAY, NEW_TIME);

        assertThrows(CommandException.class,
                EditSessionCommand.MESSAGE_SESSION_NOT_FOUND, () -> editSessionCommand.execute(model));
    }

    @Test
    public void execute_validSession_success() throws Exception {
        // Create a student with a session
        Student student = new StudentBuilder().withName(VALID_NAME)
                .withSessions(new Session(VALID_DAY, VALID_TIME)).build();
        model.addPerson(student);
        Index index = Index.fromOneBased(model.getFilteredPersonList().size());

        EditSessionCommand editSessionCommand = new EditSessionCommand(
                index, VALID_DAY, VALID_TIME, NEW_DAY, NEW_TIME);

        String expectedMessage = String.format(EditSessionCommand.MESSAGE_SUCCESS,
                student.getName());

        assertEquals(expectedMessage, editSessionCommand.execute(model).getFeedbackToUser());

        // Verify the session was updated
        Person updatedPerson = model.getFilteredPersonList().get(index.getZeroBased());
        assertTrue(updatedPerson instanceof Student);
        assertTrue(((Student) updatedPerson).hasSession(new Session(NEW_DAY, NEW_TIME)));
        assertFalse(((Student) updatedPerson).hasSession(new Session(VALID_DAY, VALID_TIME)));
    }

    @Test
    public void equals() {
        final EditSessionCommand standardCommand = new EditSessionCommand(
                INDEX_FIRST_PERSON, VALID_DAY, VALID_TIME, NEW_DAY, NEW_TIME);

        // same values -> returns true
        EditSessionCommand commandWithSameValues = new EditSessionCommand(
                INDEX_FIRST_PERSON, VALID_DAY, VALID_TIME, NEW_DAY, NEW_TIME);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditSessionCommand(
                INDEX_SECOND_PERSON, VALID_DAY, VALID_TIME, NEW_DAY, NEW_TIME)));

        // different old day -> returns false
        assertFalse(standardCommand.equals(new EditSessionCommand(
                INDEX_FIRST_PERSON, new Day("Wednesday"), VALID_TIME, NEW_DAY, NEW_TIME)));

        // different old time -> returns false
        assertFalse(standardCommand.equals(new EditSessionCommand(
                INDEX_FIRST_PERSON, VALID_DAY, new Time("1pm-2pm"), NEW_DAY, NEW_TIME)));

        // different new day -> returns false
        assertFalse(standardCommand.equals(new EditSessionCommand(
                INDEX_FIRST_PERSON, VALID_DAY, VALID_TIME, new Day("Thursday"), NEW_TIME)));

        // different new time -> returns false
        assertFalse(standardCommand.equals(new EditSessionCommand(
                INDEX_FIRST_PERSON, VALID_DAY, VALID_TIME, NEW_DAY, new Time("4pm-5pm"))));
    }

    @Test
    public void toStringMethod() {
        EditSessionCommand editSessionCommand = new EditSessionCommand(
                INDEX_FIRST_PERSON, VALID_DAY, VALID_TIME, NEW_DAY, NEW_TIME);
        String expected = "EditSessionCommand" + "{targetIndex=" + INDEX_FIRST_PERSON
                + ", oldDay=" + VALID_DAY + ", oldTime=" + VALID_TIME
                + ", newDay=" + NEW_DAY + ", newTime=" + NEW_TIME + "}";
        assertEquals(expected, editSessionCommand.toString());
    }
}
