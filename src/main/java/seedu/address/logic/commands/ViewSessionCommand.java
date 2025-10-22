package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.session.SessionSlot;

/**
 * Displays the person(s) who have a session exactly at the given weekly slot.
 * Matching logic mirrors the last working version:
 *  - typed session field if present;
 *  - common tag/label collections via reflection;
 *  - fallback to Person#toString() containment.
 * Output is formatted as a clean, multi-line block.
 */
public class ViewSessionCommand extends Command {

    /** Backward-compatible camelCase command word accepted by the top-level parser. */
    public static final String COMMAND_WORD = "viewSession";
    /** Lower-case alias also accepted by the top-level parser. */
    public static final String COMMAND_WORD_LOWER = "viewsession";

    public static final String MESSAGE_USAGE =
            COMMAND_WORD + " / " + COMMAND_WORD_LOWER
                    + ": Displays the person(s) who have a session at the given slot.\n"
                    + "Format: " + COMMAND_WORD_LOWER + " DAY-[START-END]\n"
                    + "Days: Mon|Tue|Wed|Thu|Fri|Sat|Sun\n"
                    + "Times: H[:MM][am|pm] (e.g., 3pm, 3:30pm) or 24h HH[:MM] (e.g., 15, 15:30)\n"
                    + "Examples:\n"
                    + "  " + COMMAND_WORD_LOWER + " Mon-[5pm-6pm]\n"
                    + "  " + COMMAND_WORD_LOWER + " Tue-[15:00-17:00]";

    public static final String MESSAGE_NO_SESSION = "No session exists at the specified slot.";
    public static final String MESSAGE_RESULT_HEADER = "Session %s:";

    private final SessionSlot slot;

    public ViewSessionCommand(SessionSlot slot) {
        this.slot = requireNonNull(slot);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        final String normalized = slot.toString(); // e.g. Mon-[17:00-18:00]
        final String userLike = slot.toUserFormat(); // e.g. Mon-[5pm-6pm]
        final String userLikeLower = userLike.toLowerCase(Locale.ROOT);

        // --- MATCHING: unchanged from the version that worked for you ---
        List<Person> matches = model.getFilteredPersonList().stream()
                .filter(p -> hasTypedSession(p)
                        || hasLabeledSession(p, normalized, userLikeLower)
                        || hasStringSession(p, normalized, userLikeLower))
                .collect(Collectors.toList());
        // ----------------------------------------------------------------

        if (matches.isEmpty()) {
            return new CommandResult(MESSAGE_NO_SESSION);
        }

        // Pretty rendering only; does not affect matching.
        String listing = matches.stream()
                .map(ViewSessionCommand::formatPersonBlock)
                .collect(Collectors.joining("\n\n"));

        String message = String.format(MESSAGE_RESULT_HEADER, userLike) + "\n\n" + listing;
        return new CommandResult(message);
    }

    // ---- matching helpers (unchanged) ----
    private boolean hasTypedSession(Person p) {
        return p.getSessionSlot().isPresent() && p.getSessionSlot().get().equals(slot);
    }

    private boolean hasLabeledSession(Person p, String normalized, String userLikeLower) {
        // Probe common collection-style accessors without a compile-time dependency.
        final String[] candidates = new String[] {
            "getTags", "getTagList", "getLabels", "getLabelList",
            "getSubjects", "getSubjectList", "getSessions", "getSessionList"
        };
        for (String name : candidates) {
            try {
                Method m = p.getClass().getMethod(name);
                Object value = m.invoke(p);
                if (value instanceof Iterable<?>) {
                    for (Object elem : (Iterable<?>) value) {
                        String s = String.valueOf(elem);
                        if (s.equalsIgnoreCase(normalized)
                                || s.toLowerCase(Locale.ROOT).equals(userLikeLower)) {
                            return true;
                        }
                        String lower = s.toLowerCase(Locale.ROOT);
                        if (lower.contains(userLikeLower) || s.contains(normalized)) {
                            return true;
                        }
                    }
                }
            } catch (ReflectiveOperationException ignored) {
                // try next accessor name
            }
        }
        return false;
    }

    private boolean hasStringSession(Person p, String normalized, String userLikeLower) {
        String s = p.toString();
        return s.contains(normalized) || s.toLowerCase(Locale.ROOT).contains(userLikeLower);
    }
    // --------------------------------------

    /** Formats a single person in a clean, multi-line block. */
    private static String formatPersonBlock(Person p) {
        String tags = String.join(", ", toStrings(extractLabelLikeIterable(p)));
        StringBuilder sb = new StringBuilder();
        sb.append("Name: ").append(p.getName()).append("\n")
                .append("Phone: ").append(p.getPhone()).append("\n")
                .append("Address: ").append(p.getAddress()).append("\n")
                .append("Role: ").append(p.getRole()).append("\n")
                .append("Tags: ").append(tags.isEmpty() ? "-" : tags);
        return sb.toString();
    }

    /** Best-effort extraction of tag/label collections for display purposes. */
    private static Iterable<?> extractLabelLikeIterable(Person p) {
        final String[] candidates = new String[] {
            "getTags", "getTagList", "getLabels", "getLabelList",
            "getSubjects", "getSubjectList", "getSessions", "getSessionList"
        };
        for (String name : candidates) {
            try {
                Method m = p.getClass().getMethod(name);
                Object value = m.invoke(p);
                if (value instanceof Iterable<?>) {
                    return (Iterable<?>) value;
                }
            } catch (ReflectiveOperationException ignored) {
                // continue
            }
        }
        return List.of();
    }

    private static List<String> toStrings(Iterable<?> it) {
        List<String> out = new ArrayList<>();
        for (Object o : it) {
            out.add(String.valueOf(o));
        }
        return out;
    }

    @Override
    public boolean equals(Object o) {
        return o == this
                || (o instanceof ViewSessionCommand
                && slot.equals(((ViewSessionCommand) o).slot));
    }
}
