package seedu.address.model;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Optional;

import javafx.collections.ObservableList;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.calendar.ReadOnlyCalendar;
import seedu.address.model.calendar.activity.Activity;
import seedu.address.model.calendar.activity.ActivityReference;
import seedu.address.model.calendar.task.Task;
import seedu.address.model.calendar.task.TaskCompletionStatistics;
import seedu.address.model.calendar.task.TaskReference;
import seedu.address.model.calendar.task.exceptions.RepeatedCompleteException;

/**
 * The API of the Model component.
 */
public interface Model {

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
