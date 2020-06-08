package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import seedu.address.model.calendar.activity.Activity;
import seedu.address.model.calendar.activity.Lesson;
import seedu.address.model.calendar.activity.Meeting;

/**
 * An UI component that displays information of an {@code Activity}.
 */
public class ActivityCard extends UiPart<Region> {

    private static final String FXML = "ActivityListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */
    public final Activity activity;

    @FXML
    private VBox detailsPane;
    @FXML
    private Label indexLabel;
    @FXML
    private Label descriptionLabel;
    @FXML
    private Label venueLabel;
    @FXML
    private Label timeLabel;
    @FXML
    private Label noteLabel;

    /**
     * Instantiates a new Activity card.
     *
     * @param activity       the activity
     * @param displayedIndex the displayed index
     */
    public ActivityCard(Activity activity, int displayedIndex) {

        super(FXML);
        this.activity = activity;

        formatActivityCard(activity);
        indexLabel.setText(displayedIndex + ". ");
        descriptionLabel.setText(activity.getDescription());
        venueLabel.setText("Venue: " + activity.getVenue());
        timeLabel.setText("Time: " + activity.getStartTime() + " - " + activity.getEndTime());

        String notes = activity.getNotes();
        if (notes.equals("")) {
            detailsPane.getChildren().remove(noteLabel);
        } else {
            noteLabel.setText("Note: " + notes);
        }

    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ActivityCard)) {
            return false;
        }

        // state check
        ActivityCard card = (ActivityCard) other;
        return indexLabel.getText().equals(card.indexLabel.getText())
                && activity.equals(card.activity);
    }

    /**
     * Formats the activity card
     *
     * @param activity the activity to be referred by the statistic card
     */
    private void formatActivityCard(Activity activity) {

        String meetingHeaderColour = "#0d75d6";
        String lessonHeaderColour = "#d6d30d";

        if (activity instanceof Meeting) {
            indexLabel.setStyle("-fx-text-fill: " + meetingHeaderColour);
            descriptionLabel.setStyle("-fx-text-fill: " + meetingHeaderColour);
        } else if (activity instanceof Lesson) {
            indexLabel.setStyle("-fx-text-fill: " + lessonHeaderColour);
            descriptionLabel.setStyle("-fx-text-fill: " + lessonHeaderColour);
        }

    }

}
