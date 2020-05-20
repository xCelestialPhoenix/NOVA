package seedu.address.model.calendar.activity;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * The Meeting type activity.
 */
public class Meeting extends Activity {

    /**
     * Instantiates a new Meeting.
     *
     * @param date        the date
     * @param startTime   the start time
     * @param endTime     the end time
     * @param venue       the venue
     * @param description the description
     * @param notes       the notes
     */
    public Meeting(LocalDate date, LocalTime startTime, LocalTime endTime, String venue, String description,
                   String notes) {

        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.venue = venue;
        this.description = description;
        this.notes = notes;
    }

    /**
     * Clones a new Meeting.
     *
     * @param meeting the meeting
     */
    public Meeting(Meeting meeting) {

        this.date = meeting.date;
        this.startTime = meeting.startTime;
        this.endTime = meeting.endTime;
        this.venue = meeting.venue;
        this.description = meeting.description;
        this.notes = meeting.notes;
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Meeting)) { //this handles null as well.
            return false;
        }

        Meeting act = (Meeting) obj;

        return onSameDate(act.date)
                && atSameTime(act.startTime, act.endTime)
                && atSameVenue(act.venue)
                && hasSameDescription(act.description);
    }
}
