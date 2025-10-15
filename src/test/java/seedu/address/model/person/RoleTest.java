package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class RoleTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Role(null));
    }

    @Test
    public void constructor_invalidPhone_throwsIllegalArgumentException() {
        String invalidRole = "";
        assertThrows(IllegalArgumentException.class, () -> new Role(invalidRole));
    }

    @Test
    public void isValidRole_validRoleStudent_returnsTrue() {
        String validRole = "student";
        assertTrue(Role.isValidRole(validRole));
    }

    @Test
    public void isValidRole_validRoleParent_returnsTrue() {
        String validRole = "parent";
        assertTrue(Role.isValidRole(validRole));
    }

    @Test
    public void isValidRole_invalidRole_returnsFalse() {
        String validRole = "friend";
        assertFalse(Role.isValidRole(validRole));
    }

}
