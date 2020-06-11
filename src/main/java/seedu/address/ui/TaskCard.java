package seedu.address.ui;

import java.time.format.DateTimeFormatter;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import seedu.address.model.calendar.task.Task;

public class TaskCard extends UiPart<Region> {

    private static final String FXML = "TaskListCard.fxml";

    public final Task task;

    @FXML
    private HBox cardPane;
    @FXML
    private VBox detailsPane;
    @FXML
    private Label indexLabel;
    @FXML
    private Label descriptionLabel;
    @FXML
    private Label dateLabel;
    @FXML
    private Label timeLabel;
    @FXML
    private Label noteLabel;

    public TaskCard(Task task, int displayedIndex) {

        super(FXML);
        this.task = task;

        indexLabel.setText(displayedIndex + ". ");
        descriptionLabel.setText(task.getDescription());
        dateLabel.setText("Date: " + task.getDueDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        timeLabel.setText("Time: " + task.getDueTime().format(DateTimeFormatter.ofPattern("hh:mm a")));

        String notes = task.getNote();
        if (notes.equals("")) {
            detailsPane.getChildren().remove(noteLabel);
        } else {
            noteLabel.setText("Note: " + notes);
        }

        if (task.isCompleted()) {
            descriptionLabel.setStyle("-fx-text-fill: #00de16");
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TaskCard)) {
            return false;
        }

        // state check
        TaskCard card = (TaskCard) other;
        return indexLabel.getText().equals(card.indexLabel.getText())
                && task.equals(card.task);
    }

}
