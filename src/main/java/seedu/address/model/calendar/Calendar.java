package seedu.address.model.calendar;

import static java.time.temporal.ChronoUnit.DAYS;
import static seedu.address.logic.constants.CalendarConstants.DAYS_PER_WEEK;
import static seedu.address.logic.constants.CalendarConstants.READING_WEEK;
import static seedu.address.logic.constants.CalendarConstants.RECESS_WEEK;
import static seedu.address.logic.constants.CalendarConstants.WEEKS_PER_SEMESTER;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import seedu.address.model.calendar.activity.Activity;
import seedu.address.model.calendar.activity.ActivityReference;
import seedu.address.model.calendar.activity.Lesson;
import seedu.address.model.calendar.activity.UniqueActivityList;
import seedu.address.model.calendar.task.Task;
import seedu.address.model.calendar.task.TaskCompletionStatistics;
import seedu.address.model.calendar.task.TaskReference;
import seedu.address.model.calendar.task.UniqueTaskList;
import seedu.address.model.calendar.task.exceptions.RepeatedCompleteException;

/**
 * The Calendar feature within NOVA.
 */
public class Calendar implements ReadOnlyCalendar {

    public static final LocalDate CALENDAR_DEFAULT_START_DATE = LocalDate.of(2020, 1, 13);

    private static final DayOfWeek FIRST_DAY_OF_WEEK = DayOfWeek.MONDAY;

    private LocalDate startDate;
    private LocalDate endDate;
    private Week[] weeks = new Week[WEEKS_PER_SEMESTER];

    /**
     * Instantiates a new calendar starting from the start date.
     *
     * @param startDate the start date of the new calendar
     */
    public Calendar(LocalDate startDate) {

        this.startDate = startDate;
        this.endDate = calculateEndDate(startDate);
        initWeeks();
    }

    public Calendar(ReadOnlyCalendar calendar) {

        startDate = calendar.getStartDate();
        endDate = calendar.getEndDate();
        weeks = calendar.getWeeks();
    }

    /**
     * Resets the current calendar to another, overwriting the dates and activities within.
     *
     * @param calendar the calendar to reset to
     */
    public void resetCalendar(ReadOnlyCalendar calendar) {

        startDate = calendar.getStartDate();
        endDate = calendar.getEndDate();
        weeks = calendar.getWeeks();
    }

    //============================= Modifications ==============================
    //=================== Activities ====================

    /**
     * Adds an activity into the calendar.
     *
     * @param activity the activity to be added
     */
    public void addActivity(Activity activity) {

        if (activity instanceof Lesson) {
            addLesson((Lesson) activity);
            return;
        }

        int week = calculateWeek(activity.getDate()); // calculateWeek() returns zero-indexed week.
        weeks[week].addActivity(activity);
    }

    /**
     * Adds lessons into the calendar.
     *
     * @param lesson the lesson to be added
     */
    public void addLesson(Lesson lesson) {

        // A simple loop across school weeks and adds an activity into each week to represent lesson
        for (Week week : weeks) {

            if (week.getWeekNum() == RECESS_WEEK) {
                // No lesson on recess week
                continue;
            }

            if (week.getWeekNum() >= READING_WEEK) {
                // No more lessons from read week onwards
                break;
            }
            week.addActivity(lesson);
        }
    }

    /**
     * Deletes an activity from the calendar.
     *
     * @param activityReference the scaled-down version of an activity used to identify the actual activity
     * @return an optional that holds the deleted activity if it exists
     */
    public Optional<Activity> deleteActivity(ActivityReference activityReference) {

        int week = calculateWeek(activityReference.getDate()); // calculateWeek() returns zero-indexed week.
        return weeks[week].deleteActivity(activityReference);
    }

    // TODO: Possible enhancement - Delete Lesson

    //=================== Task ====================

    /**
     * Adds a task into the calendar.
     *
     * @param task the task to be added into the calendar
     */
    public void addTask(Task task) {

        int week = calculateWeek(task.getDueDate());
        weeks[week].addTask(task);

    }

    /**
     * Deletes a task from the calendar.
     *
     * @param taskReference the scaled-down version of a task used to identify the actual task
     * @return an optional that holds the deleted task if it exists
     */
    public Optional<Task> deleteTask(TaskReference taskReference) {

        int week = calculateWeek(taskReference.getDueDate()); // calculateWeek() returns zero-indexed week.
        return weeks[week].deleteTask(taskReference);
    }

    /**
     * Completes a task in the calendar.
     *
     * @param taskReference the scaled-down version of a task used to identify the actual task
     * @return the optional that holds the completed task if it exists
     * @throws RepeatedCompleteException If the task has already been completed before
     */
    public Optional<Task> completeTask(TaskReference taskReference) throws RepeatedCompleteException {

        int week = calculateWeek((taskReference.getDueDate()));
        return weeks[week].completeTask(taskReference);
    }

    //======================================= Getters ========================================
    //==================== Calendar ==================

    @Override
    public LocalDate getStartDate() {

        return startDate;
    }

