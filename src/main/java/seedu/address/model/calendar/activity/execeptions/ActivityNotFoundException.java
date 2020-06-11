package seedu.address.model.calendar.activity.execeptions;

/**
 * Signals that the operation is unable to find the specified activity (Activities are considered not found if the
 * date and time are the same).
 */
public class ActivityNotFoundException extends RuntimeException {

    public ActivityNotFoundException() {
        super("Operation is unable to find the specified activity");
    }

}
