package seedu.address.model.calendar.task;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import seedu.address.model.calendar.activity.execeptions.DuplicateActivityException;
import seedu.address.model.calendar.task.exceptions.RepeatedCompleteException;

import static java.util.Objects.requireNonNull;

public class UniqueTaskList implements Iterable<Task> {

    public static final String MESSAGE_REPEATED_COMPLETE = "The tasks has already been completed.";

    private final ObservableList<Task> internalList = FXCollections.observableArrayList();
    private final ObservableList<Task> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);
    private int tasksCompleted = 0;

    public void add(Task toAdd) {

        requireNonNull(toAdd);

        if (contains(toAdd)) {
            throw new DuplicateActivityException();
        }
        for (int index = 0; index < internalList.size(); index++) {
            Task here = internalList.get(index);

            if (toAdd.dueBefore(here)) {
                internalList.add(index, toAdd);

                if (toAdd.isCompleted()) {
                    tasksCompleted += 1;
                }
                return;
            }
        }
        // Traversed the entire list with no escape. Activity is added to the end.
        internalList.add(internalList.size(), toAdd);
    }

    public Optional<Task> delete(Task toDelete) {

        requireNonNull(toDelete);

        Optional<Task> deletedTask = Optional.empty();
        int index = internalList.indexOf(toDelete);

        if (index != -1) { //If task is present
            deletedTask = Optional.of(internalList.remove(index));

            if (deletedTask.get().isCompleted()) {
                tasksCompleted -= 1;
            }
        }
        return deletedTask;
    }

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
            tasksCompleted += 1;

            // This portion is a workaround for the UI to update the task list card UI properly.
            internalList.remove(index);
            internalList.add(index, task);
        }
        return completedTask;
    }

    public boolean contains(Task toCheck) {

        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::equals);
    }

    public ObservableList<Task> asUnmodifiableObserverableList() {

        return internalUnmodifiableList;
    }

    public String getCompletionStats() {
        return tasksCompleted + "/" + internalList.size();
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

    private boolean tasksAreUnique(List<Task> tasks) {

        for (int i = 0; i < tasks.size() - 1; i++) {
            for (int j = i + 1; j < tasks.size(); j++) {
                if (tasks.get(i).equals(tasks.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }

}
