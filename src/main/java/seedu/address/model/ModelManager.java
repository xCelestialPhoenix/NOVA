package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

import seedu.address.commons.core.Config;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.calendar.Calendar;
import seedu.address.model.calendar.activity.Activity;
import seedu.address.model.person.Person;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {

    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final Calendar calendar;
    private final UserPrefs userPrefs;
    private final FilteredList<Person> filteredPersons;
    private final FilteredList<Activity> filteredActivities;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs, Config config) {

        super();
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        this.calendar = new Calendar(config);
        filteredPersons = new FilteredList<>(this.addressBook.getPersonList());
        filteredActivities = new FilteredList<>(this.calendar.viewActivityOnDate(LocalDate.of(2020, 4, 19)));

    }

    public ModelManager() {

        this(new AddressBook(), new UserPrefs(), new Config());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {

        return userPrefs;
    }

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {

        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public GuiSettings getGuiSettings() {

        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {

        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getAddressBookFilePath() {

        return userPrefs.getAddressBookFilePath();
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {

        requireNonNull(addressBookFilePath);
        userPrefs.setAddressBookFilePath(addressBookFilePath);
    }

    //=========== AddressBook ================================================================================

    @Override
    public ReadOnlyAddressBook getAddressBook() {

        return addressBook;
    }

    @Override
    public void setAddressBook(ReadOnlyAddressBook addressBook) {

        this.addressBook.resetData(addressBook);
    }

    @Override
    public boolean hasPerson(Person person) {

        requireNonNull(person);
        return addressBook.hasPerson(person);
    }

    @Override
    public void deletePerson(Person target) {

        addressBook.removePerson(target);
    }

    @Override
    public void addPerson(Person person) {

        addressBook.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void setPerson(Person target, Person editedPerson) {

        requireAllNonNull(target, editedPerson);

        addressBook.setPerson(target, editedPerson);
    }

    //=========== Calendar =============================================================
    @Override
    public void addActivity(Activity activity) {

        calendar.addActivity(activity);
    }

    @Override
    public boolean hasActivity(Activity activity) {

        return calendar.hasActivity(activity);
    }

    @Override
    public boolean isWithinCalendarTime(Activity activity) {

        return calendar.isWithinCalendarTime(activity);
    }

    @Override
    public boolean isAddable(Activity activity) {

        return calendar.checkAvailability(activity);
    }

    @Override
    public ObservableList<Activity> viewActivityOnDate(LocalDate date) {

        return calendar.viewActivityOnDate(date);
    }

    @Override
    public ObservableList<Activity> getFilteredActivityList() {

        return filteredActivities;
    }

    @Override
    public Optional<Activity> getNextActivity(LocalDate today, LocalTime timeNow) {

        return calendar.getNextActivity(today, timeNow);
    }

    @Override
    public void updateFilteredActivityList(Predicate<Activity> predicate) {

        requireNonNull(predicate);
        filteredActivities.setPredicate(predicate);
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Person> getFilteredPersonList() {

        return filteredPersons;
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {

        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return addressBook.equals(other.addressBook)
                && userPrefs.equals(other.userPrefs)
                && filteredPersons.equals(other.filteredPersons);
    }


}
