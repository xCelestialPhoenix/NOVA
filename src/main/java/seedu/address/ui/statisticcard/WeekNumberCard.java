package seedu.address.ui.statisticcard;

import static seedu.address.logic.constants.CalendarConstants.EXAM_WEEK_1;
import static seedu.address.logic.constants.CalendarConstants.EXAM_WEEK_2;
import static seedu.address.logic.constants.CalendarConstants.READING_WEEK;
import static seedu.address.logic.constants.CalendarConstants.RECESS_WEEK;

import java.time.LocalDate;

import javafx.scene.image.Image;

import seedu.address.logic.Logic;

public class WeekNumberCard extends StatisticCard {

    private static final String CARD_TITLE = "Week:";
    private static final String LOGO_FILEPATH = "/images/calendar.png";
    private static final int DATA_TEXT_FONT_SIZE = 26;

    public WeekNumberCard(Logic logic) {

        this(weekNumberToString(logic.calculateWeekNumber(LocalDate.now()) + 1));
    }

    public WeekNumberCard(String data) {

        super(CARD_TITLE, data, DATA_TEXT_FONT_SIZE);

        Image logo = new Image(this.getClass().getResourceAsStream(LOGO_FILEPATH));
        setLogo(logo);

        // Show the date today on the calendar logo
        cardDateLabel.setVisible(true);
        // Set the calendar date number to today
        cardDateLabel.setText(String.valueOf(LocalDate.now().getDayOfMonth()));
    }

    public void updateData(int newWeekNumber) {
        String week = weekNumberToString(newWeekNumber);
        super.updateData(week, DATA_TEXT_FONT_SIZE);
    }

    private static String weekNumberToString(int weekNumber) {

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
}
