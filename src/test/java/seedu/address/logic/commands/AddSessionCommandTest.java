package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

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
import seedu.address.model.person.Person;
import seedu.address.model.person.Session;
import seedu.address.model.person.Student;
import seedu.address.model.person.Time;
import seedu.address.testutil.ParentBuilder;
import seedu.address.testutil.StudentBuilder;

/**
 * Unit tests for AddSessionCommand.
 */
public class AddSessionCommandTest {

    @Test
    public void execute_addSessionToStudent_success() throws Exception {
        Student student = new StudentBuilder().build();
        AddressBook ab = new AddressBook();
        ab.addPerson(student);
        Model model = new ModelManager(ab, new UserPrefs());

        Index index = Index.fromOneBased(1);
        Day day = new Day("Mon");
        Time time = new Time("12pm-1pm");
        AddSessionCommand command = new AddSessionCommand(index, day, time);

        // expected updated student
        Session newSession = new Session(day, time);
        Set<Session> updatedSessions = new HashSet<>(student.getSessions());
        updatedSessions.add(newSession);
        Person updatedStudent = new Student(
                student.getName(),
                student.getPhone(),
                student.getAddress(),
                student.getRemark(),
                student.getTags(),
                updatedSessions
        );

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(student, updatedStudent);
        String expectedMessage = String.format(AddSessionCommand.MESSAGE_SUCCESS, student.getName());

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateSession_throwsCommandException() throws Exception {
        Student student = new StudentBuilder().build();
        AddressBook ab = new AddressBook();
        ab.addPerson(student);
        Model model = new ModelManager(ab, new UserPrefs());

        Index index = Index.fromOneBased(1);
        Day day = new Day("Mon");
        Time time = new Time("12pm-1pm");
        AddSessionCommand firstAdd = new AddSessionCommand(index, day, time);
        // add once - should succeed
        firstAdd.execute(model);

        // adding same session again should fail with duplicate message
        AddSessionCommand secondAdd = new AddSessionCommand(index, day, time);
        assertCommandFailure(secondAdd, model, AddSessionCommand.MESSAGE_DUPLICATE_SESSION);
    }

    @Test
    public void execute_onParent_throwsCommandException() {
        Person parent = new ParentBuilder().build();
        AddressBook ab = new AddressBook();
        ab.addPerson(parent);
        Model model = new ModelManager(ab, new UserPrefs());

        Index index = Index.fromOneBased(1);
        Day day = new Day("Tue");
        Time time = new Time("1pm-3pm");
        AddSessionCommand command = new AddSessionCommand(index, day, time);

        assertCommandFailure(command, model, Messages.MESSAGE_ONLY_STUDENT_COMMAND);
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        Student student = new StudentBuilder().build();
        AddressBook ab = new AddressBook();
        ab.addPerson(student);
        Model model = new ModelManager(ab, new UserPrefs());

        Index outOfBoundsIndex = Index.fromOneBased(2); // only 1 person in model
        Day day = new Day("Wed");
        Time time = new Time("2pm-4pm");
        AddSessionCommand command = new AddSessionCommand(outOfBoundsIndex, day, time);

        assertCommandFailure(command, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        Index index1 = Index.fromOneBased(1);
        Index index2 = Index.fromOneBased(2);
        Day day = new Day("Mon");
        Time time = new Time("12pm-1pm");

        AddSessionCommand cmd1 = new AddSessionCommand(index1, day, time);
        AddSessionCommand cmd2 = new AddSessionCommand(index2, day, time);

        assertFalse(cmd1.equals(cmd2));

        // same object -> true
        assertTrue(cmd1.equals(cmd1));

        // null -> false
        assertFalse(cmd1.equals(null));

        // different type -> false
        assertFalse(cmd1.equals("not a command"));

        // different day -> false
        AddSessionCommand cmdDifferentDay = new AddSessionCommand(index1, new Day("Tue"), time);
        assertFalse(cmd1.equals(cmdDifferentDay));

        // different time -> false
        AddSessionCommand cmdDifferentTime = new AddSessionCommand(index1, day, new Time("1pm-4pm"));
        assertFalse(cmd1.equals(cmdDifferentTime));
    }

    @Test
    public void toStringMethod() {
        Index index = Index.fromOneBased(1);
        Day day = new Day("Mon");
        Time time = new Time("12pm-1pm");
        AddSessionCommand cmd = new AddSessionCommand(index, day, time);

        String expected = AddSessionCommand.class.getCanonicalName() + "{day time=" + day + " " + time + "}";
        assertEquals(expected, cmd.toString());
    }
}
