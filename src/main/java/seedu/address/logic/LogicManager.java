package seedu.address.logic;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;
import java.util.logging.Logger;

import javafx.collections.ObservableList;

import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.NovaParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.calendar.activity.Activity;
import seedu.address.model.calendar.task.Task;
import seedu.address.model.calendar.task.TaskCompletionStatistics;
import seedu.address.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {

    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final NovaParser novaParser;

    public LogicManager(Model model, Storage storage) {

        this.model = model;
        this.storage = storage;
        novaParser = new NovaParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {

        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        Command command = novaParser.parseCommand(commandText);
        commandResult = command.execute(model);

        try {
            storage.saveCalendar(model.getCalendar());
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }

        return commandResult;
    }

    //=========== Calendar =============================================================

    @Override
    public ObservableList<Activity> getFilteredActivityList() {

        return model.getFilteredActivityList();
    }

    @Override
    public ObservableList<Task> getFilteredTaskList() {

        return model.getFilteredTaskList();
    }

    @Override
    public Optional<Activity> getNextActivity() {

        return model.getNextActivity();
    }

    @Override
    public int calculateWeekNumber(LocalDate refDate) {

        return model.calculateWeekNumber(refDate);
    }

    @Override
    public TaskCompletionStatistics getTaskCompletionStats() {

        return model.getTaskCompletionStats();
    }

    //=============================================================================================================

    @Override
    public GuiSettings getGuiSettings() {

        return model.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {

        model.setGuiSettings(guiSettings);
    }

}
