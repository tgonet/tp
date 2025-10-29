package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.FIONA;
import static seedu.address.testutil.TypicalPersons.GEORGE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.RoleContainsKeywordsPredicate;
import seedu.address.model.person.TagContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for
 * {@code FindCommand}.
 */
public class FindCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        NameContainsKeywordsPredicate firstPredicate = new NameContainsKeywordsPredicate(
                Collections.singletonList("first"));
        NameContainsKeywordsPredicate secondPredicate = new NameContainsKeywordsPredicate(
                Collections.singletonList("second"));

        RoleContainsKeywordsPredicate firstRolePredicate = new RoleContainsKeywordsPredicate(
                Collections.singletonList("first"));
        RoleContainsKeywordsPredicate secondRolePredicate = new RoleContainsKeywordsPredicate(
                Collections.singletonList("second"));

        TagContainsKeywordsPredicate firstTagPredicate = new TagContainsKeywordsPredicate(
                Collections.singletonList("first"));
        TagContainsKeywordsPredicate secondTagPredicate = new TagContainsKeywordsPredicate(
                Collections.singletonList("second"));

        FindCommand findFirstNameCommand = new FindCommand(firstPredicate, null, null);
        FindCommand findSecondNameCommand = new FindCommand(secondPredicate, null, null);

        FindCommand findFirstRoleCommand = new FindCommand(null, firstRolePredicate, null);
        FindCommand findSecondRoleCommand = new FindCommand(null, secondRolePredicate, null);

        FindCommand findFirstTagCommand = new FindCommand(null, null, firstTagPredicate);
        FindCommand findSecondTagCommand = new FindCommand(null, null, secondTagPredicate);

        FindCommand findAllFirstCommand = new FindCommand(firstPredicate, firstRolePredicate, firstTagPredicate);
        FindCommand findAllSecondCommand = new FindCommand(secondPredicate, secondRolePredicate, secondTagPredicate);

        // same object -> returns true
        assertTrue(findFirstNameCommand.equals(findFirstNameCommand));
        assertTrue(findFirstRoleCommand.equals(findFirstRoleCommand));
        assertTrue(findFirstTagCommand.equals(findFirstTagCommand));
        assertTrue(findAllFirstCommand.equals(findAllFirstCommand));

        // same values -> returns true
        FindCommand findFirstNameCommandCopy = new FindCommand(firstPredicate, null, null);
        assertTrue(findFirstNameCommand.equals(findFirstNameCommandCopy));

        FindCommand findFirstRoleCommandCopy = new FindCommand(null, firstRolePredicate, null);
        assertTrue(findFirstRoleCommand.equals(findFirstRoleCommandCopy));

        FindCommand findFirstTagCommandCopy = new FindCommand(null, null, firstTagPredicate);
        assertTrue(findFirstTagCommand.equals(findFirstTagCommandCopy));

        FindCommand findAllFirstCommandCopy = new FindCommand(firstPredicate, firstRolePredicate, firstTagPredicate);
        assertTrue(findAllFirstCommand.equals(findAllFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstNameCommand.equals(1));
        assertFalse(findFirstRoleCommand.equals(1));
        assertFalse(findFirstTagCommand.equals(1));
        assertFalse(findAllFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstNameCommand.equals(null));
        assertFalse(findFirstRoleCommand.equals(null));
        assertFalse(findFirstTagCommand.equals(null));
        assertFalse(findAllFirstCommand.equals(null));

        // different values -> returns false
        assertFalse(findFirstNameCommand.equals(findSecondNameCommand));
        assertFalse(findFirstRoleCommand.equals(findSecondRoleCommand));
        assertFalse(findFirstTagCommand.equals(findSecondTagCommand));
        assertFalse(findAllFirstCommand.equals(findAllSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        NameContainsKeywordsPredicate predicate = prepareNamePredicate(" ");
        FindCommand command = new FindCommand(predicate, new RoleContainsKeywordsPredicate(Collections.emptyList()),
                new TagContainsKeywordsPredicate(Collections.emptyList()));
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_multipleNameKeywords_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        NameContainsKeywordsPredicate predicate = prepareNamePredicate("Kurz Elle Kunz");
        FindCommand command = new FindCommand(
            predicate,
            new RoleContainsKeywordsPredicate(Collections.emptyList()),
            new TagContainsKeywordsPredicate(Collections.emptyList())
        );
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARL, ELLE, FIONA), model.getFilteredPersonList());
    }

    @Test
    public void execute_multipleRoleKeywords_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 2);
        RoleContainsKeywordsPredicate predicate = prepareRolePredicate("parent");
        FindCommand command = new FindCommand(
            new NameContainsKeywordsPredicate(Collections.emptyList()),
            predicate,
            new TagContainsKeywordsPredicate(Collections.emptyList())
        );
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(FIONA, GEORGE), model.getFilteredPersonList());
    }

    @Test
    public void execute_multipleTagKeywords_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 2);
        TagContainsKeywordsPredicate predicate = prepareTagPredicate("math");
        FindCommand command = new FindCommand(
            new NameContainsKeywordsPredicate(Collections.emptyList()),
            new RoleContainsKeywordsPredicate(Collections.emptyList()),
            predicate
        );
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE, DANIEL), model.getFilteredPersonList());
    }

    @Test
    public void execute_combinedSearch_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        NameContainsKeywordsPredicate namePredicate = prepareNamePredicate("Benson");
        RoleContainsKeywordsPredicate rolePredicate = prepareRolePredicate("student");
        TagContainsKeywordsPredicate tagPredicate = prepareTagPredicate("science");

        FindCommand command = new FindCommand(namePredicate, rolePredicate, tagPredicate);

        // The combined predicate should match only BENSON
        expectedModel.updateFilteredPersonList(
            person -> namePredicate.test(person)
                   && rolePredicate.test(person)
                   && tagPredicate.test(person)
        );

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.singletonList(BENSON), model.getFilteredPersonList());
    }

    @Test
    public void toStringMethod() {
        NameContainsKeywordsPredicate namePredicate = new NameContainsKeywordsPredicate(Arrays.asList("keyword"));
        RoleContainsKeywordsPredicate rolePredicate = new RoleContainsKeywordsPredicate(Collections.emptyList());
        TagContainsKeywordsPredicate tagPredicate = new TagContainsKeywordsPredicate(Collections.emptyList());

        FindCommand findCommand = new FindCommand(namePredicate, rolePredicate, tagPredicate);

        String expected = FindCommand.class.getCanonicalName()
                + "{name predicate=" + namePredicate
                + ", role predicate=" + rolePredicate
                + ", tag predicate=" + tagPredicate + "}";

        assertEquals(expected, findCommand.toString());
    }

    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    private NameContainsKeywordsPredicate prepareNamePredicate(String userInput) {
        return new NameContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }

    /**
     * Parses {@code userInput} into a {@code RoleContainsKeywordsPredicate}.
     */
    private RoleContainsKeywordsPredicate prepareRolePredicate(String userInput) {
        return new RoleContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }

    /**
     * Parses {@code userInput} into a {@code TagContainsKeywordsPredicate}.
     */
    private TagContainsKeywordsPredicate prepareTagPredicate(String userInput) {
        return new TagContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
