package seedu.address.ui.statisticcard;

import java.time.format.DateTimeFormatter;

import javafx.scene.image.Image;

import seedu.address.model.calendar.activity.Activity;

public class NextActivityCard extends StatisticCard {

    private static final String CARD_TITLE = "Next activity at:";
    private static final String LOGO_FILEPATH = "/images/clock.png";
    private static final String DEFAULT_NEXT_ACTIVITY = "";
    public static final int DATA_TEXT_FONT_SIZE = 20;

    public NextActivityCard() {
        this(DEFAULT_NEXT_ACTIVITY);
    }

    public NextActivityCard(String data) {

        super(CARD_TITLE, data, DATA_TEXT_FONT_SIZE);

        Image logo = new Image(this.getClass().getResourceAsStream(LOGO_FILEPATH));
        setLogo(logo);
    }

    public void updateData(Activity nextActivity) {

        String data = nextActivity.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + "\n"
                    + nextActivity.getStartTime().format(DateTimeFormatter.ofPattern("hh:mm a"));

        super.updateData(data, DATA_TEXT_FONT_SIZE);
    }
}
