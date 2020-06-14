package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import static seedu.address.logic.constants.Messages.MESSAGE_DATE_OUT_OF_BOUND;
import static seedu.address.logic.constants.Messages.MESSAGE_NO_SUCH_TASK;
import static seedu.address.logic.constants.PrefixConstants.PREFIX_DATE;
import static seedu.address.logic.constants.PrefixConstants.PREFIX_TIME;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.calendar.task.Task;
import seedu.address.model.calendar.task.TaskReference;

/**
 * Represents the command to delete a task from the calendar of NOVA.
 */
public class DeleteTaskCommand extends Command {

    public static final String COMMAND_WORD = "remove";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Removes the task identified by the date and time.\n"
            + "Parameters: "
            + PREFIX_DATE + "DATE (dd/mm/yyyy) "
            + PREFIX_TIME + "START TIME (hh:mm) \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_DATE + "19/04/2020 "
            + PREFIX_TIME + "10:00 \n";

    public static final String MESSAGE_SUCCESS = "Removed Task: \n%1$s";

    private final LocalDate targetDate;
    private final LocalTime targetTime;

    public DeleteTaskCommand(LocalDate targetDate, LocalTime targetTime) {

        this.targetDate = targetDate;
        this.targetTime = targetTime;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {

        requireNonNull(model);

        if (!model.isWithinCalendarTime(targetDate)) {
            throw new CommandException((MESSAGE_DATE_OUT_OF_BOUND));
        }

        TaskReference taskReference = new TaskReference(targetDate, targetTime);
        Optional<Task> deletedTask = model.deleteTask(taskReference);

        if (deletedTask.isEmpty()) {
            throw new CommandException(MESSAGE_NO_SUCH_TASK);
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, deletedTask.get()));
    }

    @Override
    public boolean equals(Object other) {

        return other == this // short circuit if same object
                || (other instanceof DeleteTaskCommand // instanceof handles nulls
                && targetDate.equals(((DeleteTaskCommand) other).targetDate) // state check
                && targetTime.equals(((DeleteTaskCommand) other).targetTime));
    }

}