    @Override
    public LocalDate getEndDate() {

        return endDate;
    }

    @Override
    public Week[] getWeeks() {

        return weeks;
    }

    //==================== Activities ==================

    @Override
    public ObservableList<Activity> getActivities() {

        ObservableList<Activity> activities = FXCollections.observableArrayList();

        for (Week week : weeks) {
            activities.addAll(week.getActivities());
        }
        return activities;
    }

    @Override
    public ObservableList<Activity> viewActivityOnDate(LocalDate date) {

        int weekNum = calculateWeek(date); // calculateWeek() returns zero-indexed week.

        if (weekNum >= WEEKS_PER_SEMESTER || weekNum < 0) {
            return new UniqueActivityList().asUnmodifiableObservableList();
        }

        return weeks[weekNum].viewActivityOnDate(date);
    }

    @Override
    public Optional<Activity> getNextActivity() {

        int weekNumber = calculateWeek(LocalDate.now()); // calculateWeek() returns zero-indexed week.

        if (weekNumber >= WEEKS_PER_SEMESTER) {
            return Optional.empty();
        }

        if (weekNumber < 0) {
            weekNumber = 0;
        }

        Optional<Activity> nextActivity = weeks[weekNumber].getNextActivity();
        weekNumber += 1;

        while (nextActivity.isEmpty() && weekNumber < WEEKS_PER_SEMESTER) {
            nextActivity = weeks[weekNumber].getFirstActivity();
            weekNumber++;
        }
        return nextActivity;
    }

    //==================== Tasks ==================


    @Override
    public ObservableList<Task> getTasks() {

        ObservableList<Task> tasks = FXCollections.observableArrayList();

        for (Week week : weeks) {
            tasks.addAll(week.getFilteredTaskList());
        }
        return tasks;
    }

    @Override
    public ObservableList<Task> getWeekTaskList() {

        int weekNumber = calculateWeek(LocalDate.now());

        if (weekNumber >= WEEKS_PER_SEMESTER) {

            return new UniqueTaskList().asUnmodifiableObservableList();
        }

        return weeks[weekNumber].getFilteredTaskList();
    }

    @Override
    public TaskCompletionStatistics getTaskCompletionStats() {

        int weekNumber = calculateWeek(LocalDate.now());

        if (weekNumber >= WEEKS_PER_SEMESTER) {

            return new TaskCompletionStatistics();
        }

        return weeks[weekNumber].getTaskCompletionStats();
    }

    //======================================= Utilities ========================================

    /**
     * Calculates the end date of the calendar given the start date.
     *
     * @param startDate the start date of the calendar
     * @return the calculated end date of the calendar
     */
    public static LocalDate calculateEndDate(LocalDate startDate) {

        return startDate.plusDays(DAYS_PER_WEEK * WEEKS_PER_SEMESTER);
    }

    @Override
    public int calculateWeek(LocalDate date) {

        int days = (int) DAYS.between(startDate, date);
        return days / DAYS_PER_WEEK;

    }

    @Override
    public boolean hasActivity(Activity activity) {

        if (activity instanceof Lesson) {
            return weeks[0].hasActivity(activity);
        }

        int week = calculateWeek(activity.getDate()); // calculateWeek() returns zero-indexed week.
        return weeks[week].hasActivity(activity);
    }

    @Override
    public boolean hasTask(Task task) {

        int week = calculateWeek(task.getDueDate());
        return weeks[week].hasTask(task);
    }

    @Override
    public boolean isWithinCalendarRange(Activity activity) {

        return isValidDate(activity.getDate());
    }

    @Override
    public boolean isWithinCalendarRange(LocalDate date) {

        return isValidDate(date);
    }

    @Override
    public boolean isValidStartDay(DayOfWeek day) {

        return day.equals(FIRST_DAY_OF_WEEK);
    }

    @Override
    public boolean isAddable(Activity activity) {

        if (activity instanceof Lesson) {
            boolean canAdd = true;
            for (Week week : weeks) {

                if (week.getWeekNum() == RECESS_WEEK) {
                    // No lesson on recess week
                    continue;
                }

                if (week.getWeekNum() >= READING_WEEK) {
                    // No more lessons from reading week onwards
                    break;
                }

                canAdd = week.isAddable(activity);

                if (!canAdd) {
                    break;
                }
            }
            return canAdd;
        }

        int week = calculateWeek(activity.getDate()); // calculateWeek() returns zero-indexed week.
        return weeks[week].isAddable(activity);
    }

    /**
     * Initializes the week array
     */

    private void initWeeks() {

        for (int index = 0; index < weeks.length; index++) {
            weeks[index] = new Week(index + 1, startDate.plusWeeks(index));
        }
    }

    /**
     * Returns true if the date lies within the calendar's range
     *
     * @param date the date to be tested
     * @return true if the date lies within the calendar's rnage
     */
    private boolean isValidDate(LocalDate date) {

        return !date.isBefore(startDate) && !date.isAfter(endDate);
    }

}
