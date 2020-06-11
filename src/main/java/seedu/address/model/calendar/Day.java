package seedu.address.model.calendar;

import java.time.LocalDate;
import java.util.Optional;

import javafx.collections.ObservableList;

import seedu.address.model.calendar.activity.Activity;
import seedu.address.model.calendar.activity.ActivityReference;
import seedu.address.model.calendar.activity.Lesson;
import seedu.address.model.calendar.activity.Meeting;
import seedu.address.model.calendar.activity.UniqueActivityList;

/**
 * The day representation within the calendar of NOVA. Also contains an activity list to record all the activities
 * happening on the day.
 */
public class Day {

    private UniqueActivityList activities = new UniqueActivityList();
    private LocalDate date;

    /**
     * Instantiates a new Day.
     *
     * @param date the date of the day
     */
    public Day(LocalDate date) {

        this.date = date;
    }

    //============================= Modifications to the day ==============================

    /**
     * Adds an activity into the activity list.
     *
     * @param activity the activity to be added
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
     * Deletes an activity from the day.
     *
     * @param activityReference the scaled-down version of an activity used to identify the actual activity
     * @return An optional that holds the deleted activity if it exists
     */
    public Optional<Activity> deleteActivity(ActivityReference activityReference) {

        return activities.delete(activityReference);
    }

    //======================================= Getters ========================================

    /**
     * Gets the activities happening on a specific date.
     *
     * @return the list of activities happening on that date
     */
    public ObservableList<Activity> getActivities() {

        return activities.asUnmodifiableObservableList();
    }

    /**
     * Gets the first activity of the week.
     *
     * @return an optional that holds the first activity of the day if it exists
     */
    public Optional<Activity> getFirstActivity() {

        return activities.getFirstActivity();
    }

    /**
     * Gets the next upcoming activity from now.
     *
     * @return an optional that holds the next activity if it exists
     */
    public Optional<Activity> getNextActivity() {

        return activities.getNextActivity();
    }

    //======================================= Utilities ========================================

    /**
     * Returns true if the activity can be added into the week.
     *
     * @param activity the activity to be tested
     * @return true if the activity can be added into the week
     */
    public boolean isAddable(Activity activity) {

        return activities.isAddable(activity);
    }

    /**
     * Returns true if the activity of interest is already in the calendar.
     *
     * @param activity the activity to be tested
     * @return true if the activity is already in the calendar
     */
    public boolean hasActivity(Activity activity) {

        return activities.contains(activity);
    }

}
