package seedu.address.ui;

import static java.time.temporal.ChronoUnit.DAYS;
import static seedu.address.commons.contants.CalendarConstants.DAYS_PER_WEEK;
import static seedu.address.commons.contants.CalendarConstants.EXAM_WEEK_1;
import static seedu.address.commons.contants.CalendarConstants.EXAM_WEEK_2;
import static seedu.address.commons.contants.CalendarConstants.READING_WEEK;
import static seedu.address.commons.contants.CalendarConstants.RECESS_WEEK;

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

/**
 * The type Statistic card.
 */
public class StatisticCard extends UiPart<Node> {

    public static final String WEEK_NUM_TITLE = "Week:";
    public static final String NEXT_EVENT_TITLE = "Next event at:";
    public static final String TASK_COMPLETION_TITLE = "Task completed:";

    private static final String FXML = "StatisticsCard.fxml";
    private static final String CALENDAR_FILEPATH = "/images/calendar.png";
    private static final String CLOCK_FILEPATH = "/images/clock.png";
    private static final String TICK_FILEPATH = "/images/tick.png";

    // Default data to display
    private static final String DEFAULT_NEXT_EVENT = "";
    private static final String DEFAULT_TASK_COMPLETION = "0";

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

    /**
     * Instantiates a new Statistic card.
     *
     * @param title  the title
     * @param config the config
     */
    public StatisticCard(String title, Config config) {

        this(title, getDefaultData(title, config));
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

            // Set the date number to today
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
            }
        }

        // Set up the display for data
        Text text = new Text(data);
        text.setFill(Color.WHITE);
        if (title.equals(NEXT_EVENT_TITLE)) {
            text.setFont(Font.font("Segoe UI Bold", 20));
        } else {
            text.setFont(Font.font("Segoe UI Bold", 26));
        }

        try {
            setLogo(title);
        } catch (Exception e) {
            e.printStackTrace();
        }
        cardTitleLabel.setText(title);
        textFlow.getChildren().add(text);
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

    /**
     * Calculates the week number.
     *
     * @param config the config
     * @return the week number
     */
    public static String getWeekNumber(Config config) {

        LocalDate today = LocalDate.now();
        LocalDate startDate = config.getStartDate();
        int days = (int) DAYS.between(startDate, today);
        return String.valueOf((days / DAYS_PER_WEEK) + 1);
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

    /**
     * Updates data.
     *
     * @param data the data
     */
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

}
