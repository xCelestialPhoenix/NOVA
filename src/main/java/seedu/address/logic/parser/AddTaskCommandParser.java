package seedu.address.logic.parser;

import java.time.LocalDate;
import java.time.LocalTime;

import seedu.address.logic.commands.AddTaskCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.calendar.task.Task;

import static seedu.address.logic.constants.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.constants.PrefixConstants.PREFIX_DATE;
import static seedu.address.logic.constants.PrefixConstants.PREFIX_DESCRIPTION;
import static seedu.address.logic.constants.PrefixConstants.PREFIX_NOTES;
import static seedu.address.logic.constants.PrefixConstants.PREFIX_TIME;

public class AddTaskCommandParser implements Parser<AddTaskCommand> {

    @Override
    public AddTaskCommand parse(String args) throws ParseException {

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_DESCRIPTION, PREFIX_DATE, PREFIX_TIME, PREFIX_NOTES);

        if (!ParserUtil.arePrefixesPresent(argMultimap, PREFIX_DESCRIPTION, PREFIX_DATE, PREFIX_TIME)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTaskCommand.MESSAGE_USAGE));
        }

        String desc = argMultimap.getValue(PREFIX_DESCRIPTION).get();
        LocalDate date = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get());
        LocalTime time = ParserUtil.parseTime(argMultimap.getValue(PREFIX_TIME).get());
        String notes = argMultimap.getValue(PREFIX_NOTES).isEmpty()
                ? ""
                : argMultimap.getValue(PREFIX_NOTES).get();

        Task task = new Task(desc, date, time, notes);

        return new AddTaskCommand(task);
    }

}
