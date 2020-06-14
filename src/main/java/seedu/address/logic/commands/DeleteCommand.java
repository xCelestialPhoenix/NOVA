package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.commands.AddCommand.MESSAGE_DATE_OUT_OF_BOUND;
import static seedu.address.logic.constants.Messages.MESSAGE_NO_SUCH_ACTIVITY;
import static seedu.address.logic.constants.PrefixConstants.PREFIX_DATE;
import static seedu.address.logic.constants.PrefixConstants.PREFIX_START_TIME;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.calendar.activity.Activity;
import seedu.address.model.calendar.activity.ActivityReference;

/**
 * Represents the command to delete an activity from the calendar in NOVA.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the activity identified by the date and time.\n"
            + "Parameters: "
            + PREFIX_DATE + "DATE (dd/mm/yyyy) "
            + PREFIX_START_TIME + "START TIME (hh:mm) \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_DATE + "19/04/2020 "
            + PREFIX_START_TIME + "10:00 \n";

    public static final String MESSAGE_DELETE_ACTIVITY_SUCCESS = "Deleted Activity: %1$s";

    private final LocalDate targetDate;
    private final LocalTime targetTime;

    public DeleteCommand(LocalDate targetDate, LocalTime targetTime) {

        this.targetDate = targetDate;
        this.targetTime = targetTime;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {

        requireNonNull(model);

        ActivityReference activityReference = new ActivityReference(targetDate, targetTime);
        if (!model.isWithinCalendarTime(activityReference)) {
            throw new CommandException(MESSAGE_DATE_OUT_OF_BOUND);
        }

        Optional<Activity> deletedActivity = model.deleteActivity(activityReference);

        if (deletedActivity.isEmpty()) {
            throw new CommandException(MESSAGE_NO_SUCH_ACTIVITY);
        }
        return new CommandResult(String.format(MESSAGE_DELETE_ACTIVITY_SUCCESS, deletedActivity.get()));
    }

    @Override
    public boolean equals(Object other) {

        return other == this // short circuit if same object
                || (other instanceof DeleteCommand // instanceof handles nulls
                && targetDate.equals(((DeleteCommand) other).targetDate) // state check
                && targetTime.equals(((DeleteCommand) other).targetTime));
    }

}
