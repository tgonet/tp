package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.session.SessionSlot;

public class ViewSessionCommandTest {

    @Test
    public void execute_emptyModel_returnsNoSessionMessage() throws Exception {
        Model model = new ModelManager(new AddressBook(), new UserPrefs()); // no persons added
        SessionSlot slot = SessionSlot.parse("Tue-[10-11]");

        CommandResult result = new ViewSessionCommand(slot).execute(model);

        assertEquals(ViewSessionCommand.MESSAGE_NO_SESSION, result.getFeedbackToUser());
    }

    @Test
    public void equals_contract() {
        SessionSlot a = SessionSlot.parse("Mon-[3pm-5pm]");
        SessionSlot b = SessionSlot.parse("Mon-[4pm-6pm]");

        ViewSessionCommand c1 = new ViewSessionCommand(a);
        ViewSessionCommand c2 = new ViewSessionCommand(a);
        ViewSessionCommand c3 = new ViewSessionCommand(b);

        assertEquals(c1, c1);
        assertEquals(c1, c2);
        assertNotEquals(c1, c3);
        assertNotEquals(c1, null);
        assertNotEquals(c1, new Object());
    }
}
