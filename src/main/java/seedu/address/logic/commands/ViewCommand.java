package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.constants.PrefixConstants.PREFIX_DATE;

import java.time.LocalDate;

import javafx.collections.ObservableList;

import seedu.address.model.Model;
import seedu.address.model.calendar.activity.Activity;

/**
 * The type View command.
 */
public class ViewCommand extends Command {

    /**
     * The constant COMMAND_WORD.
     */
    public static final String COMMAND_WORD = "view";
    /**
     * The constant MESSAGE_USAGE.
     */
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": View the activities on a particular date.\n"
            + "Parameters: "
            + PREFIX_DATE + "DATE \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_DATE + "19/09/2020 ";

    /**
     * The constant MESSAGE_SUCCESS.
     */
    public static final String MESSAGE_SUCCESS = "Activities on that date: \n%1$s";
    /**
     * The constant MESSAGE_NO_ACTIVITY.
     */
    public static final String MESSAGE_NO_ACTIVITY = "There is no activity on that date.";

    private final LocalDate toView;

    /**
     * Instantiates a new View command.
     *
     * @param date the date
     */
    public ViewCommand(LocalDate date) {

        requireNonNull(date);
        toView = date;
    }

    @Override
    public CommandResult execute(Model model) {

        requireNonNull(model);
        ObservableList<Activity> activities = model.viewActivityOnDate(toView);
        if (activities.isEmpty()) {
            return new CommandResult(MESSAGE_NO_ACTIVITY);
        }

        StringBuilder sb = new StringBuilder();
        for (Activity activity : activities) {
            sb.append(activity);
            sb.append("\n");
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, sb));
    }

}
