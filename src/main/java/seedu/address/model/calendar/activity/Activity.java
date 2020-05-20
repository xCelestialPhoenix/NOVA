package seedu.address.model.calendar.activity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

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
     * Is valid boolean.
     *
     * @return the boolean
     */
    public boolean isValid() {

        return startTime.isBefore(endTime);
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
     * Start during boolean.
     *
     * @param activity the activity
     * @return the boolean
     */
    public boolean startDuring(Activity activity) {

        return startTime.isAfter(activity.startTime) && startTime.isBefore(activity.endTime);
    }

    /**
     * Ends before boolean.
     *
     * @param activity the activity
     * @return the boolean
     */
    public boolean endsBefore(Activity activity) {

        return !endTime.isAfter(activity.startTime);
    }

    /**
     * Ends with boolean.
     *
     * @param activity the activity
     * @return the boolean
     */
    public boolean endsWith(Activity activity) {

        return endTime.equals(activity.endTime);
    }

    /**
     * On same date boolean.
     *
     * @param date the date
     * @return the boolean
     */
    public boolean onSameDate(LocalDate date) {

        return this.date.equals(date);
    }

    /**
     * At same time boolean.
     *
     * @param start the start
     * @param end   the end
     * @return the boolean
     */
    public boolean atSameTime(LocalTime start, LocalTime end) {

        return startTime.equals(start) && endTime.equals(end);
    }

    /**
     * At same venue boolean.
     *
     * @param venue the venue
     * @return the boolean
     */
    public boolean atSameVenue(String venue) {

        return this.venue.equals(venue);
    }

    /**
     * Has same description boolean.
     *
     * @param description the description
     * @return the boolean
     */
    public boolean hasSameDescription(String description) {

        return this.description.equals(description);
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        sb.append("Description: " + description + "\n");
        sb.append("Date: " + date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + "\n");
        sb.append("Time: " + startTime + " - " + endTime + "\n");
        sb.append("Venue: " + venue + "\n");

        if (!notes.equals("")) {
            sb.append("Notes: " + notes + "\n");
        }
        return sb.toString();
    }

}
