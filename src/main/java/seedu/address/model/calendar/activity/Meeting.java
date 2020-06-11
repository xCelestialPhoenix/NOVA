package seedu.address.model.calendar.activity;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * The Meeting type activity representation to mark important meetings in the calendar of NOVA.
 */
public class Meeting extends Activity {

    /**
     * Instantiates a new Meeting.
     *
     * @param date        the meeting date
     * @param startTime   the start time
     * @param endTime     the end time
     * @param venue       the venue
     * @param description the description
     * @param notes       additional side notes
     */
    public Meeting(String description, String venue, LocalDate date, LocalTime startTime, LocalTime endTime,
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
     * @param toClone the meeting to be clone
     */
    public Meeting(Meeting toClone) {

        this.date = toClone.date;
        this.startTime = toClone.startTime;
        this.endTime = toClone.endTime;
        this.venue = toClone.venue;
        this.description = toClone.description;
        this.notes = toClone.notes;
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

        return hasSameDescription(act)
                && atSameVenue(act)
                && onSameDate(act)
                && atSameTime(act);
    }

}
