package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.calendar.ReadOnlyCalendar;

public class JsonCalendarStorage implements CalendarStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonCalendarStorage.class);

    private final Path filePath;

    public JsonCalendarStorage(Path filePath) {

        this.filePath = filePath;
    }

    @Override
    public Path getCalendarFilePath() {

        return filePath;
    }

    @Override
    public Optional<ReadOnlyCalendar> readCalendar() throws DataConversionException, IOException {

        return readCalendar(filePath);
    }

    @Override
    public Optional<ReadOnlyCalendar> readCalendar(Path filePath) throws DataConversionException, IOException {

        requireNonNull(filePath);

        Optional<JsonSerializableCalendar> jsonCalendar = JsonUtil.readJsonFile(filePath,
                JsonSerializableCalendar.class);

        if (jsonCalendar.isEmpty()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonCalendar.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveCalendar(ReadOnlyCalendar calendar) throws IOException {

        saveCalendar(calendar, filePath);
    }

    @Override
    public void saveCalendar(ReadOnlyCalendar calendar, Path filePath) throws IOException {

        requireNonNull(calendar);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableCalendar(calendar), filePath);
    }

}
