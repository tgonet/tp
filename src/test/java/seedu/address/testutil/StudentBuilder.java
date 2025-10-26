package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.person.Name;
import seedu.address.model.person.Session;
import seedu.address.model.person.Student;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Student objects.
 */
public class StudentBuilder extends PersonBuilder<StudentBuilder> {

    private Set<Tag> tags;
    private Set<Session> sessions;
    private Name parentName;

    /**
     * Creates a {@code StudentBuilder} with the default details.
     */
    public StudentBuilder() {
        super();
        tags = new HashSet<>();
        sessions = new HashSet<>();
    }

    /**
     * Initializes the PersonBuilder with the data of {@code studentToCopy}.
     */
    public StudentBuilder(Student studentToCopy) {
        name = studentToCopy.getName();
        phone = studentToCopy.getPhone();
        address = studentToCopy.getAddress();
        remark = studentToCopy.getRemark();
        tags = new HashSet<>(studentToCopy.getTags());
        sessions = new HashSet<>(studentToCopy.getSessions());
        parentName = studentToCopy.getParentName();
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Student} that we are building.
     */
    public StudentBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Student} that we are building.
     */
    public StudentBuilder withSessions(Session ... sessions) {
        this.sessions = SampleDataUtil.getSessionSet(sessions);
        return this;
    }

    /**
     * Sets the {@code ParentName} of the {@code Student} that we are building.
     */
    public StudentBuilder withParentName(String parentName) {
        this.parentName = new Name(parentName);
        return this;
    }

    @Override
    protected StudentBuilder self() {
        return this;
    }

    @Override
    public Student build() {
        return new Student(name, phone, address, remark, tags, sessions, parentName);
    }

}
