package seedu.address.model;

import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;
    Predicate<Appointment> PREDICATE_SHOW_ALL_APPOINTMENTS = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' address book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setAddressBookFilePath(Path addressBookFilePath);

    /**
     * Replaces address book data with the data in {@code addressBook}.
     */
    void setAddressBook(ReadOnlyAddressBook addressBook);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    boolean hasPerson(Person person);

    /**
     * Deletes the given person.
     * The person must exist in the address book.
     */
    void deletePerson(Person target);

    /**
     * Adds the given person.
     * {@code person} must not already exist in the address book.
     */
    void addPerson(Person person);

    /**
     * Adds the given person at specified index.
     * {@code person} must not already exist in the address book.
     */
    void addPerson(Person person, int index);

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    void setPerson(Person target, Person editedPerson);

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<Person> getFilteredPersonList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);

    /**
     * Updates the main filter of the filtered person list to filter by the given {@code masterPredicate}
     * @throws NullPointerException if {@code masterPredicate} is null.
     */
    void updateFilteredPersonList(FilteredPersonListMasterPredicate masterPredicate);

    /**
     * Updates the order of the person list according to the given parameter.
     *
     * @param comparator Specifies new comparison criteria to order person list by.
     */
    void updateSortingOrder(Comparator<Person> comparator);

    /**
     * Adds the given appointment.
     * {@code appointment} must not conflict with any existing appointments.
     */
    void addAppointment(Appointment appointment);

    /**
     * Updates all appointments with {@code oldName} to {@code newName}.
     */
    void updateAppointments(Name oldName, Name newName);

    /**
     * Deletes and returns the appointment at the specified index.
     */
    Appointment deleteAppointment(int index);

    /**
     * Deletes all appointments with the given name.
     */
    void deleteAppointments(Name name);

    /** Returns an unmodifiable view of the filtered appointment list */
    ObservableList<Appointment> getFilteredAppointmentList();

    /**
     * Updates the filter of the filtered appointment list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredAppointmentList(Predicate<Appointment> predicate);

    /**
     * Returns a list of appointments that conflict with the given appointment.
     */
    List<Appointment> getConflictingAppointments(Appointment appointment);

    /**
     * Represents the main predicates for filteredPersonList.
     * Each enum value is also a {@link Predicate}.
     * <p>
     * There are 2 cases for filteredPersonList:
     * <ol>
     *     <li>filteredPersonList shows only current (i.e. unarchived) persons</li>
     *     <li>filteredPersonList shows only archived persons</li>
     * </ol>
     * </p>
     */
    enum FilteredPersonListMasterPredicate implements Predicate<Person> {
        SHOW_ONLY_CURRENT_PERSONS(person -> !person.isArchived()),
        SHOW_ONLY_ARCHIVED_PERSONS(Person::isArchived);

        private final Predicate<Person> predicate;

        FilteredPersonListMasterPredicate(Predicate<Person> predicate) {
            this.predicate = predicate;
        }

        @Override
        public boolean test(Person person) {
            return predicate.test(person);
        }
    }
}
