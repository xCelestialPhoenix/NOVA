package seedu.address.model;

import static java.util.Objects.requireNonNull;

import seedu.address.model.calendar.Calendar;

public class NOVA implements ReadOnlyNOVA {

    private Calendar calendar = new Calendar();

    public NOVA() {

    }

    public NOVA(ReadOnlyNOVA toBeCopied) {

        this();
        resetData(toBeCopied);
    }

    public void resetData(ReadOnlyNOVA newData) {

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
