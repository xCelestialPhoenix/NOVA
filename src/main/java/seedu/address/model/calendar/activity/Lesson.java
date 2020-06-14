package seedu.address.model.calendar.activity;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * The Lesson type activity representation to mark school lessons in the calendar of NOVA.
 */
public class Lesson extends Activity {

    private final DayOfWeek day;

    /**
     * Instantiates a new Lesson.
     *
     * @param description the description
     * @param venue       the venue
     * @param day         the lesson day
     * @param startTime   the start time
     * @param endTime     the end time
     * @param notes       the notes
     */
    public Lesson(String description, String venue, DayOfWeek day, LocalTime startTime, LocalTime endTime,
                  String notes) {

        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
        this.venue = venue;
        this.description = description;
        this.notes = notes;

        // The first creation of a lesson does not have a date because it will be repeated across the academic semester
        date = null;
    }

    /**
     * Clones a new Lesson and instantiates the date.
     *
     * @param lesson the lesson
     * @param date   the date
     */
    public Lesson(Lesson lesson, LocalDate date) {

        this.date = date;
        this.day = lesson.day;
        this.startTime = lesson.startTime;
        this.endTime = lesson.endTime;
        this.venue = lesson.venue;
        this.description = lesson.description;
        this.notes = lesson.notes;
    }

    //============================= Getters ==============================

    /**
     * Gets the day of the week when the lesson happens.
     *
     * @return the day
     */
    public DayOfWeek getDay() {

        return day;
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        sb.append("Description: " + description + "\n")
                .append("Time: " + startTime + " - " + endTime + "\n")
                .append("Venue: " + venue + "\n");

        if (date == null) {
            sb.append("Day: " + day.toString().toLowerCase() + "\n");
        } else {
            sb.append("Date: " + date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + "\n");
        }

        if (!notes.equals("")) {
            sb.append("Notes: " + notes + "\n");
        }
        return sb.toString();

    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Lesson)) {
            return false;
        }

        Lesson act = (Lesson) obj;

        return hasSameDescription(act)
                && atSameVenue(act)
                && onSameDay(act)
                && atSameTime(act);
    }

    /**
     * Returns true if this lesson and the other lesson falls on the same day of the week.
     *
     * @param otherLesson the lesson to be checked
     * @return true if this lesson and the other lesson falls on the same day of the week
     */
    private boolean onSameDay(Lesson otherLesson) {

        return day.equals(otherLesson.day);
    }

}
