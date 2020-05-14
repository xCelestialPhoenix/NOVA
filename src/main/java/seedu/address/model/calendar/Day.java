package seedu.address.model.calendar;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import seedu.address.model.calendar.activity.Activity;
import seedu.address.model.calendar.activity.Lesson;
import seedu.address.model.calendar.activity.Meeting;

/**
 * The type Day.
 */
public class Day {

    private List<Activity> activities = new LinkedList<>();
    private LocalDate date;

    /**
     * Instantiates a new Day.
     *
     * @param date the date
     */
    public Day(LocalDate date) {

        this.date = date;
    }

    /**
     * Add activity boolean.
     *
     * @param activity the activity
     * @return the boolean
     */
    public boolean addActivity(Activity activity) {

        for (int index = 0; index < activities.size(); index++) {
            Activity here = activities.get(index);

            if (activity.startBefore(here)) {
                if (activity.endsBefore(here)) {
                    add(activity, index);
                    return true;
                }
                return false;
            }
        }
        // Traversed the entire list with no escape. Activity is added to the end.
        add(activity, activities.size());
        return true;
    }

    /**
     * Check availability boolean.
     *
     * @param activity the activity
     * @return the boolean
     */
    public boolean checkAvailability(Activity activity) {

        boolean isAvailable = true;

        for (Activity tracker : activities) {

            if (activity.startBefore(tracker)) {
                if (!activity.endsBefore(tracker)) {
                    isAvailable = false;
                }
                break;
            }
        }
        return isAvailable;
    }

    /**
     * Adds an activity into the list of activities.
     * @param activity the activity to add
     * @param index the index to add the activity at
     */
    private void add(Activity activity, int index) {

        if (activity instanceof Meeting) {
            Meeting meeting = new Meeting((Meeting) activity);
            activities.add(index, meeting);
        } else if (activity instanceof Lesson) {
            Lesson lesson = new Lesson((Lesson) activity, date);
            activities.add(index, lesson);
        }
    }

}
