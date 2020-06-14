package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.constants.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.constants.PrefixConstants.PREFIX_DATE;

import java.time.LocalDate;

import seedu.address.logic.commands.ResetCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class ResetCommandParser implements Parser<ResetCommand> {

    @Override
    public ResetCommand parse(String args) throws ParseException {

        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_DATE);
        if (argMultimap.getValue(PREFIX_DATE).isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ResetCommand.MESSAGE_USAGE));
        }
        LocalDate newStartDate = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get());
        return new ResetCommand(newStartDate);
    }

}
