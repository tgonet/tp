package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.person.Address;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Remark;
import seedu.address.model.person.Role;

public class ViewCommandTest {

    private Model model;
    private Person person;

    @BeforeEach
    public void setUp() {
        person = new Person(
                new Name("Alice Tan"),
                new Phone("91234567"),
                new Address("123 Kent Ridge Drive"),
                new Role("Trainer"),
                new Remark("Prefers morning sessions"),
                Collections.emptySet());
        model = new ModelManager();
        model.addPerson(person);
    }

    @Test
    public void execute_validIndex_success() throws Exception {
        ViewCommand command = new ViewCommand(Index.fromOneBased(1));
        CommandResult result = command.execute(model);
        String output = result.getFeedbackToUser();
        assertTrue(output.contains("Alice Tan"));
        assertTrue(output.contains("Remark: Prefers morning sessions"));
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        ViewCommand command = new ViewCommand(Index.fromOneBased(5));
        assertThrows(CommandException.class, () -> command.execute(model));
    }

    @Test
    public void equals_sameIndex_true() {
        ViewCommand a = new ViewCommand(Index.fromOneBased(1));
        ViewCommand b = new ViewCommand(Index.fromOneBased(1));
        assertTrue(a.equals(b));
    }
}
