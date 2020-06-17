package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;
import seedu.address.model.calendar.ReadOnlyCalendar;

/**
 * Manages storage of NOVA's data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private final UserPrefsStorage userPrefsStorage;
    private final CalendarStorage calendarStorage;


    public StorageManager(CalendarStorage calendarStorage, UserPrefsStorage userPrefsStorage) {

        super();
        this.calendarStorage = calendarStorage;
        this.userPrefsStorage = userPrefsStorage;
    }

    // ================ UserPrefs methods ==============================

    @Override
    public Path getUserPrefsFilePath() {

        return userPrefsStorage.getUserPrefsFilePath();
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException {

        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException {

        userPrefsStorage.saveUserPrefs(userPrefs);
    }

    // ==================== Calendar methods ===================


    @Override
    public Path getCalendarFilePath() {

        return calendarStorage.getCalendarFilePath();
    }

    @Override
    public Optional<ReadOnlyCalendar> readCalendar() throws DataConversionException, IOException {

        return readCalendar(calendarStorage.getCalendarFilePath());
    }

    @Override
    public Optional<ReadOnlyCalendar> readCalendar(Path filePath) throws DataConversionException, IOException {

        logger.fine("Attempting to read data from file: " + filePath);
        return calendarStorage.readCalendar(filePath);
    }

    @Override
    public void saveCalendar(ReadOnlyCalendar calendar) throws IOException {

        saveCalendar(calendar, calendarStorage.getCalendarFilePath());
    }

    @Override
    public void saveCalendar(ReadOnlyCalendar calendar, Path filePath) throws IOException {

        logger.fine("Attempting to write to data file: " + filePath);
        calendarStorage.saveCalendar(calendar, filePath);
    }

}
