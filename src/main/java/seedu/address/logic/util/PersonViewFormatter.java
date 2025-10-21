package seedu.address.logic.util;

import static java.util.stream.Collectors.joining;

import java.util.Objects;

import seedu.address.model.person.Person;
import seedu.address.model.person.Student;
import seedu.address.model.tag.Tag;

/**
 * Formats a {@link Person} for textual presentation in command feedback.
 * Keeps presentation concerns out of command logic (single level of abstraction).
 */
public final class PersonViewFormatter {
    private PersonViewFormatter() { }

    /**
     * Returns a multi-line, human-readable string of a person's key details,
     * including Remark.
     */
    public static String format(Person p) {
        Objects.requireNonNull(p);

        final String name = p.getName().toString();
        final String phone = p.getPhone().toString();
        final String address = p.getAddress().toString();

        // Role present in your constructor; still guard for null accessors.
        final String role = safeToString(p.getRole());

        // Join tags deterministically for stable tests.
        final String tags;
        if (p instanceof Student s) {
            tags = s.getTags().isEmpty()
                    ? "-"
                    : s.getTags().stream()
                    .map(Tag::toString)
                    .sorted()
                    .collect(joining(", "));
        } else {
            tags = "-";
        }

        // Remark may be optional/null in your model (constructor didn't require it).
        final String remark = normalizeEmpty(safeToString(p.getRemark()));

        StringBuilder sb = new StringBuilder()
                .append("Name: ").append(name).append(System.lineSeparator())
                .append("Phone: ").append(phone).append(System.lineSeparator())
                .append("Address: ").append(address).append(System.lineSeparator())
                .append("Role: ").append(role).append(System.lineSeparator());
        if (p instanceof Student s) {
            sb.append("Tags: ").append(tags).append(System.lineSeparator());
        }
        sb.append("Remark: ").append(remark);
        return sb.toString();
    }

    private static String safeToString(Object o) {
        return o == null ? "-" : o.toString();
    }

    private static String normalizeEmpty(String s) {
        return (s == null || s.isBlank()) ? "-" : s;
    }
}
