package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;


/**
 * Represents a predicate (boolean-valued function) that determines if a
 * {@code Person} has tags containing any of the keywords given.
 */
public class TagContainsKeywordsPredicate implements Predicate<Person> {
    private final List<String> keywords;

    public TagContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    /**
     * Returns true if the keywords list is empty.
     * @return true if the keywords list is empty, false otherwise.
     */
    public boolean isEmpty() {
        return this.keywords.isEmpty();
    }

    /**
     * Tests if a {@code Person}'s tags contain any of the keywords given.
     *
     * @param person the person to test
     * @return true if the person's tags contain any of the keywords, false otherwise
     */
    @Override
    public boolean test(Person person) {
        if (!(person instanceof Student student)) {
            return false;
        }

        return keywords.stream()
                .anyMatch(keyword -> student.getTags().stream()
                        .anyMatch(tag -> StringUtil.containsWordIgnoreCase(tag.tagName, keyword)));
    }

    /**
     * Returns true if the other is a {@code TagContainsKeywordsPredicate} that represents the same testing logic as
     *     this object.
     *
     * @param other the object to test
     * @return true if the other object is a {@code TagContainsKeywordsPredicate} that represents the same testing logic
     *     as this object, false otherwise
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof TagContainsKeywordsPredicate)) {
            return false;
        }

        TagContainsKeywordsPredicate otherTagContainsKeywordsPredicate = (TagContainsKeywordsPredicate) other;
        return keywords.equals(otherTagContainsKeywordsPredicate.keywords);
    }

    /**
     * Returns a string representation of this {@code TagContainsKeywordsPredicate}.
     * The string representation is in the format of "TagContainsKeywordsPredicate{keywords=[...]}",
     * where the keywords are separated by spaces.
     *
     * @return a string representation of this {@code TagContainsKeywordsPredicate}.
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
