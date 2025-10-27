package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;

import seedu.address.model.Model;
import seedu.address.model.person.Day;
import seedu.address.model.person.Person;
import seedu.address.model.person.Session;
import seedu.address.model.person.Student;
import seedu.address.model.person.Time;
import seedu.address.model.session.SessionSlot;

/**
 * List students with at least one session on given day; order by earliest start on that day.
 */
public class ViewSessionCommand extends Command {
    /** Kept for historical callers. */
    public static final String COMMAND_WORD = "viewSession";
    /** Lowercase alias used by AddressBookParser. */
    public static final String COMMAND_WORD_LOWER = "viewsession";

    public static final String MESSAGE_USAGE = COMMAND_WORD_LOWER + ": List all sessions on a day; earliest first.\n"
            + "Parameters: d/DAY\n"
            + "Example: " + COMMAND_WORD_LOWER + " d/Tuesday";

    private final DayOfWeek day;

    /**
     * Build command targeting one weekday.
     * @param day weekday; not null
     */
    public ViewSessionCommand(DayOfWeek day) {
        requireNonNull(day);
        this.day = day;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(this::hasSessionOnDay);
        model.sortFilteredPersonList(byEarliestStartOnDay());
        return new CommandResult(String.format("showing sessions on %s", day));
    }

    /**
     * Check if person has any session on target day.
     * Accepts both single-slot path and multi-session path in Student.
     * @param p person
     * @return true if at least one match
     */
    private boolean hasSessionOnDay(Person p) {
        // legacy single-slot path (if used elsewhere)
        if (p.getSessionSlot().map(s -> sameDay(s.getDay(), day)).orElse(false)) {
            return true;
        }

        // multi-session path used by addsession
        if (p instanceof Student s) {
            Set<Session> sessions = s.getSessions();
            if (sessions == null || sessions.isEmpty()) {
                return false;
            }
            for (Session sess : sessions) {
                if (sess == null) {
                    continue;
                }
                Day d = sess.getDay();
                if (d != null && sameDay(d.getValue(), day)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Comparator: earliest start time on target day, then name.
     * Persons without time info sink using sentinel.
     * @return comparator
     */
    private Comparator<Person> byEarliestStartOnDay() {
        return Comparator
                .comparing(this::earliestStartOnDayOrMax)
                .thenComparing(p -> p.getName().fullName, String.CASE_INSENSITIVE_ORDER);
    }

    /**
     * Compute earliest start on target day for person or MAX sentinel.
     * @param p person
     * @return start time or 23:59:59
     */
    private LocalTime earliestStartOnDayOrMax(Person p) {
        Optional<LocalTime> legacy = p.getSessionSlot()
                .filter(s -> sameDay(s.getDay(), day))
                .map(SessionSlot::getStart);
        if (legacy.isPresent()) {
            return legacy.get();
        }

        if (p instanceof Student s) {
            LocalTime best = null;
            for (Session sess : s.getSessions()) {
                if (sess == null) {
                    continue;
                }
                Day d = sess.getDay();
                if (d == null || !sameDay(d.getValue(), day)) {
                    continue;
                }
                Time t = sess.getTime();
                LocalTime start = parseStart(t == null ? null : t.getValue()).orElse(null);
                if (start != null && (best == null || start.isBefore(best))) {
                    best = start;
                }
            }
            if (best != null) {
                return best;
            }
        }
        return SessionSlot.MAX_TIME;
    }

    /**
     * Map stored day token to target day; supports enum or string like "Mon" / "Monday".
     * @param stored stored day token
     * @param target target enum
     * @return equality under 3-letter canonical form
     */
    private static boolean sameDay(Object stored, DayOfWeek target) {
        if (stored == null || target == null) {
            return false;
        }
        String a = head3(stored instanceof DayOfWeek ? ((DayOfWeek) stored).toString() : stored.toString());
        String b = head3(target.toString());
        return a.equals(b);
    }

    /**
     * Parse start time from "TIME" value used by addsession, e.g. "12pm-2pm", "9:30am-11am", "14:00-15:00".
     * @param value time range string
     * @return start LocalTime if parseable
     */
    private static Optional<LocalTime> parseStart(String value) {
        if (value == null) {
            return Optional.empty();
        }
        String s = value.trim().toLowerCase(Locale.ROOT);
        // split by '-', en dash, em dash
        String[] parts = s.split("\\s*[-–—]\\s*", 2);
        if (parts.length == 0) {
            return Optional.empty();
        }
        return parseClock(parts[0]);
    }

    /**
     * Parse single clock token like "12pm", "9:30am", "14:05".
     * @param token clock text
     * @return LocalTime if valid
     */
    private static Optional<LocalTime> parseClock(String token) {
        if (token == null) {
            return Optional.empty();
        }
        String t = token.trim().toLowerCase(Locale.ROOT);
        // hh[:mm][am|pm]
        java.util.regex.Matcher m = java.util.regex.Pattern
                .compile("^(\\d{1,2})(?::(\\d{2}))?\\s*(am|pm)?$")
                .matcher(t);
        if (!m.matches()) {
            return Optional.empty();
        }
        try {
            int h = Integer.parseInt(m.group(1));
            int min = m.group(2) == null ? 0 : Integer.parseInt(m.group(2));
            String ap = m.group(3);
            if (ap != null) {
                if (ap.equals("am")) {
                    if (h == 12) {
                        h = 0;
                    }
                } else if (ap.equals("pm")) {
                    if (h != 12) {
                        h += 12;
                    }
                }
            }
            if (h == 24) {
                h = 0;
            }
            return Optional.of(LocalTime.of(h, min));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    /**
     * Canonical 3-letter day code in uppercase.
     * @param s input
     * @return first 3 letters uppercased
     */
    private static String head3(String s) {
        String u = s == null ? "" : s.trim().toUpperCase(Locale.ROOT);
        return u.length() >= 3 ? u.substring(0, 3) : u;
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof ViewSessionCommand o && day.equals(o.day));
    }

    @Override
    public String toString() {
        return COMMAND_WORD_LOWER + " d/" + day;
    }
}
