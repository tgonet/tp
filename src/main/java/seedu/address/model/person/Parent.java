package seedu.address.model.person;

import seedu.address.model.tag.Tag;

import java.util.Set;

public class Parent extends Person {

    /**
     * Every field must be present and not null.
     *
     * @param name
     * @param phone
     * @param address
     * @param remark
     * @param tags
     */
    public Parent(Name name, Phone phone, Address address, Remark remark, Set<Tag> tags) {
        super(name, phone, address, remark, tags);
    }
}
