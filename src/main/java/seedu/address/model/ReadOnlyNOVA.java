package seedu.address.model;

import seedu.address.model.calendar.Calendar;

/**
 * Unmodifiable view of NOVA
 */
public interface ReadOnlyNOVA {

    Calendar getCalendar();
}
