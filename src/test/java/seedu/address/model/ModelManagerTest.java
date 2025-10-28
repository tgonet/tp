package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.nio.file.Path;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.GuiSettings;

/**
 * Tests for {@link ModelManager} value semantics.
 * Equality is defined over address book data and user prefs; transient filtered/sorted
 * state is not part of equality.
 */
public class ModelManagerTest {

    /**
     * Equality should hold for identical data and prefs, independent of filtered/sorted view.
     */
    @Test
    public void equals() {
        AddressBook ab1 = new AddressBook();
        AddressBook ab2 = new AddressBook();
        UserPrefs prefs1 = new UserPrefs();
        UserPrefs prefs2 = new UserPrefs();

        ModelManager m1 = new ModelManager(ab1, prefs1);
        ModelManager m2 = new ModelManager(ab2, prefs2);

        assertEquals(m1, m2);

        // tweak prefs; equality should now fail
        UserPrefs prefs3 = new UserPrefs();
        prefs3.setGuiSettings(new GuiSettings(600, 600, 10, 10));
        ModelManager m3 = new ModelManager(ab1, prefs3);
        assertNotEquals(m1, m3);
    }

    /**
     * Basic pass-throughs for prefs getters remain stable.
     */
    @Test
    public void prefs_passthrough_ok() {
        UserPrefs p = new UserPrefs();
        p.setGuiSettings(new GuiSettings(300, 400, 5, 5));
        p.setAddressBookFilePath(Path.of("data", "test.json"));
        ModelManager m = new ModelManager(new AddressBook(), p);

        assertEquals(p.getGuiSettings(), m.getGuiSettings());
        assertEquals(p.getAddressBookFilePath(), m.getAddressBookFilePath());
    }
}
