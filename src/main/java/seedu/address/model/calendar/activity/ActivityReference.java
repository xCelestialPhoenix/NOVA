package seedu.address.model.calendar.activity;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * The type used to reference to an activity given only the date and start time.
 */
public class ActivityReference extends Activity {

    /**
     * Instantiates a new Activity reference.
     *
     * @param date      the reference date
     * @param startTime the reference start time
     */
    public ActivityReference(LocalDate date, LocalTime startTime) {

        this.date = date;
        this.startTime = startTime;

        this.description = null;
        this.venue = null;
        this.endTime = null;
        this.notes = null;
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Activity)) {
            return false;
        }

        Activity act = (Activity) obj;

        return onSameDate(act.date) && hasSameStartTime(act.startTime);

    }

    private boolean hasSameStartTime(LocalTime startTime) {

        return this.startTime.equals(startTime);
    }

}
