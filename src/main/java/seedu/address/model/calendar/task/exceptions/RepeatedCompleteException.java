package seedu.address.model.calendar.task.exceptions;

public class RepeatedCompleteException extends Exception {

    public RepeatedCompleteException(String message) {

        super(message);
    }

    /**
     * Constructs a new {@code CommandException} with the specified detail {@code message} and {@code cause}.
     */
    public RepeatedCompleteException(String message, Throwable cause) {

        super(message, cause);
    }

}
