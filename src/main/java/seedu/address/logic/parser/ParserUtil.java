package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.stream.Stream;

import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_DAY = "The day is not recognized.";
    public static final String MESSAGE_INVALID_DATE = "The date is invalid. Please enter in the form dd/mm/yyyy.";
    public static final String MESSAGE_INVALID_TIME = "The time is invalid. Please enter in the form hh:mm.";

    /**
     * Parse date local date.
     *
     * @param date the date
     * @return the local date
     * @throws ParseException the parse exception
     */
    public static LocalDate parseDate(String date) throws ParseException {

        requireNonNull(date);

        try {
            return LocalDate.parse(date, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        } catch (DateTimeParseException dtpe) {
            throw new ParseException(MESSAGE_INVALID_DATE);
        }
    }

    /**
     * Parse time local time.
     *
     * @param time the time
     * @return the local time
     * @throws ParseException the parse exception
     */
    public static LocalTime parseTime(String time) throws ParseException {

        requireNonNull(time);

        try {
            return LocalTime.parse(time, DateTimeFormatter.ofPattern("HH:mm"));
        } catch (DateTimeParseException dtpe) {
            throw new ParseException(MESSAGE_INVALID_TIME);
        }
    }

    /**
     * Parse day day of week.
     *
     * @param day the day
     * @return the day of week
     * @throws ParseException the parse exception
     */
    public static DayOfWeek parseDay(String day) throws ParseException {

        requireNonNull(day);

        try {
            return DayOfWeek.valueOf(day);
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new ParseException(MESSAGE_INVALID_DAY);
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    public static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {

        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
