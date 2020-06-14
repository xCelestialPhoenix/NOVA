package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;

import seedu.address.model.calendar.Calendar;

public class Nova implements ReadOnlyNova {

    private Calendar calendar;

    public Nova(LocalDate calendarStartDate) {

        calendar = new Calendar(calendarStartDate);
    }

    public Nova(ReadOnlyNova toBeCopied) {

        resetData(toBeCopied);
    }

    public void resetData(ReadOnlyNova newData) {

        requireNonNull(newData);

        setCalendar(newData.getCalendar());
    }

    @Override
    public Calendar getCalendar() {

        return calendar;
    }

    public void setCalendar(Calendar calendar) {

        this.calendar = calendar;
    }

}
