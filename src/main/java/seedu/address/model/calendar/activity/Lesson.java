package seedu.address.model.calendar.activity;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * The Lesson type activity.
 */
public class Lesson extends Activity {

    private final DayOfWeek day;

    /**
     * Instantiates a new Lesson.
     *
     * @param day         the day
     * @param startTime   the start time
     * @param endTime     the end time
     * @param venue       the venue
     * @param description the description
     * @param notes       the notes
     */
    public Lesson(DayOfWeek day, LocalTime startTime, LocalTime endTime, String venue, String description,
                  String notes) {

        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
        this.venue = venue;
        this.description = description;
        this.notes = notes;
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

    public DayOfWeek getDay() {

        return day;
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        sb.append("Description: " + description + "\n");
        if (date == null) {
            sb.append("Day: " + day + "\n");
        } else {
            sb.append("Date: " + date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + "\n");
        }
        sb.append("Time: " + startTime + " - " + endTime + "\n");
        sb.append("Venue: " + venue + "\n");

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

        return onSameDay(act.day)
                && atSameTime(act.startTime, act.endTime)
                && atSameVenue(act.venue)
                && hasSameDescription(act.description);
    }

    private boolean onSameDay(DayOfWeek day) {

        return this.day.equals(day);
    }

}
