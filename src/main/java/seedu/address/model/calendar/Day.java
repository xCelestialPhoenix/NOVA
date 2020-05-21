package seedu.address.model.calendar;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import javafx.collections.ObservableList;

import seedu.address.model.calendar.activity.Activity;
import seedu.address.model.calendar.activity.ActivityReference;
import seedu.address.model.calendar.activity.Lesson;
import seedu.address.model.calendar.activity.Meeting;
import seedu.address.model.calendar.activity.UniqueActivityList;

/**
 * The type Day.
 */
public class Day {

    private UniqueActivityList activities = new UniqueActivityList();
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
     */
    public void addActivity(Activity activity) {

        if (activity instanceof Meeting) {
            Meeting meeting = new Meeting((Meeting) activity);
            activities.add(meeting);
        } else if (activity instanceof Lesson) {
            Lesson lesson = new Lesson((Lesson) activity, date);
            activities.add(lesson);
        }
    }

    /**
     * Deletes an activity.
     *
     * @param activityReference the activity reference
     * @return An optional that holds the deleted activity if it exists
     */
    public Optional<Activity> deleteActivity(ActivityReference activityReference) {

        return activities.delete(activityReference);
    }

    /**
     * Gets activities.
     *
     * @return the activities
     */
    public ObservableList<Activity> getActivities() {

        return activities.asUnmodifiableObservableList();
    }

    /**
     * Check availability boolean.
     *
     * @param activity the activity
     * @return the boolean
     */
    public boolean isAddable(Activity activity) {

        return activities.isAddable(activity);
    }

    /**
     * Has activity boolean.
     *
     * @param activity the activity
     * @return the boolean
     */
    public boolean hasActivity(Activity activity) {

        return activities.contains(activity);
    }

    /**
     * Gets first activity.
     *
     * @return the first activity
     */
    public Optional<Activity> getFirstActivity() {

        return activities.getFirstActivity();
    }

    /**
     * Gets next activity.
     *
     * @param timeNow the time now
     * @return the next activity
     */
    public Optional<Activity> getNextActivity(LocalTime timeNow) {

        return activities.getNextActivity(timeNow);
    }

}
