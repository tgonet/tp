package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.CARL;
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

        FindCommand findFirstNameCommand = new FindCommand(firstPredicate, null);
        FindCommand findSecondNameCommand = new FindCommand(secondPredicate, null);

        FindCommand findFirstRoleCommand = new FindCommand(null, firstRolePredicate);
        FindCommand findSecondRoleCommand = new FindCommand(null, secondRolePredicate);

        FindCommand findBothFirstCommand = new FindCommand(firstPredicate, firstRolePredicate);
        FindCommand findBothSecondCommand = new FindCommand(secondPredicate, secondRolePredicate);

        // same object -> returns true
        assertTrue(findFirstNameCommand.equals(findFirstNameCommand));
        assertTrue(findFirstRoleCommand.equals(findFirstRoleCommand));
        assertTrue(findBothFirstCommand.equals(findBothFirstCommand));

        // same values -> returns true
        FindCommand findFirstNameCommandCopy = new FindCommand(firstPredicate, null);
        assertTrue(findFirstNameCommand.equals(findFirstNameCommandCopy));

        FindCommand findFirstRoleCommandCopy = new FindCommand(null, firstRolePredicate);
        assertTrue(findFirstRoleCommand.equals(findFirstRoleCommandCopy));

        FindCommand findBothFirstCommandCopy = new FindCommand(firstPredicate, firstRolePredicate);
        assertTrue(findBothFirstCommand.equals(findBothFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstNameCommand.equals(1));
        assertFalse(findFirstRoleCommand.equals(1));
        assertFalse(findBothFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstNameCommand.equals(null));
        assertFalse(findFirstRoleCommand.equals(null));
        assertFalse(findBothFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(findFirstNameCommand.equals(findSecondNameCommand));
        assertFalse(findFirstRoleCommand.equals(findSecondRoleCommand));
        assertFalse(findBothFirstCommand.equals(findBothSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        NameContainsKeywordsPredicate predicate = prepareNamePredicate(" ");
        FindCommand command = new FindCommand(predicate, null);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_multipleNameKeywords_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        NameContainsKeywordsPredicate predicate = prepareNamePredicate("Kurz Elle Kunz");
        FindCommand command = new FindCommand(predicate, null);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARL, ELLE, FIONA), model.getFilteredPersonList());
    }

    @Test
    public void execute_multipleRoleKeywords_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 2);
        RoleContainsKeywordsPredicate predicate = prepareRolePredicate("parent");
        FindCommand command = new FindCommand(null, predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(FIONA, GEORGE), model.getFilteredPersonList());
    }

    @Test
    public void toStringMethod() {
        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate(Arrays.asList("keyword"));
        FindCommand findCommand = new FindCommand(predicate, null);
        String expected = FindCommand.class.getCanonicalName() + "{name predicate=" + predicate
                + ", role predicate=null}";
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
}
