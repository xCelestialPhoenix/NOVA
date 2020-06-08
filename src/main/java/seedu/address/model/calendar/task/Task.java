package seedu.address.model.calendar.task;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Task {

    protected final String description;
    protected final LocalDate deadlineDate;
    protected final LocalTime deadlineTime;
    protected final String note;
    protected boolean isComplete;

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

    public boolean dueBefore(Task other) {

        return deadlineDate.isBefore(other.deadlineDate) || deadlineTime.isBefore(other.deadlineTime);
    }

    public boolean dueAfter(Task other) {

        return deadlineDate.isAfter(other.deadlineDate) || deadlineTime.isAfter(other.deadlineTime);
    }

    public boolean isCompleted() {

        return isComplete;
    }

    public void complete() {

        isComplete = true;
    }

    @Override
    public boolean equals(Object other) {

        if (this == other) {
            return true;
        }

        if (!(other instanceof Task)) {
            return false;
        }

        Task toCompare = (Task) other;

        return description.equals(toCompare.description)
                && deadlineDate.equals(toCompare.deadlineDate)
                && deadlineTime.equals(toCompare.deadlineTime);
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        sb.append("Description: " + description + "\n");
        sb.append("Deadline: " + deadlineDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                + " " + deadlineTime.format(DateTimeFormatter.ofPattern("hh:mm a")) + "\n");

        if (!note.equals("")) {
            sb.append("Notes: " + note + "\n");
        }
        return sb.toString();
    }
}
