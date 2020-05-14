package seedu.address.model.calendar.activity;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * The Lesson type activity.
 */
public class Lesson extends Activity {

    /**
     * Instantiates a new Lesson.
     *
     * @param date        the date
     * @param startTime   the start time
     * @param endTime     the end time
     * @param venue       the venue
     * @param description the description
     * @param notes       the notes
     */
    public Lesson(LocalDate date, LocalTime startTime, LocalTime endTime, String venue, String description,
                  String notes) {

        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.venue = venue;
        this.description = description;
        this.notes = notes;
    }

    /**
     * Clones a new Lesson and instantiates the date.
     *
     * @param lesson the lesson
     * @param date   the date
     */
    public Lesson(Lesson lesson, LocalDate date) {

        this.date = date;
        this.startTime = lesson.startTime;
        this.endTime = lesson.endTime;
        this.venue = lesson.venue;
        this.description = lesson.description;
        this.notes = lesson.notes;
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

        return onSameDay(act.date)
                && atSameTime(act.startTime, act.endTime)
                && atSameVenue(act.venue)
                && hasSameDescription(act.description);
    }

    private boolean onSameDay(LocalDate date) {

        return this.date.getDayOfWeek().equals(date.getDayOfWeek());
    }

    private boolean atSameTime(LocalTime start, LocalTime end) {

        return startTime.equals(start) && endTime.equals(end);
    }

    private boolean atSameVenue(String venue) {

        return this.venue.equals(venue);
    }

    private boolean hasSameDescription(String description) {

        return this.description.equals(description);
    }

}
