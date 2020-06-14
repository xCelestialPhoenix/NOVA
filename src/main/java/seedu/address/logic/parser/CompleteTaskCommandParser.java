package seedu.address.logic.parser;

import static seedu.address.logic.constants.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.constants.PrefixConstants.PREFIX_DATE;
import static seedu.address.logic.constants.PrefixConstants.PREFIX_TIME;

import java.time.LocalDate;
import java.time.LocalTime;

import seedu.address.logic.commands.CompleteTaskCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class CompleteTaskCommandParser implements Parser<CompleteTaskCommand> {

    @Override
    public CompleteTaskCommand parse(String args) throws ParseException {

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_DATE, PREFIX_TIME);

        if (!ParserUtil.arePrefixesPresent(argMultimap, PREFIX_DATE, PREFIX_TIME)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, CompleteTaskCommand.MESSAGE_USAGE));
        }

        LocalDate date = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get());
        LocalTime time = ParserUtil.parseTime(argMultimap.getValue(PREFIX_TIME).get());

        return new CompleteTaskCommand(date, time);
    }

}
