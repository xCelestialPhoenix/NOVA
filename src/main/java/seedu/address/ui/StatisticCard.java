package seedu.address.ui;

import static java.time.temporal.ChronoUnit.DAYS;
import static seedu.address.logic.constants.CalendarConstants.DAYS_PER_WEEK;
import static seedu.address.logic.constants.CalendarConstants.EXAM_WEEK_1;
import static seedu.address.logic.constants.CalendarConstants.EXAM_WEEK_2;
import static seedu.address.logic.constants.CalendarConstants.READING_WEEK;
import static seedu.address.logic.constants.CalendarConstants.RECESS_WEEK;

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
import seedu.address.logic.Logic;

/**
 * The type Statistic card.
 */
public class StatisticCard extends UiPart<Node> {

    public static final String WEEK_NUM_TITLE = "Week:";
    public static final String NEXT_ACTIVITY_TITLE = "Next activity at:";
    public static final String TASK_COMPLETION_TITLE = "Task completed:";

    private static final String FXML = "StatisticsCard.fxml";
    private static final String CALENDAR_FILEPATH = "/images/calendar.png";
    private static final String CLOCK_FILEPATH = "/images/clock.png";
    private static final String TICK_FILEPATH = "/images/tick.png";

    // Default data to display
    private static final String DEFAULT_NEXT_ACTIVITY = "";
    private static final String DEFAULT_TASK_COMPLETION = "100";

    // Default font size
    private static final int DEFAULT_DATA_FONT_SIZE = 26;
    private static final int NEXT_ACTIVITY_DATA_FONT_SIZE = 20;

    @FXML
    private AnchorPane statisticCardRoot;

    @FXML
    private ImageView cardLogoImageView;

    @FXML
    private Label cardTitleLabel;

    @FXML
    private Label cardDateLabel;

    @FXML
    private TextFlow dataTextFlow;

    /**
     * Instantiates a new Statistic card.
     *
     * @param title  the title
     * @param logic the logic
     */
    public StatisticCard(String title, Logic logic) {

        this(title, getDefaultData(title, logic));
    }

    /**
     * Instantiates a new Statistic card.
     *
     * @param title the title
     * @param data  the data
     */
    public StatisticCard(String title, String data) {

        super(FXML);

        if (!title.equals(WEEK_NUM_TITLE)) {
            cardDateLabel.setVisible(false);
        } else { //Is week number statistic card

            // Set the calendar date number to today
            cardDateLabel.setText(String.valueOf(LocalDate.now().getDayOfMonth()));

            //Align week number with academic calendar
            int weekNumber = Integer.parseInt(data);
            data = getWeekString(weekNumber);
        }

        try {
            setLogo(title);
        } catch (Exception e) {
            e.printStackTrace();
        }

        cardTitleLabel.setText(title);
        writeToTextFlow(data);
    }

    private static String getDefaultData(String title, Logic logic) {

        String data;

        switch (title) {
        case WEEK_NUM_TITLE:
            int weekNumber = logic.calculateWeekNumber(LocalDate.now()) + 1; // calculateWeekNumber is zero-indexed
            data = String.valueOf(weekNumber);
            break;
        case NEXT_ACTIVITY_TITLE:
            data = DEFAULT_NEXT_ACTIVITY;
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

    private static boolean isRecessWeek(int weekNumber) {

        return weekNumber == RECESS_WEEK;
    }

    private static boolean isAfterRecessWeek(int weekNumber) {

        return weekNumber > RECESS_WEEK;
    }

    private static boolean isAfterSemester(int weekNumber) {

        return weekNumber > EXAM_WEEK_2;
    }

    private static boolean isExamWeek(int weekNumber) {

        return weekNumber == EXAM_WEEK_1 || weekNumber == EXAM_WEEK_2;
    }

    private static boolean isReadingWeek(int weekNumber) {

        return weekNumber == READING_WEEK;
    }

    private static boolean isBeforeSemester(int weekNumber) {

        return weekNumber < 1;
    }

    /**
     * Updates data.
     *
     * @param data the data
     */
    public void updateData(String data) {

        if(cardTitleLabel.getText().equals(WEEK_NUM_TITLE)) {
            data = getWeekString(Integer.parseInt(data));
        }
        writeToTextFlow(data);
    }

    private void setLogo(String title) throws Exception {

        Image img;
        switch (title) {
        case WEEK_NUM_TITLE:
            img = new Image(this.getClass().getResourceAsStream(CALENDAR_FILEPATH));
            break;
        case NEXT_ACTIVITY_TITLE:
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

    private void writeToTextFlow(String data) {
        Text text = new Text(data);
        text.setFill(Color.WHITE);
        if(cardTitleLabel.getText().equals(NEXT_ACTIVITY_TITLE)) {
            text.setFont(Font.font("Segoe UI Bold", NEXT_ACTIVITY_DATA_FONT_SIZE));
        } else {
            text.setFont(Font.font("Segoe UI Bold", DEFAULT_DATA_FONT_SIZE));
        }
        dataTextFlow.getChildren().clear();
        dataTextFlow.getChildren().add(text);
    }

    private static String getWeekString(int weekNumber) {

        String week = "";

        if (isRecessWeek(weekNumber)) {
            week = "Recess";
        } else if (isReadingWeek(weekNumber)) {
            week = "Reading";
        } else if (isExamWeek(weekNumber)) {
            week = "Exam";
        } else if (isAfterSemester(weekNumber)) {
            week = "Break";
        } else if (isAfterRecessWeek(weekNumber)) {
            week = String.valueOf(weekNumber - 1); // Actual week number is one more than academic week
        } else if (isBeforeSemester(weekNumber)) {
            week = "Pre-sem";
        } else {
            week = String.valueOf(weekNumber);
        }
        return week;
    }
}
