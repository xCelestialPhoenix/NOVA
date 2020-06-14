package seedu.address.logic.constants;

/**
 * Container for user visible messages.
 */
public class Messages {

    // =============================== General ===============================

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_PERSON_DISPLAYED_INDEX = "The person index provided is invalid";
    public static final String MESSAGE_PERSONS_LISTED_OVERVIEW = "%1$d persons listed!";

    // =============================== Calendar ===============================

    public static final String MESSAGE_INVALID_CALENDAR_START_DAY = "The start day has to be a Monday";

    // =============================== Activity ===============================

    public static final String MESSAGE_NO_SUCH_ACTIVITY = "The activity does not exists";
    public static final String MESSAGE_NO_ACTIVITY = "There is no activity on that date.";
    public static final String MESSAGE_INVALID_ACTIVITY = "This activity is not valid";
    public static final String MESSAGE_DATE_OUT_OF_BOUND = "The date is outside of the range of the calendar";
    public static final String MESSAGE_DUPLICATE_ACTIVITY = "The activity already exists in the calendar";
    public static final String MESSAGE_ACTIVITY_OVERLAP = "This activity overlaps with another activity";

    // =============================== Task ===============================

    public static final String MESSAGE_NO_SUCH_TASK = "The task does not exists";
    public static final String MESSAGE_DUPLICATE_TASK = "The task already exists in the calendar";



}
