package seedu.address.model.util;

import java.time.LocalDate;
import java.time.LocalTime;

import seedu.address.model.calendar.Calendar;
import seedu.address.model.calendar.ReadOnlyCalendar;
import seedu.address.model.calendar.activity.Activity;
import seedu.address.model.calendar.activity.Lesson;
import seedu.address.model.calendar.activity.Meeting;
import seedu.address.model.calendar.task.Task;

/**
 * Contains utility methods for populating {@code NOVA} with sample data.
 */
public class SampleDataUtil {

    //Make start date a Monday of this week
    private static LocalDate SAMPLE_START_DATE =
            LocalDate.now().minusDays(LocalDate.now().getDayOfWeek().getValue() - 1);

    public static Activity[] getSampleActivities() {

        return new Activity[]{
                new Meeting("CS2103T Tutorial Discussion",
                        "SoC Hangouts", LocalDate.now(),
                        LocalTime.of(9, 0),
                        LocalTime.of(10, 0),
                        ""),
                new Meeting("CS2103T Project Planning",
                        "",
                        LocalDate.now(),
                        LocalTime.of(12, 0),
                        LocalTime.of(14, 0),
                        ""),
                new Meeting("CS2103T Consultation",
                        "",
                        LocalDate.now(),
                        LocalTime.of(16, 0),
                        LocalTime.of(16, 30),
                        ""),
                new Meeting("External Side Project Meeting",
                        "Zoom",
                        LocalDate.now(),
                        LocalTime.of(18, 0),
                        LocalTime.of(19, 0),
                        ""),
                new Lesson("CS2103T Lecture",
                        "I3-AUD",
                        LocalDate.now().getDayOfWeek(),
                        LocalTime.of(14, 0),
                        LocalTime.of(16, 0),
                        "Read chapter on design principles"),
                new Lesson("CS2103T Tutorial",
                        "COM1-B103",
                        LocalDate.now().getDayOfWeek(),
                        LocalTime.of(10, 0),
                        LocalTime.of(11,
                                0), "")
        };
    }

    public static Task[] getSampleTasks() {

        return new Task[]{
                new Task("Complete CS2103T Tutorial", LocalDate.now(), LocalTime.of(10, 0), ""),
                new Task("Plan how to demo CS2103T final product", LocalDate.now(), LocalTime.of(23, 59), ""),
                new Task("Prepare wireframe for sponsor", LocalDate.now(), LocalTime.of(18, 0), ""),
                new Task("Complete milestone V2.0", LocalDate.now(), LocalTime.of(23, 59), "Test for bugs before " +
                        "committing to GitHub")
        };
    }

    public static ReadOnlyCalendar getSampleCalendar() {

        Calendar sampleCal = new Calendar(SAMPLE_START_DATE);
        for (Activity sampleActivity : getSampleActivities()) {
            sampleCal.addActivity(sampleActivity);
        }

        for (Task sampleTask : getSampleTasks()) {
            sampleCal.addTask(sampleTask);
        }
        return sampleCal;
    }

}
