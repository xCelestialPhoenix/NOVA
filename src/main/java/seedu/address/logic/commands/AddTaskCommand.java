package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.calendar.task.Task;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.commands.AddCommand.MESSAGE_DATE_OUT_OF_BOUND;
import static seedu.address.logic.constants.PrefixConstants.PREFIX_DATE;
import static seedu.address.logic.constants.PrefixConstants.PREFIX_DESCRIPTION;
import static seedu.address.logic.constants.PrefixConstants.PREFIX_NOTES;
import static seedu.address.logic.constants.PrefixConstants.PREFIX_TIME;

public class AddTaskCommand extends Command {

    public static final String COMMAND_WORD = "task";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task. \n"
            + "Parameters: "
            + PREFIX_DESCRIPTION + "DESC "
            + PREFIX_DATE + "DATE "
            + PREFIX_TIME + "TIME "
            + PREFIX_NOTES + "NOTES \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_DESCRIPTION + "Complete UML diagram "
            + PREFIX_DATE + "19/04/2020 "
            + PREFIX_TIME + "17:00 "
            + PREFIX_NOTES + "Let project lead look through \n";

    public static final String MESSAGE_SUCCESS = "New task added: \n%1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the calendar";

    private final Task toAdd;

    public AddTaskCommand(Task task) {

        requireNonNull(task);
        toAdd = task;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if(!model.isWithinCalendarTime(toAdd.getDeadlineDate())) {
            throw new CommandException(MESSAGE_DATE_OUT_OF_BOUND);
        }

        if (model.hasTask(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        }

        model.addTask(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

}
