package seedu.address.ui;

import static java.time.temporal.ChronoUnit.DAYS;

import java.time.LocalDate;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import seedu.address.commons.core.Config;

public class StatisticCard extends UiPart<Node> {

    public static final String WEEK_NUM_TITLE = "Week:";
    public static final String NEXT_EVENT_TITLE = "Next event is at:";
    public static final String TASK_COMPLETION_TITLE = "Task completed:";

    private static final String FXML = "StatisticsCard.fxml";
    private static final String CALENDAR_FILEPATH = "/images/calendar.png";
    private static final String CLOCK_FILEPATH = "/images/clock.png";
    private static final String TICK_FILEPATH = "/images/tick.png";

    // Default data to display
    private static final String DEFAULT_NEXT_EVENT = "";
    private static final String DEFAULT_TASK_COMPLETION = "0";

    // Week number statistic card constants
    private static final int RECESS_WEEK = 7;
    private static final int READING_WEEK = 15;
    private static final int EXAM_WEEK_1 = 16;
    private static final int EXAM_WEEK_2 = 17;
    private static final int DAYS_PER_WEEK = 7;

    @FXML
    private AnchorPane statisticCardRoot;

    @FXML
    private ImageView cardLogoImageView;

    @FXML
    private Label cardTitleLabel;

    @FXML
    private Label cardDataLabel;

    @FXML
    private Label cardDateLabel;

    @FXML
    private TextFlow textFlow;

    public StatisticCard(String title, Config config) {

        this(title, getDefaultData(title, config));
    }

    public StatisticCard(String title, String data) {

        super(FXML);

        if (!title.equals(WEEK_NUM_TITLE)) {
            cardDateLabel.setVisible(false);
        } else { //Is week number statistic card
            cardDateLabel.setText(String.valueOf(LocalDate.now().getDayOfMonth()));
            //Align week number with academic calendar
            int weekNumber = Integer.parseInt(data);

            if (isRecessWeek(weekNumber)) {
                data = "Recess";
            } else if (isReadingWeek(weekNumber)) {
                data = "Reading";
            } else if (isExamWeek(weekNumber)) {
                data = "Exam";
            } else if (isAfterSemester(weekNumber)) {
                data = "Break";
            } else if (isAfterRecessWeek(weekNumber)) {
                weekNumber -= 1; // Actual week number is one more than academic week
                data = String.valueOf(weekNumber);
            } else {
                // Do nothing. Week number is correct.
            }
        }

        Text text = new Text(data);
        text.setFill(Color.WHITE);
        text.setFont(Font.font("Segoe UI Bold", 24));

        try {
            setLogo(title);
        } catch (Exception e) {
            e.printStackTrace();
        }
        cardTitleLabel.setText(title);
        textFlow.getChildren().add(text);
    }

    private boolean isRecessWeek(int weekNumber) {
        return weekNumber == RECESS_WEEK;
    }

    private boolean isAfterRecessWeek(int weekNumber) {
        return weekNumber > RECESS_WEEK;
    }

    private boolean isAfterSemester(int weekNumber) {
        return weekNumber > EXAM_WEEK_2;
    }

    private boolean isExamWeek(int weekNumber) {
        return weekNumber == EXAM_WEEK_1 || weekNumber == EXAM_WEEK_2;
    }

    private boolean isReadingWeek(int weekNumber) {
        return weekNumber == READING_WEEK;
    }

    public void updateData(String data) {
        cardDataLabel.setText(data);
    }

    private void setLogo(String title) throws Exception {
        Image img;
        switch (title) {
        case WEEK_NUM_TITLE:
            img = new Image(this.getClass().getResourceAsStream(CALENDAR_FILEPATH));
            break;
        case NEXT_EVENT_TITLE:
            img = new Image(this.getClass().getResourceAsStream(CLOCK_FILEPATH));
            break;
        case TASK_COMPLETION_TITLE:
            img = new Image(this.getClass().getResourceAsStream(TICK_FILEPATH));
            break;
        default:
            //Something went wrong
            throw new Exception();
        }
        cardLogoImageView.setImage(img);
    }

    private static String getDefaultData(String title, Config config) {

        String data;

        switch (title) {
        case WEEK_NUM_TITLE:
            data = getWeekNumber(config);
            break;
        case NEXT_EVENT_TITLE:
            data = DEFAULT_NEXT_EVENT;
            break;
        case TASK_COMPLETION_TITLE:
            data = DEFAULT_TASK_COMPLETION + "%";
            break;
        default:
            data = "";
            //Invalid title
        }
        return data;
    }

    public static String getWeekNumber(Config config) {
        LocalDate today = LocalDate.now();
        LocalDate startDate = config.getStartDate();
        int days = (int) DAYS.between(startDate, today);
        return String.valueOf((days / DAYS_PER_WEEK) + 1);
    }
}
