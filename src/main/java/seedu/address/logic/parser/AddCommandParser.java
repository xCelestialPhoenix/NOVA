package seedu.address.logic.parser;

import static seedu.address.logic.constants.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.constants.PrefixConstants.PREFIX_DATE;
import static seedu.address.logic.constants.PrefixConstants.PREFIX_DESCRIPTION;
import static seedu.address.logic.constants.PrefixConstants.PREFIX_END_TIME;
import static seedu.address.logic.constants.PrefixConstants.PREFIX_NOTES;
import static seedu.address.logic.constants.PrefixConstants.PREFIX_START_TIME;
import static seedu.address.logic.constants.PrefixConstants.PREFIX_TYPE;
import static seedu.address.logic.constants.PrefixConstants.PREFIX_VENUE;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.calendar.activity.Activity;
import seedu.address.model.calendar.activity.Lesson;
import seedu.address.model.calendar.activity.Meeting;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TYPE, PREFIX_DESCRIPTION,
                        PREFIX_VENUE, PREFIX_DATE, PREFIX_START_TIME,
                        PREFIX_END_TIME, PREFIX_NOTES);

        if (!ParserUtil.arePrefixesPresent(argMultimap, PREFIX_TYPE, PREFIX_DESCRIPTION,
                PREFIX_VENUE, PREFIX_DATE, PREFIX_START_TIME,
                PREFIX_END_TIME) || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        String type = argMultimap.getValue(PREFIX_TYPE).get();
        String desc = argMultimap.getValue(PREFIX_DESCRIPTION).get();
        String venue = argMultimap.getValue(PREFIX_VENUE).get();
        LocalTime startTime = ParserUtil.parseTime(argMultimap.getValue(PREFIX_START_TIME).get());
        LocalTime endTime = ParserUtil.parseTime(argMultimap.getValue(PREFIX_END_TIME).get());
        String notes = argMultimap.getValue(PREFIX_NOTES).isEmpty()
                ? ""
                : argMultimap.getValue(PREFIX_NOTES).get();

        Activity activity;

        switch (type) {
        case "meeting":
            LocalDate date = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get());
            activity = new Meeting(desc, venue, date, startTime, endTime, notes);
            break;
        case "lesson":
            DayOfWeek day = ParserUtil.parseDay(argMultimap.getValue(PREFIX_DATE).get().toUpperCase());
            activity = new Lesson(desc, venue, day, startTime, endTime, notes);
            break;
        default:
            throw new ParseException("Unknown type");
        }
        return new AddCommand(activity);
    }


}
