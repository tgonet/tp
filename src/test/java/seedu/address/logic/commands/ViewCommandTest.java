package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.util.SampleDataUtil;

public class ViewCommandTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        // Prefer project’s typical/sample utilities to satisfy all invariants
        model = new ModelManager(SampleDataUtil.getSampleAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_validIndex_success() throws Exception {
        ViewCommand cmd = new ViewCommand(Index.fromOneBased(1));
        CommandResult res = cmd.execute(model);
        String out = res.getFeedbackToUser();
        assertTrue(out.contains("Name: "));
        assertTrue(out.contains("Phone: "));
        assertTrue(out.contains("Address: "));
        assertTrue(out.contains("Role: "));
        assertTrue(out.contains("Remark: "));
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        int outOfRange = model.getFilteredPersonList().size() + 1;
        ViewCommand cmd = new ViewCommand(Index.fromOneBased(outOfRange));
        assertThrows(CommandException.class, () -> cmd.execute(model));
    }

    @Test
    public void equals_sameIndex_true() {
        ViewCommand a = new ViewCommand(Index.fromOneBased(1));
        ViewCommand b = new ViewCommand(Index.fromOneBased(1));
        assertTrue(a.equals(b));
    }
}
