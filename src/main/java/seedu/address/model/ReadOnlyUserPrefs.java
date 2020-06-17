package seedu.address.model;

import java.time.LocalDate;

import seedu.address.commons.core.GuiSettings;

/**
 * Unmodifiable view of user prefs.
 */
public interface ReadOnlyUserPrefs {

    GuiSettings getGuiSettings();

    LocalDate getCalendarStartDate();

}
