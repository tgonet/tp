package seedu.address.logic.util;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Address;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Remark;
import seedu.address.model.person.Role;

public class PersonViewFormatterTest {

    @Test
    public void format_allFieldsPresent_includesRemark() {
        Person p = new Person(
                new Name("Alice"),
                new Phone("91234567"),
                new Address("Kent Ridge"),
                new Role("Trainer"),
                new Remark("Morning only"),
                Collections.emptySet());
        String formatted = PersonViewFormatter.format(p);
        assertTrue(formatted.contains("Name: Alice"));
        assertTrue(formatted.contains("Remark: Morning only"));
    }

    @Test
    public void format_nullRemark_replacesWithDash() {
        Person p = new Person(
                new Name("Bob"),
                new Phone("98765432"),
                new Address("Clementi"),
                new Role("Member"),
                null,
                Collections.emptySet());
        String formatted = PersonViewFormatter.format(p);
        assertTrue(formatted.contains("Remark: -"));
    }
}
