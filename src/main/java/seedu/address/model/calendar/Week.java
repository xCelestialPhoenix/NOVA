package seedu.address.model.calendar;

import static seedu.address.logic.constants.CalendarConstants.DAYS_PER_WEEK;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import javafx.collections.ObservableList;

import seedu.address.model.calendar.activity.Activity;
import seedu.address.model.calendar.activity.Lesson;

/**
 * The type Week.
 */
public class Week {

    private final LocalDate startOfWeek;
    private final int weekNum;
    private final Day[] days = new Day[DAYS_PER_WEEK];

    /**
     * Instantiates a new Week.
     *
     * @param weekNum     the week num
     * @param startOfWeek the start of week
     */
    public Week(int weekNum, LocalDate startOfWeek) {

        this.weekNum = weekNum;
        this.startOfWeek = startOfWeek;
        initDays();
    }

    /**
     * Add activity boolean.
     *
     * @param activity the activity
     */
    public void addActivity(Activity activity) {

        int dayNumber = getDayNumber(activity) - 1; // getDayNumber is one-indexed
        days[dayNumber].addActivity(activity);
    }

    /**
     * View activity on date observable list.
     *
     * @param date the date
     * @return the observable list
     */
    public ObservableList<Activity> viewActivityOnDate(LocalDate date) {

        int day = date.getDayOfWeek().getValue() - 1;
        return days[day].getActivities();
    }


    /**
     * Gets first activity.
     *
     * @return the first activity
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

    public Optional<Activity> getNextActivity(LocalDate today, LocalTime timeNow) {

        int dayNumber = today.getDayOfWeek().getValue() - 1;
        Optional<Activity> nextActivity = days[dayNumber].getNextActivity(timeNow);
        dayNumber += 1;

        while (nextActivity.isEmpty() && dayNumber < DAYS_PER_WEEK) {
            nextActivity = days[dayNumber].getFirstActivity();
            dayNumber++;
        }
        return nextActivity;
    }

    /**
     * Check availability boolean.
     *
     * @param activity the activity
     * @return the boolean
     */
    public boolean isAddable(Activity activity) {

        int dayNumber = getDayNumber(activity) - 1; // getDayNumber is one-indexed
        return days[dayNumber].isAddable(activity);
    }

    /**
     * Has activity boolean.
     *
     * @param activity the activity
     * @return the boolean
     */
    public boolean hasActivity(Activity activity) {

        int dayNumber = getDayNumber(activity) - 1; // getDayNumber is one-indexed
        return days[dayNumber].hasActivity(activity);
    }

    /**
     * Gets week num.
     *
     * @return the week num
     */
    public int getWeekNum() {

        return weekNum;
    }

    /**
     * Initializes all the days within the week.
     */

    private void initDays() {

        for (int index = 0; index < days.length; index++) {
            days[index] = new Day(startOfWeek.plusDays(index));
        }
    }

    private int getDayNumber(Activity activity) {

        if (activity instanceof Lesson) {
            return ((Lesson) activity).getDay().getValue();
        } else {
            return activity.getDate().getDayOfWeek().getValue();
        }
    }

}
