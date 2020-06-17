package seedu.address.logic;

import java.time.LocalDate;
import java.util.Optional;

import javafx.collections.ObservableList;

import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.calendar.activity.Activity;
import seedu.address.model.calendar.task.Task;
import seedu.address.model.calendar.task.TaskCompletionStatistics;

/**
 * API of the Logic component
 */
public interface Logic {

    /**
     * Executes the command and returns the result.
     *
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException   If an error occurs during parsing.
     */
    CommandResult execute(String commandText) throws CommandException, ParseException;

    //=========== Calendar =============================================================

    ObservableList<Activity> getFilteredActivityList();

    ObservableList<Task> getFilteredTaskList();

    Optional<Activity> getNextActivity();

    int calculateWeekNumber(LocalDate refDate);

    TaskCompletionStatistics getTaskCompletionStats();

    //===================================================================================

    GuiSettings getGuiSettings();

    void setGuiSettings(GuiSettings guiSettings);

}
