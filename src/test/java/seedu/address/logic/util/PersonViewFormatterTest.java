package seedu.address.logic.util;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class PersonViewFormatterTest {

    @Test
    public void format_allFieldsPresent_includesRemark() {
        Person p = new PersonBuilder()
                .withRemark("Prefers morning sessions")
                .build();
        String formatted = PersonViewFormatter.format(p);
        assertTrue(formatted.contains("Name: "));
        assertTrue(formatted.contains("Remark: Prefers morning sessions"));
    }

    @Test
    public void format_emptyRemark_replacesWithDash() {
        Person p = new PersonBuilder()
                .withRemark("") // not null; empty allowed by model
                .build();
        String formatted = PersonViewFormatter.format(p);
        assertTrue(formatted.contains("Remark: -"));
    }
}
