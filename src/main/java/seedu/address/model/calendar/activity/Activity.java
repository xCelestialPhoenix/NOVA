package seedu.address.model.calendar.activity;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * The type Activity.
 */
public abstract class Activity {

    /**
     * The Date.
     */
    protected LocalDate date;
    /**
     * The Start time.
     */
    protected LocalTime startTime;
    /**
     * The End time.
     */
    protected LocalTime endTime;
    /**
     * The Venue.
     */
    protected String venue;
    /**
     * The Description.
     */
    protected String description;
    /**
     * The Notes.
     */
    protected String notes;

    /**
     * Gets the activity start time.
     *
     * @return the start time
     */
    public LocalTime getStartTime() {

        return startTime;
    }

    /**
     * Gets the activity end time.
     *
     * @return the end time
     */
    public LocalTime getEndTime() {

        return endTime;
    }

    /**
     * Gets venue.
     *
     * @return the venue
     */
    public String getVenue() {

        return venue;
    }

    /**
     * Gets description.
     *
     * @return the description
     */
    public String getDescription() {

        return description;
    }

    /**
     * Gets notes.
     *
     * @return the notes
     */
    public String getNotes() {

        return notes;
    }

    /**
     * Gets date.
     *
     * @return the date
     */
    public LocalDate getDate() {

        return date;
    }

    /**
     * Start before boolean.
     *
     * @param activity the activity
     * @return the boolean
     */
    public boolean startBefore(Activity activity) {

        return startTime.isBefore(activity.startTime);
    }

    /**
     * Ends before boolean.
     *
     * @param activity the activity
     * @return the boolean
     */
    public boolean endsBefore(Activity activity) {

        return endTime.isBefore(activity.startTime);
    }

}
