package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.person.Address;
import seedu.address.model.person.Name;
import seedu.address.model.person.Parent;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Remark;
import seedu.address.model.person.Student;
import seedu.address.model.session.SessionSlot;
import seedu.address.model.session.SessionSlot.Day;
import seedu.address.model.session.SessionSlot.TimeRange;
import seedu.address.testutil.TypicalPersons;

/**
 * Execution coverage for {@link ViewSessionCommand}.
 * Uses a focused Model stub and tolerant reflective construction so
 * the tests work across branches with slightly different constructors.
 */
public class ViewSessionCommandExecuteTest {

    // -------------------------------------------------------------------------
    // Minimal Model stub – only what ViewSessionCommand.execute() touches
    // -------------------------------------------------------------------------

    private static final class ModelStub implements Model {
        private final ObservableList<Person> list;

        ModelStub(List<Person> persons) {
            this.list = FXCollections.observableArrayList(persons);
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            return list;
        }

        // Everything below is unused by these tests.
        @Override public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw u();
        }

        @Override public ReadOnlyUserPrefs getUserPrefs() {
            throw u();
        }

        @Override public void setGuiSettings(GuiSettings guiSettings) {
            throw u();
        }

        @Override public GuiSettings getGuiSettings() {
            throw u();
        }

        @Override public void setAddressBookFilePath(java.nio.file.Path path) {
            throw u();
        }

        @Override public java.nio.file.Path getAddressBookFilePath() {
            throw u();
        }

        @Override public void setAddressBook(ReadOnlyAddressBook addressBook) {
            throw u();
        }

        @Override public ReadOnlyAddressBook getAddressBook() {
            throw u();
        }

        @Override public boolean hasPerson(Person person) {
            throw u();
        }

        @Override public void deletePerson(Person target) {
            throw u();
        }

        @Override public void addPerson(Person person) {
            throw u();
        }

        @Override public void setPerson(Person target, Person editedPerson) {
            throw u();
        }

        @Override public void updateFilteredPersonList(Predicate<Person> predicate) {
            throw u();
        }

