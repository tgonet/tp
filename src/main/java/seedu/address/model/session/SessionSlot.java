package seedu.address.model.session;

import static java.util.Objects.requireNonNull;

import java.io.Serializable;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;
import java.util.Objects;

/**
 * Immutable value object representing a weekly session slot, e.g. {@code Mon-[15:00-17:00]}.
 * <p>
 * Times are normalized to 24-hour {@code HH:mm}. Parsing accepts:
 * <ul>
 *   <li>Day: Mon|Tue|Wed|Thu|Fri|Sat|Sun (case-insensitive)</li>
 *   <li>Times: {@code H[:MM][am|pm]} (e.g. 3pm, 3:30pm) or 24h {@code HH[:MM]} (e.g. 15, 15:30)</li>
 * </ul>
 */
public final class SessionSlot implements Serializable {

    private static final DateTimeFormatter OUTPUT_FMT = DateTimeFormatter.ofPattern("HH:mm");
    private static final DateTimeFormatter HM_12 = DateTimeFormatter.ofPattern("h:mma", Locale.ROOT);

    /**
     * Enumeration of days in a week.
     */
    public enum Day {
        MON, TUE, WED, THU, FRI, SAT, SUN;

        /**
         * Parses a 3-letter day string (e.g. "mon") into a {@link Day}.
         *
         * @param s input string
         * @return parsed day
         * @throws IllegalArgumentException if the string does not match a supported day
         */
        public static Day parse(String s) {
            requireNonNull(s);
            String t = s.trim().toLowerCase(Locale.ROOT);
            switch (t) {
            case "mon":
                return MON;
            case "tue":
                return TUE;
            case "wed":
                return WED;
            case "thu":
                return THU;
            case "fri":
                return FRI;
            case "sat":
                return SAT;
            case "sun":
                return SUN;
            default:
                throw new IllegalArgumentException("Unknown day: " + s);
            }
        }

        @Override
        public String toString() {
            switch (this) {
            case MON:
                return "Mon";
            case TUE:
                return "Tue";
            case WED:
                return "Wed";
            case THU:
                return "Thu";
            case FRI:
                return "Fri";
            case SAT:
                return "Sat";
            case SUN:
                return "Sun";
            default:
                throw new AssertionError(this);
            }
        }
    }

    /**
     * Inclusive-exclusive time range ({@code [start, end)}) within a day.
     */
    public static final class TimeRange implements Serializable {
        public final LocalTime start;
        public final LocalTime end;

        private TimeRange(LocalTime start, LocalTime end) {
            this.start = start;
            this.end = end;
            if (!end.isAfter(start)) {
                throw new IllegalArgumentException("End time must be after start time");
            }
        }

        /**
         * Creates a new {@code TimeRange}.
         *
         * @param start start time inclusive
         * @param end end time exclusive
         * @return a validated {@code TimeRange}
         * @throws IllegalArgumentException if {@code end} is not after {@code start}
         */
        public static TimeRange of(LocalTime start, LocalTime end) {
            return new TimeRange(start, end);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof TimeRange)) {
                return false;
            }
            TimeRange that = (TimeRange) o;
            return start.equals(that.start) && end.equals(that.end);
        }

        @Override
        public int hashCode() {
            return Objects.hash(start, end);
        }

        @Override
        public String toString() {
            return OUTPUT_FMT.format(start) + "-" + OUTPUT_FMT.format(end);
        }
    }

    public final Day day;
    public final TimeRange time;

    /**
     * Constructs a {@code SessionSlot}.
     *
     * @param day day of week
     * @param time time range (must have {@code end} after {@code start})
     */
    public SessionSlot(Day day, TimeRange time) {
        this.day = requireNonNull(day);
        this.time = requireNonNull(time);
    }

    /**
     * Parses strings like {@code Mon-[3pm-5pm]} or {@code Tue-[15:00-17:00]}, case-insensitive.
     *
     * @param raw user input
     * @return parsed {@code SessionSlot}
     * @throws IllegalArgumentException if the format is invalid
     */
    public static SessionSlot parse(String raw) {
        requireNonNull(raw);
        String s = raw.trim();

        if (!s.matches("(?i)^(Mon|Tue|Wed|Thu|Fri|Sat|Sun)\\s*-\\s*\\[.+\\]$")) {
            throw new IllegalArgumentException("Invalid session format");
        }

        int dash = s.indexOf('-');
        String dayStr = s.substring(0, dash).trim();
        String bracket = s.substring(s.indexOf('[') + 1, s.lastIndexOf(']')).trim();
        String[] parts = bracket.split("\\s*-\\s*");

        if (parts.length != 2) {
            throw new IllegalArgumentException("Invalid time range");
        }

        LocalTime start = parseTime(parts[0]);
        LocalTime end = parseTime(parts[1]);
        return new SessionSlot(Day.parse(dayStr), TimeRange.of(start, end));
    }

    /**
     * Returns a user-like 12-hour representation, e.g. {@code Mon-[5pm-6pm]}.
     * Minutes {@code :00} are omitted.
     */
    public String toUserFormat() {
        return day + "-[" + fmt12(time.start) + "-" + fmt12(time.end) + "]";
    }

    private static String fmt12(LocalTime t) {
        String s = HM_12.format(t).toLowerCase(Locale.ROOT); // e.g. 5:00pm or 5:30pm
        return s.replace(":00", ""); // drop :00 -> 5pm
    }

    /**
     * Parses a time token into {@link LocalTime}.
     * Accepted forms: {@code H}, {@code H:MM}, {@code H[(:)MM](am|pm)}.
     *
     * @param t time token
     * @return parsed time
     * @throws IllegalArgumentException if not parsable
     */
    private static LocalTime parseTime(String t) {
        String x = t.trim().toLowerCase(Locale.ROOT);
        try {
            if (x.endsWith("am") || x.endsWith("pm")) {
                boolean pm = x.endsWith("pm");
                String body = x.substring(0, x.length() - 2);
                String[] hm = body.split(":");
                int h = Integer.parseInt(hm[0]);
                int m = hm.length == 2 ? Integer.parseInt(hm[1]) : 0;

                if (h == 12) {
                    h = 0;
                }
                if (pm) {
                    h += 12;
                }
                return LocalTime.of(h, m);
            }

            if (x.matches("^\\d{1,2}:\\d{2}$")) {
                return LocalTime.parse(x);
            }

            if (x.matches("^\\d{1,2}$")) {
                return LocalTime.of(Integer.parseInt(x), 0);
            }
        } catch (DateTimeParseException | NumberFormatException e) {
            throw new IllegalArgumentException("Invalid time: " + t);
        }

        throw new IllegalArgumentException("Invalid time: " + t);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SessionSlot)) {
            return false;
        }
        SessionSlot that = (SessionSlot) o;
        return day == that.day && time.equals(that.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(day, time);
    }

    @Override
    public String toString() {
        return day + "-[" + time + "]";
    }
}
