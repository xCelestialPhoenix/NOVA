package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.model.calendar.Calendar.CALENDAR_DEFAULT_START_DATE;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Optional;
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
import seedu.address.model.calendar.task.TaskCompletionStatistics;
import seedu.address.model.calendar.task.TaskReference;
import seedu.address.model.calendar.task.exceptions.RepeatedCompleteException;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {

    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final Calendar calendar;
    private final UserPrefs userPrefs;

    /**
     * Initializes a ModelManager with the given calendar and userPrefs.
     */
    public ModelManager(ReadOnlyCalendar calendar, ReadOnlyUserPrefs userPrefs) {

        super();
        requireAllNonNull(calendar, userPrefs);

        logger.fine("Initializing with calendar: " + calendar + " and user prefs " + userPrefs);

        this.userPrefs = new UserPrefs(userPrefs);
        this.calendar = new Calendar(calendar);
    }

    public ModelManager() {

        this(new Calendar(CALENDAR_DEFAULT_START_DATE), new UserPrefs());
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

    //=========== Calendar =============================================================

    @Override
    public ReadOnlyCalendar getCalendar() {

        return calendar;
    }

    @Override
    public void setCalendar(ReadOnlyCalendar calendar) {

        this.calendar.resetCalendar(calendar);
        userPrefs.setCalendarStartDate(calendar.getStartDate());
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

        return calendar.isAddable(activity);
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

        return calendar.getWeekTaskList();
    }

    @Override
    public Optional<Activity> getNextActivity() {

        return calendar.getNextActivity();
    }

    @Override
    public int calculateWeekNumber(LocalDate refDate) {

        return calendar.calculateWeek(refDate);
    }

    @Override
    public TaskCompletionStatistics getTaskCompletionStats() {

        return calendar.getTaskCompletionStats();
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

        return calendar.equals(other.calendar)
                && userPrefs.equals(other.userPrefs);
    }


}
