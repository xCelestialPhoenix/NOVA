package seedu.address.model.calendar.task;

import java.time.LocalDate;
import java.time.LocalTime;

public class TaskReference extends Task {

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
