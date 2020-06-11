package seedu.address.ui.statisticcard;

import javafx.scene.image.Image;

import seedu.address.model.calendar.task.TaskCompletionStatistics;

public class TaskCompletionCard extends StatisticCard {

    public static final String CARD_TITLE = "Task completed:";
    private static final String LOGO_FILEPATH = "/images/tick.png";
    private static final String DEFAULT_TASK_COMPLETION_STATS = new TaskCompletionStatistics().asFraction();
    private static final int DATA_TEXT_FONT_SIZE = 26;

    public TaskCompletionCard() {
        this(DEFAULT_TASK_COMPLETION_STATS);
    }

    public TaskCompletionCard(String data) {

        super(CARD_TITLE, data, DATA_TEXT_FONT_SIZE);

        Image logo = new Image(this.getClass().getResourceAsStream(LOGO_FILEPATH));
        setLogo(logo);
    }

    public void updateData(TaskCompletionStatistics newStatistics) {

        super.updateData(newStatistics.asFraction(), DATA_TEXT_FONT_SIZE);
    }
}
