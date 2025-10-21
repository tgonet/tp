package seedu.address.testutil;

import seedu.address.model.person.Parent;

/**
 * A utility class to help with building Parent objects.
 */
public class ParentBuilder extends PersonBuilder<ParentBuilder> {

    /**
     * Creates a {@code ParentBuilder} with the default details.
     */
    public ParentBuilder() {
        super();
    }

    /**
     * Initializes the ParentBuilder with the data of {@code parentToCopy}.
     */
    public ParentBuilder(Parent parentToCopy) {
        name = parentToCopy.getName();
        phone = parentToCopy.getPhone();
        address = parentToCopy.getAddress();
        remark = parentToCopy.getRemark();
    }

    @Override
    protected ParentBuilder self() {
        return this;
    }

    @Override
    public Parent build() {
        return new Parent(name, phone, address, remark);
    }

}
