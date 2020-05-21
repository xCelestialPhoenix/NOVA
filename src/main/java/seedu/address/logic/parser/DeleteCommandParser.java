package seedu.address.logic.parser;

import static seedu.address.logic.constants.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.constants.PrefixConstants.PREFIX_DATE;
import static seedu.address.logic.constants.PrefixConstants.PREFIX_START_TIME;

import java.time.LocalDate;
import java.time.LocalTime;

import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DeleteCommandParser implements Parser<DeleteCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns a DeleteCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteCommand parse(String args) throws ParseException {

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_DATE, PREFIX_START_TIME);

        if (!ParserUtil.arePrefixesPresent(argMultimap, PREFIX_DATE, PREFIX_START_TIME)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        }

        try {
            LocalDate targetDate = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get());
            LocalTime targetTime = ParserUtil.parseTime(argMultimap.getValue(PREFIX_START_TIME).get());
            return new DeleteCommand(targetDate, targetTime);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE), pe);
        }
    }

}
