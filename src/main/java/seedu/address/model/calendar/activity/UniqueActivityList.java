package seedu.address.model.calendar.activity;

import static java.util.Objects.requireNonNull;

import java.time.LocalTime;
import java.util.Iterator;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import seedu.address.model.calendar.activity.execeptions.DuplicateActivityException;

/**
 * A list of activities that enforces uniqueness between its elements and does not allow nulls.
 * As such, adding and updating of activities ensure that the person being added or updated is unique in terms of
 * identity in the UniqueActivityList.
 * <p>
 * Supports a minimal set of list operations.
 */
public class UniqueActivityList implements Iterable<Activity> {

    private final ObservableList<Activity> internalList = FXCollections.observableArrayList();
    private final ObservableList<Activity> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    //======================================= Modification =====================================

    /**
     * Adds a person to the list.
     * The person must not already exist in the list.
     *
     * @param toAdd the to add
     */
    public void add(Activity toAdd) {

        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateActivityException();
        }
        for (int index = 0; index < internalList.size(); index++) {
            Activity here = internalList.get(index);

            if (toAdd.startBefore(here)) {
                if (toAdd.endsBefore(here)) {
                    internalList.add(index, toAdd);
                    return;
                }
            }
        }
        // Traversed the entire list with no escape. Activity is added to the end.
        internalList.add(internalList.size(), toAdd);
    }

    /**
     * Deletes an activity from the list.
     *
     * @param toDelete the scaled-down version of an activity used to identify the actual activity
     * @return an optional that holds the deleted activity if it exists
     */
    public Optional<Activity> delete(ActivityReference toDelete) {

        requireNonNull(toDelete);

        Optional<Activity> deletedActivity = Optional.empty();
        int index = internalList.indexOf(toDelete);

        if (index != -1) { //If activity is present
            deletedActivity = Optional.of(internalList.remove(index));
        }

        return deletedActivity;
    }

    //======================================= Getters =====================================

    /**
     * Gets the first activity in the list.
     *
     * @return an optional holding the first activity if it exists
     */
    public Optional<Activity> getFirstActivity() {

        if (internalList.size() == 0) {
            return Optional.empty();
        }
        return Optional.of(internalList.get(0));
    }

    /**
     * Gets the next upcoming activity from now.
     *
     * @return an optional holding the next activity from now if it exists
     */
    public Optional<Activity> getNextActivity() {

        Optional<Activity> nextActivity = Optional.empty();
        LocalTime timeNow = LocalTime.now();

        for (Activity activity : internalList) {
            if (activity.getStartTime().isAfter(timeNow)) {
                nextActivity = Optional.of(activity);
                break;
            }
        }
        return nextActivity;
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     *
     * @return the backing list
     */
    public ObservableList<Activity> asUnmodifiableObservableList() {

        return internalUnmodifiableList;
    }

    //======================================= Utilities =====================================

    /**
     * Returns true if the list contains an equivalent activity as the given argument.
     *
     * @param toCheck the activity to check
     * @return true if the list contains an equivalent activity as the given argument
     */
    public boolean contains(Activity toCheck) {

        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::equals);
    }

    /**
     * Returns true if the activity can be added into the activity list.
     *
     * @param toCheck the activity to be tested
     * @return true if the activity can be added into the calendar
     */
    public boolean isAddable(Activity toCheck) {

        boolean isAddable = true;

        for (Activity pointer : internalList) {

            if (toCheck.startBefore(pointer)) {
                if (!toCheck.endsBefore(pointer)) {
                    isAddable = false;
                }
                break;
            }

            if (toCheck.startDuring(pointer) || toCheck.endsBefore(pointer) || toCheck.endsWith(pointer)) {
                isAddable = false;
                break;
            }
        }
        return isAddable;
    }

    @Override
    public Iterator<Activity> iterator() {

        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {

        return other == this // short circuit if same object
                || (other instanceof UniqueActivityList // instanceof handles nulls
                && internalList.equals(((UniqueActivityList) other).internalList));
    }

    @Override
    public int hashCode() {

        return internalList.hashCode();
    }

}
