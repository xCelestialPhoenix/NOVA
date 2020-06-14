package seedu.address.logic.commands;

import static seedu.address.logic.constants.CalendarConstants.DAYS_PER_WEEK;
import static seedu.address.logic.constants.CalendarConstants.WEEKS_PER_SEMESTER;
import static seedu.address.logic.constants.Messages.MESSAGE_CALENDAR_RESET_SUCCESSFUL;
import static seedu.address.logic.constants.Messages.MESSAGE_INVALID_CALENDAR_START_DAY;
import static seedu.address.logic.constants.PrefixConstants.PREFIX_DATE;

import java.time.LocalDate;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.calendar.Calendar;

/**
 * Represents the command to reset the calendar to a clean slate and adjust the period of the calendar to start on the
 * user specified start date.
 */
public class ResetCommand extends Command {

    public static final String COMMAND_WORD = "reset";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Reset the calendar to a new date range.\n"
            + "Parameters: " + PREFIX_DATE + "DATE\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_DATE + "01/01/2021";

    private final LocalDate newCalendarStartDate;

    public ResetCommand(LocalDate newCalendarStartDate) {

        this.newCalendarStartDate = newCalendarStartDate;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {

        if (!model.isValidStartDay(newCalendarStartDate.getDayOfWeek())) {
            throw new CommandException(MESSAGE_INVALID_CALENDAR_START_DAY);
        }

        LocalDate newCalendarEndDate = newCalendarStartDate.plusDays(DAYS_PER_WEEK * WEEKS_PER_SEMESTER);
        Calendar newCalendar = new Calendar(newCalendarStartDate);
        model.setCalendar(newCalendar);
        return new CommandResult(String.format(MESSAGE_CALENDAR_RESET_SUCCESSFUL, newCalendarStartDate,
                newCalendarEndDate), true, false, false);
    }

}
