package seedu.address.model.calendar;

import static seedu.address.logic.constants.CalendarConstants.DAYS_PER_WEEK;

import java.time.LocalDate;
import java.util.Optional;

import javafx.collections.ObservableList;

import seedu.address.model.calendar.activity.Activity;
import seedu.address.model.calendar.activity.ActivityReference;
import seedu.address.model.calendar.activity.Lesson;
import seedu.address.model.calendar.task.Task;
import seedu.address.model.calendar.task.TaskCompletionStatistics;
import seedu.address.model.calendar.task.TaskReference;
import seedu.address.model.calendar.task.UniqueTaskList;
import seedu.address.model.calendar.task.exceptions.RepeatedCompleteException;

/**
 * The week representation within the calendar of NOVA. Also contains a task list to record all the tasks due for the
 * week.
 */
public class Week {

    private final LocalDate startOfWeek;
    private final int weekNum;
    private final Day[] days = new Day[DAYS_PER_WEEK];
    private final UniqueTaskList tasks = new UniqueTaskList();

    /**
     * Instantiates a new Week.
     *
     * @param weekNum     the week number
     * @param startOfWeek the start date of week
     */
    public Week(int weekNum, LocalDate startOfWeek) {

        this.weekNum = weekNum;
        this.startOfWeek = startOfWeek;
        initDays();
    }

    //============================= Modifications to the week ==============================
    //=================== Activities ====================

    /**
     * Adds an activity into the week.
     *
     * @param activity the activity to be added
     */
    public void addActivity(Activity activity) {

        int dayNumber = getDayNumber(activity) - 1; // getDayNumber() is one-indexed
        days[dayNumber].addActivity(activity);
    }

    /**
     * Deletes an activity from the week.
     *
     * @param activityReference the scaled-down version of an activity used to identify the actual activity
     * @return an optional that holds the deleted activity if it exists
     */
    public Optional<Activity> deleteActivity(ActivityReference activityReference) {

        int dayNumber = getDayNumber(activityReference) - 1; // getDayNumber() is one-indexed
        return days[dayNumber].deleteActivity(activityReference);
    }

    //=================== Task ====================

    /**
     * Adds a task into the task list.
     *
     * @param task the task to be added
     */
    public void addTask(Task task) {

        tasks.add(task);
    }

    /**
     * Deletes a task from the task list.
     *
     * @param taskReference the scaled-down version of a task used to identify the actual task
     * @return an optional that holds the deleted task if it exists
     */
    public Optional<Task> deleteTask(TaskReference taskReference) {

        return tasks.delete(taskReference);
    }

    /**
     * Completes a task in the task list.
     *
     * @param taskReference the scaled-down version of a task used to identify the actual task
     * @return an optional that holds the completed task if it exists
     * @throws RepeatedCompleteException If the task has already been completed before
     */
    public Optional<Task> completeTask(TaskReference taskReference) throws RepeatedCompleteException {
        return tasks.complete(taskReference);
    }

    //======================================= Getters ========================================
    //==================== Week ==================

    /**
     * Gets the week number of the week.
     *
     * @return the week number
     */
    public int getWeekNum() {

        return weekNum;
    }

    //==================== Activities ==================

    /**
     * Gets the activities happening on a specific date.
     *
     * @param date the date to view
     * @return the list of activities happening on that date
     */
    public ObservableList<Activity> viewActivityOnDate(LocalDate date) {

        int day = date.getDayOfWeek().getValue() - 1;
        return days[day].getActivities();
    }

    /**
     * Gets the first activity of the week.
     *
     * @return an optional that holds the first activity of the week if it exists
     */
    public Optional<Activity> getFirstActivity() {

        Optional<Activity> firstActivity = Optional.empty();
        for (Day day : days) {
            firstActivity = day.getFirstActivity();

            if (firstActivity.isPresent()) {
                break;
            }
        }
        return firstActivity;
    }

    /**
     * Gets the next upcoming activity from now.
     *
     * @return an optional that holds the next activity if it exists
     */
    public Optional<Activity> getNextActivity() {

        int dayNumber = LocalDate.now().getDayOfWeek().getValue() - 1;
        Optional<Activity> nextActivity = days[dayNumber].getNextActivity();
        dayNumber += 1;

        while (nextActivity.isEmpty() && dayNumber < DAYS_PER_WEEK) {
            nextActivity = days[dayNumber].getFirstActivity();
            dayNumber++;
        }
        return nextActivity;
    }

    //==================== Tasks ==================

    /**
     * Gets the week's task list.
     *
     * @return the week's task list
     */
    public ObservableList<Task> getFilteredTaskList() {
        return tasks.asUnmodifiableObservableList();
    }

    /**
     * Gets the statistics on the tasks completed.
     *
     * @return the statistics on the tasks completed
     */
    public TaskCompletionStatistics getTaskCompletionStats() {

        return tasks.getCompletionStats();
    }

    //======================================= Utilities ========================================

    /**
     * Returns true if the activity can be added into the week.
     *
     * @param activity the activity to be tested
     * @return true if the activity can be added into the week
     */
    public boolean isAddable(Activity activity) {

        int dayNumber = getDayNumber(activity) - 1; // getDayNumber is one-indexed
        return days[dayNumber].isAddable(activity);
    }

    /**
     * Returns true if the activity of interest is already in the calendar.
     *
     * @param activity the activity to be tested
     * @return true if the activity is already in the calendar
     */
    public boolean hasActivity(Activity activity) {

        int dayNumber = getDayNumber(activity) - 1; // getDayNumber is one-indexed
        return days[dayNumber].hasActivity(activity);
    }

    /**
     * Returns true if the task of interest is already in the calendar.
     *
     * @param task the task to be tested
     * @return true if the task is already in the calendar
     */
    public boolean hasTask(Task task) {

        return tasks.contains(task);
    }

    /**
     * Initializes all the days within the week.
     */
    private void initDays() {

        for (int index = 0; index < days.length; index++) {
            days[index] = new Day(startOfWeek.plusDays(index));
        }
    }

    /**
     * Gets the day number from the activity
     *
     * @param activity the activity to check
     * @return  the day number in accordance to DayOfWeek
     * @see java.time.DayOfWeek
     */

    private int getDayNumber(Activity activity) {

        if (activity instanceof Lesson) {
            return ((Lesson) activity).getDay().getValue();
        } else {
            return activity.getDate().getDayOfWeek().getValue();
        }
    }

}
