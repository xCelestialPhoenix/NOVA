package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.constants.PrefixConstants.PREFIX_DATE;
import static seedu.address.logic.constants.PrefixConstants.PREFIX_DESCRIPTION;
import static seedu.address.logic.constants.PrefixConstants.PREFIX_END_TIME;
import static seedu.address.logic.constants.PrefixConstants.PREFIX_NOTES;
import static seedu.address.logic.constants.PrefixConstants.PREFIX_START_TIME;
import static seedu.address.logic.constants.PrefixConstants.PREFIX_TYPE;
import static seedu.address.logic.constants.PrefixConstants.PREFIX_VENUE;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.calendar.activity.Activity;
import seedu.address.model.calendar.activity.Lesson;

/**
 * Adds a person to the address book.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an activity to the calendar. \n"
            + "Parameters: "
            + PREFIX_TYPE + "TYPE "
            + PREFIX_DESCRIPTION + "DESC "
            + PREFIX_VENUE + "VENUE "
            + PREFIX_DATE + "DATE "
            + PREFIX_START_TIME + "START "
            + PREFIX_END_TIME + "END "
            + PREFIX_NOTES + "NOTES \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_TYPE + "meeting "
            + PREFIX_DESCRIPTION + "Project Meeting "
            + PREFIX_VENUE + "School "
            + PREFIX_DATE + "19/04/2020 "
            + PREFIX_START_TIME + "10:00 "
            + PREFIX_END_TIME + "11:00 "
            + PREFIX_NOTES + "Prepare findings \n";

    public static final String MESSAGE_SUCCESS = "New activity added: \n%1$s";
    public static final String MESSAGE_DUPLICATE_ACTIVITY = "This activity already exists in the calendar";
    public static final String MESSAGE_DATE_OUT_OF_BOUND = "The date is outside of the range of the calendar";
    public static final String MESSAGE_ACTIVITY_OVERLAP = "This activity overlaps with another activity";
    public static final String MESSAGE_INVALID_ACTIVITY = "This activity is not valid";

    private final Activity toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public AddCommand(Activity activity) {

        requireNonNull(activity);
        toAdd = activity;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {

        requireNonNull(model);

        if (!toAdd.isValid()) {
            throw new CommandException(MESSAGE_INVALID_ACTIVITY);
        }

        if (!(toAdd instanceof Lesson) && !model.isWithinCalendarTime(toAdd)) {
            throw new CommandException(MESSAGE_DATE_OUT_OF_BOUND);
        }

        if (model.hasActivity(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_ACTIVITY);
        }

        if (!model.isAddable(toAdd)) {
            throw new CommandException(MESSAGE_ACTIVITY_OVERLAP);
        }

        model.addActivity(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));

    }

    @Override
    public boolean equals(Object other) {

        return other == this // short circuit if same object
                || (other instanceof AddCommand // instanceof handles nulls
                && toAdd.equals(((AddCommand) other).toAdd));
    }

}
