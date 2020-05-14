package seedu.address.commons.core;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Objects;
import java.util.logging.Level;

/**
 * Config values used by the app
 */
public class Config {

    public static final Path DEFAULT_CONFIG_FILE = Paths.get("config.json");

    // Config values customizable through config file
    private Level logLevel = Level.INFO;
    private Path userPrefsFilePath = Paths.get("preferences.json");
    private LocalDate startDate = LocalDate.of(2020, 1, 13);
    private LocalDate endDate = LocalDate.of(2020, 5, 10);

    public Level getLogLevel() {

        return logLevel;
    }

    public void setLogLevel(Level logLevel) {

        this.logLevel = logLevel;
    }

    public Path getUserPrefsFilePath() {

        return userPrefsFilePath;
    }

    public void setUserPrefsFilePath(Path userPrefsFilePath) {

        this.userPrefsFilePath = userPrefsFilePath;
    }

    public LocalDate getStartDate() {

        return startDate;
    }

    public void setStartDate(LocalDate date) {

        startDate = date;
    }

    public LocalDate getEndDate() {

        return endDate;
    }

    public void setEndDate(LocalDate date) {

        endDate = date;
    }

    @Override
    public boolean equals(Object other) {

        if (other == this) {
            return true;
        }
        if (!(other instanceof Config)) { //this handles null as well.
            return false;
        }

        Config o = (Config) other;

        return Objects.equals(logLevel, o.logLevel)
                && Objects.equals(userPrefsFilePath, o.userPrefsFilePath);
    }

    @Override
    public int hashCode() {

        return Objects.hash(logLevel, userPrefsFilePath);
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        sb.append("Current log level : " + logLevel);
        sb.append("\nPreference file Location : " + userPrefsFilePath);
        return sb.toString();
    }

}
