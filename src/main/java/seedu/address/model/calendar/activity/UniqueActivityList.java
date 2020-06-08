package seedu.address.model.calendar.activity;

import java.time.LocalTime;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import seedu.address.model.calendar.activity.execeptions.ActivityNotFoundException;
import seedu.address.model.calendar.activity.execeptions.DuplicateActivityException;
import seedu.address.model.person.exceptions.DuplicatePersonException;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

/**
 * The type Unique activity list.
 */
public class UniqueActivityList implements Iterable<Activity> {

    private final ObservableList<Activity> internalList = FXCollections.observableArrayList();
    private final ObservableList<Activity> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent person as the given argument.
     *
     * @param toCheck the to check
     * @return the boolean
     */
    public boolean contains(Activity toCheck) {

        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::equals);
    }

    /**
     * Is addable boolean.
     *
     * @param toCheck the to check
     * @return the boolean
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
     * Deletes an activity.
     *
     * @param toDelete the activity reference
     * @return An optional that holds the deleted activity if it exists
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

    /**
     * Gets first activity.
     *
     * @return the first activity
     */
    public Optional<Activity> getFirstActivity() {

        if (internalList.size() == 0) {
            return Optional.empty();
        }
        return Optional.of(internalList.get(0));
    }

    /**
     * Gets next activity.
     *
     * @param timeNow the time now
     * @return the next activity
     */
    public Optional<Activity> getNextActivity(LocalTime timeNow) {

        Optional<Activity> nextActivity = Optional.empty();

        for (Activity activity : internalList) {
            if (activity.getStartTime().isAfter(timeNow)) {
                nextActivity = Optional.of(activity);
                break;
            }
        }
        return nextActivity;
    }

    /**
     * Replaces the person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the list.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the list.
     *
     * @param target         the target
     * @param editedActivity the edited activity
     */
    public void setActivity(Activity target, Activity editedActivity) {

        requireAllNonNull(target, editedActivity);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new ActivityNotFoundException();
        }

        if (!target.equals(editedActivity) && contains(editedActivity)) {
            throw new DuplicateActivityException();
        }

        internalList.set(index, editedActivity);
    }

    /**
     * Sets activities.
     *
     * @param replacement the replacement
     */
    public void setActivities(UniqueActivityList replacement) {

        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     *
     * @param activities the activities
     */
    public void setActivities(List<Activity> activities) {

        requireAllNonNull(activities);
        if (!activitiesAreUnique(activities)) {
            throw new DuplicatePersonException();
        }

        internalList.setAll(activities);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     *
     * @return the observable list
     */
    public ObservableList<Activity> asUnmodifiableObservableList() {

        return internalUnmodifiableList;
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

    /**
     * Returns true if {@code persons} contains only unique persons.
     */
    private boolean activitiesAreUnique(List<Activity> activities) {

        for (int i = 0; i < activities.size() - 1; i++) {
            for (int j = i + 1; j < activities.size(); j++) {
                if (activities.get(i).equals(activities.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }

}
