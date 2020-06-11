package seedu.address.model.calendar.activity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * The Activity representation in the calendar of NOVA.
 */
public abstract class Activity {

    /**
     * The description of the activity.
     */
    protected String description;
    /**
     * The venue where the activity is at.
     */
    protected String venue;
    /**
     * The date when the activity happens.
     */
    protected LocalDate date;
    /**
     * The start time of the activity.
     */
    protected LocalTime startTime;
    /**
     * The end time of the activity.
     */
    protected LocalTime endTime;
    /**
     * Additional side notes of the activity.
     */
    protected String notes;

    //============================= Getters ==============================

    /**
     * Gets the activity description.
     *
     * @return the activity description
     */
    public String getDescription() {

        return description;
    }

    /**
     * Gets the activity venue.
     *
     * @return the activity venue
     */
    public String getVenue() {

        return venue;
    }

    /**
     * Gets the activity date.
     *
     * @return the activity date
     */
    public LocalDate getDate() {

        return date;
    }

    /**
     * Gets the activity start time.
     *
     * @return the activity start time
     */
    public LocalTime getStartTime() {

        return startTime;
    }

    /**
     * Gets the activity end time.
     *
     * @return the activity end time
     */
    public LocalTime getEndTime() {

        return endTime;
    }

    /**
     * Gets the activity notes.
     *
     * @return the activity notes
     */
    public String getNotes() {

        return notes;
    }

    //============================= Utilities ==============================

    /**
     * Returns true if the activity is valid.
     *
     * @return true if the activity is valid
     */
    public boolean isValid() {

        return startTime.isBefore(endTime);
    }

    /**
     * Returns true if the calling activity has the same description with the other activity.
     *
     * @param otherActivity the activity to compare with
     * @return true if the calling activity has the same description with the other activity
     */
    public boolean hasSameDescription(Activity otherActivity) {

        return description.equals(otherActivity.description);
    }

    /**
     * Returns true if the calling activity happens at the same venue with the other activity.
     *
     * @param otherActivity the activity to compare with
     * @return true if the calling activity happens at the same venue with the other activity
     */
    public boolean atSameVenue(Activity otherActivity) {

        return venue.equals(otherActivity.venue);
    }

    /**
     * Returns true if the calling activity happens on the same date with the other activity.
     *
     * @param otherActivity the activity to compare with
     * @return true if the calling activity happens on the same date with the other activity
     */
    public boolean onSameDate(Activity otherActivity) {

        return date.equals(otherActivity.date);
    }

    /**
     * Returns true if the calling activity happens at the same time with the other activity.
     *
     * @param otherActivity the activity to compare with
     * @return true if the calling activity happens at the same time with the other activity
     */
    public boolean atSameTime(Activity otherActivity) {

        return startTime.equals(otherActivity.startTime) && endTime.equals(otherActivity.endTime);
    }

    /**
     * Returns true if the calling activity starts before the other activity.
     *
     * @param otherActivity the activity to compare with
     * @return true if the calling activity starts before the other activity
     */
    public boolean startBefore(Activity otherActivity) {

        return startTime.isBefore(otherActivity.startTime);
    }

    /**
     * Returns true if the calling activity starts together with the other activity.
     *
     * @param otherActivity the activity to compare with
     * @return true if the calling activity starts together with the other activity
     */
    public boolean startsWith(Activity otherActivity) {

        return startTime.equals(otherActivity.startTime);
    }

    /**
     * Returns true if the calling activity starts during the other activity.
     *
     * @param otherActivity the activity to compare with
     * @return true if the calling activity starts during the other activity
     */
    public boolean startDuring(Activity otherActivity) {

        return startTime.isAfter(otherActivity.startTime) && startTime.isBefore(otherActivity.endTime);
    }

    /**
     * Returns true if the calling activity ends before the other activity.
     *
     * @param otherActivity the activity to compare with
     * @return true if the calling activity ends before the other activity
     */
    public boolean endsBefore(Activity otherActivity) {

        return !endTime.isAfter(otherActivity.startTime);
    }

    /**
     * Returns true if the calling activity ends together with the other activity.
     *
     * @param otherActivity the activity to compare with
     * @return true if the calling activity ends together with the other activity
     */
    public boolean endsWith(Activity otherActivity) {

        return endTime.equals(otherActivity.endTime);
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
