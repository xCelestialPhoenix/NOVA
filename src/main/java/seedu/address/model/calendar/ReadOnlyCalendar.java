package seedu.address.model.calendar;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import javafx.collections.ObservableList;

import seedu.address.model.calendar.activity.Activity;

public interface ReadOnlyCalendar {

    ObservableList<Activity> viewActivityOnDate(LocalDate date);

    Optional<Activity> getNextActivity(LocalDate today, LocalTime timeNow);

    boolean hasActivity(Activity activity);

    boolean isWithinCalendarRange(Activity activity);

    int calculateWeek(LocalDate date);

    boolean checkAvailability(Activity activity);

    LocalDate getStartDate();

    LocalDate getEndDate();

}
