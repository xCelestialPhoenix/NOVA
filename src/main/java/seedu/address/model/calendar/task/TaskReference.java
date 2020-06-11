package seedu.address.model.calendar.task;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * The entity used to reference to a task using only the date and start time.
 */
public class TaskReference extends Task {

    /**
     * Instantiates a new Task reference.
     *
     * @param date the reference date
     * @param time the reference time
     */
    public TaskReference(LocalDate date, LocalTime time) {

        super(null, date, time, null);
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Task)) {
            return false;
        }

        Task task = (Task) obj;

        return !(dueBefore(task) || dueAfter(task));

    }
}
