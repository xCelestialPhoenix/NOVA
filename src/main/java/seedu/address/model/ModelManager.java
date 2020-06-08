package seedu.address.model;

import java.nio.file.Path;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.calendar.Calendar;
import seedu.address.model.calendar.ReadOnlyCalendar;
import seedu.address.model.calendar.activity.Activity;
import seedu.address.model.calendar.activity.ActivityReference;
import seedu.address.model.calendar.task.Task;
import seedu.address.model.calendar.task.TaskReference;
import seedu.address.model.calendar.task.exceptions.RepeatedCompleteException;
import seedu.address.model.person.Person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {

    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final Calendar calendar;
    private final UserPrefs userPrefs;
    private final FilteredList<Person> filteredPersons;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs) {

        super();
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        this.calendar = new Calendar(userPrefs.getCalendarStartDate(), userPrefs.getCalendarEndDate());
        filteredPersons = new FilteredList<>(this.addressBook.getPersonList());
    }

    public ModelManager() {

        this(new AddressBook(), new UserPrefs());
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
    public ReadOnlyCalendar getCalendar() {

        return calendar;
    }

    @Override
    public void setCalendar(ReadOnlyCalendar calendar) {
        this.calendar.resetCalendar(calendar);
        userPrefs.setCalendarStartDate(calendar.getStartDate());
        userPrefs.setCalendarEndDate(calendar.getEndDate());
    }

    @Override
    public void addActivity(Activity activity) {

        calendar.addActivity(activity);
    }

    @Override
    public void addTask(Task task) {
        calendar.addTask(task);
    }

    @Override
    public Optional<Task> completeTask(TaskReference taskReference) throws RepeatedCompleteException {

        return calendar.completeTask(taskReference);
    }

    @Override
    public Optional<Activity> deleteActivity(ActivityReference activityReference) {

        return calendar.deleteActivity(activityReference);
    }

    @Override
    public Optional<Task> deleteTask(TaskReference taskReference) {

        return calendar.deleteTask(taskReference);
    }

    @Override
    public boolean hasActivity(Activity activity) {

        return calendar.hasActivity(activity);
    }

    @Override
    public boolean hasTask(Task task) {

        return calendar.hasTask(task);
    }

    @Override
    public boolean isWithinCalendarTime(Activity activity) {

        return calendar.isWithinCalendarRange(activity);
    }

    @Override
    public boolean isWithinCalendarTime(LocalDate date) {

        return calendar.isWithinCalendarRange(date);
    }

    @Override
    public boolean isValidStartDay(DayOfWeek day) {

        return calendar.isValidStartDay(day);
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

        return new FilteredList<>(calendar.viewActivityOnDate(LocalDate.now()));
    }

    @Override
    public ObservableList<Task> getFilteredTaskList() {

        return calendar.getFilteredTaskList();
    }

    @Override
    public Optional<Activity> getNextActivity(LocalDate today, LocalTime timeNow) {

        return calendar.getNextActivity(today, timeNow);
    }

    @Override
    public int calculateWeekNumber(LocalDate refDate) {

        return calendar.calculateWeek(refDate);
    }

    @Override
    public String getTaskCompletionStats() {

        return calendar.getTaskCompletionStats();
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
