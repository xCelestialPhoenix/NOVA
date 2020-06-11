package seedu.address.model;

import java.nio.file.Path;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Optional;
import java.util.function.Predicate;

import javafx.collections.ObservableList;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.calendar.ReadOnlyCalendar;
import seedu.address.model.calendar.activity.Activity;
import seedu.address.model.calendar.activity.ActivityReference;
import seedu.address.model.calendar.task.Task;
import seedu.address.model.calendar.task.TaskCompletionStatistics;
import seedu.address.model.calendar.task.TaskReference;
import seedu.address.model.calendar.task.exceptions.RepeatedCompleteException;
import seedu.address.model.person.Person;

/**
 * The API of the Model component.
 */
public interface Model {

    /**
     * {@code Predicate} that always evaluate to true
     */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

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
     * Returns the AddressBook
     */
    ReadOnlyAddressBook getAddressBook();

    /**
     * Replaces address book data with the data in {@code addressBook}.
     */
    void setAddressBook(ReadOnlyAddressBook addressBook);

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
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    void setPerson(Person target, Person editedPerson);

    /**
     * Returns an unmodifiable view of the filtered person list
     */
    ObservableList<Person> getFilteredPersonList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);


    //=========== Calendar =============================================================

    ReadOnlyCalendar getCalendar();

    void setCalendar(ReadOnlyCalendar calendar);

    void addActivity(Activity activity);

    void addTask(Task task);

    Optional<Task> completeTask(TaskReference taskReference) throws RepeatedCompleteException;

    Optional<Activity> deleteActivity(ActivityReference activityReference);

    Optional<Task> deleteTask(TaskReference taskReference);

    ObservableList<Activity> viewActivityOnDate(LocalDate date);

    ObservableList<Activity> getFilteredActivityList();

    ObservableList<Task> getFilteredTaskList();

    boolean hasActivity(Activity activity);

    boolean hasTask(Task task);

    boolean isWithinCalendarTime(Activity activity);

    boolean isWithinCalendarTime(LocalDate date);

    boolean isValidStartDay(DayOfWeek day);

    boolean isAddable(Activity activity);

    Optional<Activity> getNextActivity();

    int calculateWeekNumber(LocalDate refDate);

    TaskCompletionStatistics getTaskCompletionStats();

}
