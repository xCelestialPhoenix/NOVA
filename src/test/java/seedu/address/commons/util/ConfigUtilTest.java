package seedu.address.commons.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.address.testutil.Assert.assertThrows;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Optional;
import java.util.logging.Level;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.core.Config;
import seedu.address.commons.exceptions.DataConversionException;

/**
 * The type Config util test.
 */
public class ConfigUtilTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "ConfigUtilTest");

    /**
     * The Temp dir.
     */
    @TempDir
    public Path tempDir;

    /**
     * Read null throws null pointer exception.
     */
    @Test
    public void read_null_throwsNullPointerException() {

        assertThrows(NullPointerException.class, () -> read(null));
    }

    /**
     * Read missing file empty result.
     *
     * @throws DataConversionException the data conversion exception
     */
    @Test
    public void read_missingFile_emptyResult() throws DataConversionException {

        assertFalse(read("NonExistentFile.json").isPresent());
    }

    /**
     * Read not json format exception thrown.
     */
    @Test
    public void read_notJsonFormat_exceptionThrown() {

        assertThrows(DataConversionException.class, () -> read("NotJsonFormatConfig.json"));
    }

    /**
     * Read file in order successfully read.
     *
     * @throws DataConversionException the data conversion exception
     */
    @Test
    public void read_fileInOrder_successfullyRead() throws DataConversionException {

        Config expected = getTypicalConfig();

        Config actual = read("TypicalConfig.json").get();
        assertEquals(expected, actual);
    }

    /**
     * Read values missing from file default values used.
     *
     * @throws DataConversionException the data conversion exception
     */
    @Test
    public void read_valuesMissingFromFile_defaultValuesUsed() throws DataConversionException {

        Config actual = read("EmptyConfig.json").get();
        assertEquals(new Config(), actual);
    }

    /**
     * Read extra values in file extra values ignored.
     *
     * @throws DataConversionException the data conversion exception
     */
    @Test
    public void read_extraValuesInFile_extraValuesIgnored() throws DataConversionException {

        Config expected = getTypicalConfig();
        Config actual = read("ExtraValuesConfig.json").get();

        assertEquals(expected, actual);
    }

    private Config getTypicalConfig() {

        Config config = new Config();
        config.setLogLevel(Level.INFO);
        config.setUserPrefsFilePath(Paths.get("preferences.json"));
        config.setStartDate(LocalDate.of(2020, 1, 13));
        config.setEndDate(LocalDate.of(2020, 5, 10));
        return config;
    }

    private Optional<Config> read(String configFileInTestDataFolder) throws DataConversionException {

        Path configFilePath = addToTestDataPathIfNotNull(configFileInTestDataFolder);
        return ConfigUtil.readConfig(configFilePath);
    }

    /**
     * Save null config throws null pointer exception.
     */
    @Test
    public void save_nullConfig_throwsNullPointerException() {

        assertThrows(NullPointerException.class, () -> save(null, "SomeFile.json"));
    }

    /**
     * Save null file throws null pointer exception.
     */
    @Test
    public void save_nullFile_throwsNullPointerException() {

        assertThrows(NullPointerException.class, () -> save(new Config(), null));
    }

    /**
     * Save config all in order success.
     *
     * @throws DataConversionException the data conversion exception
     * @throws IOException             the io exception
     */
    @Test
    public void saveConfig_allInOrder_success() throws DataConversionException, IOException {

        Config original = getTypicalConfig();

        Path configFilePath = tempDir.resolve("TempConfig.json");

        //Try writing when the file doesn't exist
        ConfigUtil.saveConfig(original, configFilePath);
        Config readBack = ConfigUtil.readConfig(configFilePath).get();
        assertEquals(original, readBack);

        //Try saving when the file exists
        original.setLogLevel(Level.FINE);
        ConfigUtil.saveConfig(original, configFilePath);
        readBack = ConfigUtil.readConfig(configFilePath).get();
        assertEquals(original, readBack);
    }

    private void save(Config config, String configFileInTestDataFolder) throws IOException {

        Path configFilePath = addToTestDataPathIfNotNull(configFileInTestDataFolder);
        ConfigUtil.saveConfig(config, configFilePath);
    }

    /**
     * Adds test data folder if not null
     *
     * @param configFileInTestDataFolder
     * @return
     */

    private Path addToTestDataPathIfNotNull(String configFileInTestDataFolder) {

        return configFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(configFileInTestDataFolder)
                : null;
    }


}
