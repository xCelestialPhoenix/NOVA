package seedu.address.commons.util;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.core.Config;
import seedu.address.commons.exceptions.DataConversionException;

/**
 * A class for accessing the Config File.
 */
public class ConfigUtil {

    /**
     * Reads the config file
     *
     * @param configFilePath the config file path
     * @return the optional
     * @throws DataConversionException the data conversion exception
     */
    public static Optional<Config> readConfig(Path configFilePath) throws DataConversionException {

        return JsonUtil.readJsonFile(configFilePath, Config.class);
    }

    /**
     * Saves the configuration to file
     *
     * @param config         the config
     * @param configFilePath the config file path
     * @throws IOException the io exception
     */
    public static void saveConfig(Config config, Path configFilePath) throws IOException {

        JsonUtil.saveJsonFile(config, configFilePath);
    }

}
