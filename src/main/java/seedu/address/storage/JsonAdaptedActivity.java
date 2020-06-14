package seedu.address.storage;

import java.time.LocalDate;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.calendar.activity.Activity;
import seedu.address.model.calendar.activity.Lesson;
import seedu.address.model.calendar.activity.Meeting;

public class JsonAdaptedActivity {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Task's %s field is missing!";

    private final String description;
    private final String venue;
    private final LocalDate date;
    private final LocalTime startTime;
    private final LocalTime endTime;
    private final String notes;
    private final String activityType;

    @JsonCreator
    public JsonAdaptedActivity(@JsonProperty("description") String description,
                               @JsonProperty("venue") String venue,
                               @JsonProperty("date") LocalDate date,
                               @JsonProperty("startTime") LocalTime startTime,
                               @JsonProperty("endTime") LocalTime endTime,
                               @JsonProperty("notes") String notes,
                               @JsonProperty("activityType") String activityType) {

        this.description = description;
        this.venue = venue;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.notes = notes;
        this.activityType = activityType;
    }

    public JsonAdaptedActivity(Activity source) {

        description = source.getDescription();
        venue = source.getVenue();
        date = source.getDate();
        startTime = source.getStartTime();
        endTime = source.getEndTime();
        notes = source.getNotes();
        activityType = source.getClass().getSimpleName();
    }

    public Activity toModelType() throws IllegalValueException {

        if (description == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Description"));
        }
        if (venue == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Venue"));
        }
        if (date == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Date"));
        }
        if (startTime == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Start Time"));
        }
        if (endTime == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "End Time"));
        }
        if (notes == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Notes"));
        }

        if (activityType.equals(Meeting.class.getSimpleName())) {
            return new Meeting(description, venue, date, startTime, endTime, notes);
        }

        if (activityType.equals(Lesson.class.getSimpleName())) {
            return new Lesson(description, venue, date.getDayOfWeek(), startTime, endTime, notes);
        }

        throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Activity Type"));
    }

}
