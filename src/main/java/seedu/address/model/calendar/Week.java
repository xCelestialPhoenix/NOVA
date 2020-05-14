package seedu.address.model.calendar;

import static seedu.address.commons.contants.CalendarConstants.DAYS_PER_WEEK;

import java.time.LocalDate;

import seedu.address.model.calendar.activity.Activity;

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
     * @return the boolean
     */
    public boolean addActivity(Activity activity) {

        int dayNumber = activity.getDate().getDayOfWeek().getValue();
        return days[dayNumber].addActivity(activity);
    }

    /**
     * Check availability boolean.
     *
     * @param activity the activity
     * @return the boolean
     */
    public boolean checkAvailability(Activity activity) {

        int dayNumber = activity.getDate().getDayOfWeek().getValue();
        return days[dayNumber].checkAvailability(activity);
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
     * Gets start of week.
     *
     * @return the start of week
     */
    public LocalDate getStartOfWeek() {

        return startOfWeek;
    }

    /**
     * Initializes all the days within the week.
     */

    private void initDays() {

        for (int index = 0; index < days.length; index++) {
            days[index] = new Day(startOfWeek.plusDays(index));
        }
    }

}
