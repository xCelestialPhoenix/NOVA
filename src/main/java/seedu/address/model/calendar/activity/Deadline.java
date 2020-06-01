package seedu.address.model.calendar.activity;

import seedu.address.model.calendar.activity.task.Task;

public class Deadline extends Activity {

    public Deadline(Task task) {

        description = task.getDescription();
        date = task.getDeadlineDate();
        startTime = endTime = task.getDeadlineTime();
        notes = task.getNote();
        venue = "";
    }

    @Override
    public boolean isValid() {

        return startTime.equals(endTime);
    }

}
