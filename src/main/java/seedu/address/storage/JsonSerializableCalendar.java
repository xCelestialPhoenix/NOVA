package seedu.address.storage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.calendar.Calendar;
import seedu.address.model.calendar.ReadOnlyCalendar;
import seedu.address.model.calendar.activity.Activity;
import seedu.address.model.calendar.activity.Lesson;

@JsonRootName(value = "calendar")
public class JsonSerializableCalendar {

    public static final String MESSAGE_DUPLICATE_ACTIVITY = "Activity list contains duplicate activity.";

    private final LocalDate startDate;
    private final List<JsonAdaptedActivity> activities = new ArrayList<>();

    @JsonCreator
    public JsonSerializableCalendar(@JsonProperty("startDate") LocalDate startDate,
                                    @JsonProperty("activities") List<JsonAdaptedActivity> activities) {

        this.startDate = startDate;
        this.activities.addAll(activities);
    }

    public JsonSerializableCalendar(ReadOnlyCalendar source) {

        startDate = source.getStartDate();
        activities.addAll(source.getActivities().stream().map(JsonAdaptedActivity::new).collect(Collectors.toList()));
    }

    public Calendar toModelType() throws IllegalValueException {

        Calendar calendar = new Calendar(startDate);

        for (JsonAdaptedActivity jsonAdaptedActivity : activities) {
            Activity activity = jsonAdaptedActivity.toModelType();
            if (calendar.hasActivity(activity)) {

                if (activity instanceof Lesson) {
                    continue;
                }

                throw new IllegalValueException(MESSAGE_DUPLICATE_ACTIVITY);
            }
            calendar.addActivity(activity);
        }
        return calendar;
    }

}