        private static UnsupportedOperationException u() {
            return new UnsupportedOperationException("Not required by ViewSessionCommand tests.");
        }
    }

    // -------------------------------------------------------------------------
    // Flexible constructors (probe multiple common signatures across forks)
    // -------------------------------------------------------------------------

    private static Student newStudentFrom(Person base) {
        Name n = new Name(base.getName().fullName);
        Phone ph = new Phone(base.getPhone().value);
        Address ad = new Address(base.getAddress().value);
        Remark rk = base.getRemark() == null ? new Remark("") : new Remark(base.getRemark().value);

        Student s = tryNew(Student.class, n, ph, ad, rk);
        if (s != null) {
            return s;
        }
        s = tryNew(Student.class, n, ph, ad, role("student"), rk);
        if (s != null) {
            return s;
        }
        s = tryNew(Student.class, n, ph, ad, rk, Set.of(), Set.of());
        if (s != null) {
            return s;
        }
        s = tryNew(Student.class, n, ph, ad, role("student"), rk, Optional.empty());
        if (s != null) {
            return s;
        }
        throw new AssertionError("No supported Student constructor found on this branch");
    }

    private static Parent newParentFrom(Person base) {
        Name n = new Name(base.getName().fullName);
        Phone ph = new Phone(base.getPhone().value);
        Address ad = new Address(base.getAddress().value);
        Remark rk = base.getRemark() == null ? new Remark("") : new Remark(base.getRemark().value);

        Parent p = tryNew(Parent.class, n, ph, ad, rk);
        if (p != null) {
            return p;
        }
        p = tryNew(Parent.class, n, ph, ad, role("parent"), rk);
        if (p != null) {
            return p;
        }
        p = tryNew(Parent.class, n, ph, ad, rk, Set.of(), Set.of());
        if (p != null) {
            return p;
        }
        p = tryNew(Parent.class, n, ph, ad, role("parent"), rk, Optional.empty());
        if (p != null) {
            return p;
        }
        throw new AssertionError("No supported Parent constructor found on this branch");
    }

    @SuppressWarnings("unchecked")
    private static <T> T tryNew(Class<T> type, Object... args) {
        for (Constructor<?> c : type.getConstructors()) {
            Class<?>[] ps = c.getParameterTypes();
            if (ps.length != args.length) {
                continue;
            }
            boolean ok = true;
            for (int i = 0; i < ps.length; i++) {
                if (!wrap(ps[i]).isInstance(args[i])) {
                    ok = false;
                    break;
                }
            }
            if (!ok) {
                continue;
            }
            try {
                return (T) c.newInstance(args);
            } catch (ReflectiveOperationException ignored) {
                // try next constructor
            }
        }
        return null;
    }

    private static Class<?> wrap(Class<?> c) {
        if (!c.isPrimitive()) {
            return c;
        }
        if (c == boolean.class) {
            return Boolean.class;
        }
        if (c == byte.class) {
            return Byte.class;
        }
        if (c == short.class) {
            return Short.class;
        }
        if (c == char.class) {
            return Character.class;
        }
        if (c == int.class) {
            return Integer.class;
        }
        if (c == long.class) {
            return Long.class;
        }
        if (c == float.class) {
            return Float.class;
        }
        return Double.class;
    }

    private static Object role(String value) {
        try {
            Class<?> roleCls = Class.forName("seedu.address.model.person.Role");
            return roleCls.getConstructor(String.class).newInstance(value);
        } catch (ReflectiveOperationException e) {
            return value; // tolerate branches without Role type
        }
    }

    // -------------------------------------------------------------------------
    // Private-field helpers
    // -------------------------------------------------------------------------

    private static void setRemark(Person p, String text) {
        try {
            Field f = findInstanceField(p.getClass(), "remark");
            f.setAccessible(true);
            f.set(p, new Remark(text));
        } catch (ReflectiveOperationException e) {
            throw new AssertionError("Cannot set remark", e);
        }
    }

    private static void setTypedSessionIfSupported(Person p, SessionSlot s) {
        try {
            Field f = findInstanceField(p.getClass(), "sessionSlot");
            f.setAccessible(true);
            f.set(p, Optional.of(s));
        } catch (NoSuchFieldException ignore) {
            // old branches without typed session – remark fallback will be used by tests
        } catch (ReflectiveOperationException e) {
            throw new AssertionError("Cannot set sessionSlot", e);
        }
    }

    private static Field findInstanceField(Class<?> type, String name) throws NoSuchFieldException {
        Class<?> c = type;
        while (c != null && c != Object.class) {
            try {
                Field f = c.getDeclaredField(name);
                if (!Modifier.isStatic(f.getModifiers())) {
                    return f;
                }
            } catch (NoSuchFieldException ignore) {
                // keep walking
            }
            c = c.getSuperclass();
        }
        throw new NoSuchFieldException(name);
    }

    // -------------------------------------------------------------------------
    // Small utilities
    // -------------------------------------------------------------------------

    private static SessionSlot slot(Day d, int sh, int sm, int eh, int em) {
        return new SessionSlot(d, TimeRange.of(LocalTime.of(sh, sm), LocalTime.of(eh, em)));
    }

    private static CommandResult run(List<Person> persons, SessionSlot s) throws CommandException {
        return new ViewSessionCommand(s).execute(new ModelStub(persons));
    }

    private static boolean matchesByTypedSession(Person p, SessionSlot expect) {
        try {
            Field f = findInstanceField(p.getClass(), "sessionSlot");
            f.setAccessible(true);
            Object v = f.get(p);
            if (v instanceof Optional<?>) {
                Optional<?> opt = (Optional<?>) v;
                return opt.isPresent() && expect.equals(opt.get());
            }
            return false;
        } catch (ReflectiveOperationException e) {
            return false;
        }
    }

    // -------------------------------------------------------------------------
    // Tests
    // -------------------------------------------------------------------------

    @Test
    public void execute_noMatch_returnsNoSession() throws Exception {
        Person alice = newStudentFrom(TypicalPersons.ALICE);
        Person benson = newParentFrom(TypicalPersons.BENSON);

        CommandResult r = run(List.of(alice, benson), slot(Day.MON, 17, 0, 18, 0));
        assertEquals(ViewSessionCommand.MESSAGE_NO_SESSION, r.getFeedbackToUser());
    }

    @Test
    public void execute_matchViaRemark_formatsCleanBlock() throws Exception {
        Person p = newParentFrom(TypicalPersons.BENSON);
        setRemark(p, "Follow-up Tue-[3pm-4pm] at library.");

        CommandResult r = run(List.of(p), slot(Day.TUE, 15, 0, 16, 0));
        String out = r.getFeedbackToUser();

        assertTrue(out.startsWith("Session Tue-[3pm-4pm]:")
                || out.startsWith("Session Tue-[15:00-16:00]:"));
        assertTrue(out.contains("Name: "));
        assertTrue(out.contains("Phone: "));
        assertTrue(out.contains("Address: "));
        assertTrue(out.contains("Role: "));
    }

    @Test
    public void execute_typedSession_formatsCleanBlock() throws Exception {
        Person p = newStudentFrom(TypicalPersons.ALICE);
        SessionSlot s = slot(Day.MON, 17, 0, 18, 0);

        setTypedSessionIfSupported(p, s);
        if (!matchesByTypedSession(p, s)) {
            // Fallback for branches without typed field: ensure remark contains token
            setRemark(p, "Mon-[5pm-6pm]");
        }

        CommandResult r = run(List.of(p), s);
        String out = r.getFeedbackToUser();

        assertTrue(out.contains("Name: "));
        assertTrue(out.contains("Mon-[5pm-6pm]") || out.contains("Mon-[17:00-18:00]"));
    }

    @Test
    public void execute_multipleMatches_rendersTwoBlocks() throws Exception {
        Person a = newStudentFrom(TypicalPersons.ALICE);
        Person b = newParentFrom(TypicalPersons.BENSON);

        setRemark(a, "Wed-[1pm-2pm]");
        setRemark(b, "Reminder: Wed-[1pm-2pm]");

        CommandResult r = run(List.of(a, b), slot(Day.WED, 13, 0, 14, 0));
        String text = r.getFeedbackToUser();

        // Expect header + two blocks separated by a blank line
        String[] parts = text.split("\\R\\R");
        assertTrue(parts.length >= 3);
    }
}
