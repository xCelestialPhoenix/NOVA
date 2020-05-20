package seedu.address.logic.parser;

import static seedu.address.logic.constants.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.constants.PrefixConstants.PREFIX_ACTIVITY_DATE;

import java.time.LocalDate;
import java.util.stream.Stream;

import seedu.address.logic.commands.ViewCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * The type View command parser.
 */
public class ViewCommandParser implements Parser<ViewCommand> {

    @Override
    public ViewCommand parse(String args) throws ParseException {

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_ACTIVITY_DATE);

        if (!arePrefixesPresent(argMultimap, PREFIX_ACTIVITY_DATE) || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE));
        }

        LocalDate date = ParserUtil.parseDate(argMultimap.getValue(PREFIX_ACTIVITY_DATE).get());
        return new ViewCommand(date);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {

        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
