package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Day;
import seedu.address.model.person.Session;
import seedu.address.model.person.Student;
import seedu.address.model.person.Time;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteSessionCommand}
 */
public class DeleteSessionCommandTest {

    private static final Day TEST_DAY_ONE = new Day("Tue");
    private static final Time TEST_TIME_ONE = new Time("12pm-1pm");

    private static final Day TEST_DAY_CORRECT = new Day("Mon");
    private static final Time TEST_TIME_CORRECT = new Time("12pm-3pm");

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validStudentIndexSessionDayTime_success() {
        Index studentIndex = Index.fromOneBased(5);
        Student studentToBeChanged = (Student) model.getFilteredPersonList().get(studentIndex.getZeroBased());

        DeleteSessionCommand deleteSessionCommand = new DeleteSessionCommand(studentIndex,
                TEST_DAY_CORRECT, TEST_TIME_CORRECT);

        String expectedMessage = String.format(DeleteSessionCommand.MESSAGE_DELETE_SESSION_SUCCESS,
                studentToBeChanged.getName());

        // expected updated student after session removal
        Set<Session> updatedSessions = new HashSet<>(studentToBeChanged.getSessions());
        updatedSessions.remove(new Session(TEST_DAY_CORRECT, TEST_TIME_CORRECT));
        Student updatedStudent = new Student(
                studentToBeChanged.getName(),
                studentToBeChanged.getPhone(),
                studentToBeChanged.getAddress(),
                studentToBeChanged.getRemark(),
                studentToBeChanged.getTags(),
                updatedSessions
        );

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(model.getFilteredPersonList().get(4), updatedStudent);

        assertCommandSuccess(deleteSessionCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        DeleteSessionCommand deleteSessionCommand = new DeleteSessionCommand(outOfBoundIndex,
                TEST_DAY_ONE, TEST_TIME_ONE);

        assertCommandFailure(deleteSessionCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_personIsNotStudent_failure() {
        // 6th person is a Parent
        Index parentIndex = Index.fromOneBased(6);
        DeleteSessionCommand deleteSessionCommand = new DeleteSessionCommand(parentIndex, TEST_DAY_ONE, TEST_TIME_ONE);

        assertCommandFailure(deleteSessionCommand, model, Messages.MESSAGE_ONLY_STUDENT_COMMAND);
    }

    @Test
    public void execute_nonexistentSession_failure() {
        // 5th person is a Student. However, their session is Monday 12pm-3pm and not Tuesday 12pm-1pm.
        Index studentIndex = Index.fromOneBased(5);
        DeleteSessionCommand deleteSessionCommand = new DeleteSessionCommand(studentIndex, TEST_DAY_ONE, TEST_TIME_ONE);

        assertCommandFailure(deleteSessionCommand, model, DeleteSessionCommand.MESSAGE_NONEXISTENT_SESSION);
    }

    @Test
    public void equals() {
        final DeleteSessionCommand standardCommand = new DeleteSessionCommand(INDEX_FIRST_PERSON,
                TEST_DAY_ONE, TEST_TIME_ONE);

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new DeleteSessionCommand(INDEX_SECOND_PERSON, TEST_DAY_ONE, TEST_TIME_ONE)));

        // same day but different time -> returns false
        assertFalse(standardCommand.equals(new DeleteSessionCommand(
                INDEX_FIRST_PERSON, TEST_DAY_ONE, TEST_TIME_CORRECT)));

        // same time but different day -> returns false
        assertFalse(standardCommand.equals(new DeleteSessionCommand(
                INDEX_FIRST_PERSON, TEST_DAY_CORRECT, TEST_TIME_ONE)));

        // same day and time -> returns true
        assertTrue(standardCommand.equals(new DeleteSessionCommand(
                INDEX_FIRST_PERSON, new Day("Tue"), new Time("12pm-1pm"))));
    }

    @Test
    public void toStringMethod() {
        Index index = Index.fromOneBased(1);
        DeleteSessionCommand deleteSessionCommand = new DeleteSessionCommand(index, TEST_DAY_ONE, TEST_TIME_ONE);
        String expected = DeleteSessionCommand.class.getCanonicalName() + "{person index=" + index + ", day time="
                + TEST_DAY_ONE + " " + TEST_TIME_ONE + "}";
        assertEquals(expected, deleteSessionCommand.toString());
    }
}
