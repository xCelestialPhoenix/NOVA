package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.calendar.task.Task;

public class JsonAdaptedTask {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Task's %s field is missing!";

    private final String description;
    private final LocalDate dueDate;
    private final LocalTime dueTime;
    private final String note;
    private final boolean isCompleted;

    @JsonCreator
    public JsonAdaptedTask(@JsonProperty("descritpion") String description,
                           @JsonProperty("dueDate") LocalDate dueDate,
                           @JsonProperty("dueTime") LocalTime dueTime,
                           @JsonProperty("note") String note,
                           @JsonProperty("isCompleted") boolean isCompleted) {

        this.description = description;
        this.dueDate = dueDate;
        this.dueTime = dueTime;
        this.note = note;
        this.isCompleted = isCompleted;
    }

    public JsonAdaptedTask(Task source) {

        requireNonNull(source);

        description = source.getDescription();
        dueDate = source.getDueDate();
        dueTime = source.getDueTime();
        note = source.getNote();
        isCompleted = source.isCompleted();
    }

    public Task toModelType() throws IllegalValueException {

        if (description == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Description"));
        }

        if (dueDate == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Due Date"));
        }

        if (dueTime == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Due Time"));
        }

        if (note == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Note"));
        }

        Task task = new Task(description, dueDate, dueTime, note);

        if (isCompleted) {
            task.complete();
        }

        return task;
    }

}
