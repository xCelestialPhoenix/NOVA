package seedu.address.model.calendar.activity.execeptions;

/**
 * The type Duplicate activity exception.
 */
public class DuplicateActivityException extends RuntimeException {

    /**
     * Instantiates a new Duplicate activity exception.
     */
    public DuplicateActivityException() {

        super("Operation would result in duplicate activities");
    }

}
