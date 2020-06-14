package seedu.address.storage;

import static seedu.address.logic.constants.Messages.MESSAGE_DUPLICATE_ACTIVITY;
import static seedu.address.logic.constants.Messages.MESSAGE_DUPLICATE_TASK;

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
import seedu.address.model.calendar.task.Task;

@JsonRootName(value = "calendar")
public class JsonSerializableCalendar {

    private final LocalDate startDate;
    private final List<JsonAdaptedActivity> activities = new ArrayList<>();
    private final List<JsonAdaptedTask> tasks = new ArrayList<>();

    @JsonCreator
    public JsonSerializableCalendar(@JsonProperty("startDate") LocalDate startDate,
                                    @JsonProperty("activities") List<JsonAdaptedActivity> activities,
                                    @JsonProperty("tasks") List<JsonAdaptedTask> tasks) {

        this.startDate = startDate;
        this.activities.addAll(activities);
        this.tasks.addAll(tasks);
    }

    public JsonSerializableCalendar(ReadOnlyCalendar source) {

        startDate = source.getStartDate();
        activities.addAll(source.getActivities().stream().map(JsonAdaptedActivity::new).collect(Collectors.toList()));
        tasks.addAll(source.getTasks().stream().map(JsonAdaptedTask::new).collect(Collectors.toList()));
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

        for (JsonAdaptedTask jsonAdaptedTask : tasks) {

            Task task = jsonAdaptedTask.toModelType();

            if (calendar.hasTask(task)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_TASK);
            }

            calendar.addTask(task);
        }

        return calendar;
    }

}
