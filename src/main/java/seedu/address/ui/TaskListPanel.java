package seedu.address.ui;


import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;

import seedu.address.model.calendar.task.Task;

public class TaskListPanel extends UiPart<Region> {

    private static final String FXML = "TaskListPanel.fxml";

    @FXML
    private ListView<Task> taskListView;

    public TaskListPanel(ObservableList<Task> taskList) {

        super(FXML);
        taskListView.setItems(taskList);
        taskListView.setCellFactory(listView -> new TaskListViewCell());
    }

    class TaskListViewCell extends ListCell<Task> {

        @Override
        protected void updateItem(Task task, boolean empty) {

            super.updateItem(task, empty);

            if (empty || task == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new TaskCard(task, getIndex() + 1).getRoot());
            }
        }

    }

}
