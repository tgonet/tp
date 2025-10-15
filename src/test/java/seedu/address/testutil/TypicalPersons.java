package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMARK_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMARK_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ROLE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ROLE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_SUBJECT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_SUBJECT2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.person.Person;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalPersons {

    public static final Person ALICE = new PersonBuilder().withName("Alice Pauline")
            .withAddress("123, Jurong West Ave 6, #08-111")
            .withPhone("94351253").withRemark("She likes Math.")
            .withRole("student")
            .withTags("math").build();
    public static final Person BENSON = new PersonBuilder().withName("Benson Meier")
            .withAddress("311, Clementi Ave 2, #02-25")
            .withPhone("98765432").withRemark("He likes alcohol.")
            .withRole("student")
            .withTags("science").build();
    public static final Person CARL = new PersonBuilder().withName("Carl Kurz")
            .withPhone("95352563")
            .withAddress("wall street, Singapore 678901")
            .withRole("student").build();
    public static final Person DANIEL = new PersonBuilder().withName("Daniel Meier")
            .withPhone("87652533")
            .withAddress("10th street, Singapore 654321")
            .withRole("student")
            .withTags("math").build();
    public static final Person ELLE = new PersonBuilder().withName("Elle Meyer")
            .withPhone("94822245")
            .withAddress("michegan ave, City Hall, Singapore")
            .withRole("student").build();
    public static final Person FIONA = new PersonBuilder().withName("Fiona Kunz")
            .withPhone("94824276")
            .withAddress("little tokyo, Tanjong Pagar, Singapore")
            .withRole("student").build();
    public static final Person GEORGE = new PersonBuilder().withName("George Best")
            .withPhone("94824421")
            .withAddress("4th street, Fifth Avenue, Singapore")
            .withPhone("98765432").withRemark("He likes to drink alcohol.")
            .withTags("student").build();

    // Manually added
    public static final Person HOON = new PersonBuilder().withName("Hoon Meier")
            .withPhone("84824243")
            .withAddress("little india, Downtown Line, SMRT, Singapore")
            .withRole("parent").build();
    public static final Person IDA = new PersonBuilder().withName("Ida Mueller")
            .withPhone("84821310")
            .withAddress("chicago ave, East West Line, SBS, Singapore")
            .withRole("parent").build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Person AMY = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
            .withAddress(VALID_ADDRESS_AMY).withRole(VALID_ROLE_AMY)
            .withRemark((VALID_REMARK_AMY))
            .withTags(VALID_TAG_SUBJECT).build();
    public static final Person BOB = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
            .withAddress(VALID_ADDRESS_BOB).withRole(VALID_ROLE_BOB)
            .withRemark(VALID_REMARK_BOB)
            .withTags(VALID_TAG_SUBJECT, VALID_TAG_SUBJECT2).build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalPersons() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical persons.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Person person : getTypicalPersons()) {
            ab.addPerson(person);
        }
        return ab;
    }

    public static List<Person> getTypicalPersons() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
