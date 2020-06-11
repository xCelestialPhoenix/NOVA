package seedu.address.model.calendar.task;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * The Task representation to track important task to complete in the calendar of NOVA.
 */
public class Task {

    /**
     * The Task description.
     */
    protected final String description;
    /**
     * The due date.
     */
    protected final LocalDate dueDate;
    /**
     * The due timing.
     */
    protected final LocalTime dueTime;
    /**
     * Additional side notes.
     */
    protected final String note;
    /**
     * The completion status of the task.
     */
    protected boolean isComplete;

    /**
     * Instantiates a new Task.
     *
     * @param description  the task description
     * @param dueDate the deadline date
     * @param dueTime the deadline time
     * @param note         the additional side notes
     */
    public Task(String description, LocalDate dueDate, LocalTime dueTime, String note) {

        this.description = description;
        this.dueDate = dueDate;
        this.dueTime = dueTime;
        this.note = note;
        isComplete = false;
    }

    //============================= Getters ==============================

    /**
     * Gets the description of the task.
     *
     * @return the description of the task
     */
    public String getDescription() {

        return description;
    }

    /**
     * Gets the due date.
     *
     * @return the due date
     */
    public LocalDate getDueDate() {

        return dueDate;
    }

    /**
     * Gets the due timing.
     *
     * @return the due timing
     */
    public LocalTime getDueTime() {

        return dueTime;
    }

    /**
     * Gets the additional side notes.
     *
     * @return the additional side note
     */
    public String getNote() {

        return note;
    }

    //============================= Utilities ==============================

    /**
     * Returns true if this task is due before the other task.
     *
     * @param other the other task to be checked
     * @return true if this task is due before the other task
     */
    public boolean dueBefore(Task other) {

        return dueDate.isBefore(other.dueDate) || dueTime.isBefore(other.dueTime);
    }

    /**
     * Due after boolean.
     *
     * @param other the other
     * @return the boolean
     */
    public boolean dueAfter(Task other) {

        return dueDate.isAfter(other.dueDate) || dueTime.isAfter(other.dueTime);
    }

    /**
     * Is completed boolean.
     *
     * @return the boolean
     */
    public boolean isCompleted() {

        return isComplete;
    }

    /**
     * Complete.
     */
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
                && dueDate.equals(toCompare.dueDate)
                && dueTime.equals(toCompare.dueTime);
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        sb.append("Description: " + description + "\n");
        sb.append("Deadline: " + dueDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                + " " + dueTime.format(DateTimeFormatter.ofPattern("hh:mm a")) + "\n");

        if (!note.equals("")) {
            sb.append("Notes: " + note + "\n");
        }
        return sb.toString();
    }
}
