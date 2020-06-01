package seedu.address.model.calendar.activity.task;

import java.time.LocalDate;
import java.time.LocalTime;

import seedu.address.model.calendar.activity.Deadline;

public class Task {

    private final String description;
    private final LocalDate deadlineDate;
    private final LocalTime deadlineTime;
    private final String note;
    private boolean isComplete;

    public Task(String description, LocalDate deadlineDate, LocalTime deadlineTime, String note) {
        this.description = description;
        this.deadlineDate = deadlineDate;
        this.deadlineTime = deadlineTime;
        this.note = note;
        isComplete = false;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getDeadlineDate() {
        return deadlineDate;
    }

    public LocalTime getDeadlineTime() {
        return deadlineTime;
    }

    public String getNote() {
        return note;
    }

    public boolean isCompleted() {
        return isComplete;
    }

    public void complete() {
        isComplete = true;
    }

}
