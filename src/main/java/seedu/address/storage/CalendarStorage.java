package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.calendar.ReadOnlyCalendar;

public interface CalendarStorage {

    Path getCalendarFilePath();

    Optional<ReadOnlyCalendar> readCalendar() throws DataConversionException, IOException;

    Optional<ReadOnlyCalendar> readCalendar(Path filePath) throws DataConversionException, IOException;

    void saveCalendar(ReadOnlyCalendar calendar) throws IOException;

    void saveCalendar(ReadOnlyCalendar calendar, Path filePath) throws IOException;

}
