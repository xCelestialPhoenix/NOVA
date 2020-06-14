package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.commands.AddCommand.MESSAGE_DATE_OUT_OF_BOUND;
import static seedu.address.logic.constants.Messages.MESSAGE_NO_SUCH_TASK;
import static seedu.address.logic.constants.PrefixConstants.PREFIX_DATE;
import static seedu.address.logic.constants.PrefixConstants.PREFIX_TIME;
import static seedu.address.model.calendar.task.UniqueTaskList.MESSAGE_REPEATED_COMPLETE;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.calendar.task.Task;
import seedu.address.model.calendar.task.TaskReference;
import seedu.address.model.calendar.task.exceptions.RepeatedCompleteException;

/**
 * Represents the command to mark a specified task as completed.
 */
public class CompleteTaskCommand extends Command {

    public static final String COMMAND_WORD = "complete";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Marks the task as completed.\n"
            + "Parameters: "
            + PREFIX_DATE + "DATE (dd/mm/yyyy) "
            + PREFIX_TIME + "TIME (hh:mm) \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_DATE + "08/06/2020 "
            + PREFIX_TIME + "17:00 \n";

    public static final String MESSAGE_TASK_COMPLETE_SUCCESSFUL = "Task is done: \n%1$s";

    private final LocalDate targetDate;
    private final LocalTime targetTime;

    public CompleteTaskCommand(LocalDate targetDate, LocalTime targetTime) {

        this.targetDate = targetDate;
        this.targetTime = targetTime;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {

        requireNonNull(model);

        if (!model.isWithinCalendarTime(targetDate)) {
            throw new CommandException(MESSAGE_DATE_OUT_OF_BOUND);
        }

        TaskReference taskReference = new TaskReference(targetDate, targetTime);
        try {
            Optional<Task> completedTask = model.completeTask(taskReference);

            if (completedTask.isEmpty()) {
                throw new CommandException(MESSAGE_NO_SUCH_TASK);
            }
            return new CommandResult(String.format(MESSAGE_TASK_COMPLETE_SUCCESSFUL, completedTask.get()));

        } catch (RepeatedCompleteException e) {
            throw new CommandException(MESSAGE_REPEATED_COMPLETE);
        }
    }

}
