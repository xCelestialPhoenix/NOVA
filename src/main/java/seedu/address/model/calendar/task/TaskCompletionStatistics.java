package seedu.address.model.calendar.task;

import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;

/**
 * The data structure to represent the statistics of task completion.
 */
public class TaskCompletionStatistics {

    private static final Logger logger = LogsCenter.getLogger(TaskCompletionStatistics.class);

    private int totalTasks = 0;
    private int completedTasks = 0;

//======================================= Modification =====================================

    /**
     * Increments the total task count.
     */
    public void incrementTotalTasks() {

        totalTasks++;
    }

    /**
     * Increments the completed task count.
     */
    public void incrementCompletedTask() {

        if (completedTasks >= totalTasks) {
            logger.warning("Attempting to increment completed task count over total task count");
            completedTasks = totalTasks;
        } else {
            completedTasks++;
        }
    }

    /**
     * Decrements the total task count.
     */
    public void decrementTotalTasks() {

        if (totalTasks <= 0) {
            logger.warning("Attempting to decrement total task count under zero");
            totalTasks = 0;
            return;
        }

        if (totalTasks == completedTasks) {
            completedTasks--;
        }
        totalTasks--;
    }

    /**
     * Decrements the completed task count.
     */
    public void decrementCompletedTasks() {

        if (completedTasks <= 0) {
            logger.warning("Attempting to decrement completed task count under zero");
            completedTasks = 0;
            return;
        }
        completedTasks--;
    }

    //======================================= Utilities =====================================

    /**
     * Presents the statistic as a fraction.
     *
     * @return the statistic as a fraction string.
     */
    public String asFraction() {

        return completedTasks + "/" + totalTasks;
    }

    /**
     * Presents the statistic as a percentage.
     *
     * @return the statistic as a percentage string.
     */
    public String asPercentage() {

        // Divide by zero case
        if (totalTasks == 0) {
            return "100%";
        }

        int percentage = completedTasks / totalTasks;
        return percentage + "%";
    }

}
