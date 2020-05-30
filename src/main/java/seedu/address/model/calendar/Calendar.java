package seedu.address.model.calendar;

import static java.time.temporal.ChronoUnit.DAYS;
import static seedu.address.logic.constants.CalendarConstants.DAYS_PER_WEEK;
import static seedu.address.logic.constants.CalendarConstants.READING_WEEK;
import static seedu.address.logic.constants.CalendarConstants.RECESS_WEEK;
import static seedu.address.logic.constants.CalendarConstants.WEEKS_PER_SEMESTER;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import javafx.collections.ObservableList;

import seedu.address.commons.core.Config;
import seedu.address.model.calendar.activity.Activity;
import seedu.address.model.calendar.activity.ActivityReference;
import seedu.address.model.calendar.activity.Lesson;
import seedu.address.model.calendar.activity.UniqueActivityList;

/**
 * The type Calendar.
 */
public class Calendar implements ReadOnlyCalendar {

    public static final DayOfWeek FIRST_DAY_OF_WEEK = DayOfWeek.MONDAY;

    private final Week[] weeks = new Week[WEEKS_PER_SEMESTER];
    private LocalDate startDate = LocalDate.of(2020, 1, 13);
    private LocalDate endDate = LocalDate.of(2020, 5, 10);

    /**
     * Instantiates a new Calendar.
     */

    public Calendar() {}

    public Calendar(LocalDate startDate, LocalDate endDate) {

        this.startDate = startDate;
        this.endDate = endDate;
        initWeeks();
    }

    public void resetCalendar(ReadOnlyCalendar calendar) {
        startDate = calendar.getStartDate();
        endDate = calendar.getEndDate();
        initWeeks();
    }

    /**
     * Add activity boolean.
     *
     * @param activity the activity
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
     * Deletes an activity.
     *
     * @param activityReference the activity reference
     * @return An optional that holds the deleted activity if it exists
     */
    public Optional<Activity> deleteActivity(ActivityReference activityReference) {

        int week = calculateWeek(activityReference.getDate()); // calculateWeek() returns zero-indexed week.
        return weeks[week].deleteActivity(activityReference);
    }

    /**
     * View activity on date observable list.
     *
     * @param date the date
     * @return the observable list
     */
    public ObservableList<Activity> viewActivityOnDate(LocalDate date) {

        int weekNum = calculateWeek(date); // calculateWeek() returns zero-indexed week.

        if (weekNum >= WEEKS_PER_SEMESTER || weekNum < 0) {
            return new UniqueActivityList().asUnmodifiableObservableList();
        }

        return weeks[weekNum].viewActivityOnDate(date);
    }

    /**
     * Add lesson boolean.
     *
     * @param lesson the lesson
     * @return the boolean
     */
    public boolean addLesson(Lesson lesson) {

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
        return true;
    }

    /**
     * Gets next activity.
     *
     * @param today   the today
     * @param timeNow the time now
     * @return the next activity
     */
    public Optional<Activity> getNextActivity(LocalDate today, LocalTime timeNow) {

        int weekNumber = calculateWeek(today); // calculateWeek() returns zero-indexed week.

        if (weekNumber >= WEEKS_PER_SEMESTER) {
            return Optional.empty();
        }

        if (weekNumber < 0) {
            weekNumber = 0;
        }

        Optional<Activity> nextActivity = weeks[weekNumber].getNextActivity(today, timeNow);
        weekNumber += 1;

        while (nextActivity.isEmpty() && weekNumber < WEEKS_PER_SEMESTER) {
            nextActivity = weeks[weekNumber].getFirstActivity();
            weekNumber++;
        }
        return nextActivity;
    }

    /**
     * Has activity boolean.
     *
     * @param activity the activity
     * @return the boolean
     */
    public boolean hasActivity(Activity activity) {

        if (activity instanceof Lesson) {
            return weeks[0].hasActivity(activity);
        }

        int week = calculateWeek(activity.getDate()); // calculateWeek() returns zero-indexed week.
        return weeks[week].hasActivity(activity);
    }

    /**
     * Is within calendar time boolean.
     *
     * @param activity the activity
     * @return the boolean
     */
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

    /**
     * Calculates the week number in the calendar of a date
     *
     * @param date the date used the calculate the week number
     * @return the week number in zero indexing
     */
    public int calculateWeek(LocalDate date) {

        int days = (int) DAYS.between(startDate, date);
        return days / DAYS_PER_WEEK;

    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    private boolean isValidDate(LocalDate date) {

        return !date.isBefore(startDate) && !date.isAfter(endDate);
    }

    /**
     * Checks if the lesson can be added across all the weeks.
     *
     * @param activity the activity to check if it can be added into the calendar
     * @return the addability of the lesson
     */
    public boolean checkAvailability(Activity activity) {

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
     * Initialize all the weeks
     */

    private void initWeeks() {

        for (int index = 0; index < weeks.length; index++) {
            weeks[index] = new Week(index + 1, startDate.plusWeeks(index));
        }
    }

}
