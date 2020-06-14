package seedu.address.model.calendar.task;

import static java.util.Objects.requireNonNull;

import java.util.Iterator;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import seedu.address.model.calendar.activity.execeptions.DuplicateActivityException;
import seedu.address.model.calendar.task.exceptions.RepeatedCompleteException;

/**
 * A list of tasks that enforces uniqueness between its elements and does not allow nulls.
 * As such, adding and updating of activities ensure that the person being added or updated is unique in terms of
 * identity in the UniqueActivityList.
 * <p>
 * Supports a minimal set of list operations.
 */

public class UniqueTaskList implements Iterable<Task> {

    /**
     * The feedback message when a task is already completed in the list.
     */
    public static final String MESSAGE_REPEATED_COMPLETE = "The tasks has already been completed.";

    private final ObservableList<Task> internalList = FXCollections.observableArrayList();
    private final ObservableList<Task> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);
    private final TaskCompletionStatistics stats = new TaskCompletionStatistics();

    //======================================= Modification =====================================

    /**
     * Adds a task into the task list.
     *
     * @param toAdd the task to add
     */
    public void add(Task toAdd) {

        requireNonNull(toAdd);

        if (contains(toAdd)) {
            throw new DuplicateActivityException();
        }
        for (int index = 0; index < internalList.size(); index++) {
            Task here = internalList.get(index);

            if (toAdd.dueBefore(here)) {
                internalList.add(index, toAdd);
                stats.incrementTotalTasks();

                if (toAdd.isCompleted()) {
                    stats.incrementCompletedTask();
                }
                return;
            }
        }
        // Traversed the entire list with no escape. Activity is added to the end.
        internalList.add(internalList.size(), toAdd);
        stats.incrementTotalTasks();
    }

    /**
     * Deletes a task from the task list.
     *
     * @param toDelete the task to delete
     * @return an optional that holds the deleted task if it exists
     */
    public Optional<Task> delete(Task toDelete) {

        requireNonNull(toDelete);

        Optional<Task> deletedTask = Optional.empty();
        int index = internalList.indexOf(toDelete);

        if (index != -1) { //If task is present
            deletedTask = Optional.of(internalList.remove(index));

            if (deletedTask.get().isCompleted()) {
                stats.decrementCompletedTasks();
            }
            stats.decrementTotalTasks();
        }
        return deletedTask;
    }

    /**
     * Completes a task in the task list.
     *
     * @param toComplete the task to complete
     * @return an optional that holds the completed task if it exists
     * @throws RepeatedCompleteException if the requested task has already been completed
     */
    public Optional<Task> complete(Task toComplete) throws RepeatedCompleteException {

        requireNonNull(toComplete);

        Optional<Task> completedTask = Optional.empty();
        int index = internalList.indexOf(toComplete);

        if (index != -1) { //If task is present

            Task task = internalList.get(index);

            if (task.isCompleted()) {
                throw new RepeatedCompleteException(MESSAGE_REPEATED_COMPLETE);
            }

            task.complete();
            completedTask = Optional.of(task);
            stats.incrementCompletedTask();

            // This portion is a workaround for the UI to update the task list card UI properly.
            internalList.remove(index);
            internalList.add(index, task);
        }
        return completedTask;
    }

    //======================================= Getters =====================================

    /**
     * Gets the task completion statistics.
     *
     * @return the task completion statistics
     */
    public TaskCompletionStatistics getCompletionStats() {

        return stats;
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     *
     * @return the backing list
     */
    public ObservableList<Task> asUnmodifiableObservableList() {

        return internalUnmodifiableList;
    }

    //======================================= Utilities =====================================

    /**
     * Returns true if the list contains an equivalent task as the given argument.
     *
     * @param toCheck the task to check
     * @return true if the list contains an equivalent task as the given argument
     */
    public boolean contains(Task toCheck) {

        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::equals);
    }

    @Override
    public Iterator<Task> iterator() {

        return internalList.iterator();
    }

    @Override
    public int hashCode() {

        return internalList.hashCode();
    }

    @Override
    public boolean equals(Object other) {

        return this == other
                || (other instanceof UniqueTaskList
                && internalList.equals(((UniqueTaskList) other).internalList));
    }

}
