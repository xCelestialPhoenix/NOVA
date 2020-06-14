package seedu.address.model.calendar;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Optional;

import javafx.collections.ObservableList;

import seedu.address.model.calendar.activity.Activity;
import seedu.address.model.calendar.task.Task;
import seedu.address.model.calendar.task.TaskCompletionStatistics;

/**
 * The interface for all getters and utility methods. This is to prevent unwanted modifications to the existing
 * calendar.
 */
public interface ReadOnlyCalendar {

    //======================================= Getters =====================================
    //==================== Calendar ==================

    /**
     * Gets the start date of the calendar.
     *
     * @return the start date of the calendar
     */
    LocalDate getStartDate();

    /**
     * Gets the end date of the calendar.
     *
     * @return the end date of the calendar
     */
    LocalDate getEndDate();

    /**
     * Get the weeks of the calendar.
     *
     * @return the weeks within the calendar
     */
    Week[] getWeeks();

    //==================== Activities ==================

    ObservableList<Activity> getActivities();

    /**
     * Gets the activities happening on a specific date.
     *
     * @param date the date to view
     * @return the list of activities happening on that date
     */
    ObservableList<Activity> viewActivityOnDate(LocalDate date);

    /**
     * Gets the next activity from now.
     *
     * @return an optional holding the next activity from now if it exists
     */
    Optional<Activity> getNextActivity();

    //==================== Tasks ==================

    ObservableList<Task> getTasks();

    /**
     * Gets this week's task list.
     *
     * @return this week's task list
     */
    ObservableList<Task> getWeekTaskList();

    /**
     * Gets the statistics on the tasks completed.
     *
     * @return the statistics on the tasks completed
     */
    TaskCompletionStatistics getTaskCompletionStats();

    //======================================= Utilities =====================================

    /**
     * Calculates the week number in the calendar given a date.
     *
     * @param date the date to be calculated
     * @return the week number in the calendar corresponding to the given date
     */
    int calculateWeek(LocalDate date);

    /**
     * Returns true if the activity of interest is already in the calendar.
     *
     * @param activity the activity to be tested
     * @return true if the activity is already in the calendar
     */
    boolean hasActivity(Activity activity);

    /**
     * Returns true if the task of interest is already in the calendar.
     *
     * @param task the task to be tested
     * @return true if the task is already in the calendar
     */
    boolean hasTask(Task task);

    /**
     * Returns true if an activity falls within the calendar range.
     *
     * @param activity the activity to be tested
     * @return true if the activity falls within the calendar range
     */
    boolean isWithinCalendarRange(Activity activity);

    /**
     * Returns true if a date falls within the calendar range.
     *
     * @param date the date to be tested
     * @return true if the date falls within the calendar range
     */
    boolean isWithinCalendarRange(LocalDate date);

    /**
     * Returns true if the day of the week matches with the one in the calendar.
     *
     * @param startDay the start day to be tested
     * @return true if the day of the week matches with the one in the calendar
     */
    boolean isValidStartDay(DayOfWeek startDay);

    /**
     * Returns true if the activity can be added into the calendar.
     *
     * @param activity the activity to be tested
     * @return true if the activity can be added into the calendar
     */
    boolean isAddable(Activity activity);

}
