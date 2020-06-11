package seedu.address.model.calendar.activity.execeptions;

/**
 * Signals that the operation will result in duplicated Activities (Activities are considered not found if the
 * date and time are the same).
 */
public class DuplicateActivityException extends RuntimeException {

    public DuplicateActivityException() {

        super("Operation would result in duplicate activities");
    }

}
