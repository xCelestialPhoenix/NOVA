package seedu.address.model.calendar;

import static java.time.temporal.ChronoUnit.DAYS;
import static seedu.address.commons.contants.CalendarConstants.DAYS_PER_WEEK;
import static seedu.address.commons.contants.CalendarConstants.READING_WEEK;
import static seedu.address.commons.contants.CalendarConstants.RECESS_WEEK;
import static seedu.address.commons.contants.CalendarConstants.WEEKS_PER_SEMESTER;

import java.time.LocalDate;

import seedu.address.commons.core.Config;
import seedu.address.model.calendar.activity.Activity;
import seedu.address.model.calendar.activity.Lesson;

/**
 * The type Calendar.
 */
public class Calendar {

    private final Week[] weeks = new Week[WEEKS_PER_SEMESTER];
    private final LocalDate startDate;
    private final LocalDate endDate;

    /**
     * Instantiates a new Calendar.
     *
     * @param config the config
     */
    public Calendar(Config config) {

        startDate = config.getStartDate();
        endDate = config.getEndDate();
        initWeeks();
    }

    /**
     * Add activity boolean.
     *
     * @param activity the activity
     * @return the boolean
     */
    public boolean addActivity(Activity activity) {

        if (!isValidDate(activity.getDate())) {
            return false;
        }

        if (activity instanceof Lesson) {
            return addLesson((Lesson) activity);
        }

        int week = calculateWeek(activity.getDate());

        return weeks[week].addActivity(activity); // calculateWeek() returns zero-indexed week.
    }

    /**
     * Calculates the week number in the calendar of a date
     *
     * @param date the date used the calculate the week number
     * @return the week number in zero indexing
     */
    private int calculateWeek(LocalDate date) {

        int days = (int) DAYS.between(startDate, date);
        return days / DAYS_PER_WEEK;

    }

    /**
     * Add lesson boolean.
     *
     * @param lesson the lesson
     * @return the boolean
     */
    public boolean addLesson(Lesson lesson) {

        boolean canAdd = checkAvailability(lesson);

        if (!canAdd) {
            return false;
        }

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

    private boolean isValidDate(LocalDate date) {

        return date.isAfter(startDate) && date.isBefore(endDate);
    }

    /**
     * Checks if the lesson can be added across all the weeks.
     *
     * @param lesson the lesson to check if it can be added into the calendar
     * @return the addability of the lesson
     */

    private boolean checkAvailability(Lesson lesson) {

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

            canAdd = week.checkAvailability(lesson);

            if (!canAdd) {
                break;
            }
        }
        return canAdd;
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
